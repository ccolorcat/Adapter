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

import java.util.Objects;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
class GroupRvAdapterDelegate<Adapter extends GroupableRvAdapter<RvHolder, RvHolder>> {
    static final int NO_POSITION = RecyclerView.NO_POSITION;

    final Adapter mAdapter;

    GroupRvAdapterDelegate(@NonNull Adapter adapter) {
        mAdapter = Objects.requireNonNull(adapter, "adapter == null");
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

    final void notifyGroupInserted(int groupPositionStart, int groupItemCount) {
        cast().notifyItemRangeInserted(calculateSize(groupPositionStart), groupItemCount + 1);
    }

    final void notifyGroupItemInserted(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        cast().notifyItemRangeInserted(calculateSize(groupPosition) + 1 + groupItemPositionStart, groupItemCount);
    }

    final void notifyGroupItemAdded(int groupPosition, int groupItemCount) {
        cast().notifyItemRangeInserted(calculateSize(groupPosition + 1), groupItemCount);
    }

    private RecyclerView.Adapter<?> cast() {
        return (RecyclerView.Adapter<?>) mAdapter;
    }

    final int calculateSize(final int groupCount) {
        int totalSize = 0;
        for (int i = 0; i < groupCount; ++i) {
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

    final boolean isGroup(int[] groupItemPosition) {
        return groupItemPosition[1] == NO_POSITION;
    }

    final boolean isGroup(int position) {
        return calculateGroupItemPosition(position)[1] == NO_POSITION;
    }
}
