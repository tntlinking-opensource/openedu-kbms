package org.pfw.framework.aichat.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.pfw.framework.aichat.domain.TAiQpjg;
import org.pfw.framework.aichat.service.TAiQpjgService;
import org.pfw.framework.aichat.service.TAiZskService;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能管理Action.
 */
@Results
	({ 
		@Result(name =CrudActionSupport.RELOAD,location="/util/winopen.ftl" , type="freemarker"),
		@Result(name ="layerForparent",location="/util/winopenlayerForparent.ftl", type = "freemarker"),
		@Result(name="success",location="/aichat/t_ai_qpjg-list.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_qpjg-input.ftl", type = "freemarker")
	})
public class TAiQpjgAction extends CrudActionSupport<TAiQpjg> {
	@Autowired
	protected TAiQpjgService entityService;
	@Autowired
	private TAiZskService tAiZskService;
	
	private List reqlist;
	private List reqlist3;
	
	public List getReqlist3() {
		return reqlist3;
	}
	public void setReqlist3(List reqlist3) {
		this.reqlist3 = reqlist3;
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
		return INPUT;
	}

	@Override
	public String list() throws Exception {
		reqlist3 = tAiZskService.findAll();
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("sswdxx.sszsk.zskmc,sswdxx.mc");
			page.setOrder(Page.ASC + "," + Page.ASC);
		}
		page = entityService.findPage(page, filters);
		
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiQpjg) entityService.getById(id);
		} else {
			entity = new TAiQpjg();
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
	public void getwjnrbyid() {
		entity = entityService.getById(id);
		Struts2Utils.renderText(entity.getQpnr());
	}
}
