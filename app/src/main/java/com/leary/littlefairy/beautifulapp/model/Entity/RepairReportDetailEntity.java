package com.leary.littlefairy.beautifulapp.model.Entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class RepairReportDetailEntity {
    /**
     * 是否是维修人员
     * 需要在数据请求后手动设置
     */
    private boolean isDealPerson;
    private List<RepairReportEntity> list;

    public boolean isDealPerson() {
        return isDealPerson;
    }

    public void setDealPerson(boolean dealPerson) {
        isDealPerson = dealPerson;
    }

    public List<RepairReportEntity> getList() {
        return list;
    }

    public void setList(List<RepairReportEntity> list) {
        this.list = list;
    }
}
