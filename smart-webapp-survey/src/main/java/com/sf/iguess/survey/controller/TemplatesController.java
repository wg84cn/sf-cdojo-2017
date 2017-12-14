package com.sf.iguess.survey.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplatesController
{
    // private static final Logger logger = LoggerFactory.getLogger(TemplatesController.class);
    
    @GetMapping("/templates")
    String templates(HttpServletRequest request)
    {
        // 逻辑处理
        request.setAttribute("key", "hello world");
        return "/index";
    }
    
    @RequestMapping("/view/{scoreId}.html")
    public ModelAndView viewPage(@PathVariable("scoreId") String scoreId)
    {
        ModelAndView modeler = new ModelAndView("/view");
        modeler.addObject("scoreId", scoreId);
        return modeler;
    }
    
    /*
     * @RequestMapping("{pageModule}") public String getModule(HttpServletRequest request, @PathVariable String
     * pageModule) { StringBuilder sbder = new StringBuilder(); if (pageModule != null && !pageModule.trim().isEmpty())
     * { sbder.append("/").append(pageModule); } else { sbder.append("/"); } return sbder.toString(); }
     */
}
