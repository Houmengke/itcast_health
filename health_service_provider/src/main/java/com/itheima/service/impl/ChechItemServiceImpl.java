package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/3/29-15:57
 *检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class ChechItemServiceImpl implements CheckItemService {
    //注入DAO对象
    @Autowired
    private CheckItemDao checkItemDao;
    //检查项新增
    public void add(CheckItem checkItem) {

        checkItemDao.add(checkItem);

    }
    //检查项分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //将查询，分页的条件取出
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用Mybatis提供的分页助手进行分页
        PageHelper.startPage(currentPage,pageSize);
        //存储dao层数据
        Page<CheckItem> page =checkItemDao.selectByCondition(queryString);
        //转换成PageResult类型
        Long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }
        //根据id删除
    @Override
    public void deleteById(Integer id) {
        //如果检查项已经在检查组里，不允许删除，先判断是否存在关联，然后再进行删除
        Long total = checkItemDao.findTotalByCheckItemId(id);
        if (total > 0){
            //查询总数大于0，说明存在关联不能删除
            new RuntimeException();

        }

        checkItemDao.deleteById(id);
    }
    //根据id查
    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }
    //编辑更新检查项
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);

    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> list = checkItemDao.findAll();
        return list;
    }
}
