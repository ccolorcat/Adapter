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
 * Date: 2018-06-04
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings("WeakerAccess")
public abstract class SimpleVpAdapter<T> extends VpAdapter implements SingleType<T> {
    private final List<T> mData;
    @LayoutRes
    private final int mLayoutResId;

    public SimpleVpAdapter(List<T> data, @LayoutRes int layoutResId) {
        mData = Utils.requireNonNull(data, "data == null");
        mLayoutResId = layoutResId;
    }

    @Override
    public List<T> getData() {
        return mData;
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
