package org.pfw.framework.aichat.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.pfw.framework.aichat.dao.TAiWdxxDao;
import org.pfw.framework.aichat.domain.TAiZsk;
import org.pfw.framework.aichat.service.TAiZskService;
import org.pfw.framework.app.util.HttpRequestUtil2;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.core.util.PFWConfigUtil;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.pfw.framework.util.PFWSecurityUtils;
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
		@Result(name="success",location="/aichat/t_ai_zsk-list.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_zsk-input.ftl", type = "freemarker")
	})
public class TAiZskAction extends CrudActionSupport<TAiZsk> {
	@Autowired
	protected TAiZskService entityService;
	@Autowired
	private UserService userService;
	@Autowired
	private TAiWdxxDao tAiWdxxDao;
	
	private List reqlist;
	public List getReqlist() {
		return reqlist;
	}
	public void setReqlist(List reqlist) {
		this.reqlist = reqlist;
	}

	@Override
	public String delete() throws Exception {
		try {
			if(checks!=null&&checks.size()>0){
				Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
				String url = (String) poperties.get("milvus.deleteDbCollectionPartition"); 
				String res = null;
				for (String id : checks) {
					entity = entityService.getById(id);
					if(url.indexOf("https") > -1){
						res = HttpRequestUtil2.sendPost(url,"partitionName=" + entity.getZskdm());
					}else{
						res = HttpRequestUtil2.sendPost(url,"partitionName=" + entity.getZskdm(),null);
					}
					System.out.println("删除知识库结果====" + res);
				}
				entityService.delete(checks);
			}
			Struts2Utils.renderText("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			Struts2Utils.renderText("删除失败，存在关联文档！");
		}
		return null;
	}
	 
	public void deleteEnt() {
		
	}
	
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	public static void main(String[] args) {
		String num = "123123543";
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
		Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
		String url = (String) poperties.get("milvus.hasPartitionPartition"); 
		
		
		for (TAiZsk entity :(List<TAiZsk>) page.getResult()) {
			String res = null;
			if(url.indexOf("https") > -1){
				res = HttpRequestUtil2.sendPost(url,"partitionName=" + entity.getZskdm());
			}else{
				res = HttpRequestUtil2.sendPost(url,"partitionName=" + entity.getZskdm(),null);
			}
			if(StringUtils.isNotEmpty(res)){
				JSONObject jsonUtil = JSONUtil.parseObj(res);
				String status = jsonUtil.get("status").toString();
				entity.setStatus(status);
			}else{
				entity.setStatus("false");
			}
			//查询文档数量
			long wdsl = tAiWdxxDao.countHqlResult(" from TAiWdxx a where a.sszsk.id = ? ",entity.getId());
			entity.setWdsl(String.valueOf(wdsl));
			//查询字符数量
			List tmpList= tAiWdxxDao.createQuery(" select sum(a.tokens) from TAiQpjg a where a.sswdxx.sszsk.id = ?  ", entity.getId()).list();
			if(tmpList != null && tmpList.size() > 0){
				entity.setZfsl(this.convertToKFormat(String.valueOf(tmpList.get(0))));
			}
		}
		return SUCCESS;
	}
	public  String convertToKFormat(String numStr) {
        try {
            double number = Double.parseDouble(numStr); // 改为double以支持小数
            double kValue = number / 1000;
            return String.format("%,.2fK", kValue); // 修改为保留两位小数
        } catch (NumberFormatException e) {
            return numStr;
        }
    }
	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiZsk) entityService.getById(id);
		} else {
			entity = new TAiZsk();
		}
	}

	@Override
	public String save() throws Exception {
		String nowdata  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		entity.setCzsj(nowdata);
		entity.setCzr(userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName()));
		if (!StringUtils.isEmpty(id))
		{
			entityService.update(entity);
		}else{
			entityService.save(entity);
			
			Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
			String url = (String) poperties.get("milvus.createDbCollectionPartition"); // 得到properties文件中name为school.name的值
			String res = null;
			if(url.indexOf("https") > -1){
				res = HttpRequestUtil2.sendPost(url,"partitionName=" + entity.getZskdm());
			}else{
				res = HttpRequestUtil2.sendPost(url,"partitionName=" + entity.getZskdm(),null);
			}
			System.out.println("创建知识库结果====" + res);
			
		}
		Struts2Utils.setPromptInfoToReq("保存成功!!");

		
		return RELOAD;
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

	public void getzakjson() throws Exception {
		
		String kword = Struts2Utils.getParameter("kword");
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		if(StringUtils.isNotEmpty(kword)){
			filters.add(new PropertyFilter("LIKES_zskmc",kword));
		}
		List<TAiZsk> retlst = entityService.find(filters); 
		
		String retstr = "";
		if(retlst != null && retlst.size() > 0){
			retstr = Struts2Utils.listToJson(retlst, "id","zskmc");
		}
		
		Struts2Utils.renderText(retstr);
	}

}
