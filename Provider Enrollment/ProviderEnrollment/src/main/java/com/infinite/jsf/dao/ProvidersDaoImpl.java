package com.infinite.jsf.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.infinite.jsf.Util.SessionHelper;
import com.infinite.jsf.model.Providers;

public class ProvidersDaoImpl implements ProvidersDao{
	SessionFactory sf;
	Session session;

	@Override
	public boolean addProviders(Providers provider) {
	    Transaction transaction = null;
	    Session session = null;

	    try {
	        // Step 1: Open Session and begin transaction
	        sf = SessionHelper.getSessionFactory();
	        session = sf.openSession();
	        transaction = session.beginTransaction();

	        // Step 2: Generate unique provider ID
	        String newId = generateProviderId(session); // pass the open session
	        provider.setProviderId(newId);

	        // Step 3: Save provider
	        session.save(provider);

	        // Step 4: Commit
	        transaction.commit();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (transaction != null) transaction.rollback();
	        return false;
	    } finally {
	        if (session != null && session.isOpen())
	        	session.close();
	    }
	}
	
	
	private String generateProviderId(Session session) {
	    try {
	        String hql = "SELECT MAX(p.providerId) FROM Providers p";
	        Query query = session.createQuery(hql);
	        String latestId = (String) query.uniqueResult();

	        if (latestId == null) {
	            return "PROV001";
	        } else {
	            int num = Integer.parseInt(latestId.substring(4)); // Remove "PROV"
	            return "PROV" + String.format("%03d", num + 1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "PROV001"; // fallback
	    }
	}



	public Providers getProvidersByEmail(String email) {
		 sf = SessionHelper.getSessionFactory();
         session = sf.openSession();
		 Providers provider = null;

	        try  {
	            String hql = "FROM Providers WHERE email = :email";
	            Query query = session.createQuery(hql);
	            query.setParameter("email", email);
	            provider = (Providers) query.uniqueResult();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return provider;
	}

	@Override
	public Providers getProvidersById(String providerId) {
		 sf = SessionHelper.getSessionFactory();
		 session = sf.openSession();
		 Providers provider = null;

	        try{
	            provider = (Providers) session.get(Providers.class, providerId);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return provider;
	}

	@Override
	public boolean updatePassword(String email, String newPassword) {
		sf = SessionHelper.getSessionFactory();
		session = sf.openSession();
		Transaction transaction = null;
        boolean isUpdated = false;

        try{
            transaction = session.beginTransaction();

            String hql = "UPDATE Providers SET password = :newPassword WHERE email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("newPassword", newPassword);
            query.setParameter("email", email);

            int rowsAffected = query.executeUpdate();
            transaction.commit();

            isUpdated = (rowsAffected > 0);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return isUpdated;
    }

	@Override
	public boolean emailExists(String email) {
		Session session = null;
	    try {
	        session = SessionHelper.getSessionFactory().openSession();
	        Query query = session.createQuery("FROM Providers WHERE email = :email");
	        query.setParameter("email", email);
	        return query.uniqueResult() != null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}

	@Override
	public boolean phoneExists(String phone) {
		Session session = null;
	    try {
	        session = SessionHelper.getSessionFactory().openSession();
	        Query query = session.createQuery("FROM Providers WHERE telephone = :telephone");
	        query.setParameter("telephone", phone);
	        return query.uniqueResult() != null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}

	@Override
	public boolean zipcodeExists(String zipcode) {
		Session session = null;
	    try {
	        session = SessionHelper.getSessionFactory().openSession();
	        Query query = session.createQuery("FROM Providers WHERE zipcode = :zipcode");
	        query.setParameter("zipcode", zipcode);
	        return query.uniqueResult() != null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}

}
