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
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link FixedChoiceRvAdapter} 一旦创建，绑定的数据就不可更改，自动记录 item 的选中状态。
 * <p>
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/ccolorcat
 */
public abstract class FixedChoiceRvAdapter<T> extends ChoiceRvAdapter {
    private final List<? extends T> mData;
    @LayoutRes
    private final int mItemLayoutResId;
    private SparseBooleanArray mRecords = new SparseBooleanArray();

    public FixedChoiceRvAdapter(@NonNull List<? extends T> data, @LayoutRes int itemLayoutResId) {
        mData = new ArrayList<>(data);
        mItemLayoutResId = itemLayoutResId;
    }

    @Override
    protected final boolean isSelected(int position) {
        return super.isSelected(position) || mRecords.get(position, false);
    }

    @Override
    protected final void updateItem(int position, boolean selected) {
        super.updateItem(position, selected);
        mRecords.put(position, selected);
    }

    @Override
    public final int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public final int getItemCount() {
        return mData.size();
    }

    @Override
    public final int getLayoutResId(int viewType) {
        return mItemLayoutResId;
    }

    @Override
    public void bindView(@NonNull RvHolder holder, int position) {
        bindView(holder, mData.get(position));
    }

    protected abstract void bindView(@NonNull RvHolder holder, T data);
}
