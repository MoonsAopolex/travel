package com.aopolex.travel.controller.backstage;

import com.aopolex.travel.pojo.Permission;
import com.aopolex.travel.service.PermissionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backstage/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/permission/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size){
        Page<Permission> permissionPage = permissionService.selctPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissionPage",permissionPage);
        modelAndView.setViewName("/backstage/permission_all");
        return modelAndView;
    }

    //新增权限
    @RequestMapping("/add")
    public String add(Permission permission){
        permissionService.add(permission);
        return "redirect:/backstage/permission/all";
    }

    //根据id查询权限
    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid){
        Permission permission = permissionService.findById(pid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permission",permission);
        modelAndView.setViewName("/backstage/permission_edit");
        return modelAndView;
    }
    //更新
    @RequestMapping("/update")
    public String update(Permission permission){
        permissionService.update(permission);
        return "redirect:/backstage/permission/all";
    }

    //根据ID删除权限
    @RequestMapping("delete")
    public String delete(Integer pid){
        permissionService.delete(pid);
        return "redirect:/backstage/permission/all";
    }
}
