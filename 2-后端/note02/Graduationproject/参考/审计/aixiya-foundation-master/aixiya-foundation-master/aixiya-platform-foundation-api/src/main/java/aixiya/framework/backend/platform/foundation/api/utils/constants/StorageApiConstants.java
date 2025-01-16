package aixiya.framework.backend.platform.foundation.api.utils.constants;

/**
 * @Author wangqun865@163.com
 */
public class StorageApiConstants {
    /**
     * 0：public-功能中心规划-都不可用  1:产品规划-只产品查询 2:中心规划-只中心查询 3：产品中心同时规划-只同时查询（需同时满足）4：产品中心同时规划-全部可用（无需满足任何条件）
     */
    public static final String SOURCE_TPYE_PUBLIC = "0";
    public static final String SOURCE_TPYE_CLIENT = "1";
    public static final String SOURCE_TPYE_CENTER = "2";
    public static final String SOURCE_TPYE_BOTH = "3";
    public static final String SOURCE_TPYE_NEITHER = "4";
}
