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

import androidx.annotation.NonNull;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
final class GroupChoiceRvAdapterDelegate extends GroupRvAdapterDelegate<GroupableChoiceRvAdapter<RvHolder, RvHolder>> {

    GroupChoiceRvAdapterDelegate(@NonNull GroupableChoiceRvAdapter<RvHolder, RvHolder> adapter) {
        super(adapter);
    }

    final void updateItemView(@NonNull RvHolder holder, boolean selected) {
        int[] gip = calculateGroupItemPosition(holder.getAdapterPosition());
        if (isGroup(gip)) {
            mAdapter.updateGroupView(holder, gip[0], selected);
        } else {
            mAdapter.updateGroupItemView(holder, gip[0], gip[1], selected);
        }
    }

    final void updateItem(int position, boolean selected) {
        int[] gip = calculateGroupItemPosition(position);
        if (isGroup(gip)) {
            mAdapter.updateGroup(gip[0], selected);
        } else {
            mAdapter.updateGroupItem(gip[0], gip[1], selected);
        }
    }

    final boolean isSelected(int position) {
        int[] gip = calculateGroupItemPosition(position);
        if (isGroup(gip)) {
            return mAdapter.isGroupSelected(gip[0]);
        }
        return mAdapter.isGroupItemSelected(gip[0], gip[1]);
    }

    final boolean isSelectable(int position) {
        int[] gip = calculateGroupItemPosition(position);
        if (isGroup(gip)) {
            return mAdapter.isGroupSelectable(gip[0]);
        }
        return mAdapter.isGroupItemSelectable(gip[0], gip[1]);
    }
}
