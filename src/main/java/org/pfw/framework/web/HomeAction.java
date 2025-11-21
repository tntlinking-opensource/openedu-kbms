package org.pfw.framework.web;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Query;
import org.pfw.framework.core.util.PFWConfigUtil;
import org.pfw.framework.domain.security.Resource;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.flow.service.TFlowSjdqztService;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.ResourceService;
import org.pfw.framework.service.security.UserService;
import org.pfw.framework.util.PFWSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;



@Results( { 
	@Result(name =ActionSupport.LOGIN,location="login.action", type = "redirect"),
	@Result(name ="qzmmxg",location="/security/user!qzmodifypassword.action", type = "redirect")
})
public class HomeAction extends ActionSupport{
	private static final long serialVersionUID = -2628011648052951372L;
	private List menus;
	private String parentId;
	private List lctxs;
	private String zzxxmc;
	private List urlmenus;
	private Resource curResource;
	private User usr;
	// 用户的照片
	public String userPhoto;
	
	@Autowired
	private UserService usrService;
	@Autowired
	private TFlowSjdqztService tfdqztservice;
	
	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public User getUsr() {
		return usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}
	public Resource getCurResource() {
		return curResource;
	}

	public void setCurResource(Resource curResource) {
		this.curResource = curResource;
	}

	public List getUrlmenus() {
		return urlmenus;
	}

	public void setUrlmenus(List urlmenus) {
		this.urlmenus = urlmenus;
	}

	public List getMenus() {
		return menus;
	}

	public List getLctxs() {
		return lctxs;
	}

	public void setLctxs(List lctxs) {
		this.lctxs = lctxs;
	}
	
	public String getZzxxmc() {
		return zzxxmc;
	}

	public void setZzxxmc(String zzxxmc) {
		this.zzxxmc = zzxxmc;
	}




	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 我的工作台 - 多标签
	 * @return
	 */
	public String index3() {
		
		Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
		zzxxmc = (String) poperties.get("school.name"); // 得到properties文件中name为school.name的值
		
		usr = usrService.getUserByLoginName(PFWSecurityUtils.getCurrentUserName());
		String rootUrl = Struts2Utils.getSession().getServletContext()
				.getRealPath("/");
		String photoUrl = rootUrl + "mainface/image/userphoto/" + PFWSecurityUtils.getCurrentUserName()
				+ ".jpg";
		File fphoto = new File(photoUrl);
		if (fphoto.exists())
			userPhoto = PFWSecurityUtils.getCurrentUserName() + ".jpg";
		
		
		if(Struts2Utils.getSession().getAttribute("menuSess") != null)
		{
			menus = (List)Struts2Utils.getSession().getAttribute("menuSess");
		}else{
			menus=resourceService.getMenus(null);
			Struts2Utils.getSession().setAttribute("menuSess", menus);
		}
		
		
		//直接安层次显示菜单树，不考虑urlmenu情况
		//urlmenus = resourceService.getAllUrlMenus();
		
		if(usr != null && StringUtils.isNotEmpty(usr.getSfqzmmxg()) && usr.getSfqzmmxg().equals("1"))
		{
			return "qzmmxg";
		}else{
			return "index3";
		}
		
		
	}
	
	public String main()
	{

		return "main";
	}
	
	/**
	 * 导入主界面
	 * @return
	 */
	public String impmain()
	{
		
		return "impmain";
	}
	
	/**
	 * 加载特定模块下的所有子模块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String menus() {
		menus = resourceService.getMenus(parentId);
		return "menus";
	}

	private Date date;
	public Date getDate() {
		return date;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
