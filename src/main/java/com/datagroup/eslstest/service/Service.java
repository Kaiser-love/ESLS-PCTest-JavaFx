
package com.datagroup.eslstest.service;
import com.datagroup.eslstest.common.request.RequestBean;

import java.util.List;

public interface Service<T>
{
    List findBySql(String s);
    List findBySql(String s, Class clazz);
    List findAllBySql(String table, String query, String queryString, int page, int count, Class clazz);
    List findByArrtribute(String table, String query, String queryString, Class clazz);
    List findAllBySql(String table, String query, String queryString, Class clazz);
    Integer updateByArrtribute(String table, RequestBean source, RequestBean target);
    Integer deleteByIdList(String table, String query, List<Long> idList);
    // 根据传入的多属性进行搜索
    List findAllBySql(String table, String connection, RequestBean requestBean, int page, int count, Class clazz);
    // 根据传入的属性进行或搜索
    List findAllBySql(String table, String connection, String query, String queryString, int page, int count, Class clazz);
}
