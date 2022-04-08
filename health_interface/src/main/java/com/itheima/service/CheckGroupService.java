package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.awt.*;
import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/3/30-20:29
 */
public interface CheckGroupService {
    //增加检查组
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);

    public PageResult QueryPage(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    public List<CheckGroup> findAll();
}
