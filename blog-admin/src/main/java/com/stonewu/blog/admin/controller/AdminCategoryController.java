package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stonewu.blog.core.entity.Category;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.CategoryService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
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
@RequestMapping("/admin/category")
@CrossOrigin
public class AdminCategoryController extends CURDController<Category> {

    @Resource
    private CategoryService classService;

    @RequestMapping("/list")
    public ObjectResult list(Model model, Page<Category> page, Category param) {
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>();
        wrapper.setEntity(param);
        IPage<Category> result = classService.page(page, wrapper);
        return new ObjectResult(ApiResultType.SUCCESS, result);
    }

    @RequestMapping("/alllist")
    public ObjectResult alllist(Model model, Page<Category> page, Category param) {
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>();
        wrapper.setEntity(param);
        List<Category> list = classService.list(wrapper);
        return new ObjectResult(ApiResultType.SUCCESS, list);
    }

    @RequestMapping("/detail/{id}")
    public ObjectResult detail(Model model, Page<Category> page, @PathVariable(name = "id", required = true) Integer id) {
        Category category = classService.getById(id);
        return new ObjectResult(ApiResultType.SUCCESS, category);
    }

    @Override
    protected void afterAdd(Category bean, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(Category bean, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterDelete(Integer id, Map<String, String> paramMap) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Category preAdd(Category bean) {
        return bean;
    }

    @Override
    protected Category preUpdate(Category bean) {
        return bean;
    }

    @Override
    protected int preDelete(Integer id) {
        return id;
    }

    public AdminCategoryController(CategoryService service) {
        super(service);
        // TODO Auto-generated constructor stub
    }

}
