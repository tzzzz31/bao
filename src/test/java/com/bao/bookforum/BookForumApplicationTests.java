package com.bao.bookforum;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.bao.bookforum.entity.*;
import com.bao.bookforum.service.*;
import com.bao.bookforum.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class BookForumApplicationTests {

    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleTagListService articleTagListService;

    @Test
    void contextLoads() {

        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setUserName(i+"uName");
            user.setUserPassword(SecureUtil.md5("123456"));
            user.setUserFrozen(0);
            user.setUserRegisterTime(DateUtil.date());
            users.add(user);
        }
        userService.saveBatch(users,50);
    }

    //添加帖子相关的数据
    @Test
    public void addArticleData(){

        List<ArticleType> articleTypeList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ArticleType articleType = new ArticleType();
            articleType.setArticleTypeName("文章分类"+i);
            articleType.setArticleTypeSort(10);
            articleType.setArticleTypeAddTime(new Date());
            articleTypeList.add(articleType);
        }
        articleTypeService.saveBatch(articleTypeList);
        Random random = new Random();


        List<Article> articleList = new ArrayList<>();
        for (ArticleType articleType : articleTypeList){
            for (int i = 0; i < 6; i++) {
                Article article = new Article();
//                article.setArticleTypeId(articleType.getArticleTypeId());
                article.setUserId("zt");
                article.setArticleTitle("帖子标题："+i);
                article.setArticleAddTime(DateUtil.date());
                article.setArticleContext("帖子内容"+random.nextInt(1000));
                article.setArticleLikeNumber(0);
                article.setArticleLookNumber(0);
                article.setArticleCollectionNumber(0);
                articleList.add(article);
            }
        }
        articleService.saveBatch(articleList,50);

        List<ArticleTag> articleTagList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleTagName("帖子标签："+i);
            articleTag.setArticleTagAddtime(DateUtil.date());
            articleTagList.add(articleTag);
        }
        articleTagService.saveBatch(articleTagList,50);

        List<ArticleTagList> articleTagLists = new ArrayList<>();
        for (ArticleTag articleTag : articleTagList) {
            for (int i = 0; i < 3; i++) {
                ArticleTagList tagList = new ArticleTagList();
                tagList.setArticleId(articleList.get(random.nextInt(articleList.size())).getArticleId());
                tagList.setArticleTagId(articleTag.getArticleTagId());
                articleTagLists.add(tagList);
            }
        }
        articleTagListService.saveBatch(articleTagLists,50);

    }

    public static void main(String[] args) {
        String str="123456";
        System.out.println(CommonUtils.getHideMiddleStr(str));
    }



}
