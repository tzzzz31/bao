package com.bao.bookforum.controller;

import com.bao.bookforum.entity.ArticleType;
import com.bao.bookforum.entity.User;
import com.bao.bookforum.exception.R;
import com.bao.bookforum.exception.ResultCodeEnum;
import com.bao.bookforum.exception.TestException;
import com.bao.bookforum.service.IArticleService;
import com.bao.bookforum.service.IArticleTypeService;
import com.bao.bookforum.service.IUserService;
import com.bao.bookforum.utils.CommonPage;
import com.bao.bookforum.vo.ArticleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/")
public class indexController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTypeService articleTypeService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Integer pageNumber, Model model,String articleTypeId,String articleTitle){
        if(Objects.isNull(pageNumber)||pageNumber<1){
            pageNumber=1;
        }
        //若没有传入帖子类型名称
        if(Objects.isNull(articleTypeId)){
            articleTypeId=null;
        }
        //若没有传入帖子名称
        if(Objects.isNull(articleTitle)){
            articleTitle=null;
        }
        Page<ArticleVo> articlePage = new Page<>(pageNumber, 15);
        IPage<ArticleVo> articleVoIPage = articleService.articleList(articlePage, articleTitle,null,articleTypeId);
        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));

        //传入所有articleType
        List<ArticleType> articleTypeArrayList = new ArrayList<>();
        articleTypeArrayList = articleTypeService.list();
        model.addAttribute("articleTypeArrayList",articleTypeArrayList);

        return "index";
    }

    @GetMapping("/ban")
    public String userBan(){
        return "/ban";
    }


}
