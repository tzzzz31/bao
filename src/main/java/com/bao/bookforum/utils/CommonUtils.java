package com.bao.bookforum.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

public class CommonUtils {

//    public static Pattern patternPhone=Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
//    public static Pattern patternInt=Pattern.compile("^[-\\+]?[\\d]*$");
    public static String uploadFilePath="/"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"/upload/img/";

    //验证码
    public static CircleCaptcha getCaptcha(HttpServletRequest request){
        CircleCaptcha captcha= CaptchaUtil.createCircleCaptcha(80,40,4,0);
        String circleCaptchaCode =captcha.getCode();
        request.getSession().setAttribute("circleCaptchaCode",circleCaptchaCode);
        return captcha;
    }

    //处理文件名称
    public static String getFileName(String fileName){
        int index=fileName.lastIndexOf(".");
        fileName=fileName.substring(0,index)+"-"+ IdUtil.simpleUUID()+fileName.substring(index);
        return fileName;
    }

    //获取文件路径
    public static String getUploadFilePath(){
        return uploadFilePath;
    }

    public static String getUploadPath(){
        File path=null;
        try {
            path=new File(ResourceUtils.getURL("classpath:").getPath());
        }catch (FileNotFoundException e){
            e.printStackTrace();;
        }
        //判断路径是否存在，否则创建
        if (!path.exists()){
            path=new File("");
        }
        File uploda=new File(path.getAbsolutePath(),"static"+uploadFilePath);
        if (!uploda.exists()){
            uploda.mkdir();
        }
        return uploda.getAbsolutePath();
    }

    //隐藏字符串中间字符，只显示首尾字符
    public static String getHideMiddleStr(String str){

        if(StringUtils.isEmpty(str)){
            return null;
        }

        char[] chars = str.toCharArray();

        if(chars.length<=1){

            return chars[0]+"**";
        }

        return chars[0]+"**"+chars[chars.length-1];
    }
}
