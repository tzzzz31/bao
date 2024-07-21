package com.bao.bookforum.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.system.HostInfo;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.bao.bookforum.dto.article.ArticlePageDto;
import com.bao.bookforum.dto.article.PublishArticleActionDto;
import com.bao.bookforum.dto.user.UserDto;
import com.bao.bookforum.entity.ArticleTag;
import com.bao.bookforum.entity.ArticleType;
import com.bao.bookforum.entity.Comment;
import com.bao.bookforum.entity.User;
import com.bao.bookforum.exception.R;
import com.bao.bookforum.exception.ResultCodeEnum;
import com.bao.bookforum.service.*;
import com.bao.bookforum.utils.CommonPage;
import com.bao.bookforum.utils.CommonUtils;
import com.bao.bookforum.vo.ArticleVo;
import com.bao.bookforum.vo.CommentVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentService commentService;

    //获取文件路径
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, MultipartFile file){
        //判断文件是否为空
        if(file.isEmpty()){
            return null;
        }

        String fileName=file.getOriginalFilename();
        fileName= CommonUtils.getFileName(fileName);
        String filePath=CommonUtils.getUploadPath();
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath+ File.separator+fileName))) {
            out.write(file.getBytes());
            out.flush();
            return CommonUtils.getUploadFilePath()+fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //用户管理页面
    @GetMapping("/mannager")
    public String userManager(){
        return "/user/userManager";
    }

    //发布帖子界面
    @GetMapping("/publishArticle")
    public String releaseArticle(HttpServletRequest request, Model model){
        //获取类型
//        List<ArticleType> articleTypeList = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery().isNull(ArticleType::getArticleTypeId));
        List<ArticleType> articleTypeList = articleTypeService.list();
        model.addAttribute("articleTypeList",articleTypeList);

        //获取标签
        List<ArticleTag> articleTagList = articleTagService.list();
        model.addAttribute("articleTagList",articleTagList);
        return "/user/publishArticle";
    }

    //发布帖子
    @PostMapping("/publishArticleAction")
    @ResponseBody
    public R publishArticleAction(HttpServletRequest request , PublishArticleActionDto publishArticleActionDto, HttpServletResponse response){

        return articleService.publishArticleAction(publishArticleActionDto,request,response);

    }
    //我的文章
    @GetMapping("/myArticleList")
    public String myArticleList(HttpServletRequest request,Integer pageNumber,Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(Objects.isNull(pageNumber)||pageNumber<1){
            pageNumber=1;
        }
        Page<ArticleVo> articlePage = new Page<>(pageNumber, 15);
        IPage<ArticleVo> articleVoIPage = articleService.articleList(articlePage, null,user.getUserId(),null);
        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));
        return "/user/myArticleList";
    }

    //删除我的文章
    @PostMapping("/myArticleDel")
    @ResponseBody
    private R myArticleDel(String articleId){
        if(articleService.removeById(articleId)){
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/article/list")
    public String articleList(Model model,@Valid ArticlePageDto articlePageDto){

        IPage<ArticleVo> articleVoPage = new Page<>(articlePageDto.getPageNumber(), 20);
        IPage<ArticleVo> articleVoIPage = articleService.articleList(articleVoPage,articlePageDto.getArticleTitle(),null,null);
        model.addAttribute("articleVoIPage",CommonPage.restPage(articleVoIPage));
        if (StringUtils.isNotEmpty(articlePageDto.getArticleTitle())){
            model.addAttribute("articleTitle",articlePageDto.getArticleTitle());
        }
        return "/user/articleList";

    }

    //用户详情
    @GetMapping("/")
    public String userIndex(Model model,HttpServletRequest request){
        //获取用户
        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("userName",user.getUserName());
        model.addAttribute("userRegisterTime",user.getUserRegisterTime());
        model.addAttribute("userPassword",user.getUserPassword());
        model.addAttribute("userId",user.getUserId());

        return "/user/user";
    }

    //修改用户
    @PostMapping("/update")
    @ResponseBody
    public R userUpdate(@Valid UserDto userDto){

        User user = userService.getById(userDto.getUserId());
        if(Objects.isNull(user)){
            return R.error();
        }
        String userPassword = userDto.getUserPassword();
        if(StringUtils.isNotEmpty(userPassword)){
            userDto.setUserPassword(userPassword);
        }else {
            //要是没有修改密码，则把原来密码给修改后
            userDto.setUserPassword(null);
        }
        BeanUtils.copyProperties(userDto,user);
        System.out.println("要修改的密码："+user.getUserPassword());
        if(userService.updateById(user)){
            return R.setResult(ResultCodeEnum.UPDATE_SUCCESS);
        }
        return R.error();
    }

    //储存评论
    @PostMapping("/saveComment")
    @ResponseBody
    public R userSaveComment(HttpServletRequest request,String articleId,String commentContent){
        System.out.println(articleId);
        System.out.println("评论内容"+commentContent);
        if(StringUtils.isEmpty(articleId)||StringUtils.isEmpty(commentContent)){
            return R.error();
        }
        User user = (User) request.getSession().getAttribute("user");
        if(Objects.isNull(user)){
            return R.setResult(ResultCodeEnum.TIME_OUT);
        }
        String userId = user.getUserId();
        Comment comment = commentService.getOne(Wrappers.<Comment>lambdaQuery().eq(Comment::getUserId, userId).select(Comment::getCommentTime).orderByDesc(Comment::getCommentTime),false);
        //判断评论不为空，并且评论时间间隔大于5s

        if(Objects.nonNull(comment) && Objects.nonNull(comment.getCommentTime())){
            if(comment.getCommentTime().getTime()+5000>System.currentTimeMillis()){
                return R.setResult(ResultCodeEnum.TIME_ERROR);
            }
        }
        String random = UUID.randomUUID().toString();
        if(random.isEmpty()){
            return R.error();
        }
        Comment comment1 = new Comment();
        comment1.setCommentId(random);
        comment1.setArticleId(articleId);
        comment1.setUserId(userId);
        comment1.setCommentContext(commentContent);
        comment1.setCommentTime(DateUtil.date());
        comment1.setCommentLikeNumber(0);
        if(commentService.save(comment1)){
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment1,commentVo);
            commentVo.setUserName( userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserId,commentVo.getUserId()).select(User::getUserName)).getUserName());

            return R.ok(commentVo);
        }
        return R.error();
    }
}
