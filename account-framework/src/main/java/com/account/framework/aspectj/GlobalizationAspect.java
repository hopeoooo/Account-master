package com.account.framework.aspectj;

import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.LanguageMagEnum;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 国际化处理
 *
 * @author hope
 */
@Aspect
@Component
public class GlobalizationAspect {


    @AfterReturning(pointcut = "@annotation(preAuthorize)", returning = "obj")
    public Object doAfterReturning(JoinPoint joinPoint, PreAuthorize preAuthorize, Object obj) {
        //获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> enumeration = request.getHeaderNames();
        String language = "zh_cn";
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            if("language".equals(name)){
                language = request.getHeader(name);
            }
        }
        if("en_us".equals(language)){
            if (obj instanceof AjaxResult) {
                AjaxResult ajaxResult = (AjaxResult) obj;
                String msg = (String) ajaxResult.get("msg");
                ajaxResult.put("msg", LanguageMagEnum.getEnByZh(msg));
            }
            if (obj instanceof TableDataInfo) {
                TableDataInfo tableDataInfo = (TableDataInfo) obj;
                String msg = tableDataInfo.getMsg();
                tableDataInfo.setMsg(LanguageMagEnum.getEnByZh(msg));
            }
        }
        return obj;
    }


}
