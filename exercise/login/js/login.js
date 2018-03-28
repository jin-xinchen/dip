    var btn = document.getElementById('btn');  
    var re = document.getElementById('re');  
    var user = document.getElementById('user');  
    var password = document.getElementById('password');  
    btn.onclick = function(){  
        var isValidate=false;  
        if (!user.value.match(/^\S{2,20}$/)) {  
            user.className = 'userRed';  
            user.focus();  
            return;  
        } else {  
            user.className = 'text';  
            isValidate=true;  
        }  
      
        if (password.value.length<3 || password.value.length>20) {  
            password.className = 'userRed';  
            password.focus();  
            return;  
        } else {  
            password.className = 'text';  
            isValidate=true;  
        }  
        if (isValidate) {  
            var ajax = Ajax();  
            ajax.get('login.php?user='+document.getElementById('user').value+'&password='+document.getElementById('password').value, function(data){  
                var con = document.getElementById('con');  
                eval(data);  
                if (login) {  
                    con.innerHTML = '<font color="green">Login...</font>';  
                    location = 'xx.jsp';   
                } else {  
                    con.innerHTML = '<font color="red">Error！</font>';  
                }  
            });  
        }  
          
    }  
    re.onclick = function(){  
        user.value="";  
        password.value="";  
    }  