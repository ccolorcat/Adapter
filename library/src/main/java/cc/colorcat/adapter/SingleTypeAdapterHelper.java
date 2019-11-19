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

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public abstract class SingleTypeAdapterHelper<T> implements Cloneable {
    private List<T> mData;

    boolean attachAdapter(SingleType<T> singleTypeAdapter) {
        mData = singleTypeAdapter.getData();
        return true;
    }

    public final void append(@NonNull List<? extends T> newData) {
        insertItems(mData.size(), newData);
    }

    public void insertItems(int positionStart, @NonNull List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.addAll(positionStart, newData);
    }

    public void insertItem(int position, @NonNull T newData) {
        mData.add(position, Utils.requireNonNull(newData, "newData == null"));
    }

    public final void removeItem(T newData) {
        int index = mData.indexOf(newData);
        if (index != -1) {
            removeItem(index);
        }
    }

    public void removeItem(int position) {
        mData.remove(position);
    }


    public void removeItems(int positionStart, int itemCount) {
        if (positionStart + itemCount > positionStart) {
            mData.subList(positionStart, positionStart + itemCount).clear();
        }
    }

    public final void moveItem(T newData, int toPosition) {
        int index = mData.indexOf(newData);
        if (index != -1 && index != toPosition) {
            moveItem(index, toPosition);
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        T data = mData.remove(fromPosition);
        mData.add(toPosition, data);
    }

    public void replaceItem(int position, @NonNull T newData) {
        mData.set(position, Utils.requireNonNull(newData, "newData == null"));
    }

    public void replaceItems(int positionStart, @NonNull List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.subList(positionStart, positionStart + newData.size()).clear();
        mData.addAll(positionStart, newData);
    }

    public void replaceAll(@NonNull List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.clear();
        mData.addAll(newData);
    }

    public abstract boolean canHandle(SingleType<?> singleTypeAdapter);

    @Override
    public abstract SingleTypeAdapterHelper<T> clone();
}
