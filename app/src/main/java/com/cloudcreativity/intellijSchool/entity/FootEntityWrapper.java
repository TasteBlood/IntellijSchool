package com.cloudcreativity.intellijSchool.entity;

import java.util.List;

public class FootEntityWrapper {
    private int pageTotal;
    private int totalData;

    private List<FootEntity> data;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public List<FootEntity> getData() {
        return data;
    }

    public void setData(List<FootEntity> data) {
        this.data = data;
    }
}
