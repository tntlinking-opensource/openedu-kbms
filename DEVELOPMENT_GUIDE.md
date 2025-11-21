1. 项目概述
本文档提供基于JDK 7+和Tomcat 8+的Java Web项目开发规范和流程，确保团队协作高效、代码质量一致。

2. 开发环境设置
2.1 必要工具
JDK: 7或更高版本（推荐1.7.0_80+）
Tomcat: 8.x或更高版本
milvus向量数据库，安装教程：https://milvus.io/docs/zh/boolean.md
代码编辑器/IDE:
推荐IntelliJ IDEA或Eclipse（带Web开发插件）
版本控制: Git
数据库工具: 根据项目使用的数据库选择（推荐MySQL）
浏览器: Chrome/Firefox（用于前端调试）
3. 开发流程
3.1 分支策略
main/master：主分支，保持稳定可部署状态

3.2 代码提交规范
遵循Conventional Commits规范：<类型>[可选作用域]: <描述>

3.3 代码审查流程
完成功能开发后，确保：

所有自动化测试通过
代码符合项目编码规范
已更新相关文档

至少一名团队成员进行代码审查

所有CI检查通过

解决所有审查意见

合并到目标分支

4. 编码规范
4.1 Java编码规范
遵循Google Java Style Guide的核心规范：

类名使用UpperCamelCase（大驼峰式）
方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase（小驼峰式）
常量命名全部大写，单词间用下划线隔开
每行代码字符数不超过100
缩进使用4个空格，不使用制表符
代码块使用大括号，即使只有一行代码
合理使用注释，解释"为什么"而非"是什么"
4.2 Web层规范（Servlet/JSP）
Servlet类名以Servlet结尾
JSP文件放在webapp/WEB-INF/views/目录下，通过Servlet转发访问
避免在JSP中编写Java代码，使用JSTL和EL表达式
表单提交使用POST方法，查询使用GET方法
统一异常处理，使用全局异常处理器
4.3 数据库操作规范
数据库连接通过数据源获取，不硬编码连接信息
SQL语句使用参数化查询，防止SQL注入
事务管理：关键业务操作使用事务控制
数据库操作放在DAO层，不直接在Servlet中编写