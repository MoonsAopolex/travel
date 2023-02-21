package com.aopolex.travel.controller.backstage;

import com.aopolex.travel.bean.WangEditorResult;
import com.aopolex.travel.pojo.Category;
import com.aopolex.travel.pojo.Product;
import com.aopolex.travel.service.CategoryService;
import com.aopolex.travel.service.ProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/backstage/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    public ModelAndView all(@RequestParam(defaultValue = "1")int page,
                            @RequestParam(defaultValue = "5")int size){
        Page<Product> productPage = productService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productPage",productPage);
        modelAndView.setViewName("/backstage/product_all");
        return modelAndView;
    }

    //新增
    @RequestMapping("/addPage")
    public ModelAndView addPage(){
        List<Category> categoryList = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName("/backstage/product_add");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Product product){
        productService.add(product);
        return "redirect:/backstage/product/all";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public WangEditorResult upload(HttpServletRequest request, MultipartFile file) throws IOException {
        //创建文件夹，存放上传路径
        //1.设置上传文件夹的真实路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/upload/img/product/small/";
        //2.判断该文件夹是否存在，不存在就创建
        File dir = new File(realPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //拿到上传文件名
        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID()+fileName;
        //创建空文件
        File newFile = new File(dir, fileName);
        //将上传的文件写到空文件中
        file.transferTo(newFile);

        WangEditorResult wangEditorResult = new WangEditorResult();
        wangEditorResult.setErrno(0);
        String[] date = {"/upload/img/product/small/"+fileName};
        wangEditorResult.setData(date);
        return wangEditorResult;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid){
        //查询所有产品类别
        List<Category> categoryList = categoryService.findAll();
        //查询被修改的产品
        Product product = productService.findOne(pid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("/backstage/product_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/backstage/product/all";
    }

    @RequestMapping("/updateStatus")
    public String updateStatus(Integer pid,@RequestHeader("Referer")String referer){
        productService.updateStatus(pid);
        return "redirect:"+referer;
    }
}
