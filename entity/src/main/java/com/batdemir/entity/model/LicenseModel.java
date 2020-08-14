package com.batdemir.entity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LicenseModel implements Serializable {
    private String key;
    private String name;
    private String url;
    @SerializedName("node_id")
    private String nodeId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
