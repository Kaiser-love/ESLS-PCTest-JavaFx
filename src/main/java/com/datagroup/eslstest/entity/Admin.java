package com.datagroup.eslstest.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Objects;

@Data
public class Admin {
    private long id;
    @NotBlank(message = "{user.username,notBlank}")
    private String username;
    @NotBlank(message = "{user.password,notBlank}")
    private String password;
}
