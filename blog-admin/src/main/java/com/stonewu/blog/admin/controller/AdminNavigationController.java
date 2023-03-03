package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Navigation;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.NavigationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/admin/navigation")
public class AdminNavigationController extends CURDController<Navigation> {

    @Resource
    private NavigationService navigationService;

    @RequestMapping("/list")
    public ObjectResult list(Model model, Page<Navigation> page, Navigation param) {
        QueryWrapper<Navigation> wrapper = new QueryWrapper<Navigation>();
        wrapper.setEntity(param);
        IPage<Navigation> result = navigationService.page(page, wrapper);
        return new ObjectResult(ApiResultType.SUCCESS, result);
    }

    @RequestMapping("/detail/{id}")
    public ObjectResult detail(Model model, Page<Navigation> page,
                               @PathVariable(name = "id", required = true) Integer id) {
        Navigation navigation = navigationService.getById(id);
        return new ObjectResult(ApiResultType.SUCCESS, navigation);
    }

    public AdminNavigationController(NavigationService service) {
        super(service);
    }

    @Override
    protected Navigation preAdd(Navigation bean) {

        return bean;
    }

    @Override
    protected void afterAdd(Navigation bean, Map<String, String> paramMap) {

    }

    @Override
    protected Navigation preUpdate(Navigation bean) {

        return bean;
    }

    @Override
    protected void afterUpdate(Navigation bean, Map<String, String> paramMap) {

    }

    @Override
    protected int preDelete(Integer id) {

        return id;
    }

    @Override
    protected void afterDelete(Integer id, Map<String, String> paramMap) {

    }

}
