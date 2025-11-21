package org.apache.struts2.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;

/**
 * 
 * 针对SQLServer sql 查询语句中 for path时内容为text
 * 此hibernate版本会报错，自己定义一个sqlserver方言类
 * 如果需要用到可以在applicationContext.properties中修改配置项hibernate.dialect为此类
 * @author Andrew
 *
 */
public class MySQLServerDialect extends SQLServerDialect {
	public MySQLServerDialect() {
		super();
		registerHibernateType(1, "string");
		registerHibernateType(-9, "string");
		registerHibernateType(-16, "string");
		registerHibernateType(3, "double");

		registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
		registerHibernateType(Types.NVARCHAR,
				Hibernate.STRING.getName());
		registerHibernateType(Types.LONGNVARCHAR,
				Hibernate.STRING.getName());
		registerHibernateType(Types.DECIMAL,
				Hibernate.DOUBLE.getName());
	}
}
