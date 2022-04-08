package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/4/1-14:38
 * 体检套餐服务
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutpPth;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealIds = setmeal.getId();
        this.setSetmealAndCheckGroup(setmealIds,checkgroupIds);
        //将图片名称保存在Redis集合中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
            //当添加套餐后需要生成静态页面（套餐列表，和套餐详情页面）
            // generateMobileStaticHtml();

    }
    //生成当前方法所需要的静态页面
//    public void generateMobileStaticHtml(){
//
//        //准备模板文件中所需的数据
//        List<Setmeal> list = this.findAll();
//        //生成套餐列表静态页面
//        generateMobileSetmealListHtml(list);
//        //生成套餐详情静态页面（多个）
//        generateMobileSetmealDetailHtml(list);
//    }
//
//    //生成套餐列表静态页面
//    public void generateMobileSetmealListHtml(List<Setmeal> list) {
//        Map map = new HashMap();
//        map.put("setmealList", list);
//        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
//
//    }
//
//
//    //生成套餐详情静态页面（多个）
//    public void generateMobileSetmealDetailHtml(List<Setmeal> list) {
//        for (Setmeal setmeal : list) {
//            Map map = new HashMap();
//            map.put("setmeal", setmealDao.findById(setmeal.getId()));
//            this.generateHtml("mobile_setmeal_detail.ftl",
//                    "setmeal_detail_"+setmeal.getId()+".html",
//                    map);
//        }
//    }
//
//    //用于生成静态页面
//    public void generateHtml(String templateName,String htmlPageName,Map map){
//        //获取配置对象
//        Configuration configuration = freeMarkerConfigurer.getConfiguration();
//        Writer out = null;
//        try {
//            // 加载模版文件
//            Template template = configuration.getTemplate(templateName);
//            // 构造输出流
//            out = new FileWriter(new File(outPutpPth+ "/" +htmlPageName));
//            // 输出文件
//            template.process(map, out);
//
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //分页查询
    @Override
    public PageResult QueryPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer padeSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //使用Mybatis分页助手
        PageHelper.startPage(currentPage,padeSize);
        //存储到层返回数据
        Page<CheckGroup> page = setmealDao.selectByCondition(queryString);

        Long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total,rows);
    }


    //移动端查询所有套餐
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }


    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    //多对多关系表
    public void setSetmealAndCheckGroup(Integer setmealIds,Integer[] checkgroupIds){
            if(checkgroupIds !=null &&checkgroupIds.length > 0){
                for(Integer checkgroupId :checkgroupIds){
                    Map<String,Integer> map = new HashMap<>();
                    map.put("setmealId",setmealIds);
                    map.put("checkgroupId",checkgroupId);
                    setmealDao.setSetmealAndCheckGroup(map);
                }

            }

    }

}

