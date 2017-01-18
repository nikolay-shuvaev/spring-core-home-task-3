package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created by NICK on 18.01.2017.
 */
public abstract class AbstractDao extends JdbcDaoSupport {
    @Autowired
    public void setDS(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
}
