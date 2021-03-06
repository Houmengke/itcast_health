package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/6-21:13
 */
public interface OrderService {

   public Result order(Map map) throws Exception;

   public Map findById(Integer id) throws Exception;
}
