package com.aopolex.travel.service;

import com.aopolex.travel.bean.RoleWithStatus;
import com.aopolex.travel.mapper.AdminMapper;
import com.aopolex.travel.mapper.RoleMapper;
import com.aopolex.travel.pojo.Admin;
import com.aopolex.travel.pojo.Permission;
import com.aopolex.travel.pojo.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;

    // 分页查询管理员
    public Page<Admin> findPage(int page,int size){
        Page selectPage = adminMapper.selectPage(new Page(page, size), null);
        return selectPage;
    }

    // 新增管理员
    public void add(Admin admin){
        admin.setPassword(encoder.encode(admin.getPassword())); //密码加密
        adminMapper.insert(admin);
    }

    // 根据id查询管理员
    public Admin findById(Integer aid){
        return adminMapper.selectById(aid);
    }

    // 修改管理员
    public void update(Admin admin){
        //旧密码
        String oldPassword = adminMapper.selectById(admin.getAid()).getPassword();
        //新密码
        String newPassword = admin.getPassword();
        //如果新旧密码不同，对新密码进行加密
        if (!oldPassword.equals(newPassword)){
            admin.setPassword(encoder.encode(newPassword));
        }
        adminMapper.updateById(admin);
    }

    //查询用户详情
    public Admin findDesc(Integer aid){
        return adminMapper.findDesc(aid);
    }

    // 查询用户的角色情况
    public List<RoleWithStatus> findRole(Integer aid){
        //查询所有角色
        List<Role> roles = roleMapper.selectList(null);
        //查询用户拥有的所有角色id
        List<Integer> rids = roleMapper.findRoleIdByAdmin(aid);
        //构建带有状态的角色集合，用户拥有状态为true，否则为false
        List<RoleWithStatus> roleList = new ArrayList();
        //遍历所有角色
        for (Role role : roles) {
            //创建带有状态的角色
            RoleWithStatus roleWithStatus = new RoleWithStatus();
            //将角色属性复制给带有状态的角色
            BeanUtils.copyProperties(role,roleWithStatus);
            if (rids.contains(role.getRid())){
                roleWithStatus.setAdminHas(true);
            }else {
                roleWithStatus.setAdminHas(false);
            }
            roleList.add(roleWithStatus);
        }
        return roleList;
    }

    //重新分配角色
    public void updateRoles(Integer aid,Integer[] ids){
        //先删除所有角色
        adminMapper.deleteAdminAllRoles(aid);
        //重新添加角色
        for (Integer rid : ids) {
            adminMapper.addAdminRole(aid,rid);
        }
    }

    //修改用户状态
    public void updateStatus(Integer aid){
        Admin admin = adminMapper.selectById(aid);
        admin.setStatus(!admin.isStatus()); //状态取反
        adminMapper.updateById(admin);
    }

    //认证：根据username查询用户
    public Admin findByAdminName(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }

    //授权：根据名字查询权限
    public List<Permission> findAllPermission(String username){
        return adminMapper.findAllPermission(username);
    }
}
