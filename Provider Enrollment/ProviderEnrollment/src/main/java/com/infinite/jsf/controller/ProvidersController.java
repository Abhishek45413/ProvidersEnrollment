package com.infinite.jsf.controller;

import java.io.Serializable;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.jsf.Util.EncryptPassword;
import com.infinite.jsf.dao.ProvidersDao;
import com.infinite.jsf.dao.ProvidersDaoImpl;
import com.infinite.jsf.dao.ProvidersOtpDao;
import com.infinite.jsf.dao.ProvidersOtpDaoImpl;
import com.infinite.jsf.model.Providers;
import com.infinite.jsf.model.ProvidersStatus;

public class ProvidersController implements Serializable{

	private static final long serialVersionUID = 1L;
	private Providers provider = new Providers();
    private ProvidersDao providersDao = new ProvidersDaoImpl();
    private ProvidersOtpDao providersOtpDao = new ProvidersOtpDaoImpl();
    
    private String email;
    private String newPassword;
    private String confirmPassword;

    private String message;

    // ✅ Register New Provider
    public String registerProvider() throws Exception{
        boolean hasError = false;

        // ✅ Validate Provider Name
        if (!provider.getProviderName().matches("^[A-Za-z\\s]+$")) {
            FacesContext.getCurrentInstance().addMessage("providerName",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name must contain only alphabets.", null));
            hasError = true;
        }

        // ✅ Validate Hospital Name
        String hospital = provider.getHospitalName();
        if (hospital != null && !hospital.trim().isEmpty() && !hospital.matches("^[A-Za-z\\s]+$")) {
            FacesContext.getCurrentInstance().addMessage("hospitalName",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hospital name must contain only alphabets.", null));
            hasError = true;
        }

        // ✅ Validate Email Format
        if (!provider.getEmail().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$")) {
            FacesContext.getCurrentInstance().addMessage("email",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format.", null));
            hasError = true;
        }

        // ✅ Email Uniqueness Check
        if (providersDao.getProvidersByEmail(provider.getEmail()) != null) {
            FacesContext.getCurrentInstance().addMessage("email",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already exists.", null));
            hasError = true;
        }

        // ✅ Validate Phone Number
        if (!provider.getTelephone().matches("^[0-9]{10}$")) {
            FacesContext.getCurrentInstance().addMessage("telephone",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number must be exactly 10 digits.", null));
            hasError = true;
        }

        // ✅ Zipcode Format Validation
        if (!provider.getZipcode().matches("^[0-9]{6}$")) {
            FacesContext.getCurrentInstance().addMessage("zipcode",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zipcode must be exactly 6 digits.", null));
            hasError = true;
        }

        if (hasError) {
            return null; // stay on the same page
        }

        // ✅ Set Default Status and Encrypt Password
        provider.setStatus(ProvidersStatus.PENDING);
//        provider.setPassword(EncryptPassword.getCode(provider.getPassword()));

        boolean isadded = providersDao.addProviders(provider);
        if (isadded) {
        	providersOtpDao.generateAndInsertOtp(provider.getEmail(), provider.getProviderId());
        	this.message = "Provider added successfully";
            System.out.println("Provider added successfully.");
            
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("email", provider.getEmail());
            return "verifyOtp.jsp?faces-redirect=true";
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed. Please try again.", null));
            
            return null;
        }
    }
    
    public String updatePassword() {
    	    try {
    	        	if (email == null) {
    	            email = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("email");
    	        }

    	        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
    	            FacesContext.getCurrentInstance().addMessage(null,
    	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null));
    	            return null;
    	        }

    	        // You should have providerId or email stored already
    	        boolean updated = providersDao.updatePassword(email, newPassword);
    	        if (updated) {
    	            FacesContext.getCurrentInstance().addMessage(null,
    	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully!", null));
    	            return "login.xhtml?faces-redirect=true";
    	        } else {
    	            FacesContext.getCurrentInstance().addMessage(null,
    	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to update password.", null));
    	            return null;
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        FacesContext.getCurrentInstance().addMessage(null,
    	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred while updating password.", null));
    	        return null;
    	    }
    	}



	// ✅ Login Existing Provider
    public String login() throws Exception{
        System.out.println("Login method triggered");

        if (provider == null || provider.getEmail() == null || provider.getPassword() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email and password are required.", null));
            return null;
        }

        String encryptedPassword = EncryptPassword.getCode(provider.getPassword());

        Providers dbProvider = providersDao.getProvidersByEmail(provider.getEmail());

        if (dbProvider != null && dbProvider.getPassword().equals(encryptedPassword)) {
            if (dbProvider.getStatus() == ProvidersStatus.APPROVED) {
                System.out.println("Login successful.");
                provider = dbProvider;
                return "Home.jsp?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Your account is not approved yet.", null));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password.", null));
            return null;
        }
    }

    // ✅ Getters and Setters

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public ProvidersDao getProviderDao() {
		return providersDao;
	}

	public void setProviderDao(ProvidersDao providerDao) {
		this.providersDao = providerDao;
	}


	public ProvidersDao getProvidersDao() {
		return providersDao;
	}


	public void setProvidersDao(ProvidersDao providersDao) {
		this.providersDao = providersDao;
	}


	public ProvidersOtpDao getProvidersOtpDao() {
		return providersOtpDao;
	}


	public void setProvidersOtpDao(ProvidersOtpDao providersOtpDao) {
		this.providersOtpDao = providersOtpDao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
