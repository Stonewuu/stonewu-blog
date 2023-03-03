package com.stonewu.blog.admin.controller;

import com.stonewu.blog.admin.config.TokenManager;
import com.stonewu.blog.admin.config.auth.token.AdminTokenAuthenticationToken;
import com.stonewu.blog.core.entity.Blogsetting;
import com.stonewu.blog.core.entity.auth.AdminUserLoginToken;
import com.stonewu.blog.core.entity.enums.ApiResultType;
import com.stonewu.blog.core.entity.properties.BlogConfig;
import com.stonewu.blog.core.entity.result.ObjectResult;
import com.stonewu.blog.core.service.BlogsettingService;
import com.stonewu.blog.core.service.MenberService;
import com.stonewu.blog.core.utils.FileUtil;
import com.stonewu.blog.core.utils.HttpConnectionHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author StoneWu
 */
@Controller
@RequestMapping(value = "/admin")
@CrossOrigin()
public class MainController {


    @Resource
    private BlogConfig config;

    @Resource
    private BlogsettingService blogsettingService;

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Resource
    private MenberService menberService;

    @Resource
    private TokenManager tokenManager;

    /**
     * 登录方法
     *
     * @return
     */
    @RequestMapping(value = "/currentInfo", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResult currentInfo(HttpServletRequest request) {
        AdminTokenAuthenticationToken authentication = (AdminTokenAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authToken = authentication.getToken();
        AdminUserLoginToken token = tokenManager.getTokenForSeries(authToken);
        Map<String, Object> result = new HashMap<>();
        result.put("name", token.getUser().getName());
        return new ObjectResult(ApiResultType.SUCCESS, result);
    }


    /**
     * 获取工资条数据
     *
     * @param start
     * @param end
     * @param cookie
     * @return
     */
    @RequestMapping(value = "/salaryInfo", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResult salarySpider(int start, int end, String cookie) {
        String todayPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";// exp: 20180808/
        StringBuffer csv = new StringBuffer("序号,姓名,工资总额,年份,月份,部门,基本工资,岗位工资,职务工资,浮动绩效,固定绩效,项目奖金,补贴,加班工资,业务提成,事假,病假,其他扣减,应发工资,社保,公积金,所得税,实发工资,备注,个税专项附加扣除,公司缴纳社保,公司缴纳公积金\n");
        for (int i = start; i <= end; i++) {
            String url = "http://oa.interlib.cn:88/oa/salary/salary_show_mine.jsp?parentId=5883&id=" + i + "&formCode=gzd";
            String result = HttpConnectionHelper.sendRequest(url, "GET", cookie);

            String nameReg = "'xm', 'macro','(.*?)'\\);";//定义正则表达式
            String depReg = "'bm', 'macro','(.*?)'\\);";//定义正则表达式
            Pattern namePatten = Pattern.compile(nameReg);//编译正则表达式
            Matcher nameMatcher = namePatten.matcher(result);// 指定要匹配的字符串
            List<String> nameMatchStrs = new ArrayList<>();

            while (nameMatcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
                nameMatchStrs.add(nameMatcher.group(1));//获取当前匹配的值
            }
            String 姓名 = null;
            for (int j = 0; j < nameMatchStrs.size(); j++) {
                姓名 = nameMatchStrs.get(j);
            }

            Pattern depPatten = Pattern.compile(depReg);//编译正则表达式
            Matcher depMatcher = depPatten.matcher(result);// 指定要匹配的字符串
            List<String> depMatchStrs = new ArrayList<>();

            while (depMatcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
                depMatchStrs.add(depMatcher.group(1));//获取当前匹配的值
            }
            String 部门 = null;
            for (int j = 0; j < depMatchStrs.size(); j++) {
                部门 = depMatchStrs.get(j);
            }

            Document document = Jsoup.parse(result);
//            String 名字ID = document.selectFirst("#cws_span_xm").text();
//            if (StringUtils.isBlank(名字ID)) {
//                continue;
//            }
//            姓名 = document.selectFirst(".row0 .column2 [value='" + 名字ID + "']").text();

//            String 部门ID = document.selectFirst("#cws_span_bm").text();
//            String 部门 = document.selectFirst(".row3 .column2 [value='" + 部门ID + "']").text();
//            部门 = 部门.replaceAll("　├『", "");
//            部门 = 部门.replaceAll("』", "");

            String 年份 = document.selectFirst("#cws_span_nf").text();
            String 月份 = document.selectFirst("#cws_span_yf").text();

            String 工资总额 = document.selectFirst("#cws_span_gzze").text();
            String 基本工资 = document.selectFirst("#cws_span_jbgz").text();
            String 岗位工资 = document.selectFirst("#cws_span_gwgz").text();
            String 职务工资 = document.selectFirst("#cws_span_zwgz").text();

            String 浮动绩效 = document.selectFirst("#cws_span_fdgz").text();
            String 固定绩效 = document.selectFirst("#cws_span_khgz").text();
            String 项目奖金 = document.selectFirst("#cws_span_xmjj").text();
            String 补贴 = document.selectFirst("#cws_span_bt").text();
            String 加班工资 = document.selectFirst("#cws_span_jbgzi").text();
            String 业务提成 = document.selectFirst("#cws_span_ywtc").text();

            String 事假 = document.selectFirst("#cws_span_sj").text();
            String 病假 = document.selectFirst("#cws_span_bj").text();
            String 其他扣减 = document.selectFirst("#cws_span_qt").text();
            String 应发工资 = document.selectFirst("#cws_span_yfgz").text();
            String 社保 = document.selectFirst("#cws_span_sb").text();
            String 公积金 = document.selectFirst("#cws_span_zf").text();
            String 所得税 = document.selectFirst("#cws_span_sds").text();
            String 实发工资 = document.selectFirst("#cws_span_bysf").text();
            String 备注 = document.selectFirst("#cws_span_bz").text();
            String 个税专项附加扣除 = document.selectFirst("#cws_span_grsds").text();
            String 公司缴纳社保 = document.selectFirst("#cws_span_gsjnsb").text();
            String 公司缴纳公积金 = document.selectFirst("#cws_span_gsjngjj").text();
            String line = i + "," + 姓名 + "," + 工资总额 + "," + 年份 + "," + 月份 + "," + 部门 + "," + 基本工资 + "," + 岗位工资 + "," + 职务工资 + "," + 浮动绩效 + "," + 固定绩效 + "," + 项目奖金 + "," + 补贴 + "," + 加班工资 + "," +
                    业务提成 + "," + 事假 + "," + 病假 + "," + 其他扣减 + "," + 应发工资 + "," + 社保 + "," + 公积金 + "," + 所得税 + "," + 实发工资 + "," + 备注 + "," + 个税专项附加扣除 + "," + 公司缴纳社保 + "," + 公司缴纳公积金 + "\n";
            csv.append(line);
        }

        String path = config.getUploadPath();
        String fileName = start + "-" + end + "工资数据.csv";
        try {
            FileUtil.uploadCsvFile(csv.toString(), path + todayPath, fileName);
        } catch (Exception e) {
        }

        Blogsetting activeSetting = blogsettingService.getActiveSetting();
        String linkPath = config.getUploadLinkPath();// /upload/
        String finalPath = activeSetting.getUploadPath() + linkPath + todayPath + fileName;

        return new ObjectResult(ApiResultType.SUCCESS, "成功！", finalPath);
    }

}
