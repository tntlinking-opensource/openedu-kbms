package org.pfw.framework.aichat.service.impl;

import java.util.List;
import java.util.LinkedHashMap;

import org.pfw.framework.aichat.dao.TAiCsglDao;
import org.pfw.framework.aichat.domain.TAiCsgl;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.aichat.service.TAiCsglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;


@Service
@Transactional
public class TAiCsglServiceImpl implements TAiCsglService {

	@Autowired
	private TAiCsglDao entityDao;

	@Transactional(readOnly=true)
	public TAiCsgl getById(String id) {
		return entityDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiCsgl> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiCsgl> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiCsgl entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiCsgl entity)
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
	public List<TAiCsgl> findAll()
	{
		return entityDao.find(" from TAiCsgl r ");
	}
	
	@Override
	public List<TAiCsgl> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiCsgl> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}
}
