package org.apache.struts2.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;   
public class MySqlDialect extends MySQL5Dialect {   
    public MySqlDialect() {   
        super(); 
        //JDBC type:1  
        registerHibernateType(Types.CHAR, Hibernate.STRING.getName());  

        //JDBC type:-9  
        registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());  

        //JDBC type:-16  
        registerHibernateType(Types.LONGNVARCHAR, Hibernate.STRING.getName());  

        //JDBC type:3  
        registerHibernateType(Types.DECIMAL, Hibernate.DOUBLE.getName());  

        //JDBC type:-1  
        registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName()); 
    }   
} 
