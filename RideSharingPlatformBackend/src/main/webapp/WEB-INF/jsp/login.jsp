<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <s:form action="loginAction">
        <s:textfield name="username" label="Mobile Number" />
        <s:password name="password" label="Password" />
        <s:submit value="Login" />
    </s:form>

    <p>Don't have an account? <a href="showSignup.action">Signup here</a></p>
    
    <!-- Display errors if any -->
    <s:actionerror />
</body>
</html>
