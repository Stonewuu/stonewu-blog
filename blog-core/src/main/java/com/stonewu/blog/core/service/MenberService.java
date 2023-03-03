package com.stonewu.blog.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.entity.Menber;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
public interface MenberService extends IService<Menber> {

    /**
     * 根据昵称和邮箱查询用户
     * @param nickName
     * @param email
     * @return
     */
    Menber getMenberByNameEmail(String nickName, String email);
}
