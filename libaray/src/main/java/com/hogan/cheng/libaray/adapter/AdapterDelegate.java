package com.hogan.cheng.libaray.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liucheng on 2017/11/6.
 * 所有Adapter的调度方法
 */
public interface AdapterDelegate<T> {

    /**
     * 获取itemAdapter类型
     * 类型定义统一写在继承WrapperAdapter的类中
     * @return itemAdapter类型
     */
    int getItemViewType();

    /**
     * 判断当前对象是否是当前itemAdapter类型
     *
     * @param items    数据列表
     * @param position 对象在数据列表中的位置
     * @return 根据对象是否是当前类型，返回true或false
     */
    boolean isForViewType(@NonNull List<T> items, int position);

    /**
     * 根据当前parent创建对应类型的ViewHolder
     *
     * @param parent RecyclerView的parent
     * @return 放回一个RecyclerView.ViewHolder的实例
     */
    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    /**
     * 将数据对象在ViewHolder中进行绑定
     *
     * @param items    数据源
     * @param position 数据位置
     * @param holder   需要绑定的ViewHolder
     */
    void onBindViewHolder(@NonNull List<T> items, int position, @NonNull RecyclerView.ViewHolder holder);
}