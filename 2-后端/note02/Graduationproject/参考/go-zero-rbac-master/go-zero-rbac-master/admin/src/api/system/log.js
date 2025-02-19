import { BaseService, Service, Permission } from "@/common/cl-admin";

@Service("system/log")
class SysLog extends BaseService {
	@Permission("clear")
	clear() {
		return this.request({
			url: "/clear",
			method: "DELETE"
		});
	}

	@Permission("getKeep")
	getKeep() {
		return this.request({
			url: "/getKeep"
		});
	}

	@Permission("setKeep")
	setKeep(value) {
		return this.request({
			url: "/setKeep",
			method: "PUT",
			data: {
				value
			}
		});
	}
}

export default SysLog;
