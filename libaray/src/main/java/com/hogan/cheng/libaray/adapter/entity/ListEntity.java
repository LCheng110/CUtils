package com.hogan.cheng.libaray.adapter.entity;

import com.hogan.cheng.libaray.adapter.DelegateAdapter;

import java.io.Serializable;

/**
 * Created by liucheng on 2017/11/7.
 */

public class ListEntity implements Serializable {
    private int entityType = DelegateAdapter.ITEM_TYPE_CONTENT;

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
}
