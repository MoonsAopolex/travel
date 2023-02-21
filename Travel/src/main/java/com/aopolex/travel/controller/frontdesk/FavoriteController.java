package com.aopolex.travel.controller.frontdesk;

import com.aopolex.travel.pojo.Member;
import com.aopolex.travel.pojo.Product;
import com.aopolex.travel.service.FavoriteService;
import com.aopolex.travel.service.ProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/add")
    public String addFavorite(Integer pid, HttpSession session, @RequestHeader("Referer")String referer){
        Member member = (Member) session.getAttribute("member");
        favoriteService.addFavorite(pid,member.getMid());
        return "redirect:"+referer;
    }

    @RequestMapping("/del")
    public String delFavorite(Integer pid, HttpSession session, @RequestHeader("Referer")String referer){
        Member member = (Member) session.getAttribute("member");
        favoriteService.delFavorite(pid,member.getMid());
        return "redirect:"+referer;
    }

    //我的收藏
    @RequestMapping("/myFavorite")
    public ModelAndView myFavoritePage(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10")int size,
                                       HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        Page<Product> productPage = favoriteService.findMemberFavorite(page, size, member.getMid());
        modelAndView.addObject("productPage",productPage);
        modelAndView.setViewName("/frontdesk/my_favorite");
        return modelAndView;
    }
}
