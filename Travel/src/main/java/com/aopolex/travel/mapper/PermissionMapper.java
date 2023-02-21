package com.aopolex.travel.mapper;

import com.aopolex.travel.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    // 查询角色拥有的所有权限的id
    List<Integer> findPermissionIdByRole(Integer rid);

}
