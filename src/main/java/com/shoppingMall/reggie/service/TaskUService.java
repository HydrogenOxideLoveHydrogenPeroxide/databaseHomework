package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.entity.TaskU;

public interface TaskUService extends IService<TaskU>{
    boolean updateTaskUStatus(long taskUId, Integer status);

    boolean passTask(Long taskUId);

    boolean onPassTask(Long taskUId);
}
