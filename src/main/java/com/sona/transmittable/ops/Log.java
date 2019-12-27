package com.sona.transmittable.ops;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 结构化日志
 */
public class Log {
    /**
     * 标题
     **/
    public String title;
    /**
     * 请求 ID
     **/
    public String requestId;
    /**
     * 时间戳, 自 1970-01-01 00:00:00 的毫秒数
     **/
    public long ts = System.currentTimeMillis();
    /**
     * 标签
     **/
    public Map<String, String> tags;
    /**
     * 上下文参数
     **/
    public Map<String, Object> fields;

    public void setTag(String key, String value) {
        if (this.tags == null) {
            this.tags = new LinkedHashMap<>(50);
        }

        this.tags.put(key, value);
    }

    public void setField(String key, String value) {
        if (this.fields == null) {
            this.fields = new LinkedHashMap<>(50);
        }

        this.fields.put(key, value);
    }

    public void setFieldOnlyOne(String key, String value) {
        if (this.fields == null) {
            this.fields = new LinkedHashMap<>(50);
        }
        this.fields.clear();
        this.fields.put(key, value);
    }

    public String toJson() {
        if (this.ts <= 0) {
            this.ts = System.currentTimeMillis();
        }

        return JSON.toJSONString(this);
    }
}
