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
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
public interface GroupableRvAdapter<GroupVH extends RecyclerView.ViewHolder, GroupItemVH extends RecyclerView.ViewHolder> {
    int getGroupViewType(int groupPosition);

    int getGroupItemViewType(int groupPosition, int groupItemPosition);

    int getGroupCount();

    int getGroupItemCount(int groupPosition);

    long getGroupId(int groupPosition);

    long getGroupItemId(int groupPosition, int groupItemPosition);

    void bindGroupView(@NonNull GroupVH holder, int groupPosition);

    void bindGroupItemView(@NonNull GroupItemVH holder, int groupPosition, int groupItemPosition);

    void notifyGroupChanged(int groupPosition);

    void notifyGroupRangeChanged(int groupPositionStart, int groupCount);

    void notifyGroupInserted(int groupPosition);

    void notifyGroupRangeInserted(int groupPositionStart, int groupCount);

    void notifyGroupAppended();

    void notifyGroupRangeAppended(int groupCount);

    void notifyGroupRemoved(int groupPosition, int groupItemCount);

    /**
     * @param itemCount the count of removed groups and their's items, call {@link GroupRvAdapter#calculateSize(int, int)}
     *                  or {@link GroupChoiceRvAdapter#calculateSize(int, int)} to calculate the itemCount
     * @see GroupRvAdapter#calculateSize(int, int)
     * @see GroupChoiceRvAdapter#calculateSize(int, int)
     */
    void notifyGroupRangeRemoved(int groupPositionStart, int itemCount);

    void notifyGroupItemChanged(int groupPosition, int groupItemPosition);

    void notifyGroupItemRangeChanged(int groupPosition, int groupItemPositionStart, int groupItemCount);

    void notifyGroupItemInserted(int groupPosition, int groupItemPosition);

    void notifyGroupItemRangeInserted(int groupPosition, int groupItemPositionStart, int groupItemCount);

    void notifyGroupItemAppended(int groupPosition);

    void notifyGroupItemRangeAppended(int groupPosition, int groupItemCount);

    void notifyGroupItemRemoved(int groupPosition, int groupItemPosition);

    void notifyGroupItemRangeRemoved(int groupPosition, int groupItemPositionStart, int groupItemCount);

    void notifyGroupItemMoved(int fromGroupPosition, int fromGroupItemPosition, int toGroupPosition, int toGroupItemPosition);
}
