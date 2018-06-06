package com.ygy.model;

import javax.validation.constraints.NotNull;

public class PvoidFile {
    @NotNull(message = "不能为空")
    private long id;
    @NotNull(message = "不能为空")
    private String name;
    @NotNull(message = "不能为空")
    private String url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}