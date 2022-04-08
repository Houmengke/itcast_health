package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 侯孟珂
 * @date 2022/3/30-20:37
 * 检查组服务
 */

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    //注入Dao对象
    @Autowired
    private CheckGroupDao checkGroupDao;

    //添加检查组
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
            //新增检查组，操作t_checkgroup表
            checkGroupDao.add(checkGroup);
            //设置检查组和检查项表，多对多关联。操作t_checkgroup_checkitem表*******
            Integer checkGroupId = checkGroup.getId();
           this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }
        //分页查询
    @Override
    public PageResult QueryPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer padeSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //使用Mybatis分页助手
        PageHelper.startPage(currentPage,padeSize);
        //存储到层返回数据
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);

        Long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total,rows);
    }
        //根据id查询检查组信息
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
      List<Integer> list = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return list;
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.edit(checkGroup);
        //如何更新关联表，可能得考虑到有增加，有删除的选项
        //解决办法，先全部删除，然后在重新添加新的关系
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteAssociation(checkGroupId);
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);

           }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> list = checkGroupDao.findAll();
        return list;
    }

    //代码冗余，重新构造一个方法
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){

        if (checkitemIds != null && checkitemIds.length >0){
            //遍历数据
            for (Integer checkitemId :checkitemIds){
                //建立Map存储对应数据
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
