package com.aopolex.travel;

import com.aopolex.travel.mapper.AdminMapper;
import com.aopolex.travel.pojo.Admin;
import com.aopolex.travel.pojo.Product;
import com.aopolex.travel.service.AdminService;
import com.aopolex.travel.service.ProductService;
import com.aopolex.travel.util.MailUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TravelApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MailUtils mailUtils;

    @Test
    public void testPage() {
        Page<Product> page = productService.findPage(1, 5);
        System.out.println(page);
    }

    @Test
    public void testDesc() {
        Admin admin = adminService.findDesc(1);
        System.out.println(admin);
    }

    @Test
    void sendMail() {
        mailUtils.sendMail("1667653102@qq.com", "这是一封测试邮件", "测试");
    }


}
