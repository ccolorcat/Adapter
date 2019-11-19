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
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public abstract class SingleTypeHelper<T> implements Cloneable {
    private static final List<SingleTypeHelper<?>> HELPER_LIST;

    static {
        HELPER_LIST = Arrays.<SingleTypeHelper<?>>asList(
                new RecyclerViewSingleTypeHelper<>(),
                new ListViewSingleTypeHelper<>(),
                new ViewPagerSingleTypeHelper<>()
        );
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> SingleTypeHelper<T> get(SingleType<T> singleType) {
        Utils.requireNonNull(singleType, "singleType == null");
        for (SingleTypeHelper<?> helper : HELPER_LIST) {
            if (helper.canHandle(singleType)) {
                SingleTypeHelper<T> cloned = (SingleTypeHelper<T>) helper.clone();
                if (cloned.attachAdapter(singleType)) {
                    return cloned;
                }
            }
        }
        return null;
    }


    private List<T> mData;

    boolean attachAdapter(SingleType<T> singleType) {
        mData = singleType.getData();
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
        T data = mData.get(fromPosition);
        mData.add(toPosition, data);
    }

    public void replaceItem(int position, T newData) {
        mData.set(position, Utils.requireNonNull(newData, "newData == null"));
    }

    public void replaceItems(int positionStart, List<? extends T> newData) {
        Utils.check(mData, newData);
        mData.subList(positionStart, positionStart + newData.size()).clear();
        mData.addAll(positionStart, newData);
    }

    public abstract boolean canHandle(SingleType<?> singleType);

    @Override
    public abstract SingleTypeHelper<T> clone();
}
