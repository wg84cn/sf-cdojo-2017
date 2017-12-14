/**
 * Project Name:basicplatform
 * File Name:HttpClientHelper.java
 * Package Name:com.smart.platform.toolkit
 * Date:2016年8月5日下午3:37:37
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient4.3工具类
 * 
 * @author tech
 * @date 2016-03-29
 *
 */
public class HttpClientHelper
{
    private static final String REQUEST_ENCODER = "UTF-8";
    
    private static final int CONN_TIME_OUT = 2000;
    
    private static final int SOCKET_TIME_OUT = 2000;
    
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientHelper.class);
    
    public static String getPageContent(String url)
        throws IOException
    {
        CloseableHttpClient httpclient = null;
        String returnMsg = null;
        try
        {
            httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            returnMsg = EntityUtils.toString(entity);
        }
        catch (Exception ex)
        {
            LOG.error("Http client exe get method error {}", ex.toString());
        }
        finally
        {
            if (httpclient != null)
            {
                httpclient.close();
            }
        }
        return returnMsg;
    }
    
    /**
     * post请求传输json参数
     * 
     * @param url url地址
     * @param json 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig =
            RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONN_TIME_OUT).build();
        
        httpPost.setConfig(requestConfig);
        try
        {
            if (null != jsonParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), REQUEST_ENCODER);
                entity.setContentEncoding(REQUEST_ENCODER);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String str = "";
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), REQUEST_ENCODER);
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                }
                catch (Exception e)
                {
                    LOG.error("post submit request error:{},{}", url, e.toString());
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("post submit request error:{},{}", url, e.toString());
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }
    
    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2 Content-type:application/x-www-form-urlencoded
     * 
     * @param url url地址
     * @param strParam 参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url, String strParam)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        try
        {
            if (null != strParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, REQUEST_ENCODER);
                entity.setContentEncoding(REQUEST_ENCODER);
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String str = "";
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), REQUEST_ENCODER);
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                }
                catch (Exception e)
                {
                    LOG.error("post submit request error:{},{}", url, e.toString());
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("post submit request error:{},{}" + url, e.toString());
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }
    
    /**
     * 发送get请求
     * 
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url)
    {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        request.setConfig(requestConfig);
        try
        {
            CloseableHttpResponse response = client.execute(request);
            
            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, REQUEST_ENCODER);
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            }
            else
            {
                LOG.error("get submit request error:" + url);
            }
        }
        catch (IOException e)
        {
            LOG.error("get submit request error:" + url, e.toString());
        }
        finally
        {
            request.releaseConnection();
        }
        return jsonResult;
    }
    
    public static String send(String url,String json){
    	CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		 // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();
        post.setConfig(requestConfig);
		try {
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");//发送json数据需要设置contentType
			post.setEntity(s);
			CloseableHttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				return result;
			}
		} catch (Exception e) {
			LOG.error("socket timeout or connect timeout error:" + url, e.toString());
		}finally {
			post.releaseConnection();
        }
		return null;
    }
}
