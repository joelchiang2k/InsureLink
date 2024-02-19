<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<sec:authorize access="!isAuthenticated()">
    
    <div class="container" style="text-align: right; margin-top: 10px;">
        <a href="login">Login</a>
    </div>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
	<td>|</td>
		<br> loggedInUser: ${loggedInUser}
		<br>Granted Authorities: <sec:authentication property="principal.authorities"/>
		
		<td></td>
		<td><a href="login?logout">Logout</a></td>
		<td><a href="#" id="checkPolicyLink">Check Policy</a></td>
		<td><a href="claim">Claim</a></td>

</sec:authorize>

<head>
    <meta charset="UTF-8">
    <title>Insurance Selection</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            
            function fetchCompanyNames() {
                $.ajax({
                    type: "GET",
                    url: "http://localhost:8484/getCompanyNames",
                    dataType: "json",
                    success: function(response) {
                        // Populate the select element with company names
                        var select = $("#insuranceCompany");
                        $.each(response, function(index, company) {
                            select.append($("<option></option>").attr("value", company).text(company));
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error("Error:", error);
                    }
                });
            }

            
            fetchCompanyNames();

           
            function fetchPlansByCompany(company, type) {
                $.ajax({
                    type: "GET",
                    url: "http://localhost:8484/getPlansByCompany",
                    data: { company: company, type: type },
                    dataType: "json",
                    success: function(response) {
                        var plansHtml = generatePlansHtml(response);
                        $("#plansContainer").html(plansHtml); 
                    },
                    error: function(xhr, status, error) {
                        console.error("Error:", error);
                    }
                });
            }
            
            
            $("#insuranceForm").submit(function(event) {
                event.preventDefault(); 
                var insuranceType = $("#insuranceType").val();
                var insuranceCompany = $("#insuranceCompany").val();
                console.log(insuranceType, insuranceCompany);
                fetchPlansByCompany(insuranceCompany, insuranceType);
            });

            
            function generatePlansHtml(plans) {
			    var html = '<table class="table table-striped">';
			    html += '<thead>';
			    html += '<tr>';
			    html += '<th>Plan Name</th>';
			    html += '<th>Description</th>';
			    html += '<th>Collision Deductible (per 6 months)</th>';
			    html += '<th>Uninsured Motorist Deductible (per 6 months)</th>';
			    html += '<th>Premium</th>';
			    html += '<th>Select Plan</th>'; // New column for the select button
			    html += '</tr>';
			    html += '</thead>';
			    html += '<tbody>';
			
			    for (var i = 0; i < plans.length; i++) {
			    	
			        var plan = plans[i];
			        console.log(plan.insurancePlanId)
			        html += '<tr>';
			        html += '<td>' + plan.planName + '</td>';
			        html += '<td>' + plan.description + '</td>';
			        html += '<td>' + '$' + plan.collisionDeductible + '</td>';
			        html += '<td>' + '$' + plan.uninsuredMotoristDeductible + '</td>';
			        html += '<td>' + '$' + plan.premium + '</td>';
			        html += '<td><button class="btn btn-primary" onclick="selectPlan(' + plan.insurancePlanId + ')">Select</button></td>'; 
			        html += '</tr>';
			    }
			
			    html += '</tbody>';
			    html += '</table>';
			
			    return html;
			}
        });
        
        function selectPlan(insurancePlanId) {
        	var email = $("#email").val();
        	var loggedInUser = "${loggedInUser}";
	        var url = "driverInfo?insurancePlanId=" + insurancePlanId + "&email=" + email + "&loggedInUser=" + loggedInUser;
   	 		window.location.href = url;
	    }
	    
	    $("#checkPolicyLink").click(function(event) {
	        // Prevent the default behavior of the link
	        event.preventDefault();
	
	     	var loggedInUser = "${loggedInUser}";
	
	        // Make an AJAX call to findUserId
	        $.ajax({
	            type: "POST",
	            url: "http://localhost:8282/findUserId",
	            data: loggedInUser,
	            success: function(response) {
	                
	                var userId = response; 
	                var url = "http://localhost:8282/policies?userId=" + userId;
	
	                // Navigate to the URL
	                window.location.href = url;
	            },
	            error: function(xhr, status, error) {
	                console.error("Error occurred while finding user ID:", error);
	            }
	        });
	    });
    </script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h2 class="text-center mt-4 mb-4">Select Insurance Type and Company</h2>
                <form id="insuranceForm">
                    <div class="form-group">
                        <label for="insuranceType">Insurance Type:</label>
                        <select class="form-control" name="insuranceType" id="insuranceType">
                            <option value="auto">Auto Insurance</option>
                            <option value="health">Health Insurance</option>
                            <!-- Add other insurance types as needed -->
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="insuranceCompany">Select Insurance Company:</label>
                        <select class="form-control" name="insuranceCompany" id="insuranceCompany">
                            <!-- Options will be dynamically populated -->
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                       
                    <sec:authorize access="hasAuthority('Admin') or hasAuthority('User')">
                    <button type="submit" class="btn btn-primary">Show Plans</button>
                    </sec:authorize>
                </form>
            </div>
        </div>
        <div class="row mt-4">
            <div class="col-md-6 offset-md-3">
                <!-- Container to display insurance plans -->
                <div id="plansContainer"></div>
            </div>
        </div>
        
    </div>
</body>
</html>
