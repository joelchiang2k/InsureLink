<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Document Upload</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <h2>Upload Driver's License</h2>
    <form id="uploadForm" enctype="multipart/form-data">
        <div class="form-group">
            <label for="license">Select Driver's License:</label>
            <input type="file" class="form-control-file" id="license" name="license" accept=".pdf,.jpg,.png" required>
        </div>
        <button type="submit" class="btn btn-primary">Upload</button>
    </form>
    <div id="status"></div>
</div>

<script>
$(document).ready(function() {
    $('#uploadForm').submit(function(event) {
        event.preventDefault();
        var formData = new FormData($(this)[0]);

        $.ajax({
            url: 'http://localhost:8484/uploadDocument',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function(response) {
                $('#status').html('<div class="alert alert-success" role="alert">Document uploaded successfully!</div>');
            },
            error: function(xhr, status, error) {
                $('#status').html('<div class="alert alert-danger" role="alert">Error uploading document: ' + error + '</div>');
            }
        });
    });
});
</script>
</body>
</html>
