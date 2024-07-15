package com.shoppingMall.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.dto.TaskDTO;
import com.shoppingMall.reggie.entity.Task;
import com.shoppingMall.reggie.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 发布新任务
     * @param taskDTO
     * @return
     */
    @PostMapping
    public R InsertTask(@RequestBody TaskDTO taskDTO){
        log.info("category:{}",taskDTO);
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO,task);
        task.setStatus(0);
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        taskService.save(task);
        return R.success("任务发布成功！");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        //分页构造器
        Page<Task> pageInfo=new Page<>(page,pageSize);
        //条件构造器对象
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Task::getCreateTime);

        //进行分页查询
        taskService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除任务
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Integer id){
        taskService.remove(id);
        return R.success("任务删除成功");
    }

    /**
     * 根据id修改任务信息
     * @param dto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Task dto){
        log.info("修改分类信息:",dto);
        Task task = new Task();
        BeanUtils.copyProperties(dto,task);
        task.setUpdateTime(LocalDateTime.now());
        taskService.updateById(task);
        return R.success("修改任务信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param task
     * @return
     */
    @GetMapping("/list")
    public R<List<Task>> list(Task task){
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();//条件构造器
        queryWrapper.eq(Task::getStatus, task.getStatus());//2通过3失败//添加条件
        queryWrapper.orderByDesc(Task::getCreateTime);//添加排序条件
        List<Task> list = taskService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/listpage")
    public R<Page> listpage(int page,int pageSize){

        //分页构造器
        Page<Task> pageInfo=new Page<>(page,pageSize);
        //条件构造器对象
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Task::getCreateTime);

        //进行分页查询
        taskService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }









}
