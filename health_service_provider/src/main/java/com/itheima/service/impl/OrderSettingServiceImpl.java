package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 侯孟珂
 * @date 2022/4/4-19:24
 * 预约管理
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> list) {
        //判断数据是否存在，然后遍历
        if(list != null&& list.size()>0){
            for (OrderSetting orderSetting :list){
                //判断日期是否进行预约设置
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate >0){
                    //当前日期被设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }

    }


    //根据月份查询预约设置
    @Override
    public List<Map> getOrderSettingByMonth(String date) {

        String begin = date +"-1";
        String end = date +"-31";
        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list != null&& list.size() >0){
            for (OrderSetting orderSetting :list){
                Map<String,Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }

        }

        return result;



    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //判断选择的日期是否已经进行了设置
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            //进行过设置，则更新
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //添加操作
            orderSettingDao.add(orderSetting);
        }

    }
}
