package com.aopolex.travel.controller.backstage;

import com.aopolex.travel.bean.PermissionWithStatus;
import com.aopolex.travel.pojo.Permission;
import com.aopolex.travel.pojo.Role;
import com.aopolex.travel.service.RoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/role/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size){
        Page<Role> rolePage = roleService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rolePage",rolePage);
        modelAndView.setViewName("/backstage/role_all");
        return modelAndView;
    }

    // 新增角色
    @RequestMapping("/add")
    public String add(Role role){
        roleService.add(role);
        return "redirect:/backstage/role/all";
    }

    // 根据ID查询角色，并跳转页面
    @RequestMapping("/edit")
    public ModelAndView edit(Integer rid){
        Role role = roleService.findById(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role",role);
        modelAndView.setViewName("/backstage/role_edit");
        return modelAndView;
    }

    //更新角色
    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return "redirect:/backstage/role/all";
    }

    //删除角色
    @RequestMapping("/delete")
    public String delete(Integer rid){
        roleService.delete(rid);
        return "redirect:/backstage/role/all";
    }

    //查询所有权限
    @RequestMapping("/findPermission")
    public ModelAndView findPermission(Integer rid){
        List<PermissionWithStatus> permissions = roleService.findPermission(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissions",permissions);
        modelAndView.addObject("rid",rid);
        modelAndView.setViewName("/backstage/role_permission");
        return modelAndView;
    }

    //更新角色的权限
    @RequestMapping("/updatePermission")
    public String managePermission(Integer rid,Integer[] ids){
        roleService.updatePermissions(rid,ids);
        return "redirect:/backstage/role/all";
    }
}
