package org.jasig.cas.client.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jdk1.5中没有封装好的包来请求https，所以这里做了一层封装,可以实现以下类型的请求
 * http post
 * http get
 * https post
 * https get
 *
 * @author qianhongwei
 */
public class HttpClientForTls12 {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientForTls12.class);

    /**
     * 定义类型，用于获取不同类型的httpclient
     */
    enum CLIENT_TYPE {
        HTTP, HTTPS
    }

    /**
     * https post请求
     *
     * @param url        请求地址
     * @param headerMap  请求头信息
     * @param contentMap 请求体信息
     * @return 结果返回
     */
    public static String httpsPost(String url, Map<String, String> headerMap, Map<String, String> contentMap) {
        return httpsPost(url, headerMap, contentMap, "UTF-8");
    }

    /**
     * http post请求
     *
     * @param url        请求地址
     * @param headerMap  请求头信息
     * @param contentMap 请求体信息
     * @return 结果返回
     */
    public static String httpPost(String url, Map<String, String> headerMap, Map<String, String> contentMap) {
        return httpPost(url, headerMap, contentMap, "UTF-8");
    }

    /**
     * https get请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 结果返回
     */
    public static String httpsGet(String url, Map<String, String> paramMap) {
        return httpsGet(url, paramMap, "UTF-8");
    }

    /**
     * http get请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 结果返回
     */
    public static String httpGet(String url, Map<String, String> paramMap) {
        return httpGet(url, paramMap, "UTF-8");
    }

    /**
     * @param url        请求地址
     * @param headerMap  请求头信息
     * @param contentMap 请求体信息
     * @param charset    编码类型
     * @return 结果返回
     */
    public static String httpsPost(String url, Map<String, String> headerMap, Map<String, String> contentMap, String charset) {
        return post(url, headerMap, contentMap, charset, CLIENT_TYPE.HTTPS);
    }

    /**
     * @param url        请求地址
     * @param headerMap  请求头信息
     * @param contentMap 请求体信息
     * @param charset    编码类型
     * @return 结果返回
     */
    public static String httpPost(String url, Map<String, String> headerMap, Map<String, String> contentMap, String charset) {
        return post(url, headerMap, contentMap, charset, CLIENT_TYPE.HTTP);
    }

    /**
     * @param url      请求地址
     * @param paramMap 请求参数
     * @param charset  编码类型
     * @return 结果返回
     */
    public static String httpsGet(String url, Map<String, String> paramMap, String charset) {
        return get(url, paramMap, charset, CLIENT_TYPE.HTTPS);
    }

    /**
     * @param url      请求地址
     * @param paramMap 请求参数
     * @param charset  编码类型
     * @return 结果返回
     */
    public static String httpGet(String url, Map<String, String> paramMap, String charset) {
        return get(url, paramMap, charset, CLIENT_TYPE.HTTP);
    }

    /**
     * post 请求的实际方法
     *
     * @param url        请求地址
     * @param headerMap  请求头信息
     * @param contentMap 请求体信息
     * @param charset    编码类型
     * @param type       协议类型
     * @return 结果返回
     */
    private static String post(String url, Map<String, String> headerMap, Map<String, String> contentMap, String charset, CLIENT_TYPE type) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("请求url为空");
        }

        String result = null;
        HttpClient httpClient = null;
        try {
            HttpPost post = new HttpPost(url);
            if (MapUtils.isNotEmpty(headerMap)) {// 设置请求头
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }

            if (MapUtils.isNotEmpty(contentMap)) {// 设置请求体
                List<NameValuePair> content = getNameValuePairList(contentMap);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(content, charset);
                post.setEntity(entity);
            }

            httpClient = getClient(type);//这里是重点，根据不同协议获取不同类型的client端
            HttpResponse response = httpClient.execute(post);//发送请求并接收返回数据
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("请求：" + url + " 异常:" + ex.getMessage());
        } finally {
            try {
                if (null != httpClient && null != httpClient.getConnectionManager()) {
                    httpClient.getConnectionManager().shutdown();
                }
            } catch (Exception e) {
                logger.error("请求：" + url + " 流关闭异常或者httpclient关闭异常");
            }
        }
    }

    /**
     * get 请求的实际方法
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @param charset  编码类型
     * @param type     协议类型
     * @return 结果返回
     */
    private static String get(String url, Map<String, String> paramMap, String charset, CLIENT_TYPE type) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("请求url为空");
        }

        String result = null;
        HttpClient httpClient = null;
        try {
            if (MapUtils.isNotEmpty(paramMap)) {// 拼接参数
                // 设置请求体
                List<NameValuePair> content = getNameValuePairList(paramMap);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(content, charset);
                String params = EntityUtils.toString(entity);
                url = url + "?" + params;
            }

            HttpGet get = new HttpGet(url);
            httpClient = getClient(type);
            HttpResponse response = httpClient.execute(get);            //发送请求并接收返回数据
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
            return result;
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new RuntimeException("请求：" + url + " 异常:" + ex.getMessage());
        } finally {
            try {
                if (null != httpClient && null != httpClient.getConnectionManager()) {
                    httpClient.getConnectionManager().shutdown();
                }
            } catch (Exception e) {
                logger.error("请求：" + url + " 流关闭异常或者httpclient关闭异常");
            }
        }
    }

    private static List<NameValuePair> getNameValuePairList(Map<String, String> paramMap) {
        List<NameValuePair> content = null;
        if (MapUtils.isNotEmpty(paramMap)) {
            // 设置请求体
            content = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                content.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return content;
    }

    /**
     * 根据指定类型返回http、https类型的客户端
     *
     * @param type 类型
     * @return 客户端
     * @throws Exception 异常信息
     */
    private static DefaultHttpClient getClient(CLIENT_TYPE type) throws Exception {
        if (type == CLIENT_TYPE.HTTP) {//http类型
            return new DefaultHttpClient();
        } else if (type == CLIENT_TYPE.HTTPS) {//https类型
            return new SSLClient();
        } else {
            throw new RuntimeException("未知协议类型，请重新指定");
        }
    }

    /**
     * 自定义SSL client
     */
    static class SSLClient extends DefaultHttpClient {
        public SSLClient() throws Exception {
            super();
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            //传输协议需要根据自己的判断　
            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            //SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        }
    }

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<String, String>();
        //param.put("fr", "aladdin");
        //String result = httpsGet("https://baike.baidu.com/item/%E5%89%81%E6%A4%92%E9%B1%BC%E5%A4%B4/1171373", param);
    	String result = httpsGet("https://mono.lantiandun.com/cas/serviceValidate?ticket=ST-7-guFffXp9o2UTnGvJotEtR37eCs8-iZh3lsgh2qjwyaZ&service=http%3A%2F%2Flocalhost%3A8080%2Ftqframeworktbv%2Fj_spring_cas_security_check", param);
    	System.out.println(result);

        //String result = httpsGet("https://baike.baidu.com/item/%E5%89%81%E6%A4%92%E9%B1%BC%E5%A4%B4/1171373?fr=aladdin", null);
        //String result = httpGet("http://www.baidu.com",null);

        //System.out.println(result);
    }
    
    
    /**
     * httpclient不支持TLS1.2问题参考
     * https://www.iteye.com/blog/ligaosong-2357472
     * @param base
     * @return
     */
    private static HttpClient httpClientEnableTLSTrust(final HttpClient base) {  
        try {  
            final SSLContext ctx = SSLContext.getInstance("TLSv1.2");  
            final TrustManager tm = new X509TrustManager() {  
                @Override  
                public void checkClientTrusted(final X509Certificate[] xcs,  
                        final String string) throws CertificateException {  
                }  
    
                @Override  
                public void checkServerTrusted(final X509Certificate[] xcs,  
                        final String string) throws CertificateException {  
                }  
    
                @Override  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
            };  
            ctx.init(null, new TrustManager[] { tm }, null);  
              
            final SSLSocketFactory ssf = new SSLSocketFactory(ctx,  
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            final ClientConnectionManager ccm = base.getConnectionManager();  
            final SchemeRegistry sr = ccm.getSchemeRegistry();  
            sr.register(new Scheme("https", 443, ssf));  
            return new DefaultHttpClient(ccm, base.getParams());  
        } catch (final Exception ex) {  
            ex.printStackTrace();  
            return null;  
        }  
    }  

}