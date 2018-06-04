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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现了自动记录 item 的选中状态的功能。
 * 如果 adapter 中的数据不存在变化，建议使用 {@link FixedSimpleChoiceRvAdapter}.
 * <p>
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/ccolorcat
 */
public abstract class AutoChoiceRvAdapter extends ChoiceRvAdapter {
    private List<Boolean> mRecords = new ArrayList<>();

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final int size = getItemCount();
        if (size > 0) {
            mRecords.clear();
            mRecords.addAll(createRecords(Boolean.FALSE, size));
        }
        registerAdapterDataObserver(mObserver);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecords.clear();
        unregisterAdapterDataObserver(mObserver);
    }

    @Override
    protected final boolean isSelected(int position) {
        return super.isSelected(position) || mRecords.get(position);
    }

    @Override
    protected final void updateItem(int position, boolean selected) {
        super.updateItem(position, selected);
        mRecords.set(position, selected);
    }

    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mRecords.clear();
            mRecords.addAll(createRecords(Boolean.FALSE, getItemCount()));
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            for (int i = positionStart, end = positionStart + itemCount; i < end; ++i) {
                mRecords.set(i, Boolean.FALSE);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            mRecords.addAll(positionStart, createRecords(Boolean.FALSE, itemCount));
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            removeRecords(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            List<Boolean> subList = mRecords.subList(fromPosition, fromPosition + itemCount);
            removeRecords(fromPosition, itemCount);
            mRecords.addAll(toPosition, subList);
        }

        private void removeRecords(int start, int count) {
            for (int i = start + count - 1; i >= start; --i) {
                mRecords.remove(i);
            }
        }
    };

    private static List<Boolean> createRecords(Boolean defaultValue, int size) {
        List<Boolean> records = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            records.add(defaultValue);
        }
        return records;
    }
}
