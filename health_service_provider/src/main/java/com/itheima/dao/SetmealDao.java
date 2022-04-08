package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/1-14:45
 */
public interface SetmealDao {
    //增加体检套餐
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map map);

    public Page<CheckGroup> selectByCondition(String queryString);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public List<Map<String, Object>> findSetmealCount();
}
