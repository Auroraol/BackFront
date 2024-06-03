import router from '../../router';

//跳转个人中心
export function goToPersonalCenterHook(account: string) {
    const token = localStorage.getItem('userAccount')
    if (token) {
        const tokenInfo = JSON.parse(window.atob(token))
        if (tokenInfo.account === account) {   //如果点击的是自己的留言的头像，就跳转到自己的个人中心
            router.push('/personalcenter')
        } else {    //不是就正常跳转到别人的个人中心
            router.push({
                path: '/otherspersonalcenter',
                query: {
                    account: account
                }
            })
        }
    } else {    //未登录，直接跳转到别人的个人中心
        router.push({
            path: '/otherspersonalcenter',
            query: {
                account: account
            }
        })
    }
}     ;