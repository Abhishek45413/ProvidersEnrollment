package com.infinite.jsf.dao;

public interface ProvidersOtpDao {
	void generateAndInsertOtp(String email, String providerId);
    boolean verifyOtp(String providerId, String otp);
    void resendOtp(String email, String providerId);

}
