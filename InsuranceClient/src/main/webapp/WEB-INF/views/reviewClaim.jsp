<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Claim Review</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<sec:authorize access="hasAuthority('Admin')">
			 <td><a href="login?logout">Logout</a></td>
			 <td><a href="Home">Home</a></td>
             <td><a href="reviewClaim">Review Claim</a></td>
			 <td><a href="reviewDoc">Review Document</a></td> 
</sec:authorize>
<body>
    <div class="container">
        <h1 class="mt-4 mb-4">Admin Claim Review</h1>
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="pending-tab" data-toggle="tab" href="#pending" role="tab" aria-controls="pending" aria-selected="true">Pending</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="approved-tab" data-toggle="tab" href="#approved" role="tab" aria-controls="approved" aria-selected="false">Approved</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="rejected-tab" data-toggle="tab" href="#rejected" role="tab" aria-controls="rejected" aria-selected="false">Rejected</a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="pending" role="tabpanel" aria-labelledby="pending-tab">
                <div id="pendingClaims" class="mt-4">
                    <!-- Pending claims will be dynamically loaded here -->
                </div>
            </div>
            <div class="tab-pane fade" id="approved" role="tabpanel" aria-labelledby="approved-tab">
                <div id="approvedClaims" class="mt-4">
                    <!-- Approved claims will be dynamically loaded here -->
                </div>
            </div>
            <div class="tab-pane fade" id="rejected" role="tabpanel" aria-labelledby="rejected-tab">
                <div id="rejectedClaims" class="mt-4">
                    <!-- Rejected claims will be dynamically loaded here -->
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for driver details -->
    <div class="modal fade" id="driverModal" tabindex="-1" role="dialog" aria-labelledby="driverModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="driverModalLabel">Driver Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="driverDetailsBody">
                    <!-- Driver details will be dynamically loaded here -->
                </div>
            </div>
        </div>
    </div>

    <script>
        
        function reviewDriverDetails(claimId, policyNumber) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8484/findDriverByPolicyNumber",
                data: {
                    policyNumber: policyNumber
                },
                success: function(response) {
                    var driverDetailsHtml = '<p><strong>Name:</strong> ' + response.name + '</p>' +
                        '<p><strong>Email:</strong> ' + response.address.email + '</p>' +
                        '<p><strong>Address:</strong> ' + response.address.street + ', ' + response.address.city + ', ' + response.address.state + ', ' + response.address.zipCode + '</p>' +
                        '<p><strong>Claim Status:</strong> ' + response.claimStatus + '</p>' +
                        '<p><strong>Driving Record:</strong> ' + response.drivingRecord + '</p>' +
                        '<p><strong>Vehicle Value:</strong> ' + response.vehicleValue + '</p>' +
                        '<p><strong>Insurance Plan:</strong> ' + response.insurancePlan.planName + '</p>' +
                        '<p><strong>VIN:</strong> ' + response.vehicle.vin + '</p>' +
                        '<p><strong>Make:</strong> ' + response.vehicle.make + '</p>' +
                        '<p><strong>Model:</strong> ' + response.vehicle.model + '</p>' +
                        '<p><strong>Year:</strong> ' + response.vehicle.year + '</p>';
                    $("#driverDetailsBody").html(driverDetailsHtml);
                    $("#driverModal").modal("show");
                },
                error: function(xhr, status, error) {
                    console.error("Error finding driver:", error);
                    alert("Error finding driver: " + error);
                }
            });
        }
        
        
        function approveClaim(claimId) {
            $.ajax({
                type: "POST",
                url: "http://localhost:8484/approveClaim",
                data: { claimId: claimId },
                success: function(response) {
                    loadPendingClaims();
                    alert("Claim approved successfully!");
                },
                error: function(xhr, status, error) {
                    console.error("Error approving claim:", error);
                    alert("Error approving claim: " + error);
                }
            });
        }

        
        function rejectClaim(claimId) {
            $.ajax({
                type: "POST",
                url: "http://localhost:8484/rejectClaim",
                data: { claimId: claimId },
                success: function(response) {
                    loadPendingClaims();
                    alert("Claim rejected successfully!");
                },
                error: function(xhr, status, error) {
                    console.error("Error rejecting claim:", error);
                    alert("Error rejecting claim: " + error);
                }
            });
        }

        
        loadPendingClaims();

        
        function loadPendingClaims() {
		    $.ajax({
		        type: "GET",
		        url: "http://localhost:8484/getPendingClaims",
		        success: function(response) {
		            $("#pendingClaims").empty();
		
		            $.each(response, function(index, claim) {
		                var claimHtml = '<div class="card mt-4">' +
		                    '<div class="card-body">' +
		                    '<h2 class="card-title">Claim ID: ' + claim.id + '</h2>' +
		                    '<p class="card-text"><strong>Policy Number:</strong> ' + claim.policyNumber + '</p>' +
		                    '<p class="card-text"><strong>Claim Amount:</strong> ' + claim.amount + '</p>' +
		                    '<p class="card-text"><strong>Reason:</strong> ' + claim.reason + '</p>' +
		                    '<button class="btn btn-primary mr-2" onclick="approveClaim(' + claim.id + ')">Approve</button>' +
		                    '<button class="btn btn-danger" onclick="rejectClaim(' + claim.id + ')">Reject</button>' +
		                    '<button class="btn btn-info ml-2" onclick="reviewDriverDetails(' + claim.id + ', \'' + claim.policyNumber + '\')">Review Driver Details</button>' +
		                    '</div>';
		
		                // Check if mishap images are available
		                if (claim.mishapImage) {
						    claimHtml += '<div class="mishap-images">';
						    // Add mishap image to the claim card
						    claimHtml += '<img src="data:image/jpeg;base64,' + claim.mishapImage + '" class="img-fluid" alt="Mishap Image">';
						    claimHtml += '</div>';
						}

		
		                claimHtml += '</div></div>';
		                $("#pendingClaims").append(claimHtml);
		            });
		        },
		        error: function(xhr, status, error) {
		            console.error("Error fetching pending claims:", error);
		        }
		    });
		}

        
        function loadApprovedClaims() {
		    $.ajax({
		        type: "GET",
		        url: "http://localhost:8484/getApprovedClaims",
		        success: function(response) {
		            $("#approvedClaims").empty();
		
		            $.each(response, function(index, claim) {
		                var claimHtml = '<div class="card mt-4">' +
		                    '<div class="card-body">' +
		                    '<h2 class="card-title">Claim ID: ' + claim.id + '</h2>' +
		                    '<p class="card-text"><strong>Policy Number:</strong> ' + claim.policyNumber + '</p>' +
		                    '<p class="card-text"><strong>Claim Amount:</strong> ' + claim.amount + '</p>' +
		                    '<p class="card-text"><strong>Reason:</strong> ' + claim.reason + '</p>';
		
		                // Check if mishap images are available
		                if (claim.mishapImage) {
						    claimHtml += '<div class="mishap-images">';
						    // Add mishap image to the claim card
						    claimHtml += '<img src="data:image/jpeg;base64,' + claim.mishapImage + '" class="img-fluid" alt="Mishap Image">';
						    claimHtml += '</div>';
						}

		
		                claimHtml += '</div></div>';
		                $("#approvedClaims").append(claimHtml);
		            });
		        },
		        error: function(xhr, status, error) {
		            console.error("Error fetching approved claims:", error);
		        }
		    });
		}
		
		<!-- Update the loadRejectedClaims() function -->
		function loadRejectedClaims() {
		    $.ajax({
		        type: "GET",
		        url: "http://localhost:8484/getRejectedClaims",
		        success: function(response) {
		            $("#rejectedClaims").empty();
		
		            $.each(response, function(index, claim) {
		                var claimHtml = '<div class="card mt-4">' +
		                    '<div class="card-body">' +
		                    '<h2 class="card-title">Claim ID: ' + claim.id + '</h2>' +
		                    '<p class="card-text"><strong>Policy Number:</strong> ' + claim.policyNumber + '</p>' +
		                    '<p class="card-text"><strong>Claim Amount:</strong> ' + claim.amount + '</p>' +
		                    '<p class="card-text"><strong>Reason:</strong> ' + claim.reason + '</p>';
		
		                // Check if mishap images are available
		                if (claim.mishapImage) {
						    claimHtml += '<div class="mishap-images">';
						    // Add mishap image to the claim card
						    claimHtml += '<img src="data:image/jpeg;base64,' + claim.mishapImage + '" class="img-fluid" alt="Mishap Image">';
						    claimHtml += '</div>';
						}

		
		                claimHtml += '</div></div>';
		                $("#rejectedClaims").append(claimHtml);
		            });
		        },
		        error: function(xhr, status, error) {
		            console.error("Error fetching rejected claims:", error);
		        }
		    });
		}


        
        $('#approved-tab').on('shown.bs.tab', function (e) {
            loadApprovedClaims();
        });

        
        $('#rejected-tab').on('shown.bs.tab', function (e) {
            loadRejectedClaims();
        });
    </script>
</body>
</html>
