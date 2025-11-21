package org.pfw.framework.aichat.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.pfw.framework.aichat.domain.TAiCsgl;
import org.pfw.framework.aichat.domain.TAiHsgl;
import org.pfw.framework.aichat.domain.TAiZsk;
import org.pfw.framework.aichat.service.TAiCsglService;
import org.pfw.framework.aichat.service.TAiHsglService;
import org.pfw.framework.aichat.service.TAiZskService;
import org.pfw.framework.base.web.CrudActionSupport;
import org.pfw.framework.domain.security.Role;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.hibernate.HibernateWebUtils;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.pfw.framework.util.PFWSecurityUtils;
import org.pfw.framework.xtgl.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能管理Action.
 */
@Results
	({ 
		@Result(name =CrudActionSupport.RELOAD,location="/util/winopen.ftl" , type="freemarker"),
		@Result(name ="layerForparent",location="/util/winopenlayerForparent.ftl", type = "freemarker"),
		@Result(name="success",location="/aichat/t_ai_hsgl-list.ftl",type = "freemarker"),
		@Result(name ="input",location="/aichat/t_ai_hsgl-input.ftl", type = "freemarker")
	})
public class TAiHsglAction extends CrudActionSupport<TAiHsgl> {
	@Autowired
	protected TAiHsglService entityService;
	@Autowired
	private TAiCsglService tAiCsglService;
	@Autowired
	private UserService userService;
	@Autowired
	private DictService dictService;
	@Autowired
	private TAiZskService tAiZskService;
	
	private List reqlist;
	private List ssflList;
	private List zskList;
	
	
	
	public List getZskList() {
		return zskList;
	}
	public void setZskList(List zskList) {
		this.zskList = zskList;
	}
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
	private List<TAiCsgl> pzList;
	public List<TAiCsgl> getPzList() {
		return pzList;
	}
	public void setPzList(List<TAiCsgl> pzList) {
		this.pzList = pzList;
	}
	@Override
	public String input() throws Exception {
//		ssflList = dictService.findAllByDM("aichat_hsfl");
		
		if(StringUtils.isNotEmpty(id)){
			pzList = tAiCsglService.findbyhql(" from TAiCsgl where sshs.id = ? ",entity.getId());
		}
		return INPUT;
	}

	@Override
	public String list() throws Exception {
//		ssflList = dictService.findAllByDM("aichat_hsfl");
		zskList = tAiZskService.findAll();
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("czsj");
			page.setOrder(Page.ASC);
		}
		page = entityService.findPage(page, filters);
		
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (!StringUtils.isEmpty(id)) {
			entity = (TAiHsgl) entityService.getById(id);
		} else {
			entity = new TAiHsgl();
		}
		
	}
	private List<TAiCsgl> cspz;
	private List<String> sszsk;
	
	
	public List<String> getSszsk() {
		return sszsk;
	}
	public void setSszsk(List<String> sszsk) {
		this.sszsk = sszsk;
	}
	public List<TAiCsgl> getCspz() {
		return cspz;
	}
	public void setCspz(List<TAiCsgl> cspz) {
		this.cspz = cspz;
	}

	private List<String> ssjs;
	
	public List<String> getSsjs() {
		return ssjs;
	}
	public void setSsjs(List<String> ssjs) {
		this.ssjs = ssjs;
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
		
		
		
		User usr = userService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());

		String nowdata  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		entity.setCzr(usr);
		entity.setCzsj(nowdata);
		
		if (!StringUtils.isEmpty(id))
		{
			entityService.update(entity);
		}else{
			entityService.save(entity);
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
		
		//先删除
		List<TAiCsgl>  pzList = tAiCsglService.findbyhql(" from TAiCsgl where sshs.id = ? ",entity.getId());
		for (TAiCsgl tjcyjbzpz : pzList) {
			tAiCsglService.deleteById(tjcyjbzpz.getId());
		}
		if(cspz != null && cspz.size() > 0){
			for (TAiCsgl cs : cspz) {
				cs.setSshs(entity);
				tAiCsglService.save(cs);
			}
		}
		
		Struts2Utils.setPromptInfoToReq("保存成功!!");
		
		return RELOAD;
	}
}
