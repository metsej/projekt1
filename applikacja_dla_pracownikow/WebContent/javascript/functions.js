
function submitSignInData(){

	var userValue = $("#userInput").val();
	var passwordValue = $("#passInput").val();
	var userFieldName = "<%= WebAppController.USER_INPUT_NAME%>";
	var passwordFieldName = "<%= WebAppController.PASSWORD_INPUT_NAME %>";

	var successResp = "<%= WebAppController.SUCCESSFUL_LOGGING_RESP %>";
	var userIsBlockedResp = "<%= WebAppController.USER_IS_BLOCKED_RESP %>";

	var emitJSON = {};
	emitJSON[userFieldName] = userValue;
	emitJSON[passwordFieldName] = passwordValue ;

	$.post('/login', emitJSON,
		    function(returnedData){
		         if (returnedData === successResp) {
		        	 $(location).attr("href", "/Welcome");
		         } else if (returnedData === userIsBlockedResp ) {
		        	 alert("Trzykrotnie podales zle haslo. Twoje konto jest zablokowane. W celu odblokowania skontaktuj sie z administratorem");
		         } else {
		        	 alert ("Zle haslo lub login");
		         }
		});
}

function getDataFromServer(type) {
    var emitJSON = {};
    emitJSON["time"] = Math.round((Date.now() - 86400000)/1000);
    emitJSON["type"] = type ;
    var data = {};


    $.post("/Report",emitJSON, function(resultJSON) {
         $.each(resultJSON, function(key, value) {
         	data[key] = value;
		 });
	});

	 return data;
}
