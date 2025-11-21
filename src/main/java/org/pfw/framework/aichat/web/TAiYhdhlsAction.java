package org.pfw.framework.aichat.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.aichat.domain.TAiYhdhls;
import org.pfw.framework.aichat.service.TAiYhdhjgService;
import org.pfw.framework.aichat.service.TAiYhdhlsService;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.pfw.framework.util.PFWSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能管理Action.
 */
@Results
	({ 
		@Result(name =CrudActionSupport.RELOAD,location="/util/winopen.ftl" , type="freemarker"),
		@Result(name ="layerForparent",location="/util/winopenlayerForparent.ftl", type = "freemarker"),
		@Result(name="success",location="/aichat/t_ai_yhdhls-list.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_yhdhls-input.ftl", type = "freemarker")
	})
public class TAiYhdhlsAction extends CrudActionSupport<TAiYhdhls> {
	@Autowired
	protected TAiYhdhlsService entityService;
	@Autowired
	protected TAiYhdhjgService tAiYhdhjgService;
	@Autowired
	private UserService userService;
	
	private List reqlist;
	public List getReqlist() {
		return reqlist;
	}
	public void setReqlist(List reqlist) {
		this.reqlist = reqlist;
	}

	@Override
	public String delete() throws Exception {
		
		if(checks!=null&&checks.size()>0){
			List<PropertyFilter> filters = new ArrayList<>();
			filters.add(new PropertyFilter("INC_ssdhls.id",checks));
			List<TAiYhdhjg> jgList = tAiYhdhjgService.find(filters);
			if(jgList != null && jgList.size() > 0 ){
				for (TAiYhdhjg tAiYhdhjg : jgList) {
					tAiYhdhjgService.deleteById(tAiYhdhjg.getId());
				}
			}
		
			
			entityService.delete(checks);
		}
		Struts2Utils.renderText("删除成功");
		return null;
	}
	
	public void delAll() {
		String zntid = Struts2Utils.getParameter("zntid");
		if(StringUtils.isNotEmpty(zntid)){
			try {

				User usr = userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());
				entityService.delByzntid(zntid, usr.getId());
			} catch (Exception e) {
				// TODO: handle exception
				Struts2Utils.renderText("系统异常");
			}
			
		}else{
			Struts2Utils.renderText("参数错误");
		}
		Struts2Utils.renderText("操作成功");
	}
	
	
	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String list() throws Exception {
	
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = entityService.findPage(page, filters);
		
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiYhdhls) entityService.getById(id);
		} else {
			entity = new TAiYhdhls();
		}
		
	}

	@Override
	public String save() throws Exception {
		if (!StringUtils.isEmpty(id))
		{
			entityService.update(entity);
		}else{
			entityService.save(entity);
		}
		Struts2Utils.setPromptInfoToReq("保存成功!!");
		
		return RELOAD;
	}
}
