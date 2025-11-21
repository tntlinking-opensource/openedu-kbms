package org.pfw.framework.aichat.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.pfw.framework.aichat.domain.TAiWdxx;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiWdxxService {

	TAiWdxx getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiWdxx> find(List<PropertyFilter> filters);
	
	List<TAiWdxx> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiWdxx entity);
	
	void update(TAiWdxx entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiWdxx> findAll();
	
	List<TAiWdxx> findbyhql(String hql,Object...val);
	
	List<TAiWdxx> findBy(String propertyName, Object value, MatchType matchType);




	Boolean batchsave(String sszskid, String laiy, String sbfs,
			String fjwjmName, String fjwjmPath, String beiz, String mc,
			String wjnr, String loginName, String dhjgid);
}
