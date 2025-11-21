package org.pfw.framework.aichat.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.pfw.framework.modules.web.struts2.Struts2Utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public  class ApacheSSEClient extends HttpServlet  {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://127.0.0.1:8081/milvus/ragChat?prompt=你是谁？");
        request.setHeader("Accept", "text/event-stream");

        HttpResponse response = httpClient.execute(request);
        InputStream inputStream = response.getEntity().getContent();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String line;
            StringBuilder eventData = new StringBuilder();
            while ((line = reader.readLine()) != null) {
            	System.out.println(line);
                
                // 忽略其他字段（如 id, event 等）
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
    }
    
    public void doGet(String url) throws  IOException {
        Struts2Utils.getResponse().setContentType("text/event-stream");
        Struts2Utils.getResponse().setCharacterEncoding("UTF-8");
        Struts2Utils.getResponse().setHeader("Cache-Control", "no-cache");
        Struts2Utils.getResponse().setHeader("Connection", "keep-alive");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             PrintWriter clientWriter = Struts2Utils.getResponse().getWriter()) {

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "text/event-stream");

            HttpResponse sseResponse = httpClient.execute(httpGet);
            InputStream inputStream = sseResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                // 转发 SSE 数据，保留原有格式
                clientWriter.write(line + "\n");
                clientWriter.flush(); // 实时推送
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 专门处理 POST 请求
    public static  void doPost(String url ,Map<String, Object> tmpMap)
            throws ServletException, IOException {
    	Struts2Utils.getResponse().setContentType("text/event-stream");
    	Struts2Utils.getResponse().setCharacterEncoding("UTF-8");
    	Struts2Utils.getResponse().setHeader("Cache-Control", "no-cache");
    	Struts2Utils.getResponse().setHeader("Connection", "keep-alive");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             PrintWriter clientWriter = Struts2Utils.getResponse().getWriter()) {

            // 1. 从 POST 请求体中读取 JSON 参数（示例：{"prompt": "你是谁？"}）
//            String jsonBody = readRequestBody(Struts2Utils.getRequest());
//            String prompt = parsePromptFromJson(jsonBody); // 自定义解析方法
        	
        	 // 1. 将 Map 转换为 JSON 字符串
            String jsonBody = JSONUtil.toJsonStr(tmpMap);
        	System.out.println(jsonBody);
            // 2. 构建 POST 请求到后端
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "text/event-stream");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody, "UTF-8")); // 透传原始 JSON
            
            
            
            // 3. 执行请求并转发 SSE 流
            HttpResponse sseResponse = httpClient.execute(httpPost);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sseResponse.getEntity().getContent(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	System.out.println(line);
                    clientWriter.write(line + "\n");
                    clientWriter.flush();
                    
                    // 检查客户端是否断开
                    if (clientWriter.checkError()) {
                        break; // 客户端断开，终止循环
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Struts2Utils.getResponse().sendError(500, "SSE Forwarding Failed: " + e.getMessage());
        }
    }
    
}
