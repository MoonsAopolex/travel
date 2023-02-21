package com.aopolex.travel.mapper;

import com.aopolex.travel.pojo.Admin;
import com.aopolex.travel.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    //查询用户详情
    Admin findDesc(Integer aid);

    //删除用户所拥有的角色
    void deleteAdminAllRoles(Integer aid);

    //给用户添加角色
    void addAdminRole(@Param("aid")Integer aid, @Param("rid")Integer rid);

    //根据管理员名查询权限
    List<Permission> findAllPermission(String username);
}
