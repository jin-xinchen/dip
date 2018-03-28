package beans;
import java.sql.*;

import javax.servlet.ServletException;
import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

public class SqlCheck {
   private boolean ok=false;//状态变量
   private String name=null;
   private String pass=null;

   public boolean check()throws Exception{
       checkSQL(this.name,this.pass);
       return this.ok;
   }
   public void setName(String name){
           this.name = name;
   }
   public String getName(){
           return name;
   }
   public void setPass(String pass){
           this.pass = pass;
   }
   public String getPass(){
           return pass;
   }

   //查询数据库，如果数据库没有这个用户就把状态变量设置为false，如果有则设置为true
  private void checkSQL(String name,String pass) throws Exception{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/user?user=root&password=数据库连接密码&characterEncoding=UTF-8";
        Connection con=DriverManager.getConnection(url);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from users where name='"+name+"' and pass='"+pass+"'");
        if(rs.next()){//表示有记录

//          
                   this.ok=true;

                }else{
                   this.ok=false;
                }
   }
}

java例子:
public class TestPwd {
    public static void main(String[] args)
    {
        //下面是密码中允许的特殊字符
        String specialchars = "!\"#$%&'()\\*\\+,-\\./:;<=>\\?@\\[\\]\\^_`{\\|}";
        String passwd[] = {"12", "123456", "a12345", "Aa1234", "A1234^", "Aa1@34", "_= As123", "!aA123", "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}Aa123"};
        
        System.out.println("不允许空格的正则表达式");
        //不允许空格的正则表达式
        String pattern1 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + specialchars + "])(?=\\S+$).{6,}";
        for(String pwd: passwd)
        {
            System.out.println(pwd + " meet requirement? "+ pwd.matches(pattern1));
        }
        
        System.out.println("----------------------------\n");
        System.out.println("允许空格的正则表达式");
        //允许空格的正则表达式
        String pattern2 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + specialchars + "])(?=).{6,}";
        for(String pwd: passwd)
        {
            System.out.println(pwd + " meet requirement? "+ pwd.matches(pattern2));
        }
    }
}

----------------------------------------------------------------------------------------------------------------------------------------------
javascript例子: javascript里的空格比JAVA里难处理，我就用了不太一样的方法
第一个按钮是允许有空格，第二个按钮是不许有空格
<html>
<head>

<script type="text/javascript">
function myfunction()
{    
    var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9])(?=.{6,})");
    
    var str = document.getElementById("pwd").value;
    var res = strongRegex.test(str);
    alert("password meet requirement? " + res);
    
}

function myfunction1()
{    
    var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\s])(?=.{6,})");
    var nospaceRegex = /\s{1,}/;
    var str = document.getElementById("pwd").value;
    var res = strongRegex.test(str) && (!nospaceRegex.test(str));
    alert("password meet requirement? " + res);
}
</script>
</head>
<body>
<input type="text" id="pwd" />
<br>
<button onclick="return myfunction();">space is ok</button>
<br>
<button onclick="return myfunction1();">space not allowed</button>
</body>
</html><html>
<head>

<script type="text/javascript">
function myfunction()
{    
    var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{6,})");
    var str = document.getElementById("pwd").value;
    var res = strongRegex.test(str);
    alert("password meet requirement? " + res);
    
}
</script>
</head>
<body>
<input type="text" id="pwd" />
<button onclick="return myfunction();">Click me</button>
</body>
</html>


---------------------------------------------------------------------------------------
网上给的一个可以根据密码强度变颜色的例子，我试了可以用，你可以参考
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
        <script>
            var myApp = angular.module("myapp", []);
            myApp.controller("PasswordController", function($scope) {

                var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
                var mediumRegex = new RegExp("^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})");

                $scope.passwordStrength = {
                    "float": "left",
                    "width": "100px",
                    "height": "25px",
                    "margin-left": "5px"
                };

                $scope.analyze = function(value) {
                    if(strongRegex.test(value)) {
                        $scope.passwordStrength["background-color"] = "green";
                    } else if(mediumRegex.test(value)) {
                        $scope.passwordStrength["background-color"] = "orange";
                    } else {
                        $scope.passwordStrength["background-color"] = "red";
                    }
                };

            });
        </script>
    </head>
    <body ng-app="myapp">
        <div ng-controller="PasswordController">
            <div style="float: left; width: 100px">
                <input type="text" ng-model="password" ng-change="analyze(password)" style="width: 100px; height: 25px" />
            </div>
            <div ng-style="passwordStrength"></div>
        </div>
    </body>
</html>