package org.pfw.framework.aichat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.pfw.framework.aichat.dao.TAiYhdhjgDao;
import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.aichat.domain.TAiYhdhls;
import org.pfw.framework.aichat.domain.TAiZnt;
import org.pfw.framework.aichat.pojo.ChatData;
import org.pfw.framework.aichat.service.TAiYhdhjgService;
import org.pfw.framework.aichat.service.TAiYhdhlsService;
import org.pfw.framework.aichat.service.TAiZntService;
import org.pfw.framework.app.util.AppUtil;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


@Service
@Transactional
public class TAiYhdhjgServiceImpl implements TAiYhdhjgService {

	@Autowired
	private TAiYhdhjgDao entityDao;
	@Autowired
	private UserService userService;
	@Autowired
	private TAiZntService tAiZntService;
	@Autowired
	private TAiYhdhlsService tAiYhdhlsService;
	
	@Transactional(readOnly=true)
	public TAiYhdhjg getById(String id) {
		return entityDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiYhdhjg> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiYhdhjg> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiYhdhjg entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiYhdhjg entity)
	{
		entityDao.update(entity);
	}

	public void deleteById(String id)
	{
		entityDao.delete(id);
	}
	
	public void delete(List<String> checks)
	{
		if(checks!=null&&checks.size()>0){
			for (String id : checks) {
				entityDao.delete(id);
			}
		}
	}

	@Override
	public List<TAiYhdhjg> findAll()
	{
		return entityDao.find(" from TAiYhdhjg r ");
	}
	
	@Override
	public List<TAiYhdhjg> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiYhdhjg> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}
	@Override
	public List<TAiYhdhjg> getdhjgList(String dhlsid) {
		
		return entityDao.find(" from TAiYhdhjg a where a.ssdhls.id = '"+dhlsid+"' order by a.czsj");
		
	}
	/**
	 * 保存用户对话历史
	 * **/
	@Override
	public String insertYhdhls(String zntid,String biaot,String loginName) {
		String JsonPStr = Struts2Utils.getParameter("callback");
		boolean isSuccess = true;
		String promptinfo = "";
		String resultinfo = "";
		String extStr  = "";
		if(StringUtils.isNotEmpty(zntid) && StringUtils.isNotEmpty(biaot) && StringUtils.isNotEmpty(loginName)){
			
			
			try {
				String nowdata  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				
				User usr = userService.getUserByLoginName(loginName);
				if(usr != null){
					TAiZnt znt = tAiZntService.getById(zntid);
					TAiYhdhls entity = new TAiYhdhls();
					if(biaot.length() > 80){
						biaot = biaot.substring(0, 79);
					}
					entity.setBiaot(biaot);
					entity.setCzr(usr);
					entity.setCzsj(nowdata);
					entity.setSsznt(znt);
					tAiYhdhlsService.save(entity);
					
					isSuccess = true;
					
					resultinfo = "[{\"entid\":\"" + entity.getId() + "\"}]";
					promptinfo = "操作成功";
				}
				else{
					isSuccess = false;
					resultinfo = "[]";
					promptinfo = "非法参数,操作不成功";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				isSuccess = false;
				resultinfo = "[]";
				promptinfo = "异常错误,操作不成功";
			}

			
		}else{
			isSuccess = false;
			resultinfo = "[]";
			promptinfo = "非法参数,操作不成功";
		}
		String res = AppUtil.generateJson(JsonPStr, isSuccess,
				promptinfo, resultinfo,extStr );
		System.out.println(res);
		return res;
	}
	/***
	 * 保存用户提问
	 * **/
	@Override
	public String insertyhtw(String zntid,String dhlsid,String biaot,String loginName) {
		// 报文格式
		String JsonPStr = Struts2Utils.getParameter("callback");
		boolean isSuccess = true;
		String promptinfo = "";
		String resultinfo = "";
		
		if(StringUtils.isNotEmpty(zntid) && StringUtils.isNotEmpty(dhlsid) && StringUtils.isNotEmpty(biaot) && StringUtils.isNotEmpty(loginName)){
			try {
				String nowdata  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				
				User usr = userService.getUserByLoginName(loginName);
				if(usr != null){
					TAiZnt znt = tAiZntService.getById(zntid);
					TAiYhdhjg entity = new TAiYhdhjg();
					
					TAiYhdhls dhls = new TAiYhdhls();
					dhls.setId(dhlsid);
					entity.setSsdhls(dhls);
					
//					entity.setRole("2");
					entity.setYhtw(biaot);
					
					 
					entity.setCzr(usr);
					entity.setCzsj(nowdata);
					entity.setSsznt(znt);
					this.save(entity);
					
					resultinfo = entity.getId();
					isSuccess = true;
					promptinfo = "操作成功";
				}
				else{
					isSuccess = false;
					resultinfo = "[]";
					promptinfo = "非法参数,操作不成功";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				isSuccess = false;
				resultinfo = "[]";
				promptinfo = "异常错误,操作不成功";
			}

		}else{
			isSuccess = false;
			resultinfo = "[]";
			promptinfo = "非法参数,操作不成功";
		}
		return AppUtil.generateJson(JsonPStr, isSuccess,
				promptinfo, resultinfo);
	
	}
	/***
	 * 保存机器人回答
	 * **/
	@Override
	public String insertbotdh(String jgid,String zntid,String dhlsid,String skgc,String zzda,String loginName) {
		// 报文格式
		String JsonPStr = Struts2Utils.getParameter("callback");
		boolean isSuccess = true;
		String promptinfo = "";
		String resultinfo = "";
		
		if(StringUtils.isNotEmpty(zntid) && StringUtils.isNotEmpty(dhlsid)  && StringUtils.isNotEmpty(loginName)){
			try {
				String nowdata  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				
//				User usr = userService.getUserByLoginName(loginName);
//				if(usr != null){
//					TAiZnt znt = tAiZntService.getById(zntid);
					TAiYhdhjg entity = this.getById(jgid);
					
					TAiYhdhls dhls = new TAiYhdhls();
//					dhls.setId(dhlsid);
//					entity.setSsdhls(dhls);
					
//					entity.setRole("1");
					entity.setSkgc(skgc);
					entity.setZzda(zzda);
					
					Integer tokens = (StringUtils.isNotEmpty(skgc) ? skgc.length() : 0) + (StringUtils.isNotEmpty(zzda) ? zzda.length() : 0);
					entity.setTokens(tokens);
					
//					entity.setCzr(usr);
//					entity.setCzsj(nowdata);
//					entity.setSsznt(znt);
					this.update(entity);
					
					isSuccess = true;
					resultinfo = "1";
					promptinfo = "操作成功";
//				}
//				else{
//					isSuccess = false;
//					resultinfo = "[]";
//					promptinfo = "非法参数,操作不成功";
//				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				isSuccess = false;
				resultinfo = "[]";
				promptinfo = "异常错误,操作不成功";
			}

		}else{
			isSuccess = false;
			resultinfo = "[]";
			promptinfo = "非法参数,操作不成功";
		}
		return AppUtil.generateJson(JsonPStr, isSuccess,
				promptinfo, resultinfo);
	
	}
	@Override
	public  void doPost(String url,String jgid ,Map<String, Object> tmpMap,String zntid,String dhlsid,String loginName)
            throws ServletException, IOException {
		Struts2Utils.getResponse().setContentType("text/event-stream");
		Struts2Utils.getResponse().setCharacterEncoding("UTF-8");
		Struts2Utils.getResponse().setHeader("Cache-Control", "no-cache");
		Struts2Utils.getResponse().setHeader("Connection", "keep-alive");
		Struts2Utils.getResponse().setHeader("X-Accel-Buffering", "no"); // 仅对 Nginx 有效
    	
    	
    	CloseableHttpClient httpClient = HttpClients.createDefault();
        PrintWriter clientWriter = Struts2Utils.getResponse().getWriter();

        String res = "";
        
        try{
        	
        	 // 1. 将 Map 转换为 JSON 字符串
            String jsonBody = JSONUtil.toJsonStr(tmpMap);
        	System.out.println(jsonBody);
            // 2. 构建 POST 请求到后端
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "text/event-stream");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody, "UTF-8")); // 透传原始 JSON

            StringBuffer thinkContent = new StringBuffer();
            StringBuffer answerContent = new StringBuffer();
            
            // 3. 执行请求并转发 SSE 流
            HttpResponse sseResponse = httpClient.execute(httpPost);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sseResponse.getEntity().getContent(), "UTF-8"))) {
                String line;
                
                Boolean ifanswer = false;
                
                while ((line = reader.readLine()) != null) {
                	//当前思考和答案
                	String nowId = "";
                	String nowThinkContent =  "";
                	String nowAnswerContent =  "";
                    
                    // 解析并处理数据
                    if (line.startsWith("data:")) {
            		    String jsonData = line.substring(5).trim();
            		    res += jsonData + ",";
            		    try {
            		        // 使用 Hutool 解析 JSON
            		        JSONObject event = JSONUtil.parseObj(jsonData);
            		        
            		        nowId = event.getStr("id").toString();
            		        
            		        
            		        JSONArray choices = event.getJSONArray("choices");
            		        
            		        if (choices != null && !choices.isEmpty()) {
            		            // 解析每个 choice 的内容
            		            for (int i = 0; i < choices.size(); i++) {
            		                JSONObject choice = choices.getJSONObject(i);
            		                JSONObject delta = choice.getJSONObject("delta");
            		                
            		                // 提取 content 并累积
            		                if (delta != null) {
            		                	
            		                	String content = delta.getStr("content");
                        		        //获取模型
                        		        String model = event.getStr("model").toString();
                        		        //官方对话模型
                        		        if(model.equals("deepseek-chat")){
                        		        	nowAnswerContent = content;
                        		        	answerContent.append(content);
                        		        //官方推理模型
                        		        }else if (model.equals("deepseek-reasoner")) {
                        		        	String tkc = delta.getStr("reasoning_content");
                        		        	if(StringUtils.isNotEmpty(tkc)){
	                        		        	thinkContent.append(tkc);
	                        		        	nowThinkContent = tkc;
                        		        	}
                        		        	if(StringUtils.isNotEmpty(content)){
                        		        		answerContent.append(content);
             		                    		nowAnswerContent = content;
                        		        	}
                        		        //本地模型
            							}else{
            							
                 		                    if (StringUtils.isNotEmpty(content)) {
                 		                    	//判断有结束标签说明思考部分结束了
                 		                    	if(content.equals("</think>")){
                 		                    		ifanswer = true;
                 		                    	}
                 		                    	//不保存这两个标签
                 		                    	if(!content.equals("<think>") && !content.equals("</think>")){
                 		                    		if(ifanswer){
                     		                    		answerContent.append(content);
                     		                    		nowAnswerContent = content;
                     		                    	}else{
                     		                    		thinkContent.append(content);
                     		                    		nowThinkContent = content;
                     		                    	}
                 		                    	}
                 		                    }
            	            		       
            							}
            		                   
            		                }
            		                
            		                // 检查流结束标志（如 finish_reason）
            		                String finishReason = choice.getStr("finish_reason");
            		                if (StringUtils.isNotEmpty(finishReason)) {
            		                	System.out.println("finishReason===" + finishReason);
                                    	insertbotdh(jgid,zntid, dhlsid, thinkContent.toString(), answerContent.toString(), loginName);
            		                	thinkContent.setLength(0); // 清空累积内容
            		                	answerContent.setLength(0); // 清空累积内容
            		                }
            		            }
            		        }
            		    } catch (Exception e) {

                        	insertbotdh(jgid,zntid, dhlsid, thinkContent.toString(), answerContent.toString(), loginName);
                        	
            		    	thinkContent.setLength(0); // 清空累积内容
		                	answerContent.setLength(0); // 清空累积内容
            		        e.printStackTrace();
            		    }
            		}
                    
                    // 检查客户端是否断开
                    if (clientWriter.checkError()) {
                    	insertbotdh(jgid,zntid, dhlsid, thinkContent.toString(), answerContent.toString(), loginName);
                    	thinkContent.setLength(0); // 清空累积内容
	                	answerContent.setLength(0); // 清空累积内容
                        break; // 客户端断开，终止循环
                    }
                   
                    ChatData chatData = new ChatData();
                    chatData.setId(nowId);
                    chatData.setThinkContent(nowThinkContent);
                    chatData.setAnswerContent(nowAnswerContent);
                    
                    System.out.println("data:" + JSONUtil.toJsonStr(chatData) + "\n\n");
                    
                    //最终流给前端
                    clientWriter.write("data:" + JSONUtil.toJsonStr(chatData) + "\n\n");
                    clientWriter.flush();
                    // 添加100毫秒延迟
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 恢复中断状态
                        break; // 可选择退出循环以响应中断
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            clientWriter.write("系统异常，请稍后再试！");
            clientWriter.flush();
            Struts2Utils.getResponse().sendError(500, "SSE Forwarding Failed: " + e.getMessage());
        }finally{
        	if(httpClient != null){
        		httpClient.close();
        	}
        	if(clientWriter != null){
        		clientWriter.flush();
        	}
        	
        }
//        System.out.println("res===>" + res);
    }
	@Override
	public  void doPostTextToSql(String url ,String jgid ,Map<String, Object> tmpMap,String zntid,String dhlsid,String loginName)throws ServletException, IOException {

    	Struts2Utils.getResponse().setContentType("text/event-stream");
    	Struts2Utils.getResponse().setCharacterEncoding("UTF-8");
    	Struts2Utils.getResponse().setHeader("Cache-Control", "no-cache");
    	Struts2Utils.getResponse().setHeader("Connection", "keep-alive");
    	
    	CloseableHttpClient httpClient = HttpClients.createDefault();
        PrintWriter clientWriter = Struts2Utils.getResponse().getWriter();
        String result = "";
        
        try{
        	 // 1. 将 Map 转换为 JSON 字符串
            String jsonBody = JSONUtil.toJsonStr(tmpMap);
        	System.out.println(jsonBody);
            // 2. 构建 POST 请求到后端
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "text/event-stream");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody, "UTF-8")); // 透传原始 JSON

            StringBuffer answerContent = new StringBuffer();
            
            
            // 3. 执行请求并转发 SSE 流
            HttpResponse sseResponse = httpClient.execute(httpPost);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sseResponse.getEntity().getContent(), "UTF-8"))) {
                String line;
                
                Boolean ifanswer = false;
                
                while ((line = reader.readLine()) != null) {
                	System.out.println(line);
                    clientWriter.write(line + "\n");
                    clientWriter.flush();
                    
                    // 解析并处理数据
                    if (line.startsWith("data:")) {
            		    String jsonData = line.substring(5).trim();
            		    try {
            		        // 使用 Hutool 解析 JSON
            		        JSONObject event = JSONUtil.parseObj(jsonData);
            		        JSONArray choices = event.getJSONArray("choices");
            		        
            		        if (choices != null && !choices.isEmpty()) {
            		            // 解析每个 choice 的内容
            		            for (int i = 0; i < choices.size(); i++) {
            		                JSONObject choice = choices.getJSONObject(i);
            		                JSONObject delta = choice.getJSONObject("delta");
            		                
            		                // 提取 content 并累积
            		                if (delta != null) {
            		                    String content = delta.getStr("content");
            		                    if (StringUtils.isNotEmpty(content)) {
            		                    	//判断有结束标签说明思考部分结束了
            		                    	if(content.equals("</think>")){
            		                    		ifanswer = true;
            		                    	}
            		                    	//不保存这两个标签
        		                    		if(ifanswer){
            		                    		answerContent.append(content);
            		                    	}
            		                    }
            		                }
            		                
            		                // 检查流结束标志（如 finish_reason）
            		                String finishReason = choice.getStr("finish_reason");
            		                if (StringUtils.isNotEmpty(finishReason)) {
            		                	
            		                	try {
            		                		List tmpList = entityDao.getSession().createSQLQuery(answerContent.toString()).list();
                		                	if(tmpList != null && tmpList.size() > 0){
                		                		result = tmpList.toString();
                		                	}
										} catch (Exception e) {
											// TODO: handle exception
											result = "参数异常，请稍后再试！";
										}
                                    	insertbotdh(jgid,zntid, dhlsid, null, result, loginName);
            		                	answerContent.setLength(0); // 清空累积内容
            		                }
            		            }
            		        }
            		    } catch (Exception e) {

                        	insertbotdh(jgid,zntid, dhlsid, null, answerContent.toString(), loginName);
                        	
		                	answerContent.setLength(0); // 清空累积内容
		                	
		                	clientWriter.write("系统异常，请稍后再试！");
	                        clientWriter.flush();
            		        e.printStackTrace();
            		    }
            		}
                    
                    // 检查客户端是否断开
                    if (clientWriter.checkError()) {
                    	insertbotdh(jgid,zntid, dhlsid, null, answerContent.toString(), loginName);
	                	answerContent.setLength(0); // 清空累积内容
	                	 // 发生异常时，返回“系统异常”消息
	                	clientWriter.write("系统异常，请稍后再试！");
                        clientWriter.flush();
                        break; // 客户端断开，终止循环
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            clientWriter.write("系统异常，请稍后再试！");
            clientWriter.flush();
            Struts2Utils.getResponse().sendError(500, "SSE Forwarding Failed: " + e.getMessage());
        }finally{
        	clientWriter.write(result);
            clientWriter.flush();
            
        	if(httpClient != null){
        		httpClient.close();
        	}
        	if(clientWriter != null){
        		clientWriter.flush();
        	}
        }
	}
	
	
	//得到2个就近聊天结果提供给机器人进行多轮对话
	@Override
	public String getTopFiveLsit(String lssjid,Integer dldhs) {
		if(dldhs == null || dldhs == 0){
			return null;
		}
		
		List<TAiYhdhjg> dhjgList = entityDao.find( " from TAiYhdhjg a where a.ssdhls.id = ? order by czsj desc ",lssjid);
		List<Map<String, String>> tmpLists = new ArrayList<Map<String, String>>();
		
		if(dhjgList != null && dhjgList.size() > 0){
			if(dhjgList.size() > 2){
				dhjgList = dhjgList.subList(0, 2);
			}
			for (TAiYhdhjg tAiYhdhjg : dhjgList) {
				Map<String, String> map = new HashMap<String, String>();
//				if(tAiYhdhjg.getRole().equals("1")){
					map.put("role", "assistant");
					map.put("content", tAiYhdhjg.getZzda());
					tmpLists.add(map);
//				}
//				if(tAiYhdhjg.getRole().equals("2")){
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("role", "user");
					map2.put("content", tAiYhdhjg.getYhtw());
//				}
				tmpLists.add(map2);
			}
		}
		return JSONUtil.toJsonStr(tmpLists);
	}
	
}
