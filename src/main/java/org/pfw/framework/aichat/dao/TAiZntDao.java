package org.pfw.framework.aichat.dao;

import org.pfw.framework.aichat.domain.TAiZnt;
import org.pfw.framework.modules.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Repository;

/**
 * 用户对象的泛型DAO类.
 */
@Repository
public class TAiZntDao extends HibernateDao<TAiZnt, String> {
}