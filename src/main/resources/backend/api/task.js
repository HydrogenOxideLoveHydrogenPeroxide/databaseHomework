// 查询列表接口
const getTaskPage = (params) => {
    return $axios({
        url: '/task/page',
        method: 'get',
        params
    })
}

// 编辑页面反查详情接口
const queryTaskById = (id) => {
    return $axios({
        url: `/task/${id}`,
        method: 'get'
    })
}

// 删除当前列的接口
const deleTaske = (id) => {
    return $axios({
        url: '/task',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editTask = (params) => {
    return $axios({
        url: '/task',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addTask = (params) => {
    return $axios({
        url: '/task',
        method: 'post',
        data: { ...params }
    })
}