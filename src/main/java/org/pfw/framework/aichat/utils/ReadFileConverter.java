package org.pfw.framework.aichat.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.HttpClientForTls12;
import org.pfw.framework.app.util.ObjectToJosn;
import org.pfw.framework.core.util.PFWConfigUtil;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;



/**
 *文件内容读取转换器
 */
public class ReadFileConverter
{
//	//sbfs：识别方式
    public static String getContents(String path,String sbfs) throws Exception
    {
    	String fileUrl = "http://localhost:8080" + path;
    	//基础识别
    	if(StringUtils.isNotEmpty(sbfs) && "1".equals(sbfs)){
    		
        	Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
    		String url = (String) poperties.get("fileToText_default_url"); 
    		Map<String, String> contentMap = new HashMap<String, String>();
    		contentMap.put("fileUrl", fileUrl );
    		String res = null;
    		if(url.indexOf("https") > -1){
    			res = HttpClientForTls12.httpsPost(url, null, contentMap);
    		}else{
    			res = HttpClientForTls12.httpPost(url, null, contentMap);
    		}
    		System.out.println(res);
    		return res;
    	}
    	//高级识别
    	if(StringUtils.isNotEmpty(sbfs) && "2".equals(sbfs)){ 
    		return  fileToText(ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/") + path);
    	}
    	return null;
    }
  

    public static String recognize(String baseUrl,String appId,String secretCode,byte[] fileContent, HashMap<String, Object> options) throws IOException {
        // Build URL with query parameters
        StringBuilder queryParams = new StringBuilder();
        // Add query parameters
        for (Map.Entry<String, Object> entry : options.entrySet()) {
            if (queryParams.length() > 0) {
                queryParams.append("&");
            }
            queryParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }

        // Create full URL with query parameters
        String fullUrl = baseUrl + (queryParams.length() > 0 ? "?" + queryParams : "");
        URL url = new URL(fullUrl);

        // Create and configure HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // Set headers
        connection.setRequestProperty("x-ti-app-id", appId);
        connection.setRequestProperty("x-ti-secret-code", secretCode);
        // 方式一：读取本地文件
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        // 方式二：使用URL方式
        // connection.setRequestProperty("Content-Type", "text/plain");

        // Enable output and send file content
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(fileContent);
            os.flush();
        }

        // Read response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("HTTP request failed with code: " + responseCode);
        }
    }
    public static String fileToText(String path) throws IOException {
    	
    	Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
    	
		String baseUrl = (String) poperties.get("fileToText_senior_url"); 
		String appId = (String) poperties.get("fileToText_senior_appId"); 
		String secretCode = (String) poperties.get("fileToText_senior_secretCode"); 
		//跳过证书
    	trustAllCertificates();
//        OCRClient client = new OCRClient(appId, secretCode);

        // Read image file
        // 方式一：读取本地文件
        byte[] fileContent = Files.readAllBytes(Paths.get(path));
        // 方式二：使用URL方式（需要将headers中的Content-Type改为'text/plain'）
        // byte[] fileContent = "https://example.com/path/to/your.pdf".getBytes(StandardCharsets.UTF_8);

        HashMap<String, Object> options = new HashMap<>();
        
        String data = "";
        String code ="500";
        try {
            String response = recognize(baseUrl,appId,secretCode,fileContent, options);
            System.out.println("response====>" + response);
            JSONObject jsonObj = JSONUtil.parseObj(response);
            if (jsonObj.containsKey("result")) {
                JSONObject result = jsonObj.getJSONObject("result");
                if (result.containsKey("markdown")) {
                	data = result.getStr("markdown");
                	code ="200";
                }
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> tmpMap = new HashMap<String, String>();
        tmpMap.put("data", data);
        tmpMap.put("code", code);
		return ObjectToJosn.mapToJosn(tmpMap);
		
	}
    

    // 在main方法或连接创建前添加
    static void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };
            
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws IOException {
//       String res = fileToText("D:\\开发指南、会议纪要\\deepseek\\测试数据\\地震矩张量及其反演.pdf");
//       System.out.println(res);
    }
}