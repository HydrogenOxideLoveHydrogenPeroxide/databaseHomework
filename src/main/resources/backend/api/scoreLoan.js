const getScoreLoanPageApi = (params) => {
    return $axios({
        url: '/scoreLoan/page',
        method: 'get',
        params
    });
};

const updateScoreLoan = (data) => {
    return $axios({
        url: '/scoreLoan',
        method: 'put',
        data
    });
};
// Function to handle PUT requests to pass score loan
const passScoreLoan = (id) => {
    return $axios({
        url: `/scoreLoan/passloan`,
        method: 'put',
        params: { id }
    });
};

// Function to handle PUT requests to reject score loan
const rejectScoreLoan = (id) => {
    return $axios({
        url: `/scoreLoan/rejectloan`,
        method: 'put',
        params: { id }
    });
};

