package com.infinite.jsf.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.jsf.dao.ProvidersOtpDao;
import com.infinite.jsf.dao.ProvidersOtpDaoImpl;

public class ProvidersOtpController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 private String providerId;
	    private String email;
	    private String enteredOtp;
	    private String message;

	    ProvidersOtpDao otpDao = new ProvidersOtpDaoImpl();

	    // --- Method to verify the entered OTP ---
	    public String verifyOtp() {
	    	if (providerId == null || enteredOtp == null || providerId.isEmpty() || enteredOtp.isEmpty()) {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Please enter OTP and Provider ID", null));
	            return null;
	        }
	    	
	        boolean result = otpDao.verifyOtp(providerId, enteredOtp);

	        if (result) {
	            message = "OTP verified successfully!";
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP verified successfully!", null));
	            return "GeneratePassword.jsp?faces-redirect=true";
	        } else {
	            message = "Invalid or expired OTP!";
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid or expired OTP!", null));
	            return null; // Stay on the same page
	        }
	    }

	    // --- Method to resend a new OTP ---
	    public void resendOtp() {
	        if (email != null && providerId != null && !email.isEmpty() && !providerId.isEmpty()) {
	            otpDao.resendOtp(email, providerId);
	            message = "New OTP has been sent to your email!";
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "New OTP has been sent to your email!", null));
	        } else {
	            message = "Email or Provider ID is missing!";
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_WARN, "Email or Provider ID is missing!", null));
	        }
	        return;
	    }

	    // Optional: trigger OTP generation from SignUp if needed
	    public void generateAndSendOtp() {
	        otpDao.generateAndInsertOtp(email, providerId);
	    }

	    // --- Getters and Setters ---
	    public String getProviderId() {
	        return providerId;
	    }

	    public void setProviderId(String providerId) {
	        this.providerId = providerId;
	    }

	    public String getEnteredOtp() {
	        return enteredOtp;
	    }

	    public void setEnteredOtp(String enteredOtp) {
	        this.enteredOtp = enteredOtp;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

}
