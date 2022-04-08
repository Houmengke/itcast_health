package com.itheima.dao;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/4-19:27
 */
public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
    public List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
    public OrderSetting findByOrderDate(Date parseString2Date);
    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);

}
