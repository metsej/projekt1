function submitSignInData(){
    var userValue = $("#userInput").val();
    var passwordValue = $("#passInput").val();
    var userFieldName = "user";
    var passwordFieldName = "password";

    var successResp = "success";
    var userIsBlockedResp = "user is blocked";

    var emitJSON = {};
    emitJSON[userFieldName] = userValue;
    emitJSON[passwordFieldName] = passwordValue ;

    $.post('/login', emitJSON,
        function(returnedData){
            if (returnedData === successResp) {
                $(location).attr("href", "/login");
            } else if (returnedData === userIsBlockedResp ) {
                alert("Trzykrotnie podales zle haslo. Twoje konto jest zablokowane. W celu odblokowania skontaktuj sie z administratorem");
            } else {
                alert ("Zle haslo lub login");
            }
        });
}



