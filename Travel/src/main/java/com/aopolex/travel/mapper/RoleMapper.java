package com.aopolex.travel.mapper;

import com.aopolex.travel.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    //查询用户拥有的所有角色的id
    List<Integer> findRoleIdByAdmin(Integer aid);

    //删除角色所有权限
    void deleteRoleAllPermission(Integer rid);

    //给角色添加权限
    void addRolePermission(@Param("rid")Integer rid,@Param("pid")Integer pid);
}
