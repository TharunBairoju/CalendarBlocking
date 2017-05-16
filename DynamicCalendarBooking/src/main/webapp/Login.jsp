<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
 content="1099197321016-i6ecrntcjgcoo2bfp8lp6ubg7mer09qr.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script type="text/javascript" src="/js/jquery-1.4.3.min.js"></script>
<script src="http://code.jquery.com/jquery-1.10.2.js"
 type="text/javascript"></script>
</head>
<body>
 <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
 <script>
  function onSignIn(googleUser) {
   var profile = googleUser.getBasicProfile();
   var fName = profile.getName(), id = profile.getId(), lName = profile
     .getGivenName(), email = profile.getEmail();
  };

function clicking(){
    $.ajax({type: "POST",
            url: "http://10.9.9.207:8080/EnrollmentAndLogin/webapi/loginvalidation/userdetails",
            data: { id:id , name: fName,email:email },
            success:function(result){
      $("#sharelink").html(result);
    }});
});
</script>

 <a href="#" onclick="signOut();">Sign out</a>
 <script>
  function signOut() {
   var auth2 = gapi.auth2.getAuthInstance();
   auth2.signOut().then(function() {
    console.log('User signed out.');
   });
  }
 </script>
 <button id="Shareitem" onclick="clicking()">sharedata</button>
 <h1 id="sharelink"></h1>
</body>
</html>