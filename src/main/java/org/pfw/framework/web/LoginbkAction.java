package org.pfw.framework.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.pfw.framework.core.log.Log;
import org.pfw.framework.dao.security.UserDao;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.pfw.framework.util.PFWSecurityUtils;
import org.pfw.framework.utils.PFWEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.WebAuthenticationDetails;

import com.opensymphony.xwork2.ActionSupport;

@Results
(
		{ 
			//@Result(name = ActionSupport.LOGIN, location = "loginbk.ftl", type = "freemarker"),
			@Result(name = ActionSupport.LOGIN, location = "/login.jsp", type = "dispatcher"),
			@Result(name = ActionSupport.SUCCESS, location = "/index.jsp", type = "redirect") 
		}
)
public class LoginbkAction extends ActionSupport
{
	private String j_username;
	private String j_password;
	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public String getJ_password() {
		return j_password;
	}

	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}

	@Autowired
	private UserDao usDao;
	@Autowired
	private UserService usservice;
	@Autowired
    @Qualifier("authenticationManager")//编辑软件会提示错误
    protected AuthenticationManager authenticationManager;
	
	public String execute()
	{
		User usrtmp = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dqsj = sdf.format(new Date());
		String ipdz = PFWSecurityUtils.getIpAddr();
		
		if(StringUtils.isNotEmpty(j_username) && StringUtils.isNotEmpty(j_password))
		{
			Criterion crit = Restrictions.eq("loginName", j_username);
			Criterion crit1 = Restrictions.eq("password", PFWEncrypt.getMD5(j_password));
			usrtmp = usDao.findUnique(crit,crit1);
			
		}else
		{
			return ActionSupport.LOGIN;
		}
		
		if(usrtmp == null)
		{
			//登录失败处理
			Log.log().info("用户名:" + j_username +"  于:" + dqsj + "" +"  尝试登录失败,  IP地址为：" + ipdz);
			String tsxx = "用户名、密码错误！";
			
			//------------应用二级等保，记录登录失败次，5分钟之内尝试超过5次，则将帐户锁定
			Criterion crit = Restrictions.eq("loginName", j_username);
			User uut = null;
			uut = usDao.findUnique(crit);
			if(uut == null)
			{
				
			}else
			{
				if(!uut.isAccountNonLocked())
				{
					tsxx = "帐户已被锁定，请联系管理员";
				}else
				{
					String dlsbcs = uut.getDlsbcs();//已登录失败次数
					if(StringUtils.isEmpty(dlsbcs))
						dlsbcs = "0";
					String dlsbsj = uut.getDlsbsj();//登录失败时间
					
					if(islock(dqsj,dlsbsj,dlsbcs))
					{
						//如果锁定了
						uut.setAccountNonLocked(false);
						uut.setDlsbcs(null);
						uut.setDlsbsj(null);
						usservice.update(uut);
						
						tsxx = "5分钟之内，尝试登录失败次数超过5次，帐户被锁定，请联系管理员！";
					}else
					{
						//登录失败，但没有锁定
						int sbcs = Integer.parseInt(dlsbcs) + 1;
						if(StringUtils.isEmpty(dlsbsj))
							uut.setDlsbsj(dqsj);
						{
							try{
								long diff = sdf.parse(dqsj).getTime() - sdf.parse(dlsbsj).getTime();
								diff = diff / (1000 * 60);
								System.out.println("间隔分钟：" + diff);
								if(diff > 5)
								{
									//大于5分钟的之后尝试的，则重新计算
									uut.setDlsbsj(dqsj);
									sbcs = 1;
								}
							}catch(Exception e)
							{
								
							}
							
						}
						uut.setDlsbcs(String.valueOf(sbcs));
						usservice.update(uut);
					}
				}
				
			}
			//------------应用二级等保，记录登录失败次，5分钟之内尝试超过5次，则将帐户锁定
			
			Struts2Utils.setPromptInfoToReq(tsxx);
			
			return ActionSupport.LOGIN;
			
		}else
		{
			if(!usrtmp.isAccountNonLocked())
			{
				Struts2Utils.setPromptInfoToReq("帐户不可用或被锁定,请联系管理员");
				return ActionSupport.LOGIN;
			}else
			{
				
				Log.log().info("用户名:" + j_username +"  于:" + dqsj + "" +"  登录成功,  IP地址为：" + ipdz);
				usrtmp.setDlsbcs(null);
				usrtmp.setDlsbsj(null);
				usservice.update(usrtmp);
				
				//------------------------处理自动模拟登录------------------------//
				
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						j_username, j_password);
				try 
				{
					System.out.println("no0session==>"+Struts2Utils.getRequest().getSession().getId());
					token.setDetails(new WebAuthenticationDetails(Struts2Utils.getRequest()));
					Authentication authenticatedUser = authenticationManager.authenticate(token);
					SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
					
					Struts2Utils.getRequest().getSession().setAttribute("SPRING_SECURITY_CONTEXT",SecurityContextHolder.getContext());
					
				} catch (AuthenticationException e) 
				{
					System.out.println("Authentication failed: " + e.getMessage());
					//Struts2Utils.getRequest().setAttribute("authFailedstr", "帐户异常：不可用或被锁定，请联系管理员");
					Struts2Utils.setPromptInfoToReq("帐户不可用或被锁定,请联系管理员");
					return ActionSupport.LOGIN;
				}
				
				//------------------------处理自动模拟登录------------------------//
				
				System.out.println("end login....");
				
				return ActionSupport.SUCCESS;
			}
		}
	}
	
	/**
	 * 
	 * @param dqsj 当前时间
	 * @param lgsj 登录失败时间
	 * @param sbcs 登录失败次数
	 * @return
	 */
	public boolean islock(String dqsj,String lgsj,String sbcs)
	{
		boolean retb = false;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int sbcsint = 0;
		if(StringUtils.isNotEmpty(sbcs))
			sbcsint = Integer.parseInt(sbcs);
		
		if(StringUtils.isEmpty(lgsj))
		{
			 //如果为空，说明是第一次登录直接返回false
		}else
		{
			try
			{
				long diff = sdf.parse(dqsj).getTime() - sdf.parse(lgsj).getTime();
				diff = diff / (1000 * 60);
				System.out.println("间隔分钟：" + diff);
				if(diff > 5)
				{
					//大于5分钟的
					
				}else{
					//5分钟之内的话,且尝试失败次数大于5次，则锁定
					if( (sbcsint+1) > 5)
					{
						retb = true;
					}
				}
			}catch(Exception e)
			{
				
			}
			
		}
		return retb;
	}
	
	public void logout() throws Exception
	{
		Struts2Utils.getRequest().getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		Struts2Utils.getResponse().sendRedirect(Struts2Utils.getRequest().getContextPath());
	}
}
