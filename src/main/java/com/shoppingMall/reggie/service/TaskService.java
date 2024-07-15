package com.shoppingMall.reggie.service;

import com.shoppingMall.reggie.entity.Task;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TaskService extends IService<Task> {
    void remove(Integer id);

    Page<Task> getTasksByUserId(Long userId, int page, int pageSize);
}
