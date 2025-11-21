package org.pfw.framework.aichat.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jasig.cas.client.util.HttpClientForTls12;
import org.pfw.framework.aichat.dao.TAiQpjgDao;
import org.pfw.framework.aichat.domain.TAiQpjg;
import org.pfw.framework.aichat.service.TAiQpjgService;
import org.pfw.framework.core.util.PFWConfigUtil;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TAiQpjgServiceImpl implements TAiQpjgService {

	@Autowired
	private TAiQpjgDao entityDao;

	@Transactional(readOnly=true)
	public TAiQpjg getById(String id) {
		return entityDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiQpjg> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiQpjg> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiQpjg entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiQpjg entity)
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
			String ids = "";
			for (String id : checks) {
				TAiQpjg entity = entityDao.get(id);
				ids += entity.getQpid() + ",";
			}
			ids = ids.substring(0,ids.length() -1 );
			Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
			String url = (String) poperties.get("milvus.deleteData"); 
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("ids", ids);
			String res = null;
			if(url.indexOf("https") > -1){
				res = HttpClientForTls12.httpsPost(url, null, contentMap);
			}else{
				res = HttpClientForTls12.httpPost(url, null, contentMap);
			}
			System.out.println("向量数据库删除结果=====" + res);
			
			
			for (String id : checks) {
				entityDao.delete(id);
			}
		}
	}
	
	
	
	@Override
	public List<TAiQpjg> findAll()
	{
		return entityDao.find(" from TAiQpjg r ");
	}
	
	@Override
	public List<TAiQpjg> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiQpjg> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}
}
