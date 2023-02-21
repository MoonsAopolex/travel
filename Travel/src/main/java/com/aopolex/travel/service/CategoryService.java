package com.aopolex.travel.service;

import com.aopolex.travel.mapper.CategoryMapper;
import com.aopolex.travel.pojo.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    //分页查询
    public Page<Category> findPage(int page,int size){
        return categoryMapper.selectPage(new Page(page, size),null);
    }

    //新增
    public void add(Category category){
        categoryMapper.insert(category);
    }

    //查询
    public Category findById(Integer cid){
        return categoryMapper.selectById(cid);
    }

    //更新
    public void update(Category category){
        categoryMapper.updateById(category);
    }

    //删除
    public void delete(Integer cid){
        categoryMapper.deleteById(cid);
    }

    //查询所有
    public List<Category> findAll(){
        return categoryMapper.selectList(null);
    }
}
