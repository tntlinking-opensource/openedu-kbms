package org.pfw.framework.aichat.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.pfw.framework.aichat.dao.TAiYhdhlsDao;
import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.aichat.domain.TAiYhdhls;
import org.pfw.framework.aichat.domain.TAiZnt;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.aichat.service.TAiYhdhjgService;
import org.pfw.framework.aichat.service.TAiYhdhlsService;
import org.pfw.framework.app.util.AppUtil;
import org.pfw.framework.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;
import org.pfw.framework.modules.web.struts2.Struts2Utils;
import org.pfw.framework.util.PFWSecurityUtils;


@Service
@Transactional
public class TAiYhdhlsServiceImpl implements TAiYhdhlsService {

	@Autowired
	private TAiYhdhlsDao entityDao;
	@Autowired
	private TAiYhdhjgService tAiYhdhjgService;

	@Transactional(readOnly=true)
	public TAiYhdhls getById(String id) {
		return entityDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiYhdhls> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiYhdhls> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiYhdhls entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiYhdhls entity)
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
	public List<TAiYhdhls> findAll()
	{
		return entityDao.find(" from TAiYhdhls r ");
	}
	
	@Override
	public List<TAiYhdhls> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiYhdhls> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}

	@Override
	public List<TAiYhdhls> findBylsdh(String zntid,String uid) {
		// TODO Auto-generated method stub
		String hql = " from TAiYhdhls a where a.ssznt = '"+zntid+"' and a.czr.id = '"+uid+"' order by a.czsj desc"; 
		return entityDao.find(hql);
	}
	@Override
	public void delByzntid(String zntid,String uid) {
		List<TAiYhdhls> dAiYhdhlList = this.findBylsdh(zntid, uid);
		if(dAiYhdhlList != null && dAiYhdhlList.size() > 0){
			for (TAiYhdhls entity : dAiYhdhlList) {
				
				List<TAiYhdhjg> jgList = tAiYhdhjgService.findbyhql(" from TAiYhdhjg a where a.ssdhls.id = ?",entity.getId());
				if(jgList != null && jgList.size() > 0 ){
					for (TAiYhdhjg tAiYhdhjg : jgList) {
						tAiYhdhjgService.deleteById(tAiYhdhjg.getId());
					}
				}
				this.deleteById(entity.getId());
			}
		}
	}
	
}
