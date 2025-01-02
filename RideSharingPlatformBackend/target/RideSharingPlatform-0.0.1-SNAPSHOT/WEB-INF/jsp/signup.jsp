<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Signup</title>
</head>
<body>
    <h2>Signup</h2>
    <s:form action="signupAction">
        <s:textfield name="firstName" label="First Name" />
        <s:textfield name="lastName" label="Last Name" />
        <s:radio name="countryCode" label="Country Code" list="{'+91'}" />
        <s:textfield name="username" label="Mobile Number" />
        <s:password name="password" label="Password" />
        <s:radio name="role" label="Role" list="{'passenger','rider'}" />
        <s:submit value="Signup" />
    </s:form>

    <p>Already have an account? <a href="showLogin.action">Login here</a></p>

    <!-- Display errors if any -->
    <s:actionerror />
</body>
</html>
