package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/3/30-20:43
 */
public interface CheckGroupDao {
     //增加检查组
    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(Map<String, Integer> map);

    public Page<CheckGroup> selectByCondition(String queryString);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup);

    public void deleteAssociation(Integer checkGroupId);

    public List<CheckGroup> findAll();
}
