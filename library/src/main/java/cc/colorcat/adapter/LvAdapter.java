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
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/fireworld
 */
public abstract class LvAdapter extends BaseAdapter {

    @NonNull
    @Override
    public final View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int viewType = getItemViewType(position);
        int layoutId = getLayoutResId(viewType);
        LvHolder holder = LvHolder.getHolder(convertView, parent, layoutId);
        bindView(holder, position);
        return holder.getRoot();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @LayoutRes
    protected abstract int getLayoutResId(int viewType);

    protected abstract void bindView(@NonNull LvHolder holder, int position);
}
