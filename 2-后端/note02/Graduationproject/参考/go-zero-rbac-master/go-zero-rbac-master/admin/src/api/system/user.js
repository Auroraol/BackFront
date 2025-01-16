import { BaseService, Service, Permission } from "@/common/cl-admin";

@Service("sys/user")
class SysUser extends BaseService {
	@Permission("move")
	move(data) {
		return this.request({
			url: "/move",
			method: "PUT",
			data
		});
	}
}

export default SysUser;
