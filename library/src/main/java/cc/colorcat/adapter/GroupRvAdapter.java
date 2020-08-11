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
public abstract class GroupRvAdapter extends RvAdapter implements GroupableRvAdapter<RvHolder, RvHolder> {
    private final GroupRvAdapterDelegate<GroupRvAdapter> mDelegate = new GroupRvAdapterDelegate<>(this);

    @Override
    public final int getItemViewType(int position) {
        return mDelegate.getItemViewType(position);
    }

    @Override
    public final int getItemCount() {
        return mDelegate.getItemCount();
    }

    @Override
    public final long getItemId(int position) {
        return mDelegate.getItemId(position);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return RecyclerView.NO_ID;
    }

    @Override
    public long getGroupItemId(int groupPosition, int groupItemPosition) {
        return RecyclerView.NO_ID;
    }

    @Override
    protected final void bindView(@NonNull RvHolder holder, int position) {
        mDelegate.bindView(holder, position);
    }

    @Override
    public final void notifyGroupChanged(int groupPosition) {
        mDelegate.notifyGroupChanged(groupPosition);
    }

    @Override
    public final void notifyGroupRangeChanged(int groupPositionStart, int groupCount) {
        mDelegate.notifyGroupRangeChanged(groupPositionStart, groupCount);
    }

    @Override
    public final void notifyGroupInserted(int groupPosition) {
        mDelegate.notifyGroupInserted(groupPosition);
    }

    @Override
    public final void notifyGroupRangeInserted(int groupPositionStart, int groupCount) {
        mDelegate.notifyGroupRangeInserted(groupPositionStart, groupCount);
    }

    @Override
    public final void notifyGroupAppended() {
        mDelegate.notifyGroupAppended();
    }

    @Override
    public final void notifyGroupRangeAppended(int groupCount) {
        mDelegate.notifyGroupRangeAppended(groupCount);
    }

    @Override
    public final void notifyGroupRemoved(int groupPosition, int groupItemCount) {
        mDelegate.notifyGroupRemoved(groupPosition, groupItemCount);
    }

    @Override
    public final void notifyGroupRangeRemoved(int groupPositionStart, int itemCount) {
        mDelegate.notifyGroupRangeRemoved(groupPositionStart, itemCount);
    }

    @Override
    public final void notifyGroupItemChanged(int groupPosition, int groupItemPosition) {
        mDelegate.notifyGroupItemChanged(groupPosition, groupItemPosition);
    }

    @Override
    public final void notifyGroupItemRangeChanged(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        mDelegate.notifyGroupItemRangeChanged(groupPosition, groupItemPositionStart, groupItemCount);
    }

    @Override
    public final void notifyGroupItemInserted(int groupPosition, int groupItemPosition) {
        mDelegate.notifyGroupItemInserted(groupPosition, groupItemPosition);
    }

    @Override
    public final void notifyGroupItemRangeInserted(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        mDelegate.notifyGroupItemRangeInserted(groupPosition, groupItemPositionStart, groupItemCount);
    }

    @Override
    public final void notifyGroupItemAppended(int groupPosition) {
        mDelegate.notifyGroupItemAppended(groupPosition);
    }

    @Override
    public final void notifyGroupItemRangeAppended(int groupPosition, int groupItemCount) {
        mDelegate.notifyGroupItemRangeAppended(groupPosition, groupItemCount);
    }

    @Override
    public final void notifyGroupItemRemoved(int groupPosition, int groupItemPosition) {
        mDelegate.notifyGroupItemRemoved(groupPosition, groupItemPosition);
    }

    @Override
    public final void notifyGroupItemRangeRemoved(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        mDelegate.notifyGroupItemRangeRemoved(groupPosition, groupItemPositionStart, groupItemCount);
    }

    @Override
    public final void notifyGroupItemMoved(int fromGroupPosition, int fromGroupItemPosition, int toGroupPosition, int toGroupItemPosition) {
        mDelegate.notifyGroupItemMoved(fromGroupPosition, fromGroupItemPosition, toGroupPosition, toGroupItemPosition);
    }

    public final boolean isGroup(int position) {
        return mDelegate.isGroup(position);
    }

    public final int[] calculateGroupItemPosition(int position) {
        return mDelegate.calculateGroupItemPosition(position);
    }

    public final int calculateSize(int groupCount) {
        return mDelegate.calculateSize(groupCount);
    }

    public final int calculateSize(int groupPositionStart, int groupCount) {
        return mDelegate.calculateSize(groupPositionStart, groupCount);
    }
}
