<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Form</title>
    <!-- Include jQuery -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    
    <script src="https://js.stripe.com/v3/"></script>
     <style>
       
        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            font-size: 0.9em;
            margin-bottom: 5px;
            display: block;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus,
        input[type="number"]:focus {
            border-color: #007bff;
            outline: none;
        }
        #card-element {
            margin-top: 10px;
        }
        #card-errors {
            color: red;
            margin-top: 10px;
        }
        button[type="submit"] {
            display: block;
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
   <div class="container">
	    <h2>Enter Payment Information</h2>
	    <form id="payment-form">
	        <div class="form-group">
	            <label for="cardholder-name">Cardholder Name:</label>
	            <input type="text" id="cardholder-name" name="cardholder-name" required>
	        </div>
	        <div class="form-group">
	            <label for="card-number">Card Number:</label>
	            <div id="card-element">
	                <!-- Stripe.js will inject the Card Element here -->
	            </div>
	            <div id="card-errors" role="alert"></div>
	        </div>
	        <div class="form-group">
	            <label for="insurance-plan">Insurance Plan:</label>
	            <span id="insurance-plan"></span>
	        </div>
	        <div class="form-group">
	            <label for="description">Description:</label>
	            <span id="description"></span>
	        </div>
	        <div class="form-group">
	            <label for="insuranceType">Insurance Type:</label>
	            <span id="insuranceType"></span>
	        </div>
	        <div class="form-group">
	            <label for="company">Company:</label>
	            <span id="company"></span>
	        </div>
	        <div class="form-group">
	            <label for="price">Price:</label>
	            <span id="price"></span>
	        </div>
	        <button type="submit">Submit Payment</button>
	    </form>
	</div>

    <script>
	    function getQueryParam(param) {
	            const urlParams = new URLSearchParams(window.location.search);
	            return urlParams.get(param);
	        }

        var driverId = getQueryParam('driverId');
        console.log("driver" + driverId);
        $(document).ready(function() {
         
            $.ajax({
			    url: 'http://localhost:8484/getDriverInfo?driverId=' + driverId,
			    type: 'GET',
			    dataType: 'json',
			    success: function(data) {
			        console.log(data.insurancePlan);
			        // Populate HTML form with retrieved data
			       $('#price').text("$" + data.insurancePlan.premium);
			       $('#company').text(data.insurancePlan.company);
			       $('#insuranceType').text(data.insurancePlan.insuranceType);
			        // Assuming insurancePlan is an object, not an array
			       $('#insurance-plan').text(data.insurancePlan.planName);
			       $('#description').text(data.insurancePlan.description);
			    },
			    error: function(xhr, status, error) {
			        console.error('Error fetching payment data:', error);
			    }
			});

           
            var stripe = Stripe('pk_test_51Oinm5JMmVc9VE0etJG00xkFa36lsu9BW7Vu315xKaVIiU4GbpyzM0w56jUPPhaAvwqUbFUtW1MGYSIfOr3iuj5w003QTs60K5');
                  
            var elements = stripe.elements();

           
            var card = elements.create('card');

      
            card.mount('#card-element');

         
            card.addEventListener('change', function(event) {
                var displayError = document.getElementById('card-errors');
                if (event.error) {
                    displayError.textContent = event.error.message;
                } else {
                    displayError.textContent = '';
                }
            });

            $('#payment-form').submit(function(event) {
			    event.preventDefault();
			    $('button[type="submit"]').prop('disabled', true);
			
			    stripe.createPaymentMethod({
			        type: 'card',
			        card: card,
			        billing_details: {
			            name: $('#cardholder-name').val()
			        }
			    }).then(function(result) {
			        if (result.error) {
			            $('#card-errors').text(result.error.message);
			            $('button[type="submit"]').prop('disabled', false);
			        } else {
			            var paymentMethodId = result.paymentMethod.id;
			            console.log("payment", paymentMethodId);
			            console.log("driver", driverId);
			            var amount = parseFloat($('#price').text().replace('$', ''));
			            console.log("amount", amount);
			            $.ajax({
			                url: 'http://localhost:8484/create-payment-intent', 
			                type: 'POST',
			                contentType: 'application/json',
			                data: JSON.stringify({
						        paymentMethodId: paymentMethodId,
						        driverId: driverId,
						        amount: amount
						    }),
			                success: function(response) {
			                    console.log("response", JSON.stringify(response.driver));
			                    
			                     $.ajax({
								        url: 'http://localhost:8484/createEmail',
								        type: 'POST',
								        contentType: 'application/json',
								        data: JSON.stringify(response.driver),
								        success: function(emailResponse) {
		
								            console.log("Email sent successfully:", emailResponse);
								        },
								        error: function(xhr, status, error) {
								            console.error('Error sending email:', error);
								        }
								    });
			                    //window.location.href = 'http://localhost:8282/success';
			                },
			                error: function(xhr, status, error) {
			                    
			                    console.error('Error processing payment:', error);
			                    $('button[type="submit"]').prop('disabled', false);
			                }
			            });
			        }
			    });
			});
        });
    </script>
</body>
</html>
