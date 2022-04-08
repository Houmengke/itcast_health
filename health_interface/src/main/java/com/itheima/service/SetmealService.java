package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/1-14:39
 */
public interface SetmealService {
   public void add(Setmeal setmeal,Integer[] checkgroupIds);

   public PageResult QueryPage(QueryPageBean queryPageBean);

   public List<Setmeal> findAll();

   public Setmeal findById(int id);

   public List<Map<String,Object>> findSetmealCount();
}
