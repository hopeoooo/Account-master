package com.account.framework.aspectj;

import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.enums.LanguageMagEnum;
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
                break;
            }
        }
        if("en_us".equals(language)){
            if (obj instanceof AjaxResult) {
                AjaxResult ajaxResult = (AjaxResult) obj;
                String msg = (String) ajaxResult.get("msg");

                msg = msg.replaceAll("新增会员","Add member ");
                msg = msg.replaceAll("修改会员","Modify member ");
                msg = msg.replaceAll("失败，卡号已存在","Failed, card number already exists");

                msg = msg.replaceAll("新增角色","Add a new character ");
                msg = msg.replaceAll("修改角色","Modify character ");
                msg = msg.replaceAll("失败，角色名称已存在","Failed, character name already exists");

                msg = msg.replaceAll("卡号：","Card number ");
                msg = msg.replaceAll("不存在","Does not exist");

                msg = msg.replaceAll("ip地址错误 ip:","Wrong ip address ip:");

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
