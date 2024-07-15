
// 新增接口
function addTaskU(taskId){
    return $axios({
        url: '/tasku/add',
        method: 'get',
        params: { taskId }
    })
}

function getTaskPage(params) {
    return $axios({
        url: '/tasku/page',
        method: 'get',
        params
    })
}

function submitTask(taskUId, status) {
    return $axios({
        url: '/tasku/changeStatus',
        method: 'get',
        params: {taskUId, status}
    })
}

