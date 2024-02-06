<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insurance Selection</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            // Function to fetch company names from the server
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

            // Call the function to fetch company names when the page loads
            fetchCompanyNames();

            // Function to fetch insurance plans based on selected company
            function fetchPlansByCompany(company, type) {
                $.ajax({
                    type: "GET",
                    url: "http://localhost:8484/getPlansByCompany",
                    data: { company: company, type: type },
                    dataType: "json",
                    success: function(response) {
                        var plansHtml = generatePlansHtml(response);
                        $("#plansContainer").html(plansHtml); // Update plans container with response
                    },
                    error: function(xhr, status, error) {
                        console.error("Error:", error);
                    }
                });
            }
            
            // Event handler for form submission
            $("#insuranceForm").submit(function(event) {
                event.preventDefault(); // Prevent default form submission
                var insuranceType = $("#insuranceType").val();
                var insuranceCompany = $("#insuranceCompany").val();
                console.log(insuranceType, insuranceCompany);
                fetchPlansByCompany(insuranceCompany, insuranceType);
            });

            // Function to generate HTML for insurance plans
            function generatePlansHtml(plans) {
                var html = '<table class="table table-striped">';
                html += '<thead>';
                html += '<tr>';
                html += '<th>Plan Name</th>';
                html += '<th>Description</th>';
                html += '<th>Premium</th>';
                html += '</tr>';
                html += '</thead>';
                html += '<tbody>';

                for (var i = 0; i < plans.length; i++) {
                    var plan = plans[i];
                    html += '<tr>';
                    html += '<td>' + plan.planName + '</td>';
                    html += '<td>' + plan.description + '</td>';
                    html += '<td>' + plan.premium + '</td>';
                    html += '</tr>';
                }

                html += '</tbody>';
                html += '</table>';

                return html;
            }
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
                        <!-- Add more fields as needed -->
                    <button type="submit" class="btn btn-primary">Show Plans</button>
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
