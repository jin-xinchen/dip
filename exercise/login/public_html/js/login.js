    var btnLogin = document.getElementById('btnLogin');  
    var btnReset = document.getElementById('btnReset');  
    var user = document.getElementById('user');  
    var password = document.getElementById('password'); 
    var errorMessage = document.getElementById('divErrorMessage');
    btnLogin.onclick = function(){  
        var isValidate=false;  
        if (!user.value.match(/^\S{3,20}$/)) {  
            user.className = 'userRed';//placeholder
            errorMessage.innerHTML="The username must be Alphanumeric, at least 3 characters in length";
            user.focus();  
            return;  
        } else {  
            user.className = 'text';  
            isValidate=true;
            errorMessage.innerHTML = '';
        }  
       var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9])(?=.{6,})");
       var str = password.value;
       var nospaceRegex = /\s{1,}/;
       //var res = strongRegex.test(str); 
       var res = strongRegex.test(str) && (!nospaceRegex.test(str));        
        if (!res) {//password.value.length<3 || password.value.length>20  
            errorMessage.innerHTML="The password must contain at least one lowercase, one uppercase, one number," +
            "one special character and at least six characters in length";
            password.className = 'userRed';  
            password.focus();  
            return;  
        } else {  
            password.className = 'text';  
            isValidate=true;
            errorMessage.innerHTML = '';
        }  
        if (isValidate) {  
            var ajax = Ajax();  
            ajax.get(
            'check?name='+document.getElementById('user').value+'&pass='+document.getElementById('password').value,
            function(data){  
               var errorMessage1 = document.getElementById('divErrorMessage');  
               var login=data;
               
                if (login==="ok") {  
                    errorMessage1.innerHTML = '<font color="green">Login...</font>';  
                    location = 'welcome';   
                } else {  
                    errorMessage1.innerHTML = '<font color="red">Try again, please!</font>';  
                }  
            });  
        }  
          
    }  
    btnReset.onclick = function(){  
        user.value="";  
        password.value="";  
    }  