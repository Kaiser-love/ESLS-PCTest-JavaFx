package com.datagroup.eslstest.dao;

import com.datagroup.eslstest.entity.SystemVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemVersionDao extends JpaRepository<SystemVersion,Long> {
}
