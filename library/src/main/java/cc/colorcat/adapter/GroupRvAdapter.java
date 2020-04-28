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
    public final void notifyGroupInserted(int groupPositionStart, int groupItemCount) {
        mDelegate.notifyGroupInserted(groupPositionStart, groupItemCount);
    }

    @Override
    public final void notifyGroupItemInserted(int groupPosition, int groupItemPositionStart, int groupItemCount) {
        mDelegate.notifyGroupItemInserted(groupPosition, groupItemPositionStart, groupItemCount);
    }

    @Override
    public final void notifyGroupItemAdded(int groupPosition, int groupItemCount) {
        mDelegate.notifyGroupItemAdded(groupPosition, groupItemCount);
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
}
