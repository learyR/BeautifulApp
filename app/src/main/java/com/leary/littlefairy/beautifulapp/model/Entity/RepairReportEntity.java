package com.leary.littlefairy.beautifulapp.model.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class RepairReportEntity implements Serializable{
    /**
     * 是否是维修人员
     * 需要在数据请求后手动设置
     */
    private boolean isDealPerson;
    private int id;//报修数据id
    private int applyPersonId;//申请人id
    private int dealPersonId;//维修人id
    private String applyPerson;//申请人名字
    private String description;//描述
    private String createTime;//时间
    private int status;//状态0: 上报中, 1: 待处理, 2: 已处理, 3: 已评价
    private String applyPortrait;
    private String typeName;
    public boolean isDealPerson() {
        return isDealPerson;
    }

    public void setDealPerson(boolean dealPerson) {
        isDealPerson = dealPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplyPersonId() {
        return applyPersonId;
    }

    public void setApplyPersonId(int applyPersonId) {
        this.applyPersonId = applyPersonId;
    }

    public int getDealPersonId() {
        return dealPersonId;
    }

    public void setDealPersonId(int dealPersonId) {
        this.dealPersonId = dealPersonId;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApplyPortrait() {
        return applyPortrait;
    }

    public void setApplyPortrait(String applyPortrait) {
        this.applyPortrait = applyPortrait;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
