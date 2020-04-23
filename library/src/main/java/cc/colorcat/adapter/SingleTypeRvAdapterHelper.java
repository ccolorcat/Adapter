/*
 * Copyright 2019 cxx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.colorcat.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
class SingleTypeRvAdapterHelper<T> extends SingleTypeAdapterHelper<T> {
    private RecyclerView.Adapter mAdapter;

    @Override
    boolean attachAdapter(@NonNull SingleType<T> singleTypeAdapter) {
        if (singleTypeAdapter instanceof RecyclerView.Adapter) {
            super.attachAdapter(singleTypeAdapter);
            mAdapter = (RecyclerView.Adapter) singleTypeAdapter;
            return true;
        }
        return false;
    }

    @Override
    public void insert(int positionStart, @NonNull List<? extends T> newData) {
        super.insert(positionStart, newData);
        mAdapter.notifyItemRangeInserted(positionStart, newData.size());
    }

    @Override
    public void insert(int position, @NonNull T newData) {
        super.insert(position, newData);
        mAdapter.notifyItemInserted(position);
    }

    @Override
    public void remove(int position) {
        super.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void remove(int positionStart, int itemCount) {
        super.remove(positionStart, itemCount);
        mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void move(int fromPosition, int toPosition) {
        super.move(fromPosition, toPosition);
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void replace(int position, @NonNull T newData) {
        super.replace(position, newData);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void replace(int positionStart, @NonNull List<? extends T> newData) {
        super.replace(positionStart, newData);
        mAdapter.notifyItemRangeChanged(positionStart, newData.size());
    }

    @Override
    public void replaceAll(@NonNull List<? extends T> newData) {
        super.replaceAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void justRefreshUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean canHandle(SingleType<?> singleTypeAdapter) {
        return singleTypeAdapter instanceof RecyclerView.Adapter;
    }

    @NonNull
    @Override
    public SingleTypeAdapterHelper<T> clone() {
        return new SingleTypeRvAdapterHelper<>();
    }
}
