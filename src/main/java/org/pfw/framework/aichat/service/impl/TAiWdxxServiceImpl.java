package org.pfw.framework.aichat.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.HttpClientForTls12;
import org.pfw.framework.aichat.dao.TAiQpjgDao;
import org.pfw.framework.aichat.dao.TAiWdxxDao;
import org.pfw.framework.aichat.domain.TAiQpjg;
import org.pfw.framework.aichat.domain.TAiWdxx;
import org.pfw.framework.aichat.domain.TAiYhdhjg;
import org.pfw.framework.aichat.domain.TAiZsk;
import org.pfw.framework.aichat.service.TAiQpjgService;
import org.pfw.framework.aichat.service.TAiWdxxService;
import org.pfw.framework.aichat.service.TAiYhdhjgService;
import org.pfw.framework.aichat.service.TAiZskService;
import org.pfw.framework.aichat.utils.ReadFileConverter;
import org.pfw.framework.core.util.PFWConfigUtil;
import org.pfw.framework.domain.security.User;
import org.pfw.framework.modules.orm.Page;
import org.pfw.framework.modules.orm.PropertyFilter;
import org.pfw.framework.modules.orm.PropertyFilter.MatchType;
import org.pfw.framework.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


@Service
@Transactional
public class TAiWdxxServiceImpl implements TAiWdxxService {

	@Autowired
	private TAiWdxxDao entityDao;
	@Autowired
	private UserService userService;
	@Autowired
	private TAiQpjgDao tAiQpjgDao;
	@Autowired
	private TAiZskService tAiZskService;
	@Autowired
	private TAiQpjgService tAiQpjgService;
	@Autowired
	private TAiYhdhjgService tAiYhdhjgService; 
	

	@Transactional(readOnly=true)
	public TAiWdxx getById(String id) {
		return entityDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page findPage(Page page, List<PropertyFilter> filters) {
		return entityDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly=true)
	public List<TAiWdxx> find(List<PropertyFilter> filters)
	{
		return entityDao.find(filters);
	}
	
	@Transactional(readOnly=true)
	public List<TAiWdxx> find(List<PropertyFilter> filters, LinkedHashMap orderbyMap) {
		return entityDao.find(filters, orderbyMap);
	}
	
	public void save(TAiWdxx entity)
	{
		entityDao.save(entity);
	}
	
	public void update(TAiWdxx entity)
	{
		entityDao.update(entity);
	}

	public void deleteById(String id)
	{
		entityDao.delete(id);
	}
	
	public void delete(List<String> checks)
	{
		//向量数据库删除开始
		List<PropertyFilter> filters = new ArrayList<>();
		filters.add(new PropertyFilter("INC_sswdxx.id",checks));
		List<TAiQpjg> qpjgList = tAiQpjgService.find(filters);
		
		if(qpjgList != null && qpjgList.size() > 0)
		{
			List<String> qpjgList2= new ArrayList<>();
			for (TAiQpjg tAiQpjg : qpjgList) {
				qpjgList2.add(tAiQpjg.getId());
			}
			tAiQpjgService.delete(qpjgList2); 
		}
		//向量数据库删除结束
		
		
		
		if(checks!=null&&checks.size()>0){
			for (String id : checks) {
				//判断是否对话结果来的，如果是修改对话结果训练状态为否
				TAiWdxx entity = this.getById(id);
				if(entity.getSsdhjg() != null){
					TAiYhdhjg ssdhjg = entity.getSsdhjg();
					ssdhjg.setSfxlh("0");
					tAiYhdhjgService.update(ssdhjg);
				}
				
			}
		}

		if(checks!=null&&checks.size()>0){
			for (String id : checks) {
				entityDao.delete(id);
			}
		}

		
	}

	@Override
	public List<TAiWdxx> findAll()
	{
		return entityDao.find(" from TAiWdxx r ");
	}
	
	@Override
	public List<TAiWdxx> findbyhql(String hql, Object... val) 
	{
		return entityDao.find(hql, val);
	}
	
	@Override
	public List<TAiWdxx> findBy(String propertyName, Object value,
			MatchType matchType) {
		return entityDao.findBy(propertyName, value, matchType);
	}
	
	@Override
	public void deleteHql(String hql, Object... val) {
		entityDao.createQuery(hql, val).executeUpdate();
	}
	//批量插入
	@Override
	public Boolean batchsave(String sszskid,String laiy,String sbfs,String fjwjmName,String fjwjmPath,String beiz,String mc,String wjnr,String loginName,String dhjgid) {
		//来源为文件
		User usr = userService.getUserByLoginName(loginName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dqsj = sdf.format(new Date());
		try {
			TAiZsk sszsk = new TAiZsk();
			sszsk.setId(sszskid);
			
			if(laiy.equals("1")){
				String[] wjNameArr = fjwjmName.split(",");
				String[] fjPathArr = fjwjmPath.split(",");

				String nowdata  = new SimpleDateFormat("yyyy-MM").format(new Date());
				String realfilePath = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/") +  "userfiles/xmwj/" ;
				
				for (int i = 0; i < fjPathArr.length; i++) {
					String fileName = wjNameArr[i];
					String filePath = fjPathArr[i];
					
					TAiWdxx entity = new TAiWdxx();
					entity.setLaiy(laiy);
					entity.setSszsk(sszsk);
					entity.setFjwjmName(fileName);
					entity.setFjwjmPath(filePath);
					entity.setMc(fileToNamePath(fileName)[0]);
					entity.setWjlx(fileToNamePath(fileName)[1]);
					entity.setBeiz(beiz);
					
					
					try {
//						String text = ReadFileConverter.getContents(realfilePath + filePath);
						String res = ReadFileConverter.getContents("/userfiles/xmwj/"  + filePath,sbfs);
						if(StringUtils.isNotEmpty(res)){
							JSONObject jsonObject = JSONUtil.parseObj(res);
							String code = jsonObject.getStr("code");
							if(StringUtils.isNotEmpty(code) && "200".equals(code)){
								String data = jsonObject.getStr("data");
								entity.setWjnr(data);
							}else{
								entity.setQpzt("0");
							}
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						entity.setQpzt("0");
					}
					entity.setCzr(usr);
					entity.setCzsj(dqsj);
					entityDao.getSession().save(entity);
					entityDao.getSession().flush();
					//进行切片
					this.startslicing(entity.getId());
					
				}
			}else{
				//手动录入
				TAiWdxx entity = new TAiWdxx();
				entity.setLaiy(laiy);
				entity.setSszsk(sszsk);
				entity.setMc(mc);
				entity.setWjnr(wjnr);
				entity.setBeiz(beiz);
				entity.setCzr(usr);
				entity.setCzsj(dqsj);
				if(StringUtils.isNotEmpty(dhjgid)){
					TAiYhdhjg ssdhjg = new TAiYhdhjg();
					ssdhjg.setId(dhjgid);
					entity.setSsdhjg(ssdhjg);
				}
				
				this.save(entity);
				entityDao.getSession().flush();
				//进行切片
				this.startslicing(entity.getId());
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
    /**
     * 开始切片
     * **/
    public void startslicing(String wdxxid) {
    	Properties poperties = PFWConfigUtil.getInstance().getProperties(); // 获取properties文件的数据
		String url = (String) poperties.get("milvus.insertData"); 
		
		
    	TAiWdxx wdxx = this.getById(wdxxid);
    	
    	TAiZsk zsk = tAiZskService.getById(wdxx.getSszsk().getId());
    	
		String MetaData = "{\"zskid\" : \""+zsk.getId()+"\" , \"zskmc\" : \""+zsk.getZskmc()+"\", \"zskdm\" : \""+zsk.getZskdm() +"\" , \"wdmc\" : \""+wdxx.getMc()+"\"}";
		
    	String content = wdxx.getWjnr();
    	String[] lawSplits = StrUtil.split(content, 1000);
    	
    	for (String text : lawSplits) {
    		TAiQpjg qpjg = new TAiQpjg();
    		qpjg.setSswdxx(wdxx);
    		qpjg.setBiaot(getTitle(text));
    		qpjg.setQpnr(text);
    		qpjg.setTokens(text.length());
    		String res = null;
    		Map<String, String> contentMap = new HashMap<String, String>();
    		contentMap.put("partitionName", zsk.getZskdm());
    		contentMap.put("text", text);
    		contentMap.put("MetaData", MetaData);
    		
    		if(url.indexOf("https") > -1){
				res = HttpClientForTls12.httpsPost(url, null, contentMap);
			}else{
				res = HttpClientForTls12.httpPost(url, null, contentMap);
			}
			if(StringUtils.isNotEmpty(res)){
				JSONObject jsonUtil = JSONUtil.parseObj(res);
				String code = jsonUtil.get("code").toString();
				if(code.equals("200")){
					String primaryKeys = jsonUtil.get("primaryKeys").toString();
//					String InsertCnt = jsonUtil.get("InsertCnt").toString();
					qpjg.setQpid(primaryKeys);
					qpjg.setSfxl("1");
				}
			}
			
			tAiQpjgDao.getSession().save(qpjg);
			tAiQpjgDao.getSession().flush();
		}
    	this.update(wdxx);
	} 
    public String getTitle(String content) {
		if(content.length() > 50){
			return content.substring(0,49);
		}
		return content;
	}
     
    /**
     * 提取文件名和文件类型（扩展名）
     * @param filePath 文件路径字符串
     * @return 字符串数组，第一个元素为文件名，第二个为文件类型（无类型时返回空字符串）
     */
    public  String[] fileToNamePath(String filePath) {
        Path path = Paths.get(filePath);
        Path fileNamePath = path.getFileName();

        // 处理路径为根目录或空的情况
        if (fileNamePath == null) {
            return new String[]{"", ""};
        }

        String fileNameWithExt = fileNamePath.toString();
        int dotIndex = fileNameWithExt.lastIndexOf('.');

        // 无扩展名或仅有点结尾的情况
        if (dotIndex == -1 || dotIndex == fileNameWithExt.length() - 1) {
            return new String[]{fileNameWithExt, ""};
        }

        // 分割文件名和扩展名
        return new String[]{
            fileNameWithExt.substring(0, dotIndex),
            fileNameWithExt.substring(dotIndex + 1)
        };
    }
}
