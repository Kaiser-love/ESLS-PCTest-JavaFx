package com.datagroup.eslstest.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "T_User", schema = "tags", catalog = "")
@Data
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "passwd")
    private String passwd;
    @Column(name = "sex")
    private Byte sex;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "address")
    private String address;
    @Column(name = "department")
    private String department;
    @Column(name = "createTime")
    private Timestamp createTime;
    @Column(name = "lastLoginTime")
    private Timestamp lastLoginTime;
    @Column(name = "status")
    private Byte status;
    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name = "shopid", referencedColumnName = "id")
    private Shop shop;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<Role> roleList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", sex=" + sex +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", shop=" + shop +
                '}';
    }
}
