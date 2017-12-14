package com.sf.iguess.survey.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
/*import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;*/
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port:0")// 使用0表示端口号随机，也可以具体指定如8888这样的固定端口
*/public class UserSurveyController {
/*
    private String dateReg;
    private Pattern pattern;
    private RestTemplate template = new TestRestTemplate();
    @Value("${local.server.port}")// 注入端口号
    
    private int port;

    @Test
    public void test3(){
        String url = "http://localhost:"+port+"/myspringboot/hello/info";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>(); 
        map.add("name", "Tom");  
        map.add("name1", "Lily");
        String result = template.postForObject(url, map, String.class);
        System.out.println(result);
        assertNotNull(result);
        assertThat(result, Matchers.containsString("Tom"));
    }*/

}