package com.leary.littlefairy.beautifulapp.base;

import java.io.Serializable;

/**
 * 模型基础类，以便以后使用数据库使用和添加公共字段使用
 * Created by longhailin on 2017/10/18.
 */

public class Entity implements Serializable {

    private int entityId;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
