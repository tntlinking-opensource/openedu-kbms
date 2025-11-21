package org.pfw.framework.aichat.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.pfw.framework.aichat.dao.TAiQpjgDao;
import org.pfw.framework.aichat.domain.TAiWdxx;
import org.pfw.framework.aichat.service.TAiQpjgService;
import org.pfw.framework.aichat.service.TAiWdxxService;
import org.pfw.framework.aichat.service.TAiZskService;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.util.PFWSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能管理Action.
 */
@Results
	({ 
		@Result(name =CrudActionSupport.RELOAD,location="/util/winopen.ftl" , type="freemarker"),
		@Result(name ="layerForparent",location="/util/winopenlayerForparent.ftl", type = "freemarker"),
		@Result(name="success",location="/aichat/t_ai_wdxx-list.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_wdxx-input.ftl", type = "freemarker")
	})
public class TAiWdxxAction extends CrudActionSupport<TAiWdxx> {
	@Autowired
	protected TAiWdxxService entityService;
	@Autowired
	private TAiZskService tAiZskService;
	@Autowired
	private TAiQpjgService tAiQpjgService;
	@Autowired
	private TAiQpjgDao tAiQpjgDao;

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
		reqlist3 = tAiZskService.findAll();
		return INPUT;
	}

	@Override
	public String list() throws Exception {
		reqlist3 = tAiZskService.findAll();
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = entityService.findPage(page, filters);
		for (TAiWdxx entity : (List<TAiWdxx>)page.getResult()) {
			long sl = tAiQpjgDao.countHqlResult(" from TAiQpjg a where a.sswdxx.id = ? ",entity.getId());
			entity.setQpsl(String.valueOf(sl));
		}
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiWdxx) entityService.getById(id);
		} else {
			entity = new TAiWdxx();
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
	//批量保存
	public String batchsave() {
		String sszskid = Struts2Utils.getParameter("sszskid");
		String laiy = Struts2Utils.getParameter("laiy");
		String fjwjmName = Struts2Utils.getParameter("fjwjmName");
		String fjwjmPath = Struts2Utils.getParameter("fjwjmPath");
		String beiz = Struts2Utils.getParameter("beiz");
		String mc = Struts2Utils.getParameter("mc");
		String wjnr = Struts2Utils.getParameter("wjnr");
		String sbfs = Struts2Utils.getParameter("sbfs");
		Boolean sfcg = entityService.batchsave(sszskid, laiy,sbfs, fjwjmName, fjwjmPath, beiz, mc, wjnr,PFWSecurityUtils.getCurrentUserName(),null);
		if(!sfcg){
			Struts2Utils.setPromptInfoToReq("保存失败!!");
			return RELOAD;
		}
		Struts2Utils.setPromptInfoToReq("保存成功!!");
		return RELOAD;
	}
	public void getwjnrbyid() {
		entity = entityService.getById(id);
		Struts2Utils.renderText(entity.getWjnr());
	}
}
