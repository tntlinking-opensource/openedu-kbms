package org.pfw.framework.aichat.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pfw.framework.aichat.dao.TAiHsglDao;
import org.pfw.framework.aichat.domain.TAiCsgl;
import org.pfw.framework.aichat.domain.TAiHsgl;
import org.pfw.framework.aichat.service.TAiCsglService;
import org.pfw.framework.aichat.service.TAiHsglService;
import org.pfw.framework.domain.security.Role;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TAiHsglServiceImpl implements TAiHsglService {

	@Autowired
	private TAiHsglDao entityDao;
	@Autowired
	private TAiCsglService tAiCsglService;
	@Autowired
	private UserService userService;
	
	
	@Transactional(readOnly=true)
	public TAiHsgl getById(String id) {
		return entityDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiHsgl> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiHsgl> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiHsgl entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiHsgl entity)
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
	public List<TAiHsgl> findAll()
	{
		return entityDao.find(" from TAiHsgl r ");
	}
	
	@Override
	public List<TAiHsgl> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiHsgl> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}
	@Override
	public TAiHsgl getHsxxByName (String name) {
		return entityDao.findUniqueBy("name", name);
	}
	
	//根据智能体id的知识库匹配  自定义函数
	@Override
	public String getFunJson(String uid,String zntid,List<String> roleids) throws Exception {
		String res = null;
		
		
		if(StringUtils.isNotEmpty(zntid)){
			String hql = " select distinct a from TAiHsgl a join a.zskSet zsk  where a.sfyx = '1' and zsk.id in ( ";
			hql += " select distinct  zntzsk.id from TAiZnt znt join znt.zskSet zntzsk where  znt.id = '"+zntid+"'";
			hql += ")";
			List<TAiHsgl> hsxxList = this.findbyhql(hql);
			
			List<TAiHsgl> raealHsxxList = new ArrayList<TAiHsgl>();
			
			if(hsxxList != null && hsxxList.size() > 0){
				for (TAiHsgl tAiHsgl : hsxxList) {
					//判断是否有权限调用该函数
					Boolean ifHasPrim = false;
					
					if(tAiHsgl.getRoleSet() != null && tAiHsgl.getRoleSet().size() > 0){
						for (Role role : tAiHsgl.getRoleSet()) {
							if(roleids.contains(role.getId())){
								ifHasPrim = true;
								break;
							}
						}
					}else{
						ifHasPrim = true;
					}
					//如果有权限
					if(ifHasPrim){
						List<TAiCsgl> csglList = tAiCsglService.findbyhql(" from TAiCsgl a where a.sshs.id = ? ",tAiHsgl.getId());
						tAiHsgl.setFunCallParList(csglList);
						raealHsxxList.add(tAiHsgl);
					}
				}
				
				res = Struts2Utils.listToJson(raealHsxxList, "id","name","description","apiurl","funCallParList[id,name,type,description,required]");
			}
		}
		System.out.println("使用工具:===========>" + res);
		return res;
	}
	
	
}
