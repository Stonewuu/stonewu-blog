package com.stonewu.blog.core.entity.custom;

public class Mail {
    public String to;
    public String content;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "to='" + to + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
