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
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public class SingleTypePagerAdapterHelper<T> extends SingleTypeAdapterHelper<T> {
    private PagerAdapter mAdapter;

    @Override
    boolean attachAdapter(@NonNull SingleType<T> singleTypeAdapter) {
        if (singleTypeAdapter instanceof PagerAdapter) {
            super.attachAdapter(singleTypeAdapter);
            mAdapter = (PagerAdapter) singleTypeAdapter;
            return true;
        }
        return false;
    }

    @Override
    public void insert(int positionStart, @NonNull List<? extends T> newData) {
        super.insert(positionStart, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void insert(int position, @NonNull T newData) {
        super.insert(position, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        super.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void remove(int positionStart, int itemCount) {
        super.remove(positionStart, itemCount);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void move(int fromPosition, int toPosition) {
        super.move(fromPosition, toPosition);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replace(int position, @NonNull T newData) {
        super.replace(position, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replace(int positionStart, @NonNull List<? extends T> newData) {
        super.replace(positionStart, newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replaceAll(@NonNull List<? extends T> newData) {
        super.replaceAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean canHandle(SingleType<?> singleTypeAdapter) {
        return singleTypeAdapter instanceof PagerAdapter;
    }

    @Override
    public SingleTypeAdapterHelper<T> clone() {
        return new SingleTypePagerAdapterHelper<>();
    }
}
