// 查询列表接口
const getDishPage = (params) => {
  return $axios({
    url: '/product/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteProduct = (ids) => {
  return $axios({
    url: '/product',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
const editDish = (params) => {
  return $axios({
    url: '/product',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addProduct = (params) => {
  return $axios({
    url: '/product',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const queryProductById = (id) => {
  return $axios({
    url: `/product/${id}`,
    method: 'get'
  })
}

// 获取菜品分类列表
const getCategoryListApi = (params) => {
  return $axios({
    url: '/category/list',
    method: 'get',
    params
  })
}

// 查菜品列表的接口
const queryDishList = (params) => {
  return $axios({
    url: '/product/list',
    method: 'get',
    params
  })
}

// 文件down预览
const commonDownload = (params) => {
  return $axios({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
const prodcutStatusByStatus = (params) => {
  return $axios({
    url: `/product/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
  })
}

const getProductPageApi= (params) => {
    return $axios({
      url: '/product/mypage',
      method: 'get',
      params
    })
}

const subProductStockApi = (id,stock)=> {
  return $axios({
    url: '/product/subStock',
    method: 'post',
    params:{id,stock}
  })
}