package com.stonewu.blog.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:general.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private String from;
    private String to;

    @Override
    public String toString() {
        return "MailProperties{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
