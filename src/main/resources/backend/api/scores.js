const getOrderDetailPage = (params) => {
    return $axios({
        url: '/score/page',
        method: 'get',
        params
    })
}

function ScoreHisPageApi(params) {
    return $axios({
        'url': '/score/ScoreHistoryByuserId',
        'method': 'get',
        params
    })
}


