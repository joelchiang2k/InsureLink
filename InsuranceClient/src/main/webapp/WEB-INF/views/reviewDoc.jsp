<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Document Review</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
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
        <h1 class="mt-4 mb-4">Admin Document Review</h1>
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
                <div id="pendingDocuments" class="mt-4">
                 
                </div>
            </div>
            <div class="tab-pane fade" id="approved" role="tabpanel" aria-labelledby="approved-tab">
                <div id="approvedDocuments" class="mt-4">
                    
                </div>
            </div>
            <div class="tab-pane fade" id="rejected" role="tabpanel" aria-labelledby="rejected-tab">
                <div id="rejectedDocuments" class="mt-4">
                   
                </div>
            </div>
        </div>
    </div>
	
	<div class="modal fade" id="driverDetailsModal" tabindex="-1" role="dialog" aria-labelledby="driverDetailsModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="driverDetailsModalLabel">Driver Details</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body" id="driverDetailsBody">
	        
	      </div>
	    </div>
	  </div>
	</div>
    <script>
	    function loadDocuments(status, container) {
		    $.ajax({
		        type: "GET",
		        url: "http://localhost:8484/get" + status.charAt(0).toUpperCase() + status.slice(1) + "Documents",
		        success: function(response) {
		            $(container).empty();
		
		            $.each(response, function(index, document) {
		            	console.log(document);
		                var documentHtml = '<div class="card mt-4" data-id="' + document.id + '">' +
		                    '<div class="card-body">' +
		                    '<h2 class="card-title">Document ID: ' + document.id + '</h2>' +
		                    '<p class="card-text"><strong>Document Name:</strong> ' + document.fileName + '</p>' +
		                    '<p class="card-text"><strong>Status:</strong> ' + document.status + '</p>';
		
		   
		                if (document.fileName.endsWith(".jpg") || document.fileName.endsWith(".png") || document.fileName.endsWith(".jpeg")) {
		                    documentHtml += '<img src="data:image/jpeg;base64,' + document.driverLicense + '" class="img-fluid" alt="Document">';
		                } else {
		                    documentHtml += '<p class="card-text"><strong>Document Data:</strong> ' + document.driverLicense + '</p>';
		                }
		
		              
		                if (status === "pending") {
						    documentHtml += '<button class="btn btn-success mr-2" onclick="approveDocument(' + document.id + ', \'' + document.driver.id + '\')">Approve</button>' +
						                    '<button class="btn btn-danger" onclick="rejectDocument(' + document.id + ', \'' + document.driver.id + '\')">Reject</button>' +
						                    '<br>' +
						                    '<button class="btn btn-info mt-2" onclick="showDriverDetails(\'' + document.driver.id + '\')">Show Driver Details</button>';
						}
		                
		               
		
		                documentHtml += '</div></div>';
		                $(container).append(documentHtml);
		            });
		        },
		        error: function(xhr, status, error) {
		            console.error("Error fetching " + status + " documents:", error);
		        }
		    });
		}
		
		
		function showDriverDetails(driverId) {
			console.log("driverId", driverId)
	       
	        $.ajax({
	            type: "POST",
	            url: "http://localhost:8484/getDriverDetails",
	            data: { driverId: driverId },
	            success: function(response) {
	            	console.log("response", response)
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
                    $("#driverDetailsModal").modal("show");
	                // Update modal body with driver details
	                //$('#driverDetailsBody').html(response);
	                // Show the modal
	                //$('#driverDetailsModal').modal('show');
	            },
	            error: function(xhr, status, error) {
	                console.error("Error fetching driver details:", error);
	            }
	        });
	    }
	
	  
	    function approveDocument(documentId, driverId) {
	        $.ajax({
	            type: "POST",
	            url: "http://localhost:8484/approveDocument",
	            data: { documentId: documentId, driverId: driverId},
	            success: function(response) {
	          
	                loadDocuments("approved", "#approvedDocuments");
	        
	                $("#pendingDocuments").find(".card[data-id='" + documentId + "']").remove();
	            },
	            error: function(xhr, status, error) {
	                console.error("Error approving document:", error);
	            }
	        });
	    }
	
	    function rejectDocument(documentId, driverId) {
	        $.ajax({
	            type: "POST",
	            url: "http://localhost:8484/rejectDocument",
	            data: { documentId: documentId, driverId: driverId},
	            success: function(response) {
	              
	                loadDocuments("rejected", "#rejectedDocuments");
	                
	                $("#pendingDocuments").find(".card[data-id='" + documentId + "']").remove();
	            },
	            error: function(xhr, status, error) {
	                console.error("Error rejecting document:", error);
	            }
	        });
	    }
	

	    loadDocuments("pending", "#pendingDocuments");
	    loadDocuments("approved", "#approvedDocuments");
	    loadDocuments("rejected", "#rejectedDocuments");
	</script>

</body>
</html>
