package com.stonewu.blog.admin.controller;

import com.stonewu.blog.core.entity.Blogsetting;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.properties.BlogConfig;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.BlogsettingService;
import com.stonewu.blog.core.utils.FileUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author StoneWu
 */
@RestController
@RequestMapping(value = "/admin/upload")
@CrossOrigin()
public class AdminUploadController {

    @Resource
    private BlogConfig config;
    @Resource
    private BlogsettingService blogsettingService;

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public ObjectResult uploadImg(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String path = config.getUploadPath();// exp:/opt/porgram/upload
        String todayPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";// exp: 20180808/
        String name = file.getOriginalFilename();
        String fileType = name.split("\\.")[1];
        String fileName = UUID.randomUUID().toString() + "." + fileType;// uuid
        try {
            FileUtil.uploadFile(file.getBytes(), path + todayPath, fileName);
        } catch (Exception e) {
            return new ObjectResult(ApiResultType.UPLOAD_ERROR);
        }
        Blogsetting activeSetting = blogsettingService.getActiveSetting();
        String linkPath = config.getUploadLinkPath();// /upload/
        String finalPath = activeSetting.getUploadPath() + linkPath + todayPath + fileName;
        return new ObjectResult(ApiResultType.SUCCESS, "上传成功！", finalPath);
    }
}
