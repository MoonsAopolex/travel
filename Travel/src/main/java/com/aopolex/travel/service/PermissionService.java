package com.aopolex.travel.service;


import com.aopolex.travel.mapper.PermissionMapper;
import com.aopolex.travel.pojo.Permission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    //分页查询
    public Page<Permission> selctPage(int page,int size){
        Page selectPage = permissionMapper.selectPage(new Page(page, size), null);
        return selectPage;
    }

    //新增权限
    public void add(Permission permission){
        permissionMapper.insert(permission);
    }

    //根据ID查询权限
    public Permission findById(Integer pid){
       return permissionMapper.selectById(pid);
    }
    //更新权限
    public void update(Permission permission){
        permissionMapper.updateById(permission);
    }

    public void delete(Integer pid){
        permissionMapper.deleteById(pid);
    }
}
