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
import android.view.View;

/**
 * Author: cxx
 * Date: 2018-06-04
 * GitHub: https://github.com/ccolorcat
 */
public final class VpHolder extends AdapterViewHolder {
    int mViewType = 0;
    int mPosition = -1;

    VpHolder(@NonNull View root) {
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
}
