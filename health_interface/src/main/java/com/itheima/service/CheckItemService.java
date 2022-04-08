package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/3/27-14:37
 * 检查项服务接口
 */
public interface CheckItemService {

    //增加检查项
    public void add(CheckItem checkItem);
    //检查项分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean);
    //删除检查项
    public void deleteById(Integer id);
    //根据id查检查信息
    public CheckItem findById(Integer id);
     //编辑检查项
    public void edit(CheckItem checkItem);
      //查询所有检查项
    public List<CheckItem> findAll();
}
