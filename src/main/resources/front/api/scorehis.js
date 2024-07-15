function ScoreHisPageApi(params) {
    return $axios({
        'url': '/score/userScoreHistory',
        'method': 'get',
        params
    })
}