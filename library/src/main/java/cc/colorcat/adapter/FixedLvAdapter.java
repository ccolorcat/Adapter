/*
 * Copyright 2018 cxx
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

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Author: cxx
 * Date: 2018-6-4
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings("WeakerAccess")
public abstract class FixedLvAdapter<T> extends LvAdapter {
    private final List<? extends T> mData;
    @LayoutRes
    private final int mItemLayoutResId;

    public FixedLvAdapter(@NonNull List<? extends T> data, @LayoutRes int itemLayoutResId) {
        mData = Utils.immutableList(data);
        mItemLayoutResId = itemLayoutResId;
    }

    @Override
    public final int getCount() {
        return mData.size();
    }

    @Override
    public final T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public final int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public final int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    protected final int getLayoutResId(int viewType) {
        return mItemLayoutResId;
    }

    @Override
    protected void bindView(@NonNull LvHolder holder, int position) {
        bindView(holder, getItem(position));
    }

    protected abstract void bindView(@NonNull LvHolder holder, T t);
}
