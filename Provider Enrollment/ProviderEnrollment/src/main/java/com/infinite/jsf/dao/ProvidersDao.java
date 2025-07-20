package com.infinite.jsf.dao;

import com.infinite.jsf.model.Providers;

public interface ProvidersDao {
	// Add a new provider (used during sign-up)
    boolean addProviders(Providers provider);

    // Get provider by email (for login and OTP purposes)
    Providers getProvidersByEmail(String email);
	

    // Get provider by ID (useful for OTP verification)
    Providers getProvidersById(String providerId);

    // Update the provider's password
    boolean updatePassword(String email, String newPassword);
    
    boolean emailExists(String email);
    boolean phoneExists(String phone);
    boolean zipcodeExists(String zipcode);


}
