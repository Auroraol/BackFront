package com.github.xiaolyuh.setting;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.xiaolyuh.support.CacheMode;

import java.io.Serializable;

/**
 * 多级缓存配置项
 *
 * @author yuhao.wang
 */
public class LayeringCacheSetting implements Serializable {
    private static final String SPLIT = "-";
    /**
     * 内部缓存名，由[一级缓存有效时间-二级缓存有效时间]组成
     */
    private String internalKey;

    /**
     * 描述，数据监控页面使用
     */
    private String depict;

    /**
     * 是否使用一级缓存
     */
    CacheMode cacheMode = CacheMode.ALL;

    /**
     * 一级缓存配置
     */
    private FirstCacheSetting firstCacheSetting;

    /**
     * 二级缓存配置
     */
    private SecondaryCacheSetting secondaryCacheSetting;

    public LayeringCacheSetting() {
    }

    public LayeringCacheSetting(FirstCacheSetting firstCacheSetting, SecondaryCacheSetting secondaryCacheSetting,
                                String depict, CacheMode cacheMode) {
        this.firstCacheSetting = firstCacheSetting;
        this.secondaryCacheSetting = secondaryCacheSetting;
        this.depict = depict;
        this.cacheMode = cacheMode;
        internalKey();
    }

    @JSONField(serialize = false, deserialize = false)
    private void internalKey() {
        // 一级缓存有效时间-二级缓存有效时间
        StringBuilder sb = new StringBuilder();
        if (firstCacheSetting != null) {
            sb.append(firstCacheSetting.getTimeUnit().toMillis(firstCacheSetting.getExpireTime()));
        }
        if (secondaryCacheSetting != null) {
            sb.append(SPLIT);
            sb.append(secondaryCacheSetting.getTimeUnit().toMillis(secondaryCacheSetting.getExpiration()));
        }
        internalKey = sb.toString();
    }

    public FirstCacheSetting getFirstCacheSetting() {
        return firstCacheSetting;
    }

    public SecondaryCacheSetting getSecondaryCacheSetting() {
        return secondaryCacheSetting;
    }

    public String getInternalKey() {
        return internalKey;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public void internalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public void setFirstCacheSetting(FirstCacheSetting firstCacheSetting) {
        this.firstCacheSetting = firstCacheSetting;
    }

    public void setSecondaryCacheSetting(SecondaryCacheSetting secondaryCacheSetting) {
        this.secondaryCacheSetting = secondaryCacheSetting;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }
}
