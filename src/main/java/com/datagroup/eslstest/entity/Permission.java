package com.datagroup.eslstest.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Data
@NoArgsConstructor
@Table(name = "permission", schema = "tags", catalog = "")
public class Permission {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private long id;
    @Column(name = "name",length = 50)
    private String name;
    @Column(name = "url", length = 50)
    private String url;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;
    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
    public Permission(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
