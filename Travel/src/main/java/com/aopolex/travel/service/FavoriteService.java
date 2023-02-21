package com.aopolex.travel.service;

import com.aopolex.travel.mapper.ProductMapper;
import com.aopolex.travel.pojo.Product;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {
    @Autowired
    private ProductMapper productMapper;

    //用户是否收藏产品
    public boolean findFavorite(Integer pid,Integer mid){
        int result = productMapper.findFavoriteByPidAndMid(pid, mid);
        if (result == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 收藏线路
     */
    public void addFavorite(Integer pid,Integer mid){
        productMapper.addFavorite(pid, mid);
    }

    /**
     * 取消收藏线路
     */
    public void delFavorite(Integer pid,Integer mid){
        productMapper.delFavorite(pid, mid);
    }

    /**
     * 查询我的收藏
     * @param page
     * @param size
     * @param mid
     * @return
     */
    public Page<Product> findMemberFavorite(int page,int size,Integer mid){
        Page favoritePage = productMapper.findMemberFavorite(new Page(page, size), mid);
        return favoritePage;
    }
}
