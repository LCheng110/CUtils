package com.hogan.cheng.libaray.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.hogan.cheng.libaray.adapter.entity.ListEntity;

import java.util.List;

/**
 * Created by liucheng on 2017/11/6.
 * AdapterDelegate所有Delegate调度类的管理者
 */

public class AdapterDelegateManager<T> {
    private SparseArray<AdapterDelegate<T>> delegateSparseArray = new SparseArray<>();

    /**
     * 添加AdapterDelegate至AdapterDelegateManager
     * @param delegate 自己重写的AdapterDelegate
     * @return 返回AdapterDelegateManager用于链式添加AdapterDelegate
     */
    public AdapterDelegateManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        delegateSparseArray.put(delegate.getItemViewType(), delegate);
        return this;
    }

    /**
     * 获取ItemViewType
     * @param items 数据列表
     * @param position 获取当前位置对象的ItemViewType
     * @return ItemViewType
     */
    public int getItemViewType(@NonNull List<T> items, int position) {
        if (items.size() > 0) {
            if (getListParameterType(items) instanceof ListEntity) {
                AdapterDelegate<T> adapterDelegate = delegateSparseArray.get(((ListEntity) items.get(0)).getEntityType());
                if (adapterDelegate.isForViewType(items, position)) {
                    return adapterDelegate.getItemViewType();
                }
            } else {
                for (int i = 0; i < delegateSparseArray.size(); i++) {
                    AdapterDelegate<T> adapterDelegate = delegateSparseArray.valueAt(i);
                    if (adapterDelegate.isForViewType(items, position)) {
                        return adapterDelegate.getItemViewType();
                    }
                }
            }
        }
        return DelegateAdapter.ITEM_TYPE_CONTENT;
    }

    /**
     * 根据viewType去对应的创建Delegate中创建对应的ViewHolder
     * @param parent Adapter的父View
     * @param viewType viewType
     * @return 由使用者自己创建的ViewHolder
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegateSparseArray.get(viewType).onCreateViewHolder(parent);
    }

    /**
     * 根据对应viewType调用对用Delegate的onBindViewHolder方法
     * @param items 数据列表
     * @param position 当前对象的位置
     * @param viewHolder viewHolder
     */
    public void onBindViewHolder(@NonNull List<T> items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (items.size() > 0) {
            if (getListParameterType(items) instanceof ListEntity) {
                AdapterDelegate<T> adapterDelegate = delegateSparseArray.get(((ListEntity) items.get(0)).getEntityType());
                if (adapterDelegate.isForViewType(items, position)) {
                    adapterDelegate.onBindViewHolder(items, position, viewHolder);
                }
            } else {
                for (int i = 0; i < delegateSparseArray.size(); i++) {
                    AdapterDelegate<T> adapterDelegate = delegateSparseArray.valueAt(i);
                    if (adapterDelegate.isForViewType(items, position)) {
                        adapterDelegate.onBindViewHolder(items, position, viewHolder);
                        break;
                    }
                }
            }
        }

    }

    private Object getListParameterType(@NonNull List<?> list) {
        if (list.size() > 0) {
            return list.get(0);
        }
        return new Object();
    }
}
