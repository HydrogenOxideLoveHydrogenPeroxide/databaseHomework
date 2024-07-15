// 查询列表接口
const getTaskUPage = (params) => {
    return $axios({
        url: '/tasku/admin/page',
        method: 'get',
        params
    })
}

//通过审核的接口
const passTaske = (taskUId) => {
    return $axios({
        url: '/tasku/pass',
        method: 'Get',
        params: { taskUId }
    })
}
// 不通过的接口
const onPassTaske = (taskUId) => {
    return $axios({
        url: '/tasku/onPass',
        method: 'Get',
        params: { taskUId }
    })
}