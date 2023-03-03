package com.stonewu.blog.core.entity.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "blog")
@PropertySource("classpath:config.properties")
@Component
public class BlogConfig {
    // 上传文件绝对路径
    private String uploadPath;
    // 服务器访问地址
    private String serverHost;
    // 上传文件访问中间路径
    private String uploadLinkPath;
    // 前端服务器的地址
    private String frontServer;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getUploadLinkPath() {
        return uploadLinkPath;
    }

    public void setUploadLinkPath(String uploadLinkPath) {
        this.uploadLinkPath = uploadLinkPath;
    }

    public String getFrontServer() {
        return frontServer;
    }

    public void setFrontServer(String frontServer) {
        this.frontServer = frontServer;
    }

}
