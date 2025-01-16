export default {
	default_avatar(url) {
		if (!url) {
			return require("../assets/images/default-avatar.png");
		}

		return url;
	},

	default_name(name) {
		if (!name) {
			return "未命名";
		}

		return name;
	}
};
