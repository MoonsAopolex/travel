package com.aopolex.travel.mapper;

import com.aopolex.travel.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper extends BaseMapper<Product> {
    Page<Product> findProductPage(Page<Product> page);

    //修改：根据id查询产品
    Product findOne(int pid);

    //查询收藏列表
    int findFavoriteByPidAndMid(@Param("pid") Integer pid, @Param("mid") Integer mid);

    //前端收藏产品
    void addFavorite(@Param("pid") Integer pid, @Param("mid") Integer mid);

    //前端取消收藏产品
    void delFavorite(@Param("pid") Integer pid,@Param("mid") Integer mid);

    //根据用户ID查询用户的收藏
    Page<Product> findMemberFavorite(Page<Product> page,Integer mid);
}
