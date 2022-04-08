package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.apache.xmlbeans.impl.jam.mutable.MElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/6-20:59
 * 订单在线服务
 */

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        //从Redis中取出验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //页面取出输入的验证码
        String validateCode = (String) map.get("validateCode");
        //比对验证码
        if (validateCodeInRedis != null && validateCode != null && validateCodeInRedis.equals(validateCode)) {
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            Result result = null;

            try {
                result = orderService.order(map);
            }catch (Exception e){
                e.printStackTrace();
                return result;
            }
            if (result.isFlag()){
                try {

                    // SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,(String)map.get("orderDate"));
                    System.out.println("体检预约成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            return result;

        }else{
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);

        }


    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }


}