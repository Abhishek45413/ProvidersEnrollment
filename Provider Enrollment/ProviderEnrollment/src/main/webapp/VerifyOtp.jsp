<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<html>
<head>
    <title>Verify OTP</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            padding: 40px;
        }
        .container {
            width: 400px;
            margin: auto;
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0px 0px 10px #cccccc;
        }
        .title {
            text-align: center;
            margin-bottom: 20px;
        }
        .btn {
            width: 100%;
            margin-top: 10px;
        }
        .resend {
            margin-top: 10px;
            text-align: right;
        }
        .messages {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<f:view>
     <h:form>
        <div class="otp-container">
            <h2 class="title">OTP Verification</h2>

            <h:panelGroup layout="block" styleClass="messages">
                <h:messages globalOnly="true" layout="table"
                            infoClass="info" errorClass="error" warnClass="warn" />
            </h:panelGroup>

            <!-- Email Field -->
            <h:outputLabel for="email" value="Email:" />
            <h:inputText id="email" value="#{providersOtpController.email}" required="true" />
            <h:message for="email" style="color: red;" /><br/><br/>

            <!-- OTP Input Field -->
            <h:outputLabel for="enteredOtp" value="Enter OTP:" />
            <h:inputText id="enteredOtp" value="#{providersOtpController.enteredOtp}" required="true" />
            <h:message for="enteredOtp" style="color: red;" /><br/><br/>

            <!-- Submit Button to Verify OTP -->
            <h:commandButton value="Verify OTP" action="#{providersOtpController.verifyOtp}" styleClass="btn" />

            <!-- Resend OTP Button -->
            <div class="resend">
                <h:outputText value="Didn't get OTP? " />
                <h:commandLink value="Resend OTP" action="#{providersOtpController.resendOtp}" />
            </div>
        </div>
    </h:form>
</f:view>
</body>
</html>
