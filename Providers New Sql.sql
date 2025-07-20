CREATE TABLE Providers (
    provider_id VARCHAR(20) PRIMARY KEY,
    provider_name VARCHAR(100) NOT NULL,
    hospital_name VARCHAR(100) NOT NULL,
    telephone VARCHAR(20)NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255),
    address VARCHAR(225) NOT NULL,
    city VARCHAR(225) NOT NULL,
    state VARCHAR(225)NOT NULL,   
    zipcode VARCHAR(10)NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Providers_Otp (
    otp_id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    otp_code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    isVerified BOOLEAN DEFAULT 0,  
    FOREIGN KEY (provider_id) REFERENCES Provider(provider_id)
);

SELECT * FROM Providers;
SELECT * FROM Providers_Otp;
