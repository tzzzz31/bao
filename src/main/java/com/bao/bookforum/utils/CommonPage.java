package com.bao.bookforum.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import com.bao.bookforum.entity.User;

import java.util.List;

@Data
public class CommonPage<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    public static <T> CommonPage<T> restPage(List<T> list){
        CommonPage<T> result=new CommonPage<T>();
        Page<T> pageInfo=new Page<T>().setRecords(list);
        result.setTotalPage((int)pageInfo.getPages());
        result.setPageNumber((int)pageInfo.getCurrent());
        result.setPageSize((int)pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getRecords());

        return result;
    }

    public static <T> CommonPage<T> restPage(IPage<T> page) {
        CommonPage<T> result = new CommonPage<>();
        result.setTotalPage((int) page.getPages());
        result.setPageNumber((int) page.getCurrent());
        result.setPageSize((int) page.getSize());
        result.setTotal(page.getTotal());
        result.setList(page.getRecords());

        return result;
    }



}
