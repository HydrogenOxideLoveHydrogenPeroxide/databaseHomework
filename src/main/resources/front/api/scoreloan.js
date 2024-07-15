// 查询列表接口
const ScoreLoanPageApi = (params) => {
    return $axios({
        url: '/scoreLoan/mypage',
        method: 'get',
        params
    })
}

// 新增接口
const addScoreLoan = (params) => {
    return $axios({
        url: '/scoreLoan',
        method: 'post',
        data: { ...params }
    })
}

//取消贷款
const cancelHandle = (id) => {
    return $axios({
        url: '/scoreLoan/statusUpdate',
        method: 'put',
        params: {id}
    })
}

//删除
const deleteHandle= (id) => {
    return $axios({
        url: '/scoreLoan',
        method: 'delete',
        params: {id}
    })
}