package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import javax.swing.*;
import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/3/29-16:02
 */
public interface CheckItemDao {
    //增加检查项
    public void add(CheckItem checkItem);
    //检查项分页，由于使用Mybatis分页助手，返回值类型必须使用Page<泛型>
    public Page<CheckItem> selectByCondition(String queryString);
    //检查项删除
    public void deleteById(Integer id);
    //判断检查项和检查组是否有关联，计算关联总数
    public Long findTotalByCheckItemId(Integer id);
    //根据id查
    public CheckItem findById(Integer id);
    //编辑检查项
    public void edit(CheckItem checkItem);
    //查询所有检查项
    public List<CheckItem> findAll();
}
