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

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/fireworld
 */
public final class RvHolder extends RecyclerView.ViewHolder {
    private final Helper mHelper;

    public RvHolder(View itemView) {
        super(itemView);
        mHelper = new Helper(this);
    }

    public Helper getHelper() {
        return mHelper;
    }


    public static class Helper extends AdapterViewHolder {
        private RvHolder mHolder;

        private Helper(RvHolder holder) {
            super(holder.itemView);
            mHolder = holder;
        }

        public RecyclerView.ViewHolder getViewHolder() {
            return mHolder;
        }

        @Override
        public int getViewType() {
            return mHolder.getItemViewType();
        }

        @Override
        public int getPosition() {
            return mHolder.getAdapterPosition();
        }
    }
}
