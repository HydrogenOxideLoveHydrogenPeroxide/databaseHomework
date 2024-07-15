package com.shoppingMall.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shoppingMall.reggie.entity.Task;
import com.shoppingMall.reggie.entity.TaskU;
import com.shoppingMall.reggie.entity.User;
import com.shoppingMall.reggie.mapper.ScoreMapper;
import com.shoppingMall.reggie.mapper.TaskUMapper;
import com.shoppingMall.reggie.mapper.UserMapper;
import com.shoppingMall.reggie.service.TaskService;
import com.shoppingMall.reggie.service.TaskUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TaskUServiceImpl extends ServiceImpl<TaskUMapper, TaskU> implements TaskUService {
    @Autowired
    private TaskUMapper taskUMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 更新 TaskU 的 status 字段
     *
     * @param taskId Task 的 ID
     * @param status 新的状态值
     * @return 是否更新成功
     */
    public boolean updateTaskUStatus(long taskId, Integer status) {
        // 查询 TaskU 记录
        LambdaQueryWrapper<TaskU> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskU::getTaskid, taskId);
        TaskU taskU = taskUMapper.selectOne(queryWrapper);

        if (taskU == null) {
            return false; // 如果没有找到对应的记录，返回 false
        }
        Task byId = taskService.getById(taskId);
        if (byId.getEndTime().isBefore(LocalDateTime.now())&&status==1){
            //超时了
            return false;
        }

        // 更新 status 字段
        taskU.setStatus(status);

        // 更新数据库记录
        return taskUMapper.updateById(taskU) > 0;
    }

    @Transactional
    @Override
    public boolean passTask(Long taskUId) {
        //首先找到这个
        TaskU taskU = taskUMapper.selectById(taskUId);
        if (taskU==null){
            return false;
        }
        //首先要更新审核字段 2通过3失败
        taskU.setStatus(2);

        //更新
        taskUMapper.updateById(taskU);


        //找到人
        System.out.printf("id:"+taskU.getUserid());
        User u = userMapper.selectById(taskU.getUserid());

        Integer sc = u.getScores();
        sc += taskU.getScore();
        u.setScores(sc);

        //更新用户的积分和创建积分获取记录

        userMapper.updateById(u);
        scoreMapper.addScoreHisByid(u.getId(),"完成任务-"+taskU.getTaskName(),taskU.getScore(),u.getScores());
        Task task = taskService.getById(taskU.getTaskid());
        task.setStatus(2);
        taskService.updateById(task);
        return true;
    }

    @Override
    public boolean onPassTask(Long taskUId) {
        //首先找到这个
        TaskU taskU = taskUMapper.selectById(taskUId);
        if (taskU==null){
            return false;
        }
        //首先要更新审核字段 2通过3失败
        taskU.setStatus(3);
        //更新
        taskUMapper.updateById(taskU);
        Task task = taskService.getById(taskU.getTaskid());
        task.setStatus(2);
        taskService.updateById(task);
        return true;
    }
}
