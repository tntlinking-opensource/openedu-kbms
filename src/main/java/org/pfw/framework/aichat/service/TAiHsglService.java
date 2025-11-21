package org.pfw.framework.aichat.service;

import java.util.List;
import java.util.LinkedHashMap;

import org.pfw.framework.aichat.domain.TAiHsgl;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiHsglService {

	TAiHsgl getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiHsgl> find(List<PropertyFilter> filters);
	
	List<TAiHsgl> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiHsgl entity);
	
	void update(TAiHsgl entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiHsgl> findAll();
	
	List<TAiHsgl> findbyhql(String hql,Object...val);
	
	List<TAiHsgl> findBy(String propertyName, Object value, MatchType matchType);


	TAiHsgl getHsxxByName(String name);

	String getFunJson(String uid, String zntid, List<String> roleids)
			throws Exception;
}
