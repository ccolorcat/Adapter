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

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public class SingleTypeLvAdapterHelper<T> extends SingleTypeAdapterHelper<T> {
    private BaseAdapter mAdapter;

    @Override
    boolean attachAdapter(SingleType<T> singleTypeAdapter) {
        if (singleTypeAdapter instanceof BaseAdapter) {
            super.attachAdapter(singleTypeAdapter);
            mAdapter = (BaseAdapter) singleTypeAdapter;
            return true;
        }
        return false;
    }

    @Override
    public void insertItems(int positionStart, @NonNull List<? extends T> newData) {
        super.insertItems(positionStart, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void insertItem(int position, @NonNull T newData) {
        super.insertItem(position, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position) {
        super.removeItem(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeItems(int positionStart, int itemCount) {
        super.removeItems(positionStart, itemCount);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        super.moveItem(fromPosition, toPosition);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replaceItem(int position, @NonNull T newData) {
        super.replaceItem(position, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replaceItems(int positionStart, @NonNull List<? extends T> newData) {
        super.replaceItems(positionStart, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replaceAll(@NonNull List<? extends T> newData) {
        super.replaceAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean canHandle(SingleType<?> singleTypeAdapter) {
        return singleTypeAdapter instanceof BaseAdapter;
    }

    @Override
    public SingleTypeAdapterHelper<T> clone() {
        return new SingleTypeLvAdapterHelper<>();
    }
}