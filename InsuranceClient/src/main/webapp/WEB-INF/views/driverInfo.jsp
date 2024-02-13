<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Driver Information</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script>
	    function getQueryParam(param) {
	        const urlParams = new URLSearchParams(window.location.search);
	        return urlParams.get(param);
	    }
	    
	    var email = getQueryParam('email');
	    var insurancePlanId = getQueryParam('insurancePlanId');
	    console.log("insurance" + insurancePlanId)
	    console.log("email" + email);
        $(document).ready(function() {
            $("#submitBtn").click(function() {
                var name = $("#name").val();
                var age = $("#age").val();
                var drivingRecord = $("#drivingRecord").val();
                var vehicleValue = $("#vehicleValue").val();
                var street = $("#street").val();
                var city = $("#city").val();
                var state = $("#state").val();
                var zipCode = $("#zipCode").val();
                
				
               
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8484/submitDriverInfo",
                    data: {
                        name: name,
                        email: email,
                        age: age,
                        drivingRecord: drivingRecord,
                        vehicleValue: vehicleValue,
                        street: street,
                        city: city,
                        state: state,
                        zipCode: zipCode,
                        insurancePlanId: insurancePlanId
                    },
                    success: function(response) {
                        var driverId = response; // Assuming the driverId is returned in the response
        
				        
				        window.location.href = "http://localhost:8282/vehicleForm?driverId=" + driverId;
                      
                    },
                    error: function(xhr, status, error) {
                        
                        alert("Error occurred while submitting form: " + error);
                    }
                });
            });
        });
    </script>
    <style>
        .form-group {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Driver Information Form</h2>
        <form id="driverForm">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="age">Age:</label>
                <input type="number" class="form-control" id="age" name="age" required>
            </div>
            <div class="form-group">
                <label for="drivingRecord">Driving Record:</label>
                <input type="number" class="form-control" id="drivingRecord" name="drivingRecord" required>
            </div>
            <div class="form-group">
                <label for="vehicleValue">Vehicle Value:</label>
                <input type="number" class="form-control" id="vehicleValue" name="vehicleValue" required>
            </div>
            <h2>Address Information</h2>
            <div class="form-group">
                <label for="street">Street:</label>
                <input type="text" class="form-control" id="street" name="street" required>
            </div>
            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" class="form-control" id="city" name="city" required>
            </div>
            <div class="form-group">
                <label for="state">State:</label>
                <input type="text" class="form-control" id="state" name="state" required>
            </div>
            <div class="form-group">
                <label for="zipCode">Zip Code:</label>
                <input type="text" class="form-control" id="zipCode" name="zipCode" required>
            </div>
            <button type="button" class="btn btn-primary" id="submitBtn">Submit</button>
        </form>
    </div>
</body>
</html>
