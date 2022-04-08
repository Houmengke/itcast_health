package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 侯孟珂
 * @date 2022/3/31-20:52
 * 体检套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private SetmealService setmealService;
    //图片上传
    @RequestMapping("/upload")
    //这里的@RequestParam("imgFile")中的参数与前端页面el-upload组件中的name保持一致
    //方法参数类型为MultipartFile，并用@RequestParam注解
    public Result uploud(@RequestParam("imgFile") MultipartFile imgFile) throws IOException {

        String originalFileName = imgFile.getOriginalFilename();//文件原始名
        //取文件名字扩展名
        int index = originalFileName.lastIndexOf(".");
        String extention = originalFileName.substring(index - 1);
        String fileName = UUID.randomUUID().toString()+extention;
       try {
           QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
           //将上传图片名称存入Redis，基于Redis的Set集合存储
           jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
       }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS ,fileName);
    }
    //添加套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal ,Integer[] checkgroupIds ) {

        try {
            setmealService.add(setmeal,checkgroupIds);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        return setmealService.QueryPage(queryPageBean);
    }
}
