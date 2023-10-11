import request from "@/utils/request";

export default {
  getUserList(searchModel) {
    return request({
      url: "/user/list",
      method: "get",
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        username: searchModel.username,
        phone: searchModel.phone,
      },
    });
  },

  addUser(user) {
    return request({
      url: "/user/add",
      method: "post",
      data: user, //data表示用json数据
    });
  },

  updateUser(user){
    return request({
    url: '/user/update',
    method: 'put',
    data: user
    });
  },

  getUserById(id) {
    return request({
      //url: '/user/' + id,
      url: `/user/${id}`,    //id为参数
      method: "get",
    });
  },

  deleteUserById(id){
    return request({
    url: `/user/${id}`,    //id为参数
    method: 'delete'
    });
  }
};
