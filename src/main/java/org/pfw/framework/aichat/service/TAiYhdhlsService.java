package org.pfw.framework.aichat.service;

import java.util.List;
import java.util.LinkedHashMap;

import org.pfw.framework.aichat.domain.TAiYhdhls;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiYhdhlsService {

	TAiYhdhls getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiYhdhls> find(List<PropertyFilter> filters);
	
	List<TAiYhdhls> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiYhdhls entity);
	
	void update(TAiYhdhls entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiYhdhls> findAll();
	
	List<TAiYhdhls> findbyhql(String hql,Object...val);
	
	List<TAiYhdhls> findBy(String propertyName, Object value, MatchType matchType);
	
	List<TAiYhdhls> findBylsdh(String zntid, String uid);

	void delByzntid(String zntid, String uid);
}
