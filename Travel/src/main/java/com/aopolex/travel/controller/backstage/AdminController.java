package com.aopolex.travel.controller.backstage;

import com.aopolex.travel.bean.RoleWithStatus;
import com.aopolex.travel.pojo.Admin;
import com.aopolex.travel.service.AdminService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/admin/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<Admin> adminPage = adminService.findPage(page, size);
        modelAndView.addObject("adminPage",adminPage);
        modelAndView.setViewName("/backstage/admin_all");
        return modelAndView;
    }

    //新增管理员
    @RequestMapping("/add")
    public String add(Admin admin){
        adminService.add(admin);
        return "redirect:/backstage/admin/all";
    }

    // 查询管理员，跳转到修改页面
    @RequestMapping("/edit")
    public ModelAndView edit(Integer aid) {
        Admin admin = adminService.findById(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin", admin);
        modelAndView.setViewName("/backstage/admin_edit");
        return modelAndView;
    }

    // 修改管理员
    @RequestMapping("/update")
    public String update(Admin admin) {
        adminService.update(admin);
        return "redirect:/backstage/admin/all";
    }

    // 查询管理员详情
    @RequestMapping("/desc")
    public ModelAndView desc(Integer aid){
        Admin admin = adminService.findDesc(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("/backstage/admin_desc");
        return modelAndView;
    }

    //查询所有角色
    @RequestMapping("/findRole")
    public ModelAndView findRole(Integer aid){
        List<RoleWithStatus> role = adminService.findRole(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles",role);
        modelAndView.addObject("aid",aid);
        modelAndView.setViewName("/backstage/admin_role");
        return modelAndView;
    }

    //修改用户角色
    @RequestMapping("/updateRole")
    public String manageRole(Integer aid,Integer[] ids){
        adminService.updateRoles(aid,ids);
        return "redirect:/backstage/admin/all";
    }

    //修改用户状态
    @RequestMapping("/updateStatus")
    public String updateStatus(Integer aid){
        adminService.updateStatus(aid);
        return "redirect:/backstage/admin/all";
    }
}
