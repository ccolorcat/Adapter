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

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/ccolorcat
 */
public abstract class SimpleChoiceRvAdapter<T> extends ChoiceRvAdapter implements SingleType<T> {
    private final List<T> mData;
    @LayoutRes
    private final int mItemLayoutResId;

    public SimpleChoiceRvAdapter(@NonNull List<T> data, @LayoutRes int itemLayoutResId) {
        mData = Utils.requireNonNull(data, "data == null");
        mItemLayoutResId = itemLayoutResId;
    }

    @Override
    public List<T> getData() {
        return mData;
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
    protected void bindView(@NonNull RvHolder holder, int position) {
        bindView(holder, mData.get(position));
    }

    protected abstract void bindView(@NonNull RvHolder holder, T data);

    /**
     * deprecated, {@link #isSelected(int, T)}
     */
    @Deprecated
    @Override
    protected boolean isSelected(int position) {
        return isSelected(position, mData.get(position));
    }

    protected boolean isSelected(int position, T data) {
        return super.isSelected(position);
    }

    /**
     * deprecated, {@link #updateItem(int, T, boolean)}
     */
    @Deprecated
    @Override
    protected void updateItem(int position, boolean selected) {
        updateItem(position, mData.get(position), selected);
    }

    protected void updateItem(int position, T data, boolean selected) {
        super.updateItem(position, selected);
    }

    /**
     * deprecated, {@link #isSelectable(int, T)}
     */
    @Deprecated
    @Override
    protected boolean isSelectable(int position) {
        return isSelectable(position, mData.get(position));
    }

    protected boolean isSelectable(int position, T data) {
        return super.isSelectable(position);
    }


    public abstract class SimpleOnItemSelectedChangeListener implements OnItemSelectedChangeListener {
        @Override
        public final void onItemSelectedChanged(int position, boolean selected) {
            onItemSelectedChanged(position, mData.get(position), selected);
        }

        public abstract void onItemSelectedChanged(int position, T data, boolean selected);
    }
}
