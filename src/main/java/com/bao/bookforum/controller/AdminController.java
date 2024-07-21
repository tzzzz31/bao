package com.bao.bookforum.controller;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.bao.bookforum.dto.article.ArticlePageDto;
import com.bao.bookforum.dto.article.ArticleTypeAddDto;
import com.bao.bookforum.dto.article.ArticleTypeUpdateDto;
import com.bao.bookforum.dto.user.UserDto;
import com.bao.bookforum.entity.*;
import com.bao.bookforum.exception.ResultCodeEnum;
import com.bao.bookforum.service.*;
import com.bao.bookforum.vo.ArticleTypeVo;
import com.bao.bookforum.vo.ArticleVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import cn.hutool.system.HostInfo;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.bao.bookforum.dto.user.UserListPageDto;
import com.bao.bookforum.utils.CommonPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.api.R;
import com.bao.bookforum.exception.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/bao123")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IArticleTagListService articleTagListService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;

    //管理员登录页面
    @GetMapping("/login")
    public String login(HttpServletRequest request){
        if (Objects.nonNull(request.getSession().getAttribute("admin"))){
            return "redirect:/bao123/";
        }
        return "/admin/login";
    }

    //管理员登录方法
    @PostMapping("/adminLogin")
    @ResponseBody
    public R adminLogin(HttpServletRequest request,String adminName,String adminPassword, String verifyCode){
        HttpSession session = request.getSession();
        String circleCaptchaCode =(String) session.getAttribute("circleCaptchaCode");
        //判断输入的验证码是否正确
//        System.out.println("输入的验证码："+verifyCode);
//        System.out.println("生成的验证码："+circleCaptchaCode);
        if(!StringUtils.isNotEmpty(verifyCode)||!verifyCode.equals(circleCaptchaCode)){
           //移除之前生成的验证码
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ERROR_CIRCLE);
        }
        //判断用户名或密码是否为空
        if(!StringUtils.isNotEmpty(adminName)||!StringUtils.isNotEmpty(adminPassword)){
            return R.setResult(ResultCodeEnum.ERROR_NULL);
        }
        //判断用户名对应密码是否正确
        Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName, adminName).eq(Admin::getAdminPassword,adminPassword),false);
        if (Objects.isNull(admin)){
            session.removeAttribute("circleCaptchaCode");
            return R.setResult(ResultCodeEnum.ADMING_ERROR);
        }
        session.setAttribute("admin",admin);
        return R.ok();
    }


    //管理员退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/bao123/login";
    }


    //管理端 首页
    @GetMapping("/")
    public String adminIndex(Model model){
        //系统信息
        OsInfo osInfo = SystemUtil.getOsInfo();
        HostInfo hostInfo = SystemUtil.getHostInfo();
        model.addAttribute("nsName",osInfo.getName());
        model.addAttribute("hostAddress",hostInfo.getAddress());
        //文章数量
        int articleTagCount=articleTagListService.count();
        int articleCount=articleService.count();
        model.addAttribute("articleTagCount",articleTagCount);
        model.addAttribute("articleCount",articleCount);
        //用户数量
        int userCount= userService.count();
        model.addAttribute("userCount",userCount);
        return "/admin/index";
    }

//管理端 用户列表
    @GetMapping("/user/list")
    public String userList(@Valid UserListPageDto userListPageDto, Model model){
        Integer pageNumber = userListPageDto.getPageNumber();
        Integer pageSize = userListPageDto.getPageSize();
        String userName=userListPageDto.getUserName();

        IPage<User> userPage = new Page<>(pageNumber, pageSize);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery().orderByDesc(User::getUserRegisterTime);
        if(StringUtils.isNotEmpty(userName)){
            userLambdaQueryWrapper.like(User::getUserName,userName);
            model.addAttribute("userName",userName);
        }
        IPage<User> userIPage = userService.page(userPage, userLambdaQueryWrapper);

        model.addAttribute("userPage",CommonPage.restPage(userIPage));

        return "/admin/userList";
    }

//删除用户
    @PostMapping("/user/del")
    @ResponseBody
    public R userDel(String userId,HttpServletRequest request){
           if(!StringUtils.isNotEmpty(userId)){
               return R.error();
           }

//        if(articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getUserId,userId))>0){
//            return R.error();
//        }

        if(userService.removeById(userId)){;
            return R.ok();
        }
        return R.error();
    }

    //用户修改
    @PostMapping("/user/update")
    @ResponseBody
    public R userUpdate(@Valid UserDto userDto){

        User user = userService.getById(userDto.getUserId());
        if(Objects.isNull(user)){
            return R.error();
        }
        Date userRegisterTime = user.getUserRegisterTime();
        String userPassword = userDto.getUserPassword();
        if(StringUtils.isNotEmpty(userPassword)){
            //用户密码和注册时间用md5加密
            userDto.setUserPassword(userPassword);
        }else {
            //要是没有修改密码，则把原来密码给修改后
            userDto.setUserPassword(null);
        }
        BeanUtils.copyProperties(userDto,user);

        if(userService.updateById(user)){
            return R.ok();
        }
        return R.error();
    }

    //帖子类型列表,包含帖子数量
    @GetMapping("/article/type/list")
    public String articleTypeList(Model model){
        List<ArticleTypeVo> articleTypeVoList = articleTypeService.articleTypeList();
//        model.addAttribute("articleTypeVoList",CommonPage.restPage(articleTypeVoList));
//        System.out.println("123333333333333333333333333"+articleTypeVoList);
        model.addAttribute("articleTypeVoList",articleTypeVoList);
        return "/admin/articleTypeList";
    }

    @PostMapping("/article/type/add")
    @ResponseBody
    public R addOrUpdate(@Valid ArticleTypeAddDto articleTypeAddDto){
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeAddDto,articleType);
        articleType.setArticleTypeAddTime(DateUtil.date());
        if(articleTypeService.save(articleType)){
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/article/type/update")
    @ResponseBody
    public R update(@Valid ArticleTypeUpdateDto articleTypeUpdateDto){
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeUpdateDto,articleType);

        String articleTypeName = articleType.getArticleTypeName();
        Integer articleTypeSort = articleType.getArticleTypeSort();

        if(StringUtils.isNotEmpty(articleTypeName)){
            articleType.setArticleTypeName(null);
        }
        if(Objects.isNull(articleTypeSort)){
            articleType.setArticleTypeSort(null);
        }

        if(articleTypeService.updateById(articleType)){
            return R.ok();
        }
        return R.error();
    }

    //删除帖子分类
    @PostMapping("/article/type/del")
    @ResponseBody
    public R articleTypeDel(@NotBlank(message = "帖子分类id不能为空") String articleTypeId){

//       if (articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getArticleId,articleTypeId))>0){
//           return R.error();
//       }

        if(articleTypeService.removeById(articleTypeId)){
            return R.ok();
        }
        return R.error();

    }

    //帖子标签列
    @GetMapping("/article/tag/list")
    public String articleTagList(Model model){
        List<ArticleTag> articleTagList = articleTagService.list(Wrappers.<ArticleTag>lambdaQuery().orderByDesc(ArticleTag::getArticleTagAddtime));
        model.addAttribute("articleTagList",articleTagList);
        return "/admin/articleTagList";

    }

    //帖子标签添加 修改
    @PostMapping("/article/tag/addOrUpdate")
    @ResponseBody
    public R articleTagAddOrUpdate(ArticleTag articleTag){

        String articleTagId = articleTag.getArticleTagId();
        if (StringUtils.isNotEmpty(articleTagId)){
            if(articleTagService.updateById(articleTag)){
                return R.ok();
            }
            return R.error();
        }

        articleTag.setArticleTagAddtime(DateUtil.date());
        if(articleTagService.save(articleTag)){
            return R.ok();
        }
        return R.error();
    }

    //帖子标签删除
    @PostMapping("/article/tag/del")
    @ResponseBody
    public R articleTagDel(String articleTagId){

        if(!StringUtils.isNotEmpty(articleTagId)){
            return R.error();
        }

        //被使用的标签无法删除，只能先删除与文章的关联
        if(articleTagListService.count(Wrappers.<ArticleTagList>lambdaQuery().eq(ArticleTagList::getArticleTagId,articleTagId))>0){
            return R.error();
        }

        if(articleTagService.removeById(articleTagId)){
            return R.ok();
        }
        return R.error();
    }

    //帖子列表
    @GetMapping("/article/list")
    public String articleList(Model model,@Valid ArticlePageDto articlePageDto){

        IPage<ArticleVo> articleVoPage = new Page<>(articlePageDto.getPageNumber(), 20);
        IPage<ArticleVo> articleVoIPage = articleService.articleList(articleVoPage,articlePageDto.getArticleTitle(),null,null);
        model.addAttribute("articleVoIPage",CommonPage.restPage(articleVoIPage));
        if (StringUtils.isNotEmpty(articlePageDto.getArticleTitle())){
            model.addAttribute("articleTitle",articlePageDto.getArticleTitle());
        }
        return "/admin/articleList";

    }

    //帖子删除
    @PostMapping("/article/del")
    @ResponseBody
    private R articleDel(String articleId){
        if(articleService.removeById(articleId)){
            return R.ok();
        }
        return R.error();
    }
}
