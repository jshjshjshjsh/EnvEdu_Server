package com.example.demo.mail.service;

import com.example.demo.exceptions.CustomMailException;
import com.example.demo.user.dto.request.EmailDTO;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendAuthMail(EmailDTO emailDTO, String authNum) {
        try {
            String title = "[Seed] 회원가입 인증 번호";
            MimeMessage message = javaMailSender.createMimeMessage();
            String content =
                    "<div style=\"margin:100px; font-family: verdana\">\n" +
                            "    <h1> 안녕하세요 SEEd입니다.</h1><br>\n" +
                            "    <p> 아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p><br>\n" +
                            "    <div align=\"center\" style=\"border:1px solid #92B8B1;\">\n" +
                            "        <h3 style=\"color:#212121\"> 회원가입 인증 코드 입니다. </h3>\n" +
                            "        <div style=\"font-size:130%\">" + authNum + "</div>\n" +
                            "        <br>\n" +
                            "    </div>\n" +
                            "    <br/>\n" +
                            "</div>";
            message.addRecipients(MimeMessage.RecipientType.TO, emailDTO.getEmail());
            message.setSubject(title);
            message.setText(content, "utf-8", "html");
            javaMailSender.send(message);
        } catch (MessagingException | MailException e) {
            log.warn(e.getMessage());
            throw new CustomMailException("메일 전송에 실패했습니다");
        }
    }
}
