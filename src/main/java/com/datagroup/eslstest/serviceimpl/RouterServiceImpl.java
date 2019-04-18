package com.datagroup.eslstest.serviceimpl;

import com.datagroup.eslstest.dao.RouterDao;
import com.datagroup.eslstest.entity.Router;
import com.datagroup.eslstest.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service("RouterService")
public class RouterServiceImpl extends BaseServiceImpl implements RouterService {
    @Autowired
    private RouterDao routerDao;
    @Override
    public List<Router> findAll() {
        return routerDao.findAll();
    }
    @Override
    public List<Router> findAll(Integer page, Integer count) {
        List<Router> content = routerDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    @Transactional
    public Router saveOne(Router router) {
        return routerDao.save(router);
    }

    @Override
    public Optional<Router> findById(Long id) {
        return routerDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            routerDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @Override
    public Router findByIp(String ip) {
        return routerDao.findByIp(ip);
    }
    @Override
    public Router findByOutNetIpAndPort(String ip,Integer port) {
        return routerDao.findByOutNetIpAndPort(ip,port);
    }

    @Override
    public Router findByBarCode(String barCode) {
        return routerDao.findByBarCode(barCode);
    }

}
