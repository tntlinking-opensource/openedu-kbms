package org.pfw.framework.aichat.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.util.PFWSecurityUtils;
import org.pfw.framework.aichat.service.TAiWdxxService;
import org.pfw.framework.aichat.service.TAiYhdhjgService;
import org.pfw.framework.aichat.service.TAiZskService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能管理Action.
 */
@Results
	({ 
		@Result(name =CrudActionSupport.RELOAD,location="/util/winopen.ftl" , type="freemarker"),
		@Result(name ="layerForparent",location="/util/winopenlayerForparent.ftl", type = "freemarker"),
		@Result(name="success",location="/aichat/t_ai_yhdhjg-list.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_yhdhjg-input.ftl", type = "freemarker")
	})
public class TAiYhdhjgAction extends CrudActionSupport<TAiYhdhjg> {
	@Autowired
	protected TAiYhdhjgService entityService;
	@Autowired
	private TAiZskService tAiZskService;
	@Autowired
	protected TAiWdxxService tAiWdxxService;
	
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
	
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("czsj");
			page.setOrder(Page.DESC);
		}
		page = entityService.findPage(page, filters);
		
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiYhdhjg) entityService.getById(id);
		} else {
			entity = new TAiYhdhjg();
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
	//问答训练
	public void wdxl() {
		String ckids = Struts2Utils.getParameter("ckids");
		try {
			if(StringUtils.isNotEmpty(ckids)){
				String hql = " from TAiYhdhjg  a where a.id in ('"+ckids.replace(",", "','")+"') ";
				hql += " and a.sftz = '1' and a.sfxlh = '0' ";
				List<TAiYhdhjg> entList = entityService.findbyhql(hql);
				if(entList != null && entList.size() > 0){
					
					for (TAiYhdhjg entity : entList) {
						Boolean sfcg = tAiWdxxService.batchsave(entity.getSszsk().getId(), "2","1", null, null, null, entity.getXghyhwt(), "问：\n" + entity.getXghyhwt() + "\n答:" + entity.getXghzzda() ,PFWSecurityUtils.getCurrentUserName(),entity.getId());
						if(sfcg){
							entity.setSfxlh("1");
							entityService.update(entity);
						}
					}
					
				}
				Struts2Utils.renderText("操作成功！");
			}else{
				Struts2Utils.renderText("操作失败，参数错误！");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Struts2Utils.renderText("操作失败！");
		}
		
		
	}
	
}
