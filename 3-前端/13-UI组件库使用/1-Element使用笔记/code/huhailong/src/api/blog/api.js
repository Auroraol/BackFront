import {form, get, post, rest} from '../../util/http'

export const uploadImage = param => form('blog/content/uploadImage', param);

export const addBlog = param => post('blog/content/add',param);

export const getBlog = param => get('blog/content/get',param);

export const delBlog = param => post('blog/content/del',param);

export const setBlog = param => post('blog/content/set',param);

export const getBlogById = param => rest('blog/content/detail',param);

export const addView = param  => get('blog/view/add',param);

export const countView = param => get('blog/view/count',param);

export const getDiscuss = param => post('blog/discuss/get',param);

export const addDiscuss = param => post('blog/discuss/add',param);

export const addReply = param => post('blog/discuss/reply/add',param);

export const delDiscuss = param => post('blog/discuss/del',param);

export const delReply = param => post('blog/discuss/reply/del',param);