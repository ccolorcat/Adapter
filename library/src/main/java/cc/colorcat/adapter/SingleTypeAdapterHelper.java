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

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public abstract class SingleTypeAdapterHelper<T> implements Cloneable {
    private List<T> mData;

    @CallSuper
    boolean attachAdapter(@NonNull SingleType<T> singleTypeAdapter) {
        mData = singleTypeAdapter.getData();
        return false;
    }

    @CallSuper
    public final void append(@NonNull List<? extends T> newData) {
        insert(mData.size(), newData);
    }

    @CallSuper
    public void insert(int positionStart, @NonNull List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.addAll(positionStart, newData);
    }

    @CallSuper
    public void insert(int position, @NonNull T newData) {
        mData.add(position, Utils.requireNonNull(newData, "newData == null"));
    }

    @CallSuper
    public final void remove(T data) {
        int index = mData.indexOf(data);
        if (index != -1) {
            remove(index);
        }
    }

    @CallSuper
    public void remove(int position) {
        mData.remove(position);
    }

    @CallSuper
    public void remove(int positionStart, int itemCount) {
        if (positionStart + itemCount > positionStart) {
            mData.subList(positionStart, positionStart + itemCount).clear();
        }
    }

    public final void move(T data, int toPosition) {
        int index = mData.indexOf(data);
        if (index != -1 && index != toPosition) {
            move(index, toPosition);
        }
    }

    @CallSuper
    public void move(int fromPosition, int toPosition) {
        T data = mData.remove(fromPosition);
        mData.add(toPosition, data);
    }

    @CallSuper
    public void replace(int position, @NonNull T newData) {
        mData.set(position, Utils.requireNonNull(newData, "newData == null"));
    }

    @CallSuper
    public void replace(int positionStart, @NonNull List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.subList(positionStart, positionStart + newData.size()).clear();
        mData.addAll(positionStart, newData);
    }

    @CallSuper
    public void replaceAll(@NonNull List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.clear();
        mData.addAll(newData);
    }

    public abstract boolean canHandle(SingleType<?> singleTypeAdapter);

    @NonNull
    @Override
    public abstract SingleTypeAdapterHelper<T> clone();
}
