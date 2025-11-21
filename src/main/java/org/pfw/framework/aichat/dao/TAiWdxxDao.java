package org.pfw.framework.aichat.dao;

import org.pfw.framework.aichat.domain.TAiWdxx;
import org.pfw.framework.modules.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Repository;

/**
 * 用户对象的泛型DAO类.
 */
@Repository
public class TAiWdxxDao extends HibernateDao<TAiWdxx, String> {
}