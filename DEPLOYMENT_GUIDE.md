openedu-kbms部署包地址：
https://toolkit.authpro.newtouch.com/api/v1/ipfs/download?hash=QmTz8sCzCHtneq3vCezNUJoxbzLQ4NDjEdsygZWXVcGgdG

# 本项目需要安装milvus向量数据库
安装教程：https://milvus.io/docs/zh/boolean.md

1. 概述
本文档介绍如何部署基于Java的Web项目，该项目使用JDK 7+和Tomcat 8+作为运行环境。

2. 部署环境要求
JDK: 7或更高版本
Tomcat: 8.x或更高版本
操作系统: Windows, Linux或macOS
3. 环境准备
3.1 JDK安装与配置
下载对应版本的JDK：

Oracle JDK 7: 官方下载地址
OpenJDK 7: 官方下载地址
安装JDK并配置环境变量：

# Linux/Mac配置示例
export JAVA_HOME=/usr/lib/jvm/java-7-openjdk
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
验证JDK安装：

java -version
javac -version
3.2 Tomcat安装与配置
下载Tomcat 8+:

Tomcat 8官方下载
解压到安装目录：

# Linux/Mac示例
tar -zxvf apache-tomcat-8.tar.gz -C /opt/
配置环境变量：

# Linux/Mac配置示例
export CATALINA_HOME=/opt/apache-tomcat-8
export PATH=$CATALINA_HOME/bin:$PATH
启动Tomcat验证安装：

# 启动
$CATALINA_HOME/bin/startup.sh  # Linux/Mac
%CATALINA_HOME%\bin\startup.bat  # Windows

# 验证（访问 http://localhost:8080）

# 停止
$CATALINA_HOME/bin/shutdown.sh  # Linux/Mac
%CATALINA_HOME%\bin\shutdown.bat  # Windows
4. 项目部署方法
4.1 方法一：直接部署WAR文件
构建项目生成WAR文件（通常在项目的target/目录下）
将WAR文件复制到Tomcat的webapps/目录：
cp your-project.war $CATALINA_HOME/webapps/
启动Tomcat，它会自动解压并部署WAR文件
访问应用：http://localhost:8080/your-project
4.2 方法二：配置Context部署
在Tomcat的conf/Catalina/localhost/目录下创建your-project.xml文件
配置内容：
<Context path="/your-project" docBase="/path/to/your/project/webapp" 
        reloadable="true" crossContext="true">
</Context>
启动Tomcat
访问应用：http://localhost:8080/your-project

5. 部署后配置
5.1 数据库配置
将数据库驱动JAR文件放入Tomcat的lib/目录
配置数据源（src/main/resources/spring/applicationContext.properties中）： 

jdbc.driver   = com.mysql.jdbc.Driver
jdbc.password = BeE3F/0kkw4=
jdbc.url      = jdbc:mysql://127.0.0.1:3306/xxgjbb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true
jdbc.username = BeE3F/0kkw4=
hibernate.dialect=org.apache.struts2.util.MySqlDialect

5.2 日志配置
配置Tomcat的conf/logging.properties调整日志级别
或在项目中使用Log4j配置（推荐）：
添加Log4j依赖
配置log4j.properties文件

6. 常见问题解决
端口冲突：修改conf/server.xml中的端口配置
内存不足：调整bin/catalina.sh（Linux/Mac）或bin/catalina.bat（Windows）中的JVM参数：
# 示例：设置初始内存为512M，最大内存为1G
export JAVA_OPTS="-Xms512m -Xmx1024m"
权限问题：确保Tomcat进程对安装目录和项目文件有读写权限
类冲突：检查WEB-INF/lib和Tomcat的lib目录中是否有重复的JAR文件