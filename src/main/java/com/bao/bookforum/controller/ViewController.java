package com.bao.bookforum.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.bao.bookforum.entity.Article;
import com.bao.bookforum.entity.ArticleType;
import com.bao.bookforum.entity.Comment;
import com.bao.bookforum.entity.User;
import com.bao.bookforum.exception.R;
import com.bao.bookforum.exception.ResultCodeEnum;
import com.bao.bookforum.service.IArticleService;
import com.bao.bookforum.service.IArticleTypeService;
import com.bao.bookforum.service.ICommentService;
import com.bao.bookforum.service.IUserService;
import com.bao.bookforum.utils.CommonUtils;
import com.bao.bookforum.vo.CommentVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jdk.nashorn.internal.codegen.CompileUnit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bao.bookforum.dto.user.userInfoDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class ViewController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private ICommentService commentService;

    //获取图像验证码
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //用工具包生成验证码
        CircleCaptcha captcha = CommonUtils.getCaptcha(request);
        //输出流输出验证码
        captcha.write(response.getOutputStream());
    }

    //用户注册页面
    @GetMapping("/register")
    public String register(HttpServletRequest request){
        if(Objects.nonNull(request.getSession().getAttribute("user"))){
            return "/redirect:/";
        }
        return "/view/register";
    }

    //用户注册方法
    @PostMapping("/userRegister")
    @ResponseBody
    public R userRegister(HttpServletRequest request,userInfoDto userInfoDto ){
        HttpSession session = request.getSession();
        String verifyCode=userInfoDto.getVerifyCode();
        //判断验证码是否为空和判断是否输入一致
        if(!(StringUtils.isNotEmpty(verifyCode))||!verifyCode.equals(session.getAttribute("circleCaptchaCode"))){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ERROR_CIRCLE);
        }
        //用户名和密码是否相同
        if(userInfoDto.getUserName().equals(userInfoDto.getUserPassword())){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ERROR_USER);
        }

        //用户名是否被使用
        if(userService.count(Wrappers.<User>lambdaQuery().eq(User::getUserName,userInfoDto.getUserName()))>0){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.HAVE_USER);
        }

        User user = new User();
        BeanUtils.copyProperties(userInfoDto,user);
        user.setUserId(IdUtil.simpleUUID());
        user.setUserRegisterTime(DateUtil.date());
        user.setUserPassword(userInfoDto.getUserPassword());
        //是否被冻结
        user.setUserFrozen(0);
        if(userService.save(user)){
            return R.ok();
        }
        return R.error();

    }

    //用户登录页面
    @GetMapping("/login")
    public String login(HttpServletRequest request,Model model){
        if(Objects.nonNull(request.getSession().getAttribute("user"))){
            return "/redirect:/";
        }
        model.addAttribute("referer",request.getHeader("referer"));
        return "/view/login";
    }

    //用户登录方法
    @PostMapping("/userLogin")
    @ResponseBody
    public R userLogin(HttpServletRequest request,userInfoDto userInfoDto){
        HttpSession session = request.getSession();
        String verifyCode = userInfoDto.getVerifyCode();
        //判断验证码是否符合规则
        if(!(StringUtils.isNotEmpty(verifyCode))||!verifyCode.equals(session.getAttribute("circleCaptchaCode"))){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ERROR_CIRCLE);
        }
        //取出数据库中的与输入的用户名相同的数据
        User userDb = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,userInfoDto.getUserName()),false);
        //判断取出的用户是否存在，否则则输出错误
        if(Objects.isNull(userDb)){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ERROR_USERNAME);
        }
        //判断取出的密码与输入的密码是否相同
        if(!(userDb.getUserPassword().equals(userInfoDto.getUserPassword()))){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ERRO_PASSWORD);
        }
        //判断用户是否被冻结
        if(userDb.getUserFrozen()==1){
            session.removeAttribute("circleCaptchaCode");
            System.out.println("被冻结了");
            return R.setResult(ResultCodeEnum.USER_BAN);
        }

        System.out.println("3");
        session.setAttribute("user",userDb);
        return R.ok();


    }

    //用户退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "/";
    }

    //帖子详情
    @GetMapping("/article")
    public String articleListView(String articleId, Model model){
        Article articleVo = articleService.getById(articleId);

        Article article = articleService.getOne(Wrappers.<Article>lambdaQuery()
                .eq(Article::getArticleId, articleVo.getArticleId())
                .select(Article::getArticleId, Article::getArticleLookNumber), false);


        //添加查看次数
        Integer articleLookNumber = article.getArticleLookNumber();
        if(Objects.isNull(articleLookNumber)||articleLookNumber<0){
            articleLookNumber=0;
        }
        articleLookNumber+=1;
        article.setArticleLookNumber(articleLookNumber);

        articleService.updateById(article);


        //帖子
        model.addAttribute("article",articleVo);

        //帖子类型
        if(Objects.isNull(article) && StringUtils.isNotEmpty(article.getArticleTypeId())){
            ArticleType articleType = articleTypeService.getOne(Wrappers.<ArticleType>lambdaQuery()
                    .eq(ArticleType::getArticleTypeId, article.getArticleTypeId())
                    .select(ArticleType::getArticleTypeName, ArticleType::getArticleTypeId), false);
            model.addAttribute("articleType",articleType);
        }

        //帖子评论
        List<CommentVo> commentList =  commentService.getArticleCommentList(article.getArticleId());
        model.addAttribute("commentList",commentList);
//        System.out.println("评论："+commentList);
        return "/view/article";
    }

    //点赞
    @PostMapping("/articleLike")
    @ResponseBody
    public R articleLike(HttpServletRequest request,String articleId){
        HttpSession session = request.getSession();
        if(Objects.nonNull(session.getAttribute("articleLikeTime"))){
            return R.error();
        }

        Article article = articleService.getById(articleId);
        Integer articleLikeNumber = article.getArticleLikeNumber();
        articleLikeNumber+=1;
        article.setArticleLikeNumber(articleLikeNumber);
        if(articleService.updateById(article)){
            session.setAttribute("articleLikeTime",true);
            return R.ok();
        }
        return R.error();
    }



}
