package com.stonewu.blog.admin.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.utils.RequestParamUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 增删改查模板，模板使用了路径/add，/update，/delete路径,
 * <p style="color:red;">
 * 使用时需要注意泛型参数T必须要有无参数的构造器，否则不要使用这个模板
 * </p>
 *
 * @param <T>
 */
public abstract class CURDController<T> extends AbstractController {
    private Logger log = LogManager.getLogger(CURDController.class);

    private IService<T> service;

    public CURDController(IService<T> service) {
        this.service = service;
    }

    /**
     * 在保存数据前会调用这个方法，使用这个方法返回的数据进行保存，可以在这里进行参数验证，或者默认数据设置，也可以什么也不做<br>
     *
     * @param bean 即将保存的数据
     * @return 将会保存的数据
     */
    protected abstract T preAdd(T bean);

    /**
     * 保存数据后调用
     *
     * @param bean
     * @return
     */
    protected abstract void afterAdd(T bean, Map<String, String> paramMap);

    /**
     * 在更新数据前会调用这个方法，使用这个方法返回的数据进行更新，可以在这里进行参数验证，或者默认数据设置，也可以什么也不做<br>
     *
     * @param bean 即将更新的数据
     * @return 将会更新的数据
     */
    protected abstract T preUpdate(T bean);

    /**
     * 将在更新后调用
     *
     * @param bean
     * @param paramMap
     * @return
     */
    protected abstract void afterUpdate(T bean, Map<String, String> paramMap);

    /**
     * 删除前会调用这个方法，这个方法需要将参数转换为int类型的id
     *
     * @param id 调用方传过来的id参数
     * @return 返回int类型的id
     */
    protected abstract int preDelete(Integer id);

    /**
     * 将在删除后调用
     *
     * @param id
     * @return
     */
    protected abstract void afterDelete(Integer id, Map<String, String> paramMap);

    private Class<T> clazz = null;

    @SuppressWarnings("unchecked")
    private T getBean() {
        synchronized (this) {
            if (clazz == null) {
                Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                try {
                    clazz = (Class<T>) Class.forName(type.getTypeName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("类型：" + type.getTypeName() + " 不存在", e);
                }
            }
        }
        try {
            T bean = (T) clazz.newInstance();
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e);
            throw new RuntimeException("实例化：" + clazz.getName() + " 失败！", e);
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public ObjectResult add(HttpServletRequest request) {
        Map<String, String> paramMap = RequestParamUtils.requestParams(request);
        T bean = getBean();
        RequestParamUtils.reflectParameter(bean, paramMap);
        try {
            boolean success = service.save(preAdd(bean));
            afterAdd(bean, paramMap);
            return new ObjectResult(ApiResultType.SUCCESS, success);
        } catch (Exception e) {
            log.error(e);
            return new ObjectResult(ApiResultType.UNKNOW_ERROR, null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ObjectResult update(HttpServletRequest request) {
        Map<String, String> paramMap = RequestParamUtils.requestParams(request);
        T bean = getBean();
        RequestParamUtils.reflectParameter(bean, paramMap);
        boolean success = service.updateById(preUpdate(bean));
        afterUpdate(bean, paramMap);
        if (success) {
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR);
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ObjectResult delete(HttpServletRequest request, @PathVariable(name = "id", required = true) Integer id) {
        Map<String, String> paramMap = RequestParamUtils.requestParams(request);
        boolean success = service.removeById(preDelete(id));
        if (success) {
            afterDelete(id, paramMap);
            return new ObjectResult(ApiResultType.SUCCESS);
        }
        return new ObjectResult(ApiResultType.UNKNOW_ERROR);
    }
}
