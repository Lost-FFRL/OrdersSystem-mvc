package com.lost.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDaoAbstract
{
    /**
     * 也可用
     * @Autowired 
     */
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
}
