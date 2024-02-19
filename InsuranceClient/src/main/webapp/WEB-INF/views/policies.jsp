<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Policies</title>
    <td><a href="Home">Home</a></td>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h1 class="mt-4 mb-4">View Policies</h1>
        <div id="policies-container">
            <!-- Policies will be dynamically loaded here -->
        </div>
    </div>

    <script>
        function getQueryParam(param) {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get(param);
        }

        var userIdString = getQueryParam('userId');

        $(document).ready(function() {
            // Function to fetch and display policies
            function fetchPolicies() {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8484/getDriverInfoForPolicy",
                    data: {
                        userIdString: userIdString
                    },
                    success: function(response) {
                        // Clear previous content
                        $("#policies-container").empty();

                        // Iterate through policies and append them to the container
                        $.each(response, function(index, obj) {
                        	console.log(obj.id)
                            // Construct the policy HTML
                            var policyHtml = '<div class="card mt-4">' +
                                '<div class="card-body">' +
                                '<h2 class="card-title">' + obj.name + '</h2>' +
                                '<p class="card-text"><strong>Policy Number:</strong> ' + obj.id + '</p>' +
                                '<p class="card-text"><strong>Plan Name:</strong> ' + obj.insurancePlan.planName + '</p>' +
                                '<p class="card-text"><strong>Company:</strong> ' + obj.insurancePlan.company + '</p>' +
                                '<p class="card-text"><strong>Description:</strong> ' + obj.insurancePlan.description + '</p>' +
                                '<p class="card-text"><strong>Insurance Type:</strong> ' + obj.insurancePlan.insuranceType + '</p>' +
                                '<p class="card-text"><strong>Uninsured Motorist Deductible:</strong> ' + obj.insurancePlan.uninsuredMotoristDeductible + '</p>' +
                                '<p class="card-text"><strong>Collision Deductible:</strong> ' + obj.insurancePlan.collisionDeductible + '</p>' +
                                '<p class="card-text"><strong>Status:</strong> ' + obj.status + '</p>' +
                                '<p class="card-text"><strong>VIN:</strong> ' + obj.vehicle.vin + '</p>' +
                                '<p class="card-text"><strong>Make:</strong> ' + obj.vehicle.make + '</p>' +
                                '<p class="card-text"><strong>Model:</strong> ' + obj.vehicle.model + '</p>' +
                                '<p class="card-text"><strong>Year:</strong> ' + obj.vehicle.year + '</p>' +
                                '</div>' +
                                '</div>';

                            // Append the policy HTML to the container
                            $("#policies-container").append(policyHtml);
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error("Error fetching policies:", error);
                    }
                });
            }

            // Initial fetch of policies
            fetchPolicies();
        });
    </script>
</body>
</html>
