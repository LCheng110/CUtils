package com.hogan.cheng.libaray.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liucheng on 2017/11/6.
 * Adapter适配基类
 * 使用者通过继承AdapterDelegate重写一个类似Adapter
 * 以组合的形式构建一个Adapter
 * 允许使用者设置不同的Delegate来实现每个item的不同实现
 */

public class DelegateAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_HEAD = 0x0F;
    public static final int ITEM_TYPE_FOOT = 0xF0;
    public static final int ITEM_TYPE_CONTENT = 0xFF;

    private AdapterDelegateManager<T> delegateManager;
    private List<T> dataList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (delegateManager == null) {
            throw new IllegalArgumentException("delegateManager not allow null! ");
        }
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (delegateManager == null) {
            throw new IllegalArgumentException("delegateManager not allow null! ");
        }
        delegateManager.onBindViewHolder(dataList, position, holder);
    }

    @Override
    public int getItemViewType(int position) {
        if (delegateManager == null) {
            throw new IllegalArgumentException("delegateManager not allow null! ");
        }
        return delegateManager.getItemViewType(dataList, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 设置Adapter的AdapterDelegateManager类
     * @param delegateManager delegateManager
     */
    public void setDelegateManager(AdapterDelegateManager<T> delegateManager) {
        this.delegateManager = delegateManager;
    }

    /**
     * 设置Adapter的数据列表
     * @param dataList 数据列表
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * 适配GridLayoutManager的HEAD和FOOT单独占据一行
     * @param recyclerView recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == ITEM_TYPE_HEAD || viewType == ITEM_TYPE_FOOT) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
        }
    }

    /**
     * 适配StaggeredGridLayoutManager的HEAD和FOOT单独占据一行
     * @param holder holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int viewType = getItemViewType(holder.getLayoutPosition());
        if (viewType == ITEM_TYPE_HEAD || viewType == ITEM_TYPE_FOOT) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) params;
                layoutParams.setFullSpan(true);
            }
        }
    }
}
