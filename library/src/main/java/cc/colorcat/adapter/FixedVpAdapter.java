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

import java.util.ArrayList;
import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 * GitHub: https://github.com/ccolorcat
 */
public abstract class FixedVpAdapter<T> extends VpAdapter {
    private List<? extends T> mData;
    @LayoutRes
    private int mLayoutResId;

    public FixedVpAdapter(List<? extends T> data, @LayoutRes int layoutResId) {
        mData = new ArrayList<>(data);
        mLayoutResId = layoutResId;
    }

    @Override
    public final int getCount() {
        return mData.size();
    }

    @Override
    public final int getViewType(int position) {
        return super.getViewType(position);
    }

    @Override
    public final int getLayoutResId(int viewType) {
        return mLayoutResId;
    }

    @Override
    public final void bindView(@NonNull VpHolder holder, int position) {
        bindView(holder, mData.get(position));
    }

    protected abstract void bindView(@NonNull VpHolder holder, T data);
}
