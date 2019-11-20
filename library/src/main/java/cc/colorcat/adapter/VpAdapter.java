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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: cxx
 * Date: 2018-06-04
 * GitHub: https://github.com/ccolorcat
 */
public abstract class VpAdapter extends PagerAdapter {
    @NonNull
    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        VpHolder holder = onCreateVpHolder(container, position);
        bindView(holder, position);
        container.addView(holder.getRoot());
        return holder.getRoot();
    }

    @Override
    public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    private VpHolder onCreateVpHolder(@NonNull ViewGroup container, int position) {
        int viewType = getViewType(position);
        int layout = getLayoutResId(viewType);
        VpHolder holder = new VpHolder(LayoutInflater.from(container.getContext()).inflate(layout, container, false));
        holder.viewType = viewType;
        holder.position = position;
        return holder;
    }

    public int getViewType(int position) {
        return 0;
    }

    public abstract int getLayoutResId(int viewType);

    public abstract void bindView(@NonNull VpHolder holder, int position);
}
