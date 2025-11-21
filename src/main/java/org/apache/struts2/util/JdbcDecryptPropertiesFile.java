package org.apache.struts2.util;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class JdbcDecryptPropertiesFile extends PropertyPlaceholderConfigurer{

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException
    {
    	try
    	{
        String password = props.getProperty("jdbc.password");
        if(null != password && "" != password && "null" != password){
            String decPassword = Encryption.decryptBasedDes(password);
            props.setProperty("jdbc.password", decPassword);
        }
        
        String username = props.getProperty("jdbc.username");
        if(null != username && "" != username && "null" != username){
            String decusername = Encryption.decryptBasedDes(username);
            props.setProperty("jdbc.username", decusername);
        }
        
        super.processProperties(beanFactory, props);
    	}catch(Exception e)
    	{
    		
    	}
    }
}