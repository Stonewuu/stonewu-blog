package com.stonewu.blog.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static boolean validateEmail(String str) {
        // 邮箱验证规则
        String regEx = "[a-zA-Z_]{0,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }
}
