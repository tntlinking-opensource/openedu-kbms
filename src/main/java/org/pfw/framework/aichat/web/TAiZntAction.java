package org.pfw.framework.aichat.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jasig.cas.client.util.HttpClientForTls12;
import org.pfw.framework.aichat.domain.TAiCsgl;
import org.pfw.framework.aichat.domain.TAiHsgl;
import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.aichat.domain.TAiYhdhls;
import org.pfw.framework.aichat.domain.TAiZnt;
import org.pfw.framework.aichat.domain.TAiZsk;
import org.pfw.framework.aichat.service.TAiCsglService;
import org.pfw.framework.aichat.service.TAiHsglService;
import org.pfw.framework.aichat.service.TAiYhdhjgService;
import org.pfw.framework.aichat.service.TAiYhdhlsService;
import org.pfw.framework.aichat.service.TAiZntService;
import org.pfw.framework.app.util.ObjectToJosn;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.core.util.PFWConfigUtil;
import org.pfw.framework.dao.security.RoleDao;
import org.pfw.framework.domain.security.Role;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.pfw.framework.util.PFWSecurityUtils;
import org.pfw.framework.xtgl.domain.Dict;
import org.pfw.framework.xtgl.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 功能管理Action.
 */
@Results
	({ 
		@Result(name =CrudActionSupport.RELOAD,location="/util/winopen.ftl" , type="freemarker"),
		@Result(name ="layerForparent",location="/util/winopenlayerForparent.ftl", type = "freemarker"),
		@Result(name="success",location="/aichat/t_ai_znt-list.ftl",type = "freemarker"),
		@Result(name="aiAgent",location="/aichat/t_ai_znt-aiAgent.ftl",type = "freemarker"),
		@Result(name="aiAgentcjdh",location="/aichat/t_ai_znt-aiAgentcjdh.ftl",type = "freemarker"),
		@Result(name="aiAgentone",location="/aichat/t_ai_znt-aiAgentone.ftl",type = "freemarker"),
		@Result(name="aiAgent2",location="/aichat/t_ai_znt-aiAgent2.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_znt-input.ftl", type = "freemarker")
	})
public class TAiZntAction extends CrudActionSupport<TAiZnt> {
	@Autowired
	protected TAiZntService entityService;
	@Autowired
	protected TAiYhdhlsService tAiYhdhlsService;
	@Autowired
	private UserService userService;
	@Autowired
	private TAiYhdhjgService tAiYhdhjgService;
	@Autowired
	private DictService dictService;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private TAiHsglService tAiHsglService;
	@Autowired
	private TAiCsglService tAiCsglService;
	
	private List reqlist;
	private List ssflList;
	
	
	public List getSsflList() {
		return ssflList;
	}
	public void setSsflList(List ssflList) {
		this.ssflList = ssflList;
	}
	public List getReqlist() {
		return reqlist;
	}
	public void setReqlist(List reqlist) {
		this.reqlist = reqlist;
	}

	@Override
	public String delete() throws Exception {
		
		if(checks!=null&&checks.size()>0){
			entityService.delete(checks);
		}
		Struts2Utils.renderText("删除成功");
		return null;
	}

	@Override
	public String input() throws Exception {
		ssflList = dictService.findAllByDM("aichat_zntfl");
		return INPUT;
	}
	

	@Override
	public String list() throws Exception {
	
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("sxh");
			page.setOrder(Page.ASC);
		}
		page = entityService.findPage(page, filters);
		
		return SUCCESS;
	}
	private User usr;
	private String dhlsid;
	private List<TAiYhdhjg> yhdhLsit;
	private List<TAiYhdhls> dhlsList; 
	private String sfdlwd;
	
	
	public String getSfdlwd() {
		return sfdlwd;
	}
	public void setSfdlwd(String sfdlwd) {
		this.sfdlwd = sfdlwd;
	}
	public String getDhlsid() {
		return dhlsid;
	}
	public void setDhlsid(String dhlsid) {
		this.dhlsid = dhlsid;
	}
	public List<TAiYhdhls> getDhlsList() {
		return dhlsList;
	}
	public void setDhlsList(List<TAiYhdhls> dhlsList) {
		this.dhlsList = dhlsList;
	}
	public User getUsr() {
		return usr;
	}
	public void setUsr(User usr) {
		this.usr = usr;
	}
	public List<TAiYhdhjg> getYhdhLsit() {
		return yhdhLsit;
	}
	public void setYhdhLsit(List<TAiYhdhjg> yhdhLsit) {
		this.yhdhLsit = yhdhLsit;
	}
	private String dhlx;
	private Map<String, List<TAiZnt>> zntMap = new LinkedHashMap<>();
	
	public Map<String, List<TAiZnt>> getZntMap() {
		return zntMap;
	}
	public void setZntMap(Map<String, List<TAiZnt>> zntMap) {
		this.zntMap = zntMap;
	}
	public String getDhlx() {
		return dhlx;
	}
	public void setDhlx(String dhlx) {
		this.dhlx = dhlx;
	}
	//整体对话
	public String aiAgent() throws Exception {
		if(StringUtils.isEmpty(id)){
			List<TAiZnt> allZntList = entityService.findbyhql(" from TAiZnt a where  a.sfyx = '1' and a.sfmrznt = '1' order by a.sxh");
			if(allZntList != null && allZntList.size() > 0){
				id = allZntList.get(0).getId();
			}
		}
		entity = (TAiZnt) entityService.getById(id);

		dhlx = StringUtils.defaultIfEmpty(Struts2Utils.getParameter("dhlx"), "1");
		
		usr = userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());
		
		dhlsList = tAiYhdhlsService.findBylsdh(id, usr.getId());
		
		//对话历史
		dhlsid = Struts2Utils.getParameter("dhlsid");
		if(StringUtils.isNotEmpty(dhlsid)){
			yhdhLsit = tAiYhdhjgService.getdhjgList(dhlsid);
		}
		if(dhlx.equals("2")){
			List<TAiZnt> allZntList = entityService.findbyhql(" from TAiZnt a where a.id != ? and a.sfyx = '1'  order by a.sxh",id);
			zntMap.put("全部", allZntList);
			
			List<Dict> ssflList = dictService.findAllByDM("aichat_zntfl");
			for (Dict dict : ssflList) {
				List<TAiZnt> zntList = entityService.findbyhql(" from TAiZnt a where a.id != ? and a.sfyx = '1' and a.ssfl.id = ?  order by a.sxh",id,dict.getId());
				zntMap.put(dict.getName(), zntList);
			}
			return "aiAgentcjdh";
		}else{
			//推荐的智能体
			zntList = entityService.findbyhql(" from TAiZnt a where a.id != ? and a.sfyx = '1' and a.sftj='1' order by a.sxh",id);
		}
		
		return "aiAgent";
	}
	//单个对话
	public String aiAgentone() throws Exception {
		prepareModel();
		entity = (TAiZnt) entityService.getById(id);
		
		usr = userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());
		
		Boolean iffw = false;
		
		if(entity.getRoleSet() != null && entity.getRoleSet().size() > 0){
			for (Role role : entity.getRoleSet()) {
				if(usr.getRoleIds().contains(role.getId())){
					iffw = true;
					break;
				}
			}
		}else{
			iffw = true;
		}
		if(!iffw){
			Struts2Utils.renderText("暂无权限访问！");
			return null;
		}
		
		dhlsList = tAiYhdhlsService.findBylsdh(id, usr.getId());
		
		//对话历史
		dhlsid = Struts2Utils.getParameter("dhlsid");
		if(StringUtils.isNotEmpty(dhlsid)){
			yhdhLsit = tAiYhdhjgService.getdhjgList(dhlsid);
		}
		return "aiAgentone";
	}
	public String aiAgent2() throws Exception {
		prepareModel();
		usr = userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());
		
		dhlsList = tAiYhdhlsService.findBylsdh(id, usr.getId());
		//对话历史
		dhlsid = Struts2Utils.getParameter("dhlsid");
		if(StringUtils.isNotEmpty(dhlsid)){
			
			yhdhLsit = tAiYhdhjgService.getdhjgList(dhlsid);
			
			
		}
		return "aiAgent2";
	}
	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiZnt) entityService.getById(id);
		} else {
			entity = new TAiZnt();
		}
		
	}
	private List<String> sszsk;


	private List<String> ssjs;
	
	public List<String> getSsjs() {
		return ssjs;
	}
	public void setSsjs(List<String> ssjs) {
		this.ssjs = ssjs;
	}
	public List<String> getSszsk() {
		return sszsk;
	}
	public void setSszsk(List<String> sszsk) {
		this.sszsk = sszsk;
	}
	@Override
	public String save() throws Exception {
		//保存角色
		Set<Role> roleSet = new HashSet<Role>();
		if(ssjs != null && ssjs.size() > 0){
			for (String roleid : ssjs) {
				Role role = new Role();
				role.setId(roleid);
				roleSet.add(role);
			}
			entity.setRoleSet(roleSet);
		}else{
			entity.setRoleSet(null);
		}
		
		//保存知识库
		Set<TAiZsk> zskSet = new HashSet<TAiZsk>();
		if(sszsk != null && sszsk.size() > 0){
			for (String roleid : sszsk) {
				TAiZsk zsk = new TAiZsk();
				zsk.setId(roleid);
				zskSet.add(zsk);
			}
			entity.setZskSet(zskSet);
		}else{
			entity.setZskSet(null);
		}
		
		if (!StringUtils.isEmpty(id))
		{
			entityService.update(entity);
		}else{
			entityService.save(entity);
		}
		Struts2Utils.setPromptInfoToReq("保存成功!!");
		
		return RELOAD;
	}
	private List<TAiZnt> zntList;
	public List<TAiZnt> getZntList() {
		return zntList;
	}
	public void setZntList(List<TAiZnt> zntList) {
		this.zntList = zntList;
	}
	public void aichat() throws Exception {
		prepareModel();
		User user = userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());
		
		String prompt = Struts2Utils.getParameter("prompt");
		
		Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
		String ragChat = (String) poperties.get("milvus.ragChat"); 

		
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		//用户问题
		tmpMap.put("prompt", prompt);
		//使用知识库
		if(entity.getZskSet() != null && entity.getZskSet().size() > 0){
			String zskdm = "";
			for (TAiZsk zsk : entity.getZskSet()) {
				zskdm += zsk.getZskdm() + ",";
			}
			zskdm = zskdm.substring(0,zskdm.length() - 1);
			tmpMap.put("partitionNames", zskdm);
		}
		//ai回答最大tokens
		tmpMap.put("maxTokens", entity.getAimaxTokens());
		tmpMap.put("source", entity.getSource());
		tmpMap.put("promptWords", entity.getPromptWords() );
		String userinfo = Struts2Utils.beanToJson(user, "loginName","name");
		userinfo = userinfo.replace("loginName", "登录账号").replace("name", "姓名");
		tmpMap.put("userinfo", userinfo);
		//函数信息json
		String hsxxjson = tAiHsglService.getFunJson(user.getId(),id,user.getRoleIds());
		tmpMap.put("funCallJson", hsxxjson);
		//回调url
		String callbackApi = (String) poperties.get("milvus.callbackApi"); 
		tmpMap.put("callbackApi", callbackApi);
		
		//保存用户提问历史
		String dhlsid = Struts2Utils.getParameter("dhlsid");
		
		
		//查询一下对话历史进行保存
		String dhlsjson = tAiYhdhjgService.getTopFiveLsit(dhlsid,entity.getDldhs());
		tmpMap.put("msgJson", dhlsjson);
		//保存用户提问
		String res = tAiYhdhjgService.insertyhtw(id, dhlsid, prompt, PFWSecurityUtils.getCurrentUserName());
		JSONObject tmpObject = JSONUtil.parseObj(res);
		String jgid = tmpObject.getStr("resultinfo");
		
		//机器人回答保存
		tAiYhdhjgService.doPost(ragChat,jgid, tmpMap, id, dhlsid, PFWSecurityUtils.getCurrentUserName());
		
		
//		ApacheSSEClient.doPost(url, tmpMap);
	}
	//函数回调api
	public void callbackApi() {
		//函数名称
		String name = Struts2Utils.getParameter("name");
		Map<String, String> resMap = new HashMap<>();
		resMap.put("code", "500");
		resMap.put("resultInfo",null);
		//		{code:200,resultinfo:"xxx"}
		try {
			if(StringUtils.isNotEmpty(name)){
				TAiHsgl hsgl = tAiHsglService.getHsxxByName(name);
				hsgl.setCount(hsgl.getCount() + 1);
				tAiHsglService.update(hsgl);
				
				List<TAiCsgl> csglList = tAiCsglService.findbyhql(" from TAiCsgl a where a.sshs.id = ? ",hsgl.getId());
				Map<String, String> tmpMap = new HashMap<>();
				for (TAiCsgl tAiCsgl : csglList) {
					String filedName = Struts2Utils.getParameter(tAiCsgl.getName());
					if(StringUtils.isNotEmpty(filedName)){
						tmpMap.put(tAiCsgl.getName(), Struts2Utils.getParameter(tAiCsgl.getName()));
					}
				}
				
				Map<String, String> headerMap = new HashMap<>();
				headerMap.put("bkappid", "tqportal");
				headerMap.put("bksecret", "lV5W1nxSBdUweKI1hKReWOdehXb1ONsWzTn9kYi8vOV");
				
				//转发请求
				String res = null;
				if(hsgl.getApiurl().contains("https")){
					res = HttpClientForTls12.httpsPost(hsgl.getApiurl(),headerMap,tmpMap);
				}else{
					res = HttpClientForTls12.httpPost(hsgl.getApiurl(), headerMap, tmpMap);
				}
				if(StringUtils.isNotEmpty(res)){
					JSONObject jsonObject = JSONUtil.parseObj(res);
					String resultinfo = jsonObject.getStr("data");
					String message = jsonObject.getStr("message");
					String resstr  = getRes(tmpMap, hsgl.getCallbkmb(), resultinfo);
					System.out.println(resstr);
					if(message.equals("success")){
						resMap.put("code", "200");
						resMap.put("resultInfo",resstr);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(ObjectToJosn.mapToJosn(resMap));
		Struts2Utils.renderText(ObjectToJosn.mapToJosn(resMap));
	}
	
	public String getRes(Map<String, String> tmpMap , String monbk,String data) {
		if(StringUtils.isNotEmpty(data)){
			monbk = monbk.replace("{{data}}", data);
		}
        for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            if(StringUtils.isNotEmpty(entry.getValue())){
            	monbk = monbk.replace(placeholder, entry.getValue());
            }
        }
        return monbk;
	}
	
 
	public void xgFiled()  {

		String sfyx = Struts2Utils.getParameter("sfyx");
		String entid = Struts2Utils.getParameter("entid");
		
		String msg = "设置成功！";
		if(StringUtils.isNotEmpty(entid) && StringUtils.isNotEmpty(sfyx)){
			try { 
				entity = entityService.getById(entid);
				entity.setSfyx(sfyx);
				entityService.update(entity);
			} catch (Exception e) {
	            e.printStackTrace();
	            msg = "设置失败，系统错误！";
	        }
		}else{
			msg = "设置失败，参数错误！";
		}
		Struts2Utils.renderText(msg);
	}

	public void getrolejson() throws Exception {
		
		String kword = Struts2Utils.getParameter("kword");
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		if(StringUtils.isNotEmpty(kword)){
			filters.add(new PropertyFilter("LIKES_name",kword));
		}
		List<Role> retlst = roleDao.find(filters); 
		
		String retstr = "";
		if(retlst != null && retlst.size() > 0){
			retstr = Struts2Utils.listToJson(retlst, "id","name");
		}
		
		Struts2Utils.renderText(retstr);
	}
	/**
	 * 用户对话历史
	 * **/
	public void insertYhdhls() {
		// 报文格式
		String zntid = Struts2Utils.getParameter("zntid");
		String biaot = Struts2Utils.getParameter("biaot");
		String loginName = Struts2Utils.getParameter("loginName");
		String res = tAiYhdhjgService.insertYhdhls(zntid, biaot, loginName);
		Struts2Utils.renderJson(res);
	}
}
