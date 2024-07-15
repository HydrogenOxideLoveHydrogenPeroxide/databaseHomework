// 查询列表接口
const getProductPage = (params) => {
    return $axios({
        url: '/product/page',
        method: 'get',
        params
    });
};

// 删除接口
const deleteProduct = (ids) => {
    return $axios({
        url: '/product',
        method: 'delete',
        params: { ids }
    });
};

// 修改接口
const updateProduct = (params) => {
    return $axios({
        url: '/product',
        method: 'put',
        data: { ...params }
    });
};

// 新增接口
const addProduct = (params) => {
    return $axios({
        url: '/product',
        method: 'post',
        data: { ...params }
    });
};

// 查询详情
const queryProductById = (id) => {
    return $axios({
        url: `/product/${id}`,
        method: 'get'
    });
};

// 获取产品分类列表
const getCategories = () => {
    return $axios({
        url: '/category/list',
        method: 'get'
    });
};

// 批量起售停售接口
const updateProductStatus = (data) => {
    return $axios({
        url: `/product/status/${data.status}`,
        method: 'post',
        params: { ids: data.id }
    });
};
