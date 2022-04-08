package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author 侯孟珂
 * @date 2022/4/3-15:41
 * 自定义Job，实施定时清理
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        //根据Redis总的保存的两个集合进行差值计算，获取垃圾图片的集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null ){
            for(String picName : set){
                //删除七牛云服务器上的照片
                QiniuUtils.deleteFileFromQiniu(picName);
                //删除Redis上面的图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("自定义任务执行，清理垃圾图片........"+picName);
            }

        }
    }

}
