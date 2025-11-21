package org.pfw.framework.aichat.service;

import java.util.List;
import java.util.LinkedHashMap;

import org.pfw.framework.aichat.domain.TAiZnt;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiZntService {

	TAiZnt getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiZnt> find(List<PropertyFilter> filters);
	
	List<TAiZnt> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiZnt entity);
	
	void update(TAiZnt entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiZnt> findAll();
	
	List<TAiZnt> findbyhql(String hql,Object...val);
	
	List<TAiZnt> findBy(String propertyName, Object value, MatchType matchType);

	TAiZnt getBydm(String dm);
}
