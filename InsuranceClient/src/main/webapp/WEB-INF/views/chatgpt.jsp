<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help Section</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        header {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 1rem 0;
        }
        main {
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
            padding: 20px;
        }
        .message-container {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            width: 100%;
            margin-bottom: 20px;
        }
        .user-message {
            align-self: flex-end;
            background-color: #007bff;
            color: #fff;
            border-radius: 10px;
            padding: 10px 15px;
            margin-bottom: 5px;
        }
        .assistant-message {
            align-self: flex-start;
            background-color: #f0f0f0;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 10px 15px;
            margin-bottom: 5px;
        }
        .help-form {
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            margin-bottom: 20px;
        }
        .help-form textarea {
            width: calc(100% - 20px);
            height: 100px;
            margin-bottom: 10px;
            resize: none;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        .help-form button {
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 4px;
            font-size: 16px;
        }
        #responseArea {
            margin-top: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: calc(100% - 20px);
            overflow-y: auto;
            max-height: 200px;
        }
    </style>
</head>
<sec:authorize access="isAuthenticated()">
	<td>|</td>
		<br> loggedInUser: ${loggedInUser}
		<br>Granted Authorities: <sec:authentication property="principal.authorities"/>
		
		<td></td>
		<td><a href="login?logout">Logout</a></td>
		<sec:authorize access="hasAuthority('User')">
			 <td><a href="Home">Home</a></td>
			 <td><a href="claim">Claim</a></td> 
			 <td><a href="help">Help</a></td> 
        </sec:authorize>
        
        <sec:authorize access="hasAuthority('Admin')">
             <td><a href="reviewClaim">Review Claim</a></td>
			 <td><a href="reviewDoc">Review Document</a></td> 
        </sec:authorize>
		

</sec:authorize>
<body>
    <header>
        <h1>Help Section</h1>
    </header>
    <main>
        <div id="responseArea" class="message-container">
            
        </div>
        <div class="help-form">
            <h2>Need Help?</h2>
            <form id="helpForm">
                <textarea id="question" placeholder="Type your question or issue here"></textarea>
                <button type="submit">Submit</button>
            </form>
        </div>
    </main>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
           
            $("#helpForm").submit(function(event) {
                event.preventDefault();
                
               
                var question = $("#question").val();
                
               
                getChatGPTResponse(question);
                
                
                $("#question").val('');
            });
        });

        function getChatGPTResponse(question) {
            
          
            console.log(question);

            $.ajax({
                url: "http://localhost:8484/ask",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(question),
                success: function(response) {
                    console.log(response);
                    appendMessage(response, 'assistant');
                },
                error: function(xhr, status, error) {
                    console.error("Error:", error);
                    appendMessage("An error occurred while fetching the response.", 'assistant');
                }
            });
        }

        function appendMessage(message, role) {
            var messageContainer = $('<div class="message-container"></div>');
            var messageElement = $('<div class="' + role + '-message"></div>').text(message);
            messageContainer.append(messageElement);
            $("#responseArea").append(messageContainer);

            $("#responseArea").scrollTop($("#responseArea")[0].scrollHeight);
        }
    </script>
</body>
</html>
