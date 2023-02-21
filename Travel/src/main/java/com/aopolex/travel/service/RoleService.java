package com.aopolex.travel.service;

import com.aopolex.travel.bean.PermissionWithStatus;
import com.aopolex.travel.mapper.PermissionMapper;
import com.aopolex.travel.mapper.RoleMapper;
import com.aopolex.travel.pojo.Permission;
import com.aopolex.travel.pojo.Role;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    // 分页查询角色
    public Page<Role> findPage(int page,int size){
        Page selectPage = roleMapper.selectPage(new Page(page, size), null);
        return selectPage;
    }

    // 新增角色
    public void add(Role role){
        roleMapper.insert(role);
    }

    // 根据ID查询角色
    public Role findById(Integer rid){
        return roleMapper.selectById(rid);
    }

    // 修改角色
    public void update(Role role){
        roleMapper.updateById(role);
    }

    // 删除角色
    public void delete(Integer rid){
        roleMapper.deleteById(rid);
    }

    // 查询角色的权限情况
    public List<PermissionWithStatus> findPermission(Integer rid){
        //查询所有权限
        List<Permission> permissions = permissionMapper.selectList(null);
        //查询角色拥有的权限
        List<Integer> pids = permissionMapper.findPermissionIdByRole(rid);
        //创建带有状态的权限集合
        List<PermissionWithStatus> permissionList = new ArrayList();
        //遍历所有角色
        for (Permission permission : permissions) {
            //创建带有状态的权限
            PermissionWithStatus permissionWithStatus = new PermissionWithStatus();
            BeanUtils.copyProperties(permission,permissionWithStatus);
            if (pids.contains(permission.getPid())){
                permissionWithStatus.setRoleHas(true);
            }else {
                permissionWithStatus.setRoleHas(false);
            }
            permissionList.add(permissionWithStatus);
        }
        return permissionList;
    }

    // 分配权限
    public void updatePermissions(Integer rid,Integer[] ids){
        //删除所有权限
        roleMapper.deleteRoleAllPermission(rid);
        //重新添加权限
        for (Integer pid : ids) {
            roleMapper.addRolePermission(rid,pid);
        }
    }
}
