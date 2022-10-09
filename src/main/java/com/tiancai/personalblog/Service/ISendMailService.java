package com.tiancai.personalblog.Service;

public interface ISendMailService {
    void sendMail(String to, String verificationCode, String status);
}
