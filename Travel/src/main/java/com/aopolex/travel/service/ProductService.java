package com.aopolex.travel.service;

import com.aopolex.travel.mapper.ProductMapper;
import com.aopolex.travel.pojo.Product;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional
@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public Page<Product> findPage(int page,int size){
        Page selectPage = productMapper.findProductPage(new Page(page, size));
        return selectPage;
    }

    //新增产品
    public void add(Product product){
        productMapper.insert(product);
    }

    //修改：根据ID查询
    public Product findOne(Integer pid){
        return productMapper.findOne(pid);
    }
    //修改：更新
    public void update(Product product){
        productMapper.updateById(product);
    }

    // 修改产品激活状态
    public void updateStatus(Integer pid){
        Product product = productMapper.selectById(pid);
        product.setStatus(!product.getStatus());
        productMapper.updateById(product);
    }

    // 旅游产品列表查询：根据产品类型cid或产品名字查询
    public Page<Product> findProduct(Integer cid,String productName,int page,int size){
        QueryWrapper<Product> queryWrapper = new QueryWrapper();
        if (cid!=null){
            queryWrapper.eq("cid",cid);
        }
        if (StringUtils.hasText(productName)){
            queryWrapper.like("productName",productName);
        }
        //还在启用的产品
        queryWrapper.eq("status",1);
        //倒序排列
        queryWrapper.orderByDesc("pid");

        Page selectPage = productMapper.selectPage(new Page(page, size), queryWrapper);
        return selectPage;
    }

}
