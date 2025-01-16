package aixiya.framework.backend.platform.foundation.api.utils.constants;

/**
* @Author:wangqun865@163.com
*/
public class RestCodeConstants {

	/**
     * 6位异常码  x'xx'xxx
     *  第一位：
     *  成功：2
     *  警告类型 3
     *  业务异常 4
     * 	系统异常5
     *
     * 	2-3位：
     * 01 : 鉴权中心  02 ：用户中心 03 船舶中心 04 功能中心
     *
     * 后3位
     * 各业务系统自定义异常码
     *
	 */

    /**
     * ocr 下载文件  转为byte 】截止异常
     */
    public static final int OCR_DOWNLOAD_ERROR = 404001;

    /**
     * OCR 调用百度异常
     */
    public static final int OCR_BAIDU_ERROR = 404002;


    //用户登陆，组织多个异常
    public static final String LOGIN_ORG_INVALID_CODE = "301001";
    
    
}
