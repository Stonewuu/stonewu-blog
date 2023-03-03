package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Menber;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.MenberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stonewu
 * @since 2018-08-28
 */
@RestController
@RequestMapping("/admin/user")
@CrossOrigin
public class AdminUserController extends CURDController<Menber> {

    @Resource
    private MenberService menberService;

    public AdminUserController(MenberService service) {
        super(service);
    }

    @RequestMapping("/list")
    public ObjectResult list(Model model, Page<Menber> page, Menber param) {
        QueryWrapper<Menber> wrapper = new QueryWrapper<Menber>();
        if(StringUtils.isNotBlank(param.getNickName())){
            wrapper.like("nick_name",param.getNickName());
        }
        IPage<Menber> result = menberService.page(page, wrapper);
        return new ObjectResult(ApiResultType.SUCCESS, result);
    }

    @Override
    protected Menber preAdd(Menber bean) {
        return bean;
    }

    @Override
    protected void afterAdd(Menber bean, Map<String, String> paramMap) {

    }

    @Override
    protected Menber preUpdate(Menber bean) {
        return bean;
    }

    @Override
    protected void afterUpdate(Menber bean, Map<String, String> paramMap) {

    }

    @Override
    protected int preDelete(Integer id) {
        return id;
    }

    @Override
    protected void afterDelete(Integer id, Map<String, String> paramMap) {

    }
}
