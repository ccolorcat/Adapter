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

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings({"WeakerAccess"})
public abstract class AdapterViewHolder<VH extends AdapterViewHolder<VH>> extends ViewHolder<VH> {
    protected AdapterViewHolder(@NonNull View root) {
        super(root);
    }

    public abstract int getViewType();

    public abstract int getPosition();
}
