/*
 * Copyright (c) 2017.  mrZQ
 *
 * icensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.zhang.qiang.hellgate.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * <p>
 * Created by mrZQ on 2016/11/7.
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = "BaseRecyclerAdapter";

    private List<T> dataList;

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public BaseRecyclerAdapter() {
        this.dataList = new ArrayList<>();
    }

    public BaseRecyclerAdapter(List<T> dataList) {
        if (dataList == null) {
            this.dataList = new ArrayList<>();
        } else {
            this.dataList = dataList;
        }
    }

    public BaseRecyclerAdapter(T[] dataList) {
        this.dataList = new ArrayList<>();
        Collections.addAll(this.dataList, dataList);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final RecyclerView.ViewHolder viewHolder = holder;
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClick(view, viewHolder.getAdapterPosition());
                }
            });
        }
        if (itemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClick(view, viewHolder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public T getData(int position) {
        if (position >= 0 && position < getItemCount()) {
            return dataList.get(position);
        } else {
            throw new ArrayIndexOutOfBoundsException("position:" + position + ", dataList size:" + dataList.size());
        }
    }

    public void updateItems(List<T> items) {
        if (items == null) {
            return;
        }
        dataList = items;
        notifyDataSetChanged();
    }

    public void addItemStart(T item) {
        addItem(item, 0);
    }

    public void addItemEnd(T item) {
        addItem(item, -1);
    }

    public void addItem(T item, int position) {
        if (position < 0) {
            position = getItemCount();
        }

        position = Math.min(position, getItemCount());
        dataList.add(position, item);
        notifyItemInserted(position);

        if (position < getItemCount() - 1) {
            int itemCount = getItemCount() - (position + 1);
            notifyItemRangeChanged(position, itemCount);
        }
    }

    public void addItems(List<T> items) {
        int startPosition = getItemCount();
        dataList.addAll(items);
        notifyItemRangeChanged(startPosition, items.size());
    }

    public void addItemsStart(List<T> items) {
        dataList.addAll(0, items);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            return;
        }
        dataList.remove(position);
        notifyItemRemoved(position);
        if (position < getItemCount()) {
            int itemCount = getItemCount() - position;
            notifyItemRangeChanged(position, itemCount);
        }
    }

    public void removeItem(T item) {
        int position = 0;
        ListIterator<T> iterator = dataList.listIterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == item) {
                iterator.remove();
                notifyItemRemoved(position);
                if (position < getItemCount() && position > 0) {
                    notifyItemRangeChanged(position, getItemCount() - position);
                }
                break;
            }
            position++;
        }
    }

    public void removeAllItems() {
        if (dataList != null) {
            dataList.clear();
        }
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener == null ? OnItemClickListener.DEFAULT : listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener == null ? OnItemLongClickListener.DEFAULT : listener;
    }

    public interface OnItemClickListener {
        OnItemClickListener DEFAULT = new OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
//                Log.d(TAG, "itemView:" + itemView + ", position:" + position);
            }
        };

        void onClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        OnItemLongClickListener DEFAULT = new OnItemLongClickListener() {
            @Override
            public void onLongClick(View itemView, int position) {
//                Log.d(TAG, "itemView:" + itemView + ", position:" + position);
            }
        };

        void onLongClick(View itemView, int position);
    }

}
