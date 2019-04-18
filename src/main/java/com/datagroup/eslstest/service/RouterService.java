package com.datagroup.eslstest.service;

import com.datagroup.eslstest.common.request.RequestBean;
import com.datagroup.eslstest.common.response.ResponseBean;
import com.datagroup.eslstest.entity.Router;

import java.util.List;
import java.util.Optional;

public interface RouterService extends Service{
    List<Router> findAll();
    List<Router> findAll(Integer page, Integer count);
    boolean deleteById(Long id);
    Router saveOne(Router router);
    Optional<Router> findById(Long id);
    Router findByIp(String ip);
    Router findByOutNetIpAndPort(String OutNetIp, Integer port);
    Router findByBarCode(String barCode);
}
