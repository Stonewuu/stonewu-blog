package com.stonewu.blog.core.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

public class RequestParamUtils {

    /**
     * 将map中的内容存入实体（将会忽略key中“_”以及大小写）
     *
     * @param obj
     * @param params
     */
    public static <T> void reflectParameter(T obj, Map<String, String> params) {
        // 获取对象的Class
        Class<?> clazz = obj.getClass();
        // 获取Class中所有已声明的属性集合
        Field[] fileds = clazz.getDeclaredFields();
        // 遍历属性结合
        Set<String> keySet = params.keySet();
        for (Field f : fileds) {

            // 过滤被final、static修饰的成员变量
            if ((f.getModifiers() & Modifier.FINAL) > 0 || (f.getModifiers() & Modifier.STATIC) > 0) {
                continue;
            }
            // 取消所有私有变量的限制
            f.setAccessible(true);// 取消Field的访问检查
            // 获取属性的类型,long/int/string....
            Class<?> fieldType = f.getType();
            // 获取属性的名字
            String fieldName = f.getName();
            String paramName = null;
            for (String key : keySet) {
                String noLineKey = key.replace("_", "");
                if (fieldName.toLowerCase().equals(noLineKey.toLowerCase())) {
                    paramName = key;
                }
            }
            if (paramName == null) {
                // 未找到key
                continue;
            }
            // 根据属性的名字从requestMap中获取value
            String paramVal = params.get(paramName);
            // 判断类型,如果是String
            try {
                if (String.class == fieldType) {
                    f.set(obj, paramVal);
                    // 判断类型,如果是long,则使用NumberUtils.toLong,即使为空,也赋默认值0L
                } else if (long.class == fieldType || Long.class == fieldType) {
                    f.set(obj, NumberUtils.toLong(paramVal));
                    // 判断类型,如果是long,则使用NumberUtils.toLong,即使为空,也赋默认值0
                } else if (int.class == fieldType || Integer.class == fieldType) {
                    f.set(obj, NumberUtils.toInt(paramVal));
                    // 判断类型,如果是int,则使用NumberUtils.toInt,即使为空,也赋默认值0
                }  else if (boolean.class == fieldType || Boolean.class == fieldType) {
                    f.set(obj, BooleanUtils.toBoolean(paramVal));
                    // 判断类型,如果是boolean，如果为空默认值为false
                } else if (BigDecimal.class == fieldType) {
                    f.set(obj, BigDecimal.valueOf(NumberUtils.toDouble(paramVal, 0)));
                    // 判断类型,如果是date,则使用DateUtil.parseDateNewFormat格式化日期格式
                } else if (Date.class == fieldType) {
                    f.set(obj, DateUtils.parseDate(paramVal, "yyyy-MM-dd HH:mm:ss"));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static <T> void injectField(T obj, String paramName, String val) {
        // 获取对象的Class
        Class<?> clazz = obj.getClass();
        // 获取Class中所有已声明的属性集合
        Field[] fileds = clazz.getDeclaredFields();
        // 遍历属性结合
        for (Field f : fileds) {

            // 过滤被final、static修饰的成员变量
            if ((f.getModifiers() & Modifier.FINAL) > 0 || (f.getModifiers() & Modifier.STATIC) > 0) {
                continue;
            }
            // 取消所有私有变量的限制
            f.setAccessible(true);// 取消Field的访问检查
            // 获取属性的类型,long/int/string....
            Class<?> fieldType = f.getType();
            // 获取属性的名字
            String fieldName = f.getName();
            String noLineKey = paramName.replace("_", "");
            try {
                if (fieldName.toLowerCase().equals(noLineKey.toLowerCase())) {
                    if (String.class == fieldType) {
                        f.set(obj, val);
                        // 判断类型,如果是long,则使用NumberUtils.toLong,即使为空,也赋默认值0L
                    } else if (long.class == fieldType || Long.class == fieldType) {
                        f.set(obj, NumberUtils.toLong(val));
                        // 判断类型,如果是int,则使用NumberUtils.toInt,即使为空,也赋默认值0
                    } else if (int.class == fieldType || Integer.class == fieldType) {
                        f.set(obj, NumberUtils.toInt(val));
                        // 判断类型,如果是date,则使用DateUtil.parseDateNewFormat格式化日期格式
                    } else if (BigDecimal.class == fieldType) {
                        f.set(obj, BigDecimal.valueOf(NumberUtils.toDouble(val, 0)));
                        // 判断类型,如果是date,则使用DateUtil.parseDateNewFormat格式化日期格式
                    } else if (Date.class == fieldType) {
                        f.set(obj, DateUtils.parseDate(val, "yyyy-MM-dd HH:mm:ss"));
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static Map<String, String> requestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        return params;
    }
}
