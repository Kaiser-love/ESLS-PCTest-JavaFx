package com.datagroup.eslstest.dao;

import com.datagroup.eslstest.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouterDao extends JpaRepository<Router,Long> {
    Router findByIp(String ip);
    Router findByOutNetIpAndPort(String outNetIp, Integer port);
    Router findByBarCode(String barCode);
}
