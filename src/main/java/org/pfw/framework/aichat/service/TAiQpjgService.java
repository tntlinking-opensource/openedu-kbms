package org.pfw.framework.aichat.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.pfw.framework.aichat.domain.TAiQpjg;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiQpjgService {

	TAiQpjg getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiQpjg> find(List<PropertyFilter> filters);
	
	List<TAiQpjg> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiQpjg entity);
	
	void update(TAiQpjg entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiQpjg> findAll();
	
	List<TAiQpjg> findbyhql(String hql,Object...val);
	
	List<TAiQpjg> findBy(String propertyName, Object value, MatchType matchType);
}
