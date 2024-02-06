<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Plans</title>
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
</head>
<body>
    <h2>Available Insurance Plans</h2>
    <div id="plansContainer"></div>
    <script>
        $(document).ready(function() {
            // Retrieve insurance type from query parameter
            var insuranceType = new URLSearchParams(window.location.search).get('insuranceType');

            // AJAX GET request to fetch insurance plans
            $.ajax({
                type: "GET",
                url: "http://localhost:8484/getPlans",
                data: { insuranceType: insuranceType },
                dataType: "json",
                success: function(plans) {
                    displayPlans(plans); // Call displayPlans function with plans data
                },
                error: function(xhr, status, error) {
                    console.error("Error:", error);
                }
            });

            // Function to display plans
            function displayPlans(plans) {
                var plansHtml = ""; // Variable to store HTML for plans
                for (var i = 0; i < plans.length; i++) {
                    var plan = plans[i];
                    plansHtml += "<div class='card mb-3'>";
                    plansHtml += "<div class='card-body'>";
                    plansHtml += "<h5 class='card-title'>" + plan.planName + "</h5>";
                    plansHtml += "<p class='card-text'>" + plan.description + "</p>";
                    plansHtml += "<p class='card-text'>Premium: $" + plan.premium + "</p>";
                    plansHtml += "</div></div>";
                }
                $("#plansContainer").html(plansHtml); // Update plans container with generated HTML
            }
        });
    </script>
</body>
</html>
