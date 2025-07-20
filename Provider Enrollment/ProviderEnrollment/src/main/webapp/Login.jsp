<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<html>
<head>
    <title>Provider Login</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet" />

    <script>
        function validateLoginForm() {
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value.trim();
            const emailError = document.getElementById("emailError");
            const passwordError = document.getElementById("passwordError");

            let isValid = true;
            emailError.innerText = "";
            passwordError.innerText = "";

            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
            if (!emailRegex.test(email)) {
                emailError.innerText = "Please enter a valid email address.";
                isValid = false;
            }

            if (password.length < 6) {
                passwordError.innerText = "Password must be at least 6 characters.";
                isValid = false;
            }

            return isValid;
        }
    </script>
</head>

<body class="bg-gray-100 flex items-center justify-center min-h-screen">
<f:view>
    <h:form onsubmit="return validateLoginForm()" styleClass="bg-white p-8 rounded shadow-md w-full max-w-md">
        <h2 class="text-2xl font-bold mb-6 text-center text-gray-800">Provider Login</h2>

        <!-- ✅ Email Field -->
        <div class="mb-4">
            <label for="email" class="block text-gray-700 font-medium mb-2">Email</label>
            <h:inputText id="email" value="#{providersController.email}" required="true"
                         styleClass="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                         validatorMessage="Invalid email format." />
            <p id="emailError" class="text-red-500 text-sm mt-1"></p>
        </div>

        <!-- ✅ Password Field -->
        <div class="mb-6">
            <label for="password" class="block text-gray-700 font-medium mb-2">Password</label>
            <h:inputSecret id="password" value="#{providersController.provider.password}" required="true"
                           styleClass="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                           validatorMessage="Password must be at least 6 characters." />
            <p id="passwordError" class="text-red-500 text-sm mt-1"></p>
        </div>

        <!-- ✅ Login Button -->
        <div class="text-center">
            <h:commandButton value="Login"
                             action="#{providersController.login}"
                             styleClass="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700" />
        </div>

        <!-- ✅ Messages -->
        <h:messages globalOnly="true" styleClass="text-sm mt-4 text-center text-red-500" />

        <!-- 🔗 Forgot Password -->
        <div class="mt-4 text-center">
            <a href="forgotPassword.xhtml" class="text-sm text-blue-600 hover:underline">Forgot your password?</a>
        </div>

        <!-- 🔗 Sign Up -->
        <div class="mt-2 text-center">
            <span class="text-sm text-gray-600">Don't have an account?</span>
            <a href="ProviderSignUp.jsp" class="text-sm text-blue-600 hover:underline">SignUp here</a>
        </div>
    </h:form>
</f:view>
</body>
</html>
