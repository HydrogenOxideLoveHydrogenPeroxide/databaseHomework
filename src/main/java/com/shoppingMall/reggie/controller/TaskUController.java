package com.shoppingMall.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.entity.Task;
import com.shoppingMall.reggie.entity.TaskU;
import com.shoppingMall.reggie.service.TaskService;
import com.shoppingMall.reggie.service.TaskUService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasku")
@Slf4j
public class TaskUController {

    @Autowired
    private TaskUService taskUService;

    @Autowired
    private TaskService taskService;


    /**
     * 接单
     * @param taskId
     * @return
     */
    @GetMapping("/add")
    @Transactional
    public R InsertTaskU(long taskId){
        log.info("task:{}",taskId);

        Task task = taskService.getById(taskId);
        if (task==null){
            return R.error("任务不存在！");
        }
        if (task.getEndTime().isBefore(LocalDateTime.now())){
            return R.error("任务已过期！");
        }

        TaskU taskU = new TaskU();//添加接单
        taskU.setStatus(0);//设置为未完成
        taskU.setTaskid(taskId);//设置任务id
        taskU.setTaskName(task.getTaskName());
        taskU.setScore(task.getScore());
        taskU.setUserid(BaseContext.getCurrentId());
        taskU.setCreateTime(LocalDateTime.now());
        taskU.setUpdateTime(LocalDateTime.now());
        taskUService.save(taskU);

        //修改单子状态
        Task byId = taskService.getById(taskId);
        byId.setStatus(1);
        taskService.updateById(byId);

        return R.success("接单成功成功！");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Task>> page(int page, int pageSize) {
        Long userId = BaseContext.getCurrentId();

        // 获取分页任务列表
        Page<Task> pageInfo = taskService.getTasksByUserId(userId, page, pageSize);
        return R.success(pageInfo);
    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/admin/page")
    public R<Page> adminPage(int page, int pageSize){

        //分页构造器
        Page<TaskU> pageInfo=new Page<>(page,pageSize);
        //条件构造器对象
        LambdaQueryWrapper<TaskU> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskU::getStatus,1);

        queryWrapper.orderByAsc(TaskU::getUpdateTime);

        //进行分页查询
        taskUService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }


    @GetMapping("/changeStatus")
    public R submitTask(@RequestParam Long taskUId, @RequestParam Integer status) {
        boolean isUpdated = taskUService.updateTaskUStatus(taskUId, status);
        if (isUpdated) {
            return R.success("任务状态更新成功！");
        } else {
            return R.error("任务状态更新失败");
        }
    }

    //通过接口
    @GetMapping("/pass")
    public R passTaskU(@RequestParam Long taskUId){
        if (taskUService.passTask(taskUId)){
            return R.success("通过成功");
        }
        return R.error("服务异常！");

    }

    //通过接口
    @GetMapping("/onPass")
    public R onPassTaskU(@RequestParam Long taskUId){
        if (taskUService.onPassTask(taskUId)){
            return R.success("通过成功");
        }
        return R.error("服务异常！");
    }
}
