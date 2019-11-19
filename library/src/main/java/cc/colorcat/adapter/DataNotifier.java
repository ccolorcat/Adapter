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

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public interface DataNotifier {
    boolean attachAdapter(Object adapter);

    void notifyDataSetChanged();

    void notifyItemChanged(int position);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyItemInserted(int position);

    void notifyItemMoved(int fromPosition, int toPosition);

    void notifyItemRangeInserted(int positionStart, int itemCount);

    void notifyItemRemoved(int position);

    void notifyItemRangeRemoved(int positionStart, int itemCount);
}
