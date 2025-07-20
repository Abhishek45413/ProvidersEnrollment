<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<html>
<head>
    <title>Set New Password</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet" />
    <script>
        function checkStrength(password) {
            let strengthBar = document.getElementById("strengthBar");
            let strengthText = document.getElementById("strengthText");
            let errorMsg = document.getElementById("passwordError");
            let strength = 0;

            const regex = /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{8,}$/;

            if (password.length >= 8) strength++;
            if (/[0-9]/.test(password)) strength++;
            if (/[!@#$%^&*]/.test(password)) strength++;
            if (/[A-Z]/.test(password)) strength++;

            strengthBar.value = strength;
            const levels = ["Very Weak", "Weak", "Moderate", "Strong", "Very Strong"];
            const colors = ["#dc2626", "#f97316", "#facc15", "#4ade80", "#22c55e"];
            strengthText.innerText = "Strength: " + levels[strength];
            strengthText.style.color = colors[strength];

            if (!regex.test(password)) {
                errorMsg.innerText = "Password must be at least 8 characters and include a number, a special character, and an uppercase letter.";
            } else {
                errorMsg.innerText = "";
            }

            checkConfirmMatch();
        }

        function checkConfirmMatch() {
            const password = document.getElementById("newPassword").value;
            const confirm = document.getElementById("confirmPassword").value;
            const confirmError = document.getElementById("confirmError");

            if (confirm && password !== confirm) {
                confirmError.innerText = "Passwords do not match.";
            } else {
                confirmError.innerText = "";
            }
        }

        function toggleInstructions() {
            const box = document.getElementById("instructionBox");
            box.classList.toggle("hidden");
        }
    </script>
</head>

<body class="bg-gray-100 flex items-center justify-center min-h-screen">
<f:view>
    <h:form styleClass="bg-white p-8 rounded shadow-md w-full max-w-md">
        <h2 class="text-2xl font-bold mb-6 text-center text-gray-800">Set Your New Password</h2>

        <!-- ✅ Email Field (Read-only) -->
        <div class="mb-4">
            <label class="block text-gray-700 font-medium mb-2">Email</label>
            <h:inputText value="#{providersController.email}" readonly="true"
                         styleClass="w-full px-4 py-2 border rounded bg-gray-100 text-gray-600" />
        </div>

        <!-- New Password Field -->
        <div class="mb-4">
            <label for="newPassword" class="block text-gray-700 font-medium mb-2">New Password</label>
            <h:inputSecret id="newPassword" value="#{providersController.newPassword}" required="true"
                           styleClass="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                           onkeyup="checkStrength(this.value)" />
            <p id="passwordError" class="text-red-500 text-sm mt-1"></p>

            <div class="mt-2">
                <progress id="strengthBar" value="0" max="4" class="w-full h-2"></progress>
                <p id="strengthText" class="text-sm mt-1 font-medium">Strength:</p>
            </div>

            <!-- ✅ Dropdown Password Instructions -->
            <div class="mt-4">
                <button type="button"
                        onclick="toggleInstructions()"
                        class="text-blue-600 hover:underline text-sm">
                    📘 Password Instructions
                </button>

                <div id="instructionBox" class="mt-2 hidden text-sm text-gray-700 bg-blue-50 border border-blue-300 p-3 rounded">
                    <ul class="list-disc pl-5">
                        <li>Minimum 8 characters</li>
                        <li>At least 1 uppercase letter (A–Z)</li>
                        <li>At least 1 number (0–9)</li>
                        <li>At least 1 special character (!@#$%^&*)</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Confirm Password Field -->
        <div class="mb-6">
            <label for="confirmPassword" class="block text-gray-700 font-medium mb-2">Confirm Password</label>
            <h:inputSecret id="confirmPassword" value="#{providersController.confirmPassword}" required="true"
                           styleClass="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                           onkeyup="checkConfirmMatch()" />
            <p id="confirmError" class="text-red-500 text-sm mt-1"></p>
        </div>

        <!-- Submit -->
        <div class="text-center">
            <h:commandButton value="Set Password"
                             action="#{providersController.updatePassword}"
                             styleClass="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700" />
        </div>

        <!-- Global Messages -->
        <h:messages globalOnly="true" styleClass="text-sm mt-4 text-center text-red-500" />

        <!-- Back to Login -->
        <div class="text-center mt-6">
            <a href="login.jsp" class="text-blue-600 hover:underline text-sm">← Back to Login</a>
        </div>
    </h:form>
</f:view>
</body>
</html>
