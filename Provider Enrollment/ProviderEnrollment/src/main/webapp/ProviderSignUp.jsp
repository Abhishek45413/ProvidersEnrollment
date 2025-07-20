<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<f:view>
  <head>
    <title>Provider Sign Up</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        padding: 30px;
      }

      .form-container {
        width: 100%;
        max-width: 500px;
        margin: auto;
        background-color: #fff;
        border-radius: 8px;
        padding: 30px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      .form-title {
        font-size: 24px;
        font-weight: bold;
        text-align: center;
        margin-bottom: 25px;
      }

      .form-group {
        margin-bottom: 15px;
      }

      label {
        display: block;
        font-weight: bold;
        margin-bottom: 5px;
      }

      .required-star {
        color: red;
      }

      .error-message {
        color: red;
        font-size: 0.9em;
      }

      h\:inputText,
      h\:inputTextarea {
        width: 100%;
      }

      #toast {
        display: none;
        position: fixed;
        top: 20px;
        center: 20px;
        background-color: #4CAF50;
        color: white;
        padding: 15px;
        border-radius: 5px;
        z-index: 9999;
        font-weight: bold;
      }

      #toast.error {
        background-color: #f44336;
      }

      .submit-button {
        text-align: center;
        margin-top: 20px;
      }
    </style>
  </head>

  <body>
    <h:form id="providerForm">
      <div class="form-container">
        <div class="form-title">Provider Sign-Up</div>

        <!-- Provider Name -->
        <div class="form-group">
          <label><span class="required-star">*</span>Provider Name:</label>
          <h:inputText id="providerName" value="#{providersController.provider.providerName}" required="true" requiredMessage="Provider Name is required">
            <f:validateRegex pattern="^[A-Za-z ]+$" />
          </h:inputText>
          <h:message for="providerName" styleClass="error-message" />
        </div>

        <!-- Hospital Name -->
        <div class="form-group">
          <label><span class="required-star">*</span>Hospital Name:</label>
          <h:inputText id="hospitalName" value="#{providersController.provider.hospitalName}" required="true" requiredMessage="Hospital Name is required">
            <f:validateRegex pattern="^[A-Za-z ]+$" />
          </h:inputText>
          <h:message for="hospitalName" styleClass="error-message" />
        </div>

        <!-- Telephone -->
        <div class="form-group">
          <label><span class="required-star">*</span>Telephone:</label>
          <h:inputText id="telephone" value="#{providersController.provider.telephone}" required="true" requiredMessage="Telephone is required">
            <f:validateRegex pattern="^\d{10}$" />
          </h:inputText>
          <h:message for="telephone" styleClass="error-message" />
        </div>

        <!-- Email -->
        <div class="form-group">
          <label><span class="required-star">*</span>Email:</label>
          <h:inputText id="email" value="#{providersController.provider.email}" required="true" requiredMessage="Email is required">
            <f:validateRegex pattern="^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$" />
          </h:inputText>
          <h:message for="email" styleClass="error-message" />
        </div>

        <!-- Address -->
        <div class="form-group">
          <label><span class="required-star">*</span>Address:</label>
          <h:inputTextarea id="address" value="#{providersController.provider.address}" required="true" requiredMessage="Address is required" rows="3" cols="40" />
          <h:message for="address" styleClass="error-message" />
        </div>

        <!-- State -->
        <div class="form-group">
          <label><span class="required-star">*</span>State:</label>
          <h:inputText id="state" value="#{providersController.provider.state}" required="true" requiredMessage="State is required" />
          <h:message for="state" styleClass="error-message" />
        </div>

        <!-- City -->
        <div class="form-group">
          <label><span class="required-star">*</span>City:</label>
          <h:inputText id="city" value="#{providersController.provider.city}" required="true" requiredMessage="City is required" />
          <h:message for="city" styleClass="error-message" />
        </div>

        <!-- Zipcode -->
        <div class="form-group">
          <label><span class="required-star">*</span>Zipcode:</label>
          <h:inputText id="zipcode" value="#{providersController.provider.zipcode}" required="true" requiredMessage="Zipcode is required">
            <f:validateRegex pattern="^\d{6}$" />
          </h:inputText>
          <h:message for="zipcode" styleClass="error-message" />
        </div>

        <!-- Submit -->
        <div class="submit-button">
          <h:commandButton value="Register" action="#{providersController.registerProvider}" />
        </div>

        <!-- Hidden Message -->
        <h:outputText id="hiddenMessage" value="#{providersController.message}" style="display:none;" />
      </div>
    </h:form>

    <!-- Toast -->
    <div id="toast"></div>

    <!-- Toast Script -->
    <script>
      window.onload = function () {
        var msgElem = document.getElementById("providerForm:hiddenMessage");
        var toast = document.getElementById("toast");

        if (msgElem && toast) {
          var message = msgElem.textContent || msgElem.innerText;
          if (message && message.trim() !== "") {
            toast.textContent = message;
            toast.style.display = "block";
            if (message.toLowerCase().includes("success")) {
              toast.classList.remove("error");
            } else {
              toast.classList.add("error");
            }
            setTimeout(() => toast.style.display = "none", 4000);
          }
        }
      };
    </script>
  </body>
</f:view>
</html>
