package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author 侯孟珂
 * @date 2022/4/6-20:17
 * 手机验证码服务
 */


@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {


    @Autowired
    private JedisPool jedisPool;
    //预约验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //随机生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            //利用阿里云发送短息验证码
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
            System.out.println("短息验证码："+validateCode.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码存储在Redis5分钟
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,validateCode.toString());

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


        //登录验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //随机生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            //利用阿里云发送短息验证码
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
            System.out.println("短息验证码："+validateCode.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码存储在Redis5分钟
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,300,validateCode.toString());

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
