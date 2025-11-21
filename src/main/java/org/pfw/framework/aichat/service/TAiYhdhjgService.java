package org.pfw.framework.aichat.service;

import java.io.IOException;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;



public interface TAiYhdhjgService {

	TAiYhdhjg getById(String id);
	
	Page findPage(Page page, List<PropertyFilter> filters);
	
	List<TAiYhdhjg> find(List<PropertyFilter> filters);
	
	List<TAiYhdhjg> find(List<PropertyFilter> filters,LinkedHashMap orderbyMap);
	
	void save(TAiYhdhjg entity);
	
	void update(TAiYhdhjg entity);

	void deleteById(String id);
	
	void delete(List<String> checks);
	
	void deleteHql(String hql,Object...val);

	List<TAiYhdhjg> findAll();
	
	List<TAiYhdhjg> findbyhql(String hql,Object...val);
	
	List<TAiYhdhjg> findBy(String propertyName, Object value, MatchType matchType);

	List<TAiYhdhjg> getdhjgList(String dhlsid);

	String insertyhtw(String zntid, String dhlsid, String biaot,
			String loginName);

	String insertYhdhls(String zntid, String biaot, String loginName);




	String getTopFiveLsit(String lssjid, Integer dldhs);

	void doPost(String url, String jgid, Map<String, Object> tmpMap,
			String zntid, String dhlsid, String loginName)
			throws ServletException, IOException;

	String insertbotdh(String jgid, String zntid, String dhlsid, String skgc,
			String zzda, String loginName);

	void doPostTextToSql(String url, String jgid, Map<String, Object> tmpMap,
			String zntid, String dhlsid, String loginName)
			throws ServletException, IOException;
}
