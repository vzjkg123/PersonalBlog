package com.tiancai.personalblog.Service.Impl;

import com.tiancai.personalblog.Service.ISendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements ISendMailService {
    @Autowired
    private JavaMailSender javaMailSender;


    private String from = "757876138@qq.com";

    private String subjectForRegister = "您正在注册www.ltcnb.top的账号";
    private String contextForRegister = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
            "    <style>\n" +
            "      #box {\n" +
            "        position: relative;\n" +
            "        top: 30px;\n" +
            "        width: 800px;\n" +
            "        height: 500px;\n" +
            "        margin: auto;\n" +
            "        background: #f7f8fa;\n" +
            "      }\n" +
            "      #brand {\n" +
            "        width: 700px;\n" +
            "        margin: auto;\n" +
            "        font-size: 20px;\n" +
            "        line-height: 30px;\n" +
            "        height: 40px;\n" +
            "      }\n" +
            "      #brand >a{\n" +
            "          text-decoration: none;\n" +
            "          color: black;\n" +
            "      }\n" +
            "      #wrapper {\n" +
            "        background: white;\n" +
            "        border-top: solid #00a4ff 2px;\n" +
            "        margin: auto;\n" +
            "        width: 700px;\n" +
            "        height: 400px;\n" +
            "        border-radius: 1px;\n" +
            "        box-shadow: gray 1px 1px 1px 1px;\n" +
            "      }\n" +
            "      #title {\n" +
            "        width: 100%;\n" +
            "        padding-top: 20px;\n" +
            "        text-align: center;\n" +
            "        margin-bottom: 30px;\n" +
            "      }\n" +
            "\n" +
            "      #content {\n" +
            "        width: 80%;\n" +
            "        margin-left: 10%;\n" +
            "        margin-top: 70px;\n" +
            "        text-align: center;\n" +
            "      }\n" +
            "      #tip {\n" +
            "        font-size: 12px;\n" +
            "        font-style: italic;\n" +
            "        bottom: 0;\n" +
            "      }\n" +
            "      #code {\n" +
            "        margin-top: 20px;\n" +
            "        margin-bottom: 40px;\n" +
            "        font-size: 28px;\n" +
            "        height: 50px;\n" +
            "        border: solid 1px;\n" +
            "      }\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <div id=\"box\">\n" +
            "      <div id=\"brand\"><a href=\"https://www.ltcnb.top\">Tiancai</a></div>\n" +
            "      <div id=\"wrapper\">\n" +
            "        <div id=\"title\"><h1>验证码</h1></div>\n" +
            "        <div id=\"content\">\n" +
            "          <div>您的验证码是：</div>\n" +
            "          <div id=\"code\">xxxxxxxx</div>\n" +
            "          <div id=\"tip\">\n" +
            "            温馨提示：<br />您正在注册账号,如果不是您的操作，请删除此邮件。<br />验证码10分钟内有效，请尽快完成操作\n" +
            "          </div>\n" +
            "        </div>\n" +
            "      </div>\n" +
            "    </div>\n" +
            "  </body>\n" +
            "</html>\n";
    private String contextForModify = contextForRegister.replace("注册账号", "找回密码");
    private String subjectForModify = subjectForRegister.replace("注册", "找回");

    //status   注册账号：1，  修改密码： 2
    @Override
    public void sendMail(String to, String verificationCode, String status) {
        String subject;
        String context;
        if (status.equals("1")) {
            subject = subjectForRegister;
            context = contextForRegister;
        } else {
            subject = subjectForModify;
            context = contextForModify;
        }
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from + "(Tiancai)");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(context.replace("xxxxxxxx", verificationCode), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
