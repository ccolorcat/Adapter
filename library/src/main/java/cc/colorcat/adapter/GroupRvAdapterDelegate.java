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
class GroupRvAdapterDelegate<Adapter extends GroupableRvAdapter<RvHolder, RvHolder>> {
    static final int NO_POSITION = RecyclerView.NO_POSITION;

    final Adapter mAdapter;

    GroupRvAdapterDelegate(@NonNull Adapter adapter) {
        mAdapter = Utils.requireNonNull(adapter, "adapter == null");
    }

    final int getItemViewType(int position) {
        int[] gip = calculateGroupItemPosition(position);
        if (isGroup(gip)) {
            return mAdapter.getGroupViewType(gip[0]);
        }
        return mAdapter.getGroupItemViewType(gip[0], gip[1]);
    }

    final int getItemCount() {
        return calculateSize(mAdapter.getGroupCount());
    }

    final long getItemId(int position) {
        int[] gip = calculateGroupItemPosition(position);
        if (isGroup(gip)) {
            return mAdapter.getGroupId(gip[0]);
        }
        return mAdapter.getGroupItemId(gip[0], gip[1]);
    }

    final void bindView(@NonNull RvHolder holder, int position) {
        int[] gip = calculateGroupItemPosition(position);
        if (isGroup(gip)) {
            mAdapter.bindGroupView(holder, gip[0]);
        } else {
            mAdapter.bindGroupItemView(holder, gip[0], gip[1]);
        }
    }

    final void notifyGroupChanged(int groupPosition) {
        cast().notifyItemRangeChanged(calculateSize(groupPosition), calculateSize(groupPosition, 1));
    }

    final void notifyGroupRangeChanged(int groupPositionStart, int groupCount) {
        cast().notifyItemRangeChanged(calculateSize(groupPositionStart), calculateSize(groupPositionStart, groupCount));
    }

    void notifyGroupInserted(int groupPosition) {
        cast().notifyItemRangeInserted(calculateSize(groupPosition), calculateSize(groupPosition, 1));
    }

    void notifyGroupRangeInserted(int groupPositionStart, int groupCount) {
        cast().notifyItemRangeInserted(calculateSize(groupPositionStart), calculateSize(groupPositionStart, groupCount));
    }

    void notifyGroupAppended() {
        notifyGroupInserted(mAdapter.getGroupCount() - 1);
    }

    void notifyGroupRangeAppended(int groupCount) {
        notifyGroupRangeInserted(mAdapter.getGroupCount() - groupCount, groupCount);
    }

    void notifyGroupRemoved(int groupPosition, int groupItemCount) {
        cast().notifyItemRangeRemoved(calculateSize(groupPosition), groupItemCount + 1);
    }

    void notifyGroupRangeRemoved(int groupPositionStart, int itemCount) {
        cast().notifyItemRangeRemoved(calculateSize(groupPositionStart), itemCount);
    }

    void notifyGroupItemChanged(int groupPosition, int groupItemPosition) {
        cast().notifyItemChanged(calculatePosition(groupPosition, groupItemPosition));
    }

    void notifyGroupItemRangeChanged(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        cast().notifyItemRangeChanged(calculatePosition(groupPosition, groupItemPositionStart), groupItemCount);
    }

    void notifyGroupItemInserted(int groupPosition, int groupItemPosition) {
        cast().notifyItemInserted(calculatePosition(groupPosition, groupItemPosition));
    }

    void notifyGroupItemRangeInserted(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        cast().notifyItemRangeInserted(calculatePosition(groupPosition, groupItemPositionStart), groupItemCount);
    }

    void notifyGroupItemAppended(int groupPosition) {
        notifyGroupItemInserted(groupPosition, mAdapter.getGroupItemCount(groupPosition) - 1);
    }

    void notifyGroupItemRangeAppended(int groupPosition, int groupItemCount) {
        notifyGroupItemRangeInserted(groupPosition, mAdapter.getGroupItemCount(groupPosition) - groupItemCount, groupItemCount);
    }

    void notifyGroupItemRemoved(int groupPosition, int groupItemPosition) {
        cast().notifyItemRemoved(calculatePosition(groupPosition, groupItemPosition));
    }

    void notifyGroupItemRangeRemoved(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        cast().notifyItemRangeRemoved(calculatePosition(groupPosition, groupItemPositionStart), groupItemCount);
    }

    void notifyGroupItemMoved(int fromGroupPosition, int fromGroupItemPosition, int toGroupPosition, int toGroupItemPosition) {
        cast().notifyItemMoved(calculatePosition(fromGroupPosition, fromGroupItemPosition), calculatePosition(toGroupPosition, toGroupItemPosition));
    }

    private RecyclerView.Adapter<?> cast() {
        return (RecyclerView.Adapter<?>) mAdapter;
    }

    final int calculateSize(final int groupCount) {
        return calculateSize(0, groupCount);
    }

    /**
     * calculate the size of items
     *
     * @param groupPositionStart include
     * @return the size of items start with groupPositionStart(include)
     * and end with (groupPositionStart + groupCount)(exclude)
     */
    final int calculateSize(final int groupPositionStart, final int groupCount) {
        final int groupPositionEnd = groupPositionStart + groupCount;
        int totalSize = 0;
        for (int i = groupPositionStart; i < groupPositionEnd; i++) {
            totalSize += (1 + mAdapter.getGroupItemCount(i));
        }
        return totalSize;
    }

    /**
     * @return [groupPosition, groupItemPosition]
     */
    final int[] calculateGroupItemPosition(final int position) {
        int[] result = {NO_POSITION, NO_POSITION};
        final int groupCount = mAdapter.getGroupCount();
        int index = -1;
        for (int i = 0; i < groupCount; ++i) {
            ++index;
            if (position == index) {
                result[0] = i;
                break;
            }
            int groupItemCount = mAdapter.getGroupItemCount(i);
            if (position <= index + groupItemCount) {
                result[0] = i;
                result[1] = position - index - 1;
                break;
            }
            index += groupItemCount;
        }
        if (result[0] == NO_POSITION) {
            throw new IllegalStateException("can't find position " + position);
        }
        return result;
    }

    final int calculatePosition(final int groupPosition, final int groupItemPosition) {
        return calculateSize(0, groupPosition) + 1 + groupItemPosition;
    }

    final boolean isGroup(int[] groupItemPosition) {
        return groupItemPosition[1] == NO_POSITION;
    }

    final boolean isGroup(int position) {
        return calculateGroupItemPosition(position)[1] == NO_POSITION;
    }
}
