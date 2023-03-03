package com.stonewu.blog.core.service;

import com.stonewu.blog.core.entity.Reply;

public interface MailService {
    void sendMail(Reply reply);

    void sendMailToAdmin(Reply reply);
}
