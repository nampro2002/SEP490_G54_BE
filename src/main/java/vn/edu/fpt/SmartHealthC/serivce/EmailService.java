package vn.edu.fpt.SmartHealthC.serivce;

public interface EmailService {
    boolean sendMail(String to, String subject, String body);
    String generateRandomCode(int codeLength);
}
