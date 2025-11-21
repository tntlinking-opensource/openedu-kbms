package org.pfw.framework.aichat.service.impl;

import java.util.List;
import java.util.LinkedHashMap;

import org.pfw.framework.aichat.dao.TAiZntDao;
import org.pfw.framework.aichat.domain.TAiZnt;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.aichat.service.TAiZntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;


@Service
@Transactional
public class TAiZntServiceImpl implements TAiZntService {

	@Autowired
	private TAiZntDao entityDao;

	@Transactional(readOnly=true)
	public TAiZnt getById(String id) {
		return entityDao.get(id);
	}
	@Override
	public TAiZnt getBydm(String dm) {
		return entityDao.findUniqueBy("zntdm", dm);
	}
	
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiZnt> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiZnt> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiZnt entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiZnt entity)
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
	public List<TAiZnt> findAll()
	{
		return entityDao.find(" from TAiZnt r ");
	}
	
	@Override
	public List<TAiZnt> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiZnt> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}
}
