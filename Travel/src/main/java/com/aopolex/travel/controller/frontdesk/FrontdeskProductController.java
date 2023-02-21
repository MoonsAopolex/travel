package com.aopolex.travel.controller.frontdesk;

import com.aopolex.travel.pojo.Member;
import com.aopolex.travel.pojo.Product;
import com.aopolex.travel.service.FavoriteService;
import com.aopolex.travel.service.ProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/product")
public class FrontdeskProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FavoriteService favoriteService;

    /**
     * 查询旅游线路
     *
     * @param cid         线路类别id
     * @param productName 线路名
     * @param page        页数
     * @param size        每页条数
     * @return
     */
    @RequestMapping("routeList")
    public ModelAndView findProduct(Integer cid, String productName,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Product> productPage = productService.findProduct(cid, productName, page, size);
        modelAndView.addObject("productPage", productPage);
        modelAndView.addObject("cid", cid);
        modelAndView.addObject("productName", productName);
        modelAndView.setViewName("/frontdesk/route_list");
        return modelAndView;
    }

    @RequestMapping("/routeDetail")
    public ModelAndView findOne(Integer pid, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.findOne(pid);
        //查询用户是否收藏该产品
        Object member = session.getAttribute("member");
        if (member == null) {// 未登录认为未收藏
            modelAndView.addObject("favorite",false);
        }else { //已登录查询是否收藏
            Member member1 = (Member) member;
            boolean favorite = favoriteService.findFavorite(pid, member1.getMid());
            modelAndView.addObject("favorite",favorite);
        }
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/frontdesk/route_detail");
        return modelAndView;
    }

}
