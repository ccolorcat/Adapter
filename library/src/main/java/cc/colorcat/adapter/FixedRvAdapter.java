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

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings({"WeakerAccess"})
public abstract class FixedRvAdapter<T> extends RvAdapter {
    private final List<? extends T> mData;
    @LayoutRes
    private final int mLayoutResId;

    public FixedRvAdapter(List<? extends T> data, @LayoutRes int layoutResId) {
        mData = Utils.immutableList(data);
        mLayoutResId = layoutResId;
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
        return mLayoutResId;
    }

    @Override
    protected void bindView(@NonNull RvHolder holder, int position) {
        bindView(holder, mData.get(position));
    }

    protected abstract void bindView(@NonNull RvHolder holder, T data);
}