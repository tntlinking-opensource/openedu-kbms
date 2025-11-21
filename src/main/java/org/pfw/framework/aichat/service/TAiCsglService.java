package org.pfw.framework.aichat.service;

import java.util.List;
import java.util.LinkedHashMap;

import org.pfw.framework.aichat.domain.TAiCsgl;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiCsglService {

	TAiCsgl getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiCsgl> find(List<PropertyFilter> filters);
	
	List<TAiCsgl> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiCsgl entity);
	
	void update(TAiCsgl entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiCsgl> findAll();
	
	List<TAiCsgl> findbyhql(String hql,Object...val);
	
	List<TAiCsgl> findBy(String propertyName, Object value, MatchType matchType);
}
