<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vehicle Information Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Enter Vehicle Information</h2>
        <form id="vehicleForm" method="post" action="processVehicle.jsp">
            <div class="form-group">
                <label for="vin">VIN:</label>
                <input type="text" class="form-control" id="vin" name="vin" required>
            </div>
            
            <div class="form-group">
                <label for="make">Make:</label>
                <input type="text" class="form-control" id="make" name="make" required>
            </div>
            
            <div class="form-group">
                <label for="model">Model:</label>
                <input type="text" class="form-control" id="model" name="model" required>
            </div>
            
            <div class="form-group">
                <label for="year">Year:</label>
                <input type="text" class="form-control" id="year" name="year" required>
            </div>
            
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>

    <script>
    	function getQueryParam(param) {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get(param);
        }

        var driverId = getQueryParam('driverId');
    	
       $(document).ready(function(){
		    $("#vehicleForm").submit(function(e){
		        e.preventDefault(); // Prevent form submission
		
		        var vin = $("#vin").val();
		        var make = $("#make").val();
		        var model = $("#model").val();
		        var year = $("#year").val();
		
		        // Create JSON object with form data
		        var formData = {
		            "vin": vin,
		            "make": make,
		            "model": model,
		            "year": year,
		            "driverId": driverId
		        };
				console.log(formData);
		        // AJAX POST request to submit form data
		        $.ajax({
		            type: "POST",
		            url: "http://localhost:8484/submitVehicleForm",
		            
		            contentType: "application/json", // Set content type to JSON
		            data: JSON.stringify(formData), // Stringify JSON object
		            success: function(response){
		                // Handle success response
		                console.log(response);
		                // Optionally redirect to another page
		                window.location.href = "http://localhost:8282/payment?driverId=" + driverId;
		            },
		            error: function(xhr, status, error){
		                // Handle error
		                console.error(xhr.responseText);
		            }
		        });
		    });
		});

    </script>
</body>
</html>
