
package com.datagroup.eslstest.serviceimpl;

import com.datagroup.eslstest.common.request.RequestBean;
import com.datagroup.eslstest.dao.BaseDao;
import com.datagroup.eslstest.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service("BaseService")
public class BaseServiceImpl implements Service {
    @Autowired
    protected BaseDao baseDao;

    @Override
    public List findBySql(String s) {
        return baseDao.findBySql(s);
    }

    @Override
    public List findBySql(String s,Class clazz) {
        return baseDao.findBySql(s,clazz);
    }

    @Override
    public List findAllBySql(String table, String query, String queryString, int page, int count,Class clazz) {
        return baseDao.findAllBySql(table,query,queryString,page,count,clazz);
    }

    @Override
    public  List findByArrtribute(String table, String query, String queryString,Class clazz) {
        return baseDao.findByArrtribute(table,query,queryString,clazz);
    }

    @Override
    public List findAllBySql(String table, String query, String queryString,Class clazz) {
        return baseDao.findAllBySql(table,query, queryString,clazz);
    }

    @Override
    public Integer updateByArrtribute(String table, RequestBean source, RequestBean target) {
        return baseDao.updateByArrtribute(table,source,target);
    }

    @Override
    public List findAllBySql(String table, String connection, RequestBean requestBean, int page, int count, Class clazz) {
        return  baseDao.findAllBySql(table,connection,requestBean,page,count,clazz);
    }

    @Override
    public List findAllBySql(String table, String connection, String query, String queryString, int page, int count, Class clazz) {
        return baseDao.findAllBySql(table,connection,query,queryString,page,count,clazz);
    }

    @Override
    public Integer deleteByIdList(String table, String query, List idList) {
        return baseDao.deleteByIdList(table,query,idList);
    }
}
