package com.sf.iguess.survey.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.constant.Constant;
import com.sf.iguess.survey.domain.Page;
import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.domain.UserScore;
import com.sf.iguess.survey.service.AnswerService;
import com.sf.iguess.survey.service.QuestionService;
import com.sf.iguess.survey.service.UserScoreService;
import com.sf.iguess.survey.service.UserService;
import com.smart.platform.constant.HttpConstant;

/**
 * @date 2017年12月4日
 * @time 下午7:34:38
 * @description
 */

@Controller
public class LoginController
{
    @Resource
    private UserService userService;
    
    @Resource
    private QuestionService questionService;
    
    @Resource
    private AnswerService answerService;
    
    @Resource
    private UserScoreService userScoreService;
    
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    
    @GetMapping("/login")
    public String toLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        Boolean isUseLogin = (Boolean)session.getAttribute(HttpConstant.ISLOGIN);
        if (isUseLogin != null && isUseLogin)
        {
            try
            {
                response.sendRedirect(request.getContextPath().equals("/") ? "" : request.getContextPath());
                return null;
            }
            catch (IOException e)
            {
                LOG.error("login error {}", e);
            }
        }
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(User user, Integer surveyType, HttpSession session)
    {
        try
        {
            String userName = user.getUserName();
            User userInfo = userService.findByUserName(userName);
            if (null == userInfo)
            {
                return new JsonResult(ResponseCode.NOT_LOGIN);
            }
            String userDecodePassword = 
                userService.getUserEncodePassword(user.getPassword(), userInfo.getUserSalt(), session);
            if (!userInfo.getPassword().equals(userDecodePassword))
            {
                return new JsonResult(ResponseCode.NOT_LOGIN);
            }
            userSessionInit(userInfo, session);
        }
        catch (Exception e)
        {
            LOG.error("login user error {}", e);
            return new JsonResult(ResponseCode.EXCEPTION);
        }
        return new JsonResult(ResponseCode.SUCCESS);
    }
    
    @RequestMapping(value = "/initLoginData", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult initLoginData(HttpSession session, Integer surveyType)
    {
        Boolean isUseLogin = (Boolean)session.getAttribute(HttpConstant.ISLOGIN);
        User userInfo = (User)session.getAttribute(Constant.USER_INFO);
        if (isUseLogin == null || !isUseLogin || userInfo == null)
        {
            return new JsonResult(ResponseCode.NOT_LOGIN, "", null);
        }
        // 管理员跳转的路径
        if (userInfo.isCurrentManage())
        {
            return new JsonResult(ResponseCode.SUCCESS, "1", getInitSuccessPage());
        }
        else
        {
            return new JsonResult(ResponseCode.SUCCESS, "0", questionService.findBySurveyType(surveyType));
        }
    }
    
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult loginUser(HttpSession session)
    {
        Boolean isUseLogin = (Boolean)session.getAttribute(HttpConstant.ISLOGIN);
        User user = (User)session.getAttribute(Constant.USER_INFO);
        if (isUseLogin != null && isUseLogin && user != null)
        {
            return new JsonResult(ResponseCode.SUCCESS, "", user);
        }
        else
        {
            return new JsonResult(ResponseCode.NOT_DATA, "", null);
        }
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult logout(HttpSession session)
    {
        session.invalidate();
        return new JsonResult(ResponseCode.SUCCESS, "", null);
    }
    
    private Page getInitSuccessPage()
    {
        Integer count = userScoreService.count();
        Page page = Page.newBuilder(1, 10, count);
        List<UserScore> userScoreList = userScoreService.findByPage(page);
        Map<String, Object> map = new HashMap<>();
        map.put("pageData", userScoreList);
        page.setCondition(map);
        return page;
    }
    
    private void userSessionInit(User user, HttpSession session)
    {
        session.setAttribute(HttpConstant.ISLOGIN, true);
        session.setAttribute(Constant.USER_ROLE_TYPE, user.getRoleType());
        user.setPassword(null);
        session.setAttribute(Constant.USER_INFO, user);
    }
}
