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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings("WeakerAccess")
public final class LvHolder extends AdapterViewHolder<LvHolder> {
    int viewType = 0;
    int position = -1;

    private LvHolder(@NonNull View root) {
        super(root);
    }

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @NonNull
    static LvHolder getHolder(@Nullable View convertView, @NonNull ViewGroup parent, @LayoutRes int layoutId) {
        LvHolder holder;
        if (convertView == null) {
            holder = new LvHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
            holder.getRoot().setTag(holder);
        } else {
            holder = (LvHolder) convertView.getTag();
        }
        return holder;
    }
}
