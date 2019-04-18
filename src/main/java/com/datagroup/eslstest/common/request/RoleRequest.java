package com.datagroup.eslstest.common.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleRequest {
    List<Long> ids;
    List<List<Long>> collectionIds;
}
