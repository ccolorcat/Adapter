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
 * {@link FixedSimpleChoiceRvAdapter} 一旦创建，绑定的数据就不可更改，自动记录 item 的选中状态。
 * <p>
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/ccolorcat
 */
public abstract class FixedSimpleChoiceRvAdapter<T> extends SimpleChoiceRvAdapter<T> {
    private SparseBooleanArray mRecords = new SparseBooleanArray();

    public FixedSimpleChoiceRvAdapter(@NonNull List<? extends T> data, @LayoutRes int itemLayoutResId) {
        super(new ArrayList<>(data), itemLayoutResId);
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
}
