import request from '@/utils/request'

export default{
  // 分页查询角色列表
  getRoleList(searchModel){
    return request({
      url: '/role/list',
      method: 'get',
      params: {
        roleName: searchModel.roleName,
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize
      }
    });
  },
  // 新增
  addRole(role){
    return request({
      url: '/role',
      method: 'post',
      data: role
    });
  },
  // 修改
  updateRole(role){
    return request({
      url: '/role',
      method: 'put',
      data: role
    });
  },
  // 保存角色数据
  saveRole(role){
    if(role.roleId == null || role.roleId == undefined){
      return this.addRole(role);
    }
    return this.updateRole(role);
  },
  // 根据id查询
  getRoleById(id){
    return request({
      url: `/role/${id}`,
      method: 'get'
    });
  },
  // 根据id删除
  deleteRoleById(id){
    return request({
      url: `/role/${id}`,
      method: 'delete'
    });
  },

}
