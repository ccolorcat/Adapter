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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: cxx
 * Date: 2018-6-1
 * GitHub: https://github.com/ccolorcat
 */
public class LvHolder extends AdapterViewHolder {
    int mViewType = 0;
    int mPosition = -1;

    protected LvHolder(@NonNull View root) {
        super(root);
    }

    @Override
    public int getViewType() {
        return mViewType;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

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
