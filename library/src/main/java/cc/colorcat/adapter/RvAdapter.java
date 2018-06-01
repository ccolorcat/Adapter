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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/fireworld
 */
public abstract class RvAdapter extends RecyclerView.Adapter<RvHolder> {
    @NonNull
    @Override
    public final RvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getLayoutResId(viewType), parent, false);
        return new RvHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RvHolder holder, int position) {
        bindView(holder, position);
    }

    @LayoutRes
    public abstract int getLayoutResId(int viewType);

    public abstract void bindView(@NonNull RvHolder holder, int position);
}
