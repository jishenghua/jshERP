package com.jsh.erp.datasource.entities;

public class PlatformConfig {
    private Long id;

    private String platformKey;

    private String platformKeyInfo;

    private String platformValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatformKey() {
        return platformKey;
    }

    public void setPlatformKey(String platformKey) {
        this.platformKey = platformKey == null ? null : platformKey.trim();
    }

    public String getPlatformKeyInfo() {
        return platformKeyInfo;
    }

    public void setPlatformKeyInfo(String platformKeyInfo) {
        this.platformKeyInfo = platformKeyInfo == null ? null : platformKeyInfo.trim();
    }

    public String getPlatformValue() {
        return platformValue;
    }

    public void setPlatformValue(String platformValue) {
        this.platformValue = platformValue == null ? null : platformValue.trim();
    }
}