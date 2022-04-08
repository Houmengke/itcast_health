package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/4-19:14
 * 预约设置
 */
public interface OrderSettingService {

   public void add(List<OrderSetting> data);

   public List<Map> getOrderSettingByMonth(String date);

   public void editNumberByDate(OrderSetting orderSetting);
}
