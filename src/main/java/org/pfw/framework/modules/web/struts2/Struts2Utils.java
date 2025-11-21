package org.pfw.framework.modules.web.struts2;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Struts2 Utils类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * 
 * @author calvin
 */
public class Struts2Utils {

	// -- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	private static Logger logger = LoggerFactory.getLogger(Struts2Utils.class);

	// -- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 取得Request Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName,
						NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName
							+ "不是一个合法的header类型");
			}

			HttpServletResponse response = ServletActionContext.getResponse();
			
			//解决tomcat7下跨域问题
			response.setHeader("Access-Control-Allow-Headers", "Authentication,content-type");
			//response.setHeader("Access-Control-Allow-Origin", "*");
			//response.setHeader("Access-Control-Allow-Methods", "*");
			//response.setHeader("Access-Control-Request-Headers", "Origin, X-Requested-With, content-Type, Accept, Authorization");
			
			

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString,
			final String... headers) {
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, final String... headers) {
		String jsonString = JSONObject.fromObject(object).toString();
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param collction
	 *            Java对象集合, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Collection<?> collction,
			final String... headers) {
		String jsonString = JSONArray.fromObject(collction).toString();
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param array
	 *            Java对象数组, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object[] array, final String... headers) {
		String jsonString = JSONArray.fromObject(array).toString();
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param contentMap
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJsonp(final String callbackName,
			final Map contentMap, final String... headers) {
		String jsonParam = JSONObject.fromObject(contentMap).toString();

		StringBuilder result = new StringBuilder().append(callbackName).append(
				"(").append(jsonParam).append(");");

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{content:'Hello World!!!'}");
		render(JS_TYPE, result.toString(), headers);
	}

	/**
	 * 将给定的字符串通过特定的分拆符转换为List
	 * 
	 * @param String
	 *            目标字符串.
	 * @param s1
	 *            分拆符.
	 * 
	 */
	public static List split(String s, String s1) {
		Object obj = null;
		StringTokenizer stringtokenizer = null;
		if (s == null)
			return ((List) (obj));
		if (s1 != null)
			stringtokenizer = new StringTokenizer(s, s1);
		else
			stringtokenizer = new StringTokenizer(s);
		if (stringtokenizer != null && stringtokenizer.hasMoreTokens()) {
			obj = new ArrayList();
			for (; stringtokenizer.hasMoreTokens(); ((List) (obj))
					.add(stringtokenizer.nextToken()))
				;
		}
		return ((List) (obj));
	}

	/**
	 * 将给定的List拆成字符串，并以特定的分融符间隔
	 * 
	 * @param String
	 *            目标字符串.
	 * @param s1
	 *            分拆符.
	 * 
	 */
	public static String listSplitToString(List<String> s, String s1) {

		if (s != null && s.size() > 0 && s1 != null && !s1.equals("")) {
			String returnStr = "";
			for (String stmp : s) {
				returnStr += stmp + s1;
			}
			if (returnStr != null && returnStr.length() > 0)
				returnStr = returnStr.substring(0, returnStr.length() - 1);
			return returnStr;
		}
		return "";
	}

	/**
	 * 根据传入的字符串，分隔符，开始字符，结束字符得到返回区配数组
	 * 如："department[id,bmmc,parent[id,bmmc,children[id,bmmc,parent[id,bmmc]]]]"  
	 * ==》department,id,bmmc,parent[id,bmmc,children[id,bmmc,parent[id,bmmc]]]]
	 */
	public static HashMap getPpstr(String targetStr ,String begStr,String endStr,String splitStr) throws Exception
	{
		int begStrCount = -1;//begStr总数
		int begXb = -1;      //begStr第一个下标
		int endStrCount = -1;//endStr总数
		int endXb = -1;      //endStr最后一个下标
		List ppKvxb = new ArrayList();
		
		for(int i=0;i<targetStr.length();i++)
		{
			String tmpStr = String.valueOf(targetStr.charAt(i));
			if(tmpStr.equals(begStr))
			{
				if(begStrCount == -1)
				{
					begXb = i;
				}
				begStrCount++;
			}
				
			if(tmpStr.equals(endStr))
			{
				endStrCount++;
				endXb = i;
			}
		}
		if(begStrCount != endStrCount || begStrCount == -1 && endXb != targetStr.length()-1)
			throw new Exception("字符串不匹配，请检查格式！！");
		List tmpLs = new ArrayList<String>();
		String str1 = targetStr.substring(0, begXb);//得到属性名
		String proStr = targetStr.substring(begXb+1,endXb);//得到属性字段

		//如果只有属性了，直接split
		
		if(proStr.indexOf("[") < 0)
		{
			List proLs = split(proStr, ",");
			HashMap retMap = new HashMap();
			retMap.put(str1, ((List<String>)proLs).toArray(new String[proLs.size()]));
			return retMap;
		}else{
			int begStrCount1 = -1;//begStr1总数
			int begXb1 = -1;      //begStr1第一个下标
			int endStrCount1 = -1;//endStr1总数
			int endXb1 = -1;      //endStr1最后一个下标
			List<String> ppKvxb1 = new ArrayList();
			
			for(int i=0;i<proStr.length();i++)
			{
				String tmpStr = String.valueOf(proStr.charAt(i));
				if(tmpStr.equals(begStr))
				{
					if(begStrCount1 == -1)
					{
						begXb1 = i;
					}
					begStrCount1++;
				}
					
				if(tmpStr.equals(endStr))
				{
					endStrCount1++;
					if(endStrCount != -1 && endStrCount1 == begStrCount1)
					{
						String t1 = proStr.substring(begXb1,i+1);
						String t2 = proStr.substring(0,begXb1);
						String t3 = t2.substring(t2.lastIndexOf(splitStr)+1);
						String t4 = t3 + t1;
						ppKvxb1.add(t4);
						begStrCount1 = -1;//begStr1总数
						begXb1 = -1;      //begStr1第一个下标
						endStrCount1 = -1;//endStr1总数
						endXb1 = -1;      //endStr1最后一个下标						
						endXb1 = i;
					}
					
				}
			}
			
			
			for(String tmplsStr : ppKvxb1)
			{
				proStr = proStr.replace(tmplsStr, "");
			}
			
			List proLs = new ArrayList();
			
			if(StringUtils.isNotEmpty(proStr))
			{
				proStr = proStr.replace(",,", ",");
				proLs = split(proStr, ",");
			}
				
			for(String tmplsStr : ppKvxb1)
			{
				proLs.add(tmplsStr);
			}
			HashMap retMap = new HashMap();
			retMap.put(str1, ((List<String>)proLs).toArray(new String[proLs.size()]));
			return retMap;			
		}
	}
	
	/**
	 * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串
	 * 
	 * @param bean
	 *            bean对象
	 * @return String
	 * example:beanToJson(user,"loginName","name","department[id,bmmc,parent[id,bmmc,children[id,bmmc,parent[id,bmmc]]]]","roleList[id,name]")
	 */
	public static String beanToJson(Object bean, String...fields) throws Exception
	{
		String muStr = "{";
		PropertyDescriptor[] props = null;
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		if (props != null) 
		{
			for(String tmpStr : fields){
				if(tmpStr.indexOf("[") < 0)
				{
					for (int i = 0; i < props.length; i++) 
					{
						String name = props[i].getName();
						//添加指定的属性
						if(tmpStr.equals(name))
						{
							try{
							String value = props[i].getReadMethod().invoke(bean).toString();
							muStr += "\""+name+"\":"+"\""+changeHtm(value)+"\",";
							}catch(Exception e)
							{}
						}						
					}						
				}else{
					HashMap tmpMap = getPpstr(tmpStr,"[","]",",");
					if(tmpMap != null)
					{
						if(tmpMap.size() != 1)
						{
							throw new Exception("计算属性时发生错误，不可识别的Map");
						}
						 Set<String> key = tmpMap.keySet();
						 String proValue = "";
						 String[] tmpfield = null;
						 for (Iterator it = key.iterator(); it.hasNext();) 
						 {
							 proValue = (String) it.next();
							 tmpfield = (String[])tmpMap.get(proValue);
						 }
						 
							for (int i = 0; i < props.length; i++) 
							{
								String name = props[i].getName();
								if(proValue.equals(name))
								{
									Object value = props[i].getReadMethod().invoke(bean);
									if(value instanceof Set || value instanceof List)
									{
										List valList = new ArrayList();
										if(value instanceof Set)
											valList = new ArrayList((Set)value);
										if(value instanceof List)
											valList = (ArrayList)value;	
										
										muStr += "\""+proValue+"\":";
										
										muStr += listToJson1(valList,tmpfield);
										
										muStr += ",";
										
									}else 
									if(value != null && !(value instanceof String))
									{
										muStr += "\""+proValue+"\":";
										muStr += beanToJson(value,tmpfield);
										muStr += ",";
										//String t2 = beanToJson2(value,tmpfield);
										//muStr += "\""+tmpfield+"\":"+t2+",";
									}else{
										break;
									}
																
								}
							}
					}
				}
			}
			muStr = muStr.substring(0,muStr.length()-1);
			muStr += "}";
		}else{
			muStr += "}";
		}
		return muStr;
	}
	
	/**
	 * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串
	 * 
	 * @param bean
	 *            bean对象
	 * @return String
	 * example:beanToJson(user,"loginName","name","department[id,bmmc,parent[id,bmmc,children[id,bmmc,parent[id,bmmc]]]]","roleList[id,name]")
	 */
	public static String beanToJson1(Object bean, String...fields) throws Exception
	{
		String muStr = "";
		muStr += "{";
		PropertyDescriptor[] props = null;
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		if (props != null) 
		{
			for(String tmpStr : fields){
				if(tmpStr.indexOf("[") < 0)
				{
					for (int i = 0; i < props.length; i++) 
					{
						String name = props[i].getName();
						//添加指定的属性
						if(tmpStr.equals(name))
						{
							try{
							String value = props[i].getReadMethod().invoke(bean).toString();
							muStr += "\""+name+"\":"+"\""+changeHtm(value)+"\",";
							}catch(Exception e)
							{}
						}						
					}						
				}else{
					HashMap tmpMap = getPpstr(tmpStr,"[","]",",");
					if(tmpMap != null)
					{
						if(tmpMap.size() != 1)
						{
							throw new Exception("计算属性时发生错误，不可识别的Map");
						}
						 Set<String> key = tmpMap.keySet();
						 String proValue = "";
						 String[] tmpfield = null;
						 for (Iterator it = key.iterator(); it.hasNext();) 
						 {
							 proValue = (String) it.next();
							 tmpfield = (String[])tmpMap.get(proValue);
						 }
						 
							for (int i = 0; i < props.length; i++) 
							{
								String name = props[i].getName();
								if(proValue.equals(name))
								{
									Object value = props[i].getReadMethod().invoke(bean);
									if(value instanceof Set || value instanceof List)
									{
										List valList = new ArrayList();
										if(value instanceof Set)
											valList = new ArrayList((Set)value);
										if(value instanceof List)
											valList = (ArrayList)value;	
										
										muStr += "\""+proValue+"\":";
										
										muStr += listToJson1(valList,tmpfield);
										
										muStr += ",";
										
									}else 
									if(value != null && !(value instanceof String))
									{
										muStr += "\""+proValue+"\":";
										muStr += beanToJson(value,tmpfield);
										muStr += ",";
										//String t2 = beanToJson2(value,tmpfield);
										//muStr += "\""+tmpfield+"\":"+t2+",";
									}else{
										break;
									}
																
								}
							}
					}
				}
			}
			muStr = muStr.substring(0,muStr.length()-1);
			muStr += "}";
		}else{
			muStr += "}";
		}
		return muStr;
		
	}	
	
	/**
	 * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	 * 
	 * @param list
	 *            列表对象
	 * @return java.lang.String
	 */
//	public static String muStr = "";
	public static String listToJson(List<?> list,String...fieldS) throws Exception{
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				String muStr = "";
				muStr += beanToJson(obj,fieldS);
				json.append(muStr);
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}
	
	public static String listToJson1(List<?> list,String...fieldS) throws Exception{
		StringBuilder json = new StringBuilder();
		String muStr  = "[";
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				muStr += beanToJson(obj,fieldS);
				muStr += ",";
			}
			muStr = muStr.substring(0,muStr.length()-1);
			muStr += "]";
		} else {
			muStr += "]";
		}
		return muStr;
	}	
	
	/**
	 * 往Session里传程序错误信息
	 */
	public static void setErrorInfo(String errInfo)
	{
		getSession().setAttribute("errorInfo", errInfo);
	}
	
	/**
	 * 往Session里传程序提示信息
	 */
	public static void setPromptInfo(String promptInfo)
	{
		getSession().setAttribute("promptInfo", promptInfo);
	}	
	
	/**
	 * 往request里传程序提示信息
	 */
	public static void setPromptInfoToReq(String promptInfo)
	{
		getRequest().setAttribute("promptInfo", promptInfo);
	}	
	
	/**
	 * 往request里传程序错误信息
	 */
	public static void setErrorInfoToReq(String errInfo)
	{
		getSession().setAttribute("errorInfo", errInfo);
	}
	
	/**
	 * 往request里传程序自定义信息
	 */
	public static void setKyStrToReq(String key,String val)
	{
		getRequest().setAttribute(key, val);
	}
	
	
	/**
	 * 通用反射执行某一个类的方法
	 * @param classname
	 * @param methodname
	 * @param args
	 * @return
	 */
	 public static Object  Invoke(String classname,String methodname,Object[] args){
		// 这里，你给我传的参数是 类的名称，以及方法的名称,args是参数的名称
		Object result = null;
		try {
			ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(Struts2Utils.getRequest().getSession().getServletContext());
			Object obj = ac1.getBean(classname);
			Class obj1 = obj.getClass();
			
			// 这里就用到反射的包了
			Method[] methods = obj1.getMethods();
			// 上面以及找到了所有的方法，是不是我们想要的方法也在里面
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];// 找到具体的某一个方法
				if (method.getName().equals(methodname)) {
					// 如果找到的方法和我们想要用的方法名称相同，就找到了这个方法了
					result = method.invoke(obj, args);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	 
	/**
	 * 字符串转换为UTF-8格式
	 * 
	 * @param s
	 * @return
	 */

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377') {
				sb.append(c);
			} else {
				byte b[];
				try {
					b = String.valueOf(c).getBytes("UTF-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}

			}
		}

		return sb.toString();
	}
	
	/**
	 * 资源更新后，需要更新spring security中的url-授权缓存
	 */
	public static void restoreResourceUrl()
	{
		try
		{
			//资源更新后，需要更新spring security中的url-授权缓存
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(Struts2Utils.getRequest().getSession().getServletContext());
		    FactoryBean factoryBean = (FactoryBean) ctx.getBean("&databaseDefinitionSource");   
		    FilterInvocationDefinitionSource fids = (FilterInvocationDefinitionSource) factoryBean.getObject();   
		    FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("filterSecurityInterceptor");   
		    filter.setObjectDefinitionSource(fids);   

		}catch(Exception e)
		{
			
		}

	}
	
	/**
	 * List元素去重
	 */
	public static List removeDuplicateWithOrder(List list) {

		Set set = new HashSet();

		List newList = new ArrayList();

		for (Iterator iter = list.iterator(); iter.hasNext();) {

			Object element = iter.next();

			if (set.add(element))

				newList.add(element);

		}

		list.clear();

		list.addAll(newList);

		return list;

	}
	
	public static String changeHtm(String changeHtmlValue)
	{
	   if(null == changeHtmlValue 
	          || "".equals(changeHtmlValue ) 
	          || changeHtmlValue.isEmpty() )
	   {
	      return "";
	   }
	 
	   if (-1 != changeHtmlValue.indexOf("：")) 
	   {
	      changeHtmlValue = changeHtmlValue.replaceAll("：", "\\：");
	   }
	  
	   if (-1 != changeHtmlValue.indexOf(":")) 
	   {
	      changeHtmlValue= changeHtmlValue.replaceAll(":", "\\:");
	   }
	 
	   if (-1 != changeHtmlValue.indexOf("'")) 
	   {
	      changeHtmlValue = changeHtmlValue.replaceAll("'", "\\'");
	   }
		
	   if (-1 != changeHtmlValue.indexOf("\"")) 
	   {
	      changeHtmlValue = changeHtmlValue.replaceAll("\"", "\\\\\"");
	   }
	 
	   if (-1 != changeHtmlValue .indexOf("\r\n")) 
	   {
	      changeHtmlValue = changeHtmlValue .replaceAll("\r\n", "<br>");
	   }
	   
	   if (-1 != changeHtmlValue .indexOf("\n")) 
	   {
	      changeHtmlValue = changeHtmlValue .replaceAll("\n", "<br>");
	   }
	   return changeHtmlValue.trim();
	}
	
	
	public static String getfwurl()  {
		
		String strBackUrl = Struts2Utils.getRequest().getScheme() + "://" + Struts2Utils.getRequest().getServerName() //服务器地址  
                + ":"   
                + Struts2Utils.getRequest().getServerPort()  + Struts2Utils.getRequest().getContextPath();      //项目名称  
		
		return strBackUrl;
	}
	
}
