package com.infinite.jsf.dao;

import java.time.LocalDateTime;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.infinite.jsf.Util.MailSender;
import com.infinite.jsf.Util.SessionHelper;
import com.infinite.jsf.model.ProvidersOtp;

public class ProvidersOtpDaoImpl implements ProvidersOtpDao{
	SessionFactory sf;
	Session session;
	
	 private String generateOtpCode() {
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
	        return String.valueOf(otp);
	    }

	@Override
	public void generateAndInsertOtp(String email, String providerId) {
		Session session = SessionHelper.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        String otp = generateOtpCode();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(5); // OTP valid for 5 minutes

        ProvidersOtp providerOtp = new ProvidersOtp();
        providerOtp.setProviderId(providerId);
        providerOtp.setOtpCode(otp);
        providerOtp.setCreatedAt(now);
        providerOtp.setExpiresAt(expiresAt);
        providerOtp.setVerified(false);

        session.save(providerOtp);
        tx.commit();
        session.close();

        // Send OTP to email
        MailSender.sendInfo(email, "Your OTP for HealthSure Registration", "Your OTP is: " + otp);
    }

	@Override
	public boolean verifyOtp(String providerId, String otp) {
		Session session = SessionHelper.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        String hql = "FROM ProvidersOtp WHERE providerId = :providerId ORDER BY createdAt DESC";
        ProvidersOtp latestOtp = (ProvidersOtp) session.createQuery(hql)
                .setParameter("providerId", providerId)
                .setMaxResults(1)
                .uniqueResult();

        if (latestOtp != null &&
            !latestOtp.isVerified() &&
            latestOtp.getOtpCode().equals(otp) &&
            latestOtp.getExpiresAt().isAfter(LocalDateTime.now())) {
            
            latestOtp.setVerified(true);
            session.update(latestOtp);
            tx.commit();
            session.close();
            return true;
        }

        tx.commit();
        session.close();
        return false;
    }

	@Override
	public void resendOtp(String email, String providerId) {
		Session session = null;
	    Transaction tx = null;

	    try {
	        session = SessionHelper.getSessionFactory().openSession();
	        tx = session.beginTransaction();

	        String newOtp = generateOtpCode();
	        LocalDateTime now = LocalDateTime.now();
	        LocalDateTime expiresAt = now.plusMinutes(5); // New expiry time

	        ProvidersOtp newOtpEntry = new ProvidersOtp();
	        newOtpEntry.setProviderId(providerId);
	        newOtpEntry.setOtpCode(newOtp);
	        newOtpEntry.setCreatedAt(now);
	        newOtpEntry.setExpiresAt(expiresAt);
	        newOtpEntry.setVerified(false);

	        session.save(newOtpEntry);
	        tx.commit();

	        // Send the OTP to email
	        MailSender.sendInfo(email, "Your  New OTP for HealthSure Registration", "Your new OTP is: " + newOtp);

	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
		
	}
}
