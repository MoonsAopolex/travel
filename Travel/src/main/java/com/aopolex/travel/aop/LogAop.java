package com.aopolex.travel.aop;

import com.aopolex.travel.bean.Log;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    private HttpServletRequest request;
    private Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Pointcut("execution(* com.aopolex.travel.controller.backstage.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void doBefore(){
        // 记录访问时间
        Date date = new Date();
        request.setAttribute("visitTime",date);
    }

    // 访问的路径
    // 访问者
    // 访问ip
    // 异常信息
    @After("pointCut()")
    public void doAfter(){
        Log log = new Log();
        // 访问时间
        Date visitTime = (Date) request.getAttribute("visitTime");
        //访问时长
        Date now = new Date();
        int executionTime = (int) (now.getTime() - visitTime.getTime());
        //访问ip
        String ip = request.getRemoteAddr();
        //访问路径
        String uri = request.getRequestURI();
        // 拿到security中的User对象
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User){
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        log.setIp(ip);
        log.setUrl(uri);
        log.setVisitTime(visitTime);
        log.setExecutionTime(executionTime);
        logger.info(log.toString());
    }

    @AfterThrowing(pointcut = "pointCut()",throwing = "ex")
    public void afterThrowing(Throwable ex){
        Log log = new Log();

        Date visitTime = (Date) request.getAttribute("visitTime"); // 访问时间
        Date now = new Date();
        int executionTime = (int) (now.getTime() - visitTime.getTime()); // 访问时长
        String ip = request.getRemoteAddr(); // 访问ip
        String url = request.getRequestURI(); // 访问路径
        // 拿到Security中的User对象
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User){
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        log.setExecutionTime(executionTime);
        log.setUrl(url);
        log.setIp(ip);
        log.setVisitTime(visitTime);

        // 异常信息
        String exMessage = ex.getMessage();
        log.setExceptionMessage(exMessage);

        logger.info(log.toString());
    }
}
