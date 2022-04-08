package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Member;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author 侯孟珂
 * @date 2022/3/27-14:08
 * 检查项管理
 */


@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){

        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
        //如果进入catch，则checkItem调用失败
            e.printStackTrace();//打印异常信息
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    //检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult = checkItemService.pageQuery(queryPageBean);

       return pageResult;
    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    //删除检查项操作
    @RequestMapping("/delete")
    public Result delete(Integer id){

        try {
            checkItemService.deleteById(id);
        }catch (Exception e){
            //如果进入catch，则checkItem调用失败
            e.printStackTrace();//打印异常信息
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);

          }
    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id){

        try{
            CheckItem checkItem = checkItemService.findById(id);
                return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //编辑检查项操作
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){

        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            //如果进入catch，则checkItem调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }
    //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll(){

        try{
            //取得全部数据得用List*******
            List<CheckItem> list = (List<CheckItem>) checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
