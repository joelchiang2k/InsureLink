<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claim Intimation</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<td><a href="Home">Home</a></td>
<body>
    <div class="container">
        <h1 class="mt-4 mb-4">Claim Intimation</h1>
        <form id="claimForm">
            <div class="form-group">
                <label for="policyNumber">Policy Number:</label>
                <input type="number" class="form-control" id="policyNumber" name="policyNumber">
            </div>
            <div class="form-group">
                <label for="claimAmount">Claim Amount:</label>
                <input type="number" class="form-control" id="claimAmount" name="claimAmount">
            </div>
            <div class="form-group">
                <label for="reason">Reason:</label>
                <textarea class="form-control" id="reason" name="reason"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit Claim</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#claimForm").submit(function(event) {
                event.preventDefault();
                
                var formData = {
                    policyNumber: $("#policyNumber").val(),
                    amount: $("#claimAmount").val(),
                    reason: $("#reason").val()
                };
				console.log(formData)
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8484/saveClaim",
                    contentType: "application/json",
                    data: JSON.stringify(formData),
                    success: function(response) {
                        alert("Claim submitted successfully!");
                    },
                    error: function(xhr, status, error) {
                        alert("Error submitting claim: " + error);
                    }
                });
            });
        });
    </script>
</body>
</html>
