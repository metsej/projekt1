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

function refresh(){
    $(".outer").height($(window).height() - $(".headerChartsPanel").outerHeight());
    var imgProportion = $(".background_pic").height() /  $(".background_pic").width();
    var windowsProportion = $(window).height() / $(window).width() ;
    console.log(imgProportion+" vs "+windowsProportion);
    if(imgProportion > windowsProportion){
        $(".background_pic").css("max-width","100%");
        $(".background_pic").css("max-height","none");
    }else{
        $(".background_pic").css("max-width","none");
        $(".background_pic").css("max-height","100%");
    }
}




