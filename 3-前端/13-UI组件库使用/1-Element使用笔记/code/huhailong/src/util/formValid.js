// 表单有效性检查

/**
 * 校验邮箱
 * @param {email address} param 
 * @returns 
 */
 export const checkEmail = param => {
    let reg = /^([a-zA-Z]|[0-9])(\w)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
    if(reg.test(param)){
        return true;
    }else{
        return false;
    }
}
/**
 * 校验用户名，由英文数字下划线和减号
 * @param {username} param 
 * @returns 
 */
export const checkUsername = param => {
    let reg =  /^[a-zA-Z0-9_-]{4,20}$/;
    if(reg.test(param)){
        return true;
    }else{
        return false;
    }
}
/**
 * 校验密码 必须包含数字和字母
 * @param {password} param 
 * @returns 
 */
export const checkPassword = param => {
    let reg = /^(?=.*?[a-z)(?=.*>[A-Z])(?=.*?[0-9])[a-zA_Z0-9]{6,20}$/
    if(reg.test(param)){
        return true;
    }else{
        return false;
    }
}

export const checkRegister = (register) => {
    let result = {
        status: true,
        message: 'pass'
    };
    if(!checkUsername(register.username)||register.username==undefined||register.username==''){
        result.status = false;
        result.type = 'username';
        result.message = '需要长度大于4且不包含特殊字符'
        return result;
    }
    if(!checkEmail(register.userEmail)){
        result.status = false;
        result.type = 'email';
        result.message = '邮箱格式非法'
        return result;
    }
    if(!checkPassword(register.password)){
        result.status = false;
        result.type = 'password';
        result.message = '英文+数字且长度大于6小于20'
        return result;
    }
    if(register.password!=register.compirePassword){
        result.status = false;
        result.type = 'compirePassword';
        result.message = '请确认俩次输入密码是否相同'
    }
    return result;
}

export const checkLogin = (username,password,captcha) => {
    let result = {
        status: true,
        message: 'pass'
    };
    if(username==null||username.trim()==''){
        result.status = false;
        result.type = 'username';
        result.message = '请输入账号'
        return result;
    }
    if(password==null||password.trim()==''){
        result.status = false;
        result.type = 'password';
        result.message = '请输入密码'
        return result;
    }
    if(captcha==null||captcha.trim()==''){
        result.status = false;
        result.type = 'captcha';
        result.message = '请输入验证码'
        return result;
    }
    return result;
}

export const checkFindPassword = (username,userEmail) => {
    console.log(username,userEmail)
    let result = {
        status: true,
        message: 'pass'
    };
    if(username==null||username.trim()==''){
        result.status = false;
        result.type = 'username';
        result.message = '请输入账号'
        return result;
    }
    if(!checkEmail(userEmail)){
        result.status = false;
        result.type = 'email';
        result.message = '邮箱格式非法'
        return result;
    }
    return result;
}

export const checkSendBlog = (title,imageUrl,subtitle) => {
    let result = {
        status: true,
        message: 'pass'
    };
    if(title==null||title.trim()==''){
        result.status = false;
        result.type = 'title';
        result.message = '请设置标题'
        return result;
    }
    if(subtitle==null||subtitle.trim()==''){
        result.status = false;
        result.type = 'subtitle';
        result.message = '请输入内容描述'
        return result;
    }
    if(imageUrl==null||imageUrl.trim()==''){
        result.status = false;
        result.type = 'imageUrl';
        result.message = '请上传一张封面'
        return result;
    }
    return result;
}

export const checkBlog = (blog) => {
    let result = {
        status: true,
        message: 'pass'
    };
    if(blog.title==null||blog.title.trim()==''){
        result.status = false;
        result.type = 'title';
        result.message = '请输入文章标题'
        return result;
    }
    if(blog.subtitle==null||blog.subtitle.trim()==''){
        result.status = false;
        result.type = 'subtitle';
        result.message = '请输入文章描述'
        return result;
    }
    // if(blog.coverImage==null||blog.coverImage.trim()==''){
    //     result.status = false;
    //     result.type = 'coverImage';
    //     result.message = '请上传文章封面'
    //     return result;
    // }
    return result;
}