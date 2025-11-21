package org.pfw.framework.aichat.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.pfw.framework.aichat.domain.TAiZsk;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiZskService {

	TAiZsk getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiZsk> find(List<PropertyFilter> filters);
	
	List<TAiZsk> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiZsk entity);
	
	void update(TAiZsk entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiZsk> findAll();
	
	List<TAiZsk> findbyhql(String hql,Object...val);
	
	List<TAiZsk> findBy(String propertyName, Object value, MatchType matchType);
}
