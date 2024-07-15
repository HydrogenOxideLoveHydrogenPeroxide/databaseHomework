package com.shoppingMall.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shoppingMall.reggie.common.CustomException;
import com.shoppingMall.reggie.entity.Task;
import com.shoppingMall.reggie.entity.TaskU;
import com.shoppingMall.reggie.mapper.TaskMapper;
import com.shoppingMall.reggie.service.TaskService;
import com.shoppingMall.reggie.service.TaskUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskUService taskUService;

    @Autowired
    private TaskService taskService;
    @Override
    public void remove(Integer id) {
        LambdaQueryWrapper<TaskU> taskULambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类ID进行查询
        taskULambdaQueryWrapper.eq(TaskU::getTaskid,id);
        int count1 = (int) taskUService.count(taskULambdaQueryWrapper);
        if(count1>0){
            //已经被接了
            throw new CustomException("当前任务以接");
        }

        super.removeById(id);

    }

    @Override
    public Page<Task> getTasksByUserId(Long userId, int page, int pageSize) {
        LambdaQueryWrapper<TaskU> taskUQueryWrapper = new LambdaQueryWrapper<>();
        taskUQueryWrapper.eq(TaskU::getUserid, userId);
        List<TaskU> taskUList = taskUService.list(taskUQueryWrapper);

        // 提取 taskIds
        List<Long> taskIds = taskUList.stream()
                .map(TaskU::getTaskid)
                .collect(Collectors.toList());

        if (taskIds.isEmpty()) {
            return new Page<>(page, pageSize);
        }

        // 分页构造器
        Page<Task> pageInfo = new Page<>(page, pageSize);

        // 条件构造器
        LambdaQueryWrapper<Task> taskQueryWrapper = new LambdaQueryWrapper<>();
        taskQueryWrapper.in(Task::getId, taskIds);
        taskQueryWrapper.orderByDesc(Task::getCreateTime);

        // 进行分页查询
        taskService.page(pageInfo, taskQueryWrapper);

        // 映射 TaskU 的 status 到 Task
        Map<Long, Integer> taskUStatusMap = taskUList.stream()
                .collect(Collectors.toMap(TaskU::getTaskid, TaskU::getStatus));

        List<Task> tasks = pageInfo.getRecords().stream()
                .map(task -> {
                    Integer status = taskUStatusMap.get(task.getId());
                    if (status != null) {
                        task.setStatus(status); // 映射 TaskU 的 status 到 Task
                    }
                    return task;
                })
                .collect(Collectors.toList());

        pageInfo.setRecords(tasks);

        return pageInfo;
    }
}
