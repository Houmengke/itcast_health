package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/3/30-20:22
 * 检查组控制
 */

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;
    //添加检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){

        try{
            checkGroupService.add(checkGroup,checkitemIds);

        }catch (Exception e){
                e.printStackTrace();;
                return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        return checkGroupService.QueryPage(queryPageBean);
    }
    //根据id查找检查组信息
    @RequestMapping("/findById")
    public Result findById(Integer id){

        try{
            CheckGroup checkGroup=checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);

        }catch (Exception e){
            e.printStackTrace();;
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }


    }
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){

        try{
            List<Integer> list=checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);

        }catch (Exception e){
            e.printStackTrace();;
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }


    }
    //添加检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){

        try{
            checkGroupService.edit(checkGroup,checkitemIds);

        }catch (Exception e){
            e.printStackTrace();;
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(){

        try{
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);

        }catch (Exception e){
            e.printStackTrace();;
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }


    }

}
