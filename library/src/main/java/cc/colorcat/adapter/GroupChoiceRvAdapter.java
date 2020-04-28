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
public abstract class GroupChoiceRvAdapter extends ChoiceRvAdapter implements GroupableChoiceRvAdapter<RvHolder, RvHolder> {
    public static final String TAG = "GroupChoice";

    private final GroupChoiceRvAdapterDelegate mDelegate = new GroupChoiceRvAdapterDelegate(this);
    private OnGroupItemSelectedChangeListener mGroupItemSelectedListener;

    /**
     * @see #setOnGroupItemSelectedChangeListener(OnGroupItemSelectedChangeListener)
     */
    @Deprecated
    @Override
    public final void setOnItemSelectedChangeListener(OnItemSelectedChangeListener listener) {
        throw new UnsupportedOperationException("unsupported");
    }

    /**
     * @see #getOnGroupItemSelectedChangeListener()
     */
    @Deprecated
    @Override
    public final OnItemSelectedChangeListener getOnItemSelectedChangeListener() {
        throw new UnsupportedOperationException("unsupported");
    }

    public final void setOnGroupItemSelectedChangeListener(OnGroupItemSelectedChangeListener listener) {
        mGroupItemSelectedListener = listener;
        super.setOnItemSelectedChangeListener(GroupOnItemSelectedChangeListener.of(mDelegate, listener));
    }

    public final OnGroupItemSelectedChangeListener getOnGroupItemSelectedChangeListener() {
        return mGroupItemSelectedListener;
    }


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
    protected final void updateItemView(@NonNull RvHolder holder, boolean selected) {
        mDelegate.updateItemView(holder, selected);
    }

    @Override
    public void updateGroupView(@NonNull RvHolder holder, int groupPosition, boolean selected) {
        super.updateItemView(holder, selected);
    }

    @Override
    public void updateGroupItemView(@NonNull RvHolder holder, int groupPosition, int groupItemPosition, boolean selected) {
        super.updateItemView(holder, selected);
    }

    @Override
    protected final void updateItem(int position, boolean selected) {
        super.updateItem(position, selected);
        mDelegate.updateItem(position, selected);
    }

    @Override
    public void updateGroup(int groupPosition, boolean selected) {

    }

    @Override
    public void updateGroupItem(int groupPosition, int groupItemPosition, boolean selected) {

    }

    @Override
    protected final boolean isSelected(int position) {
        return super.isSelected(position) || mDelegate.isSelected(position);
    }

    @Override
    public boolean isGroupSelected(int groupPosition) {
        return false;
    }

    @Override
    public boolean isGroupItemSelected(int groupPosition, int groupItemPosition) {
        return false;
    }

    @Override
    protected final boolean isSelectable(int position) {
        return mDelegate.isSelectable(position);
    }

    @Override
    public boolean isGroupSelectable(int groupPosition) {
        return groupPosition != GroupRvAdapterDelegate.NO_POSITION;
    }

    @Override
    public boolean isGroupItemSelectable(int groupPosition, int groupItemPosition) {
        return groupPosition != GroupRvAdapterDelegate.NO_POSITION
                && groupItemPosition != GroupRvAdapterDelegate.NO_POSITION;
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


    public interface OnGroupItemSelectedChangeListener {
        void onGroupSelectedChanged(int groupPosition, boolean selected);

        void onGroupItemSelectedChanged(int groupPosition, int groupItemPosition, boolean selected);
    }


    private static class GroupOnItemSelectedChangeListener implements OnItemSelectedChangeListener {
        private final GroupChoiceRvAdapterDelegate mDelegate;
        private final OnGroupItemSelectedChangeListener mListener;

        public static GroupOnItemSelectedChangeListener of(
                GroupChoiceRvAdapterDelegate delegate,
                OnGroupItemSelectedChangeListener listener
        ) {
            return listener == null ? null : new GroupOnItemSelectedChangeListener(delegate, listener);
        }

        private GroupOnItemSelectedChangeListener(
                GroupChoiceRvAdapterDelegate delegate,
                OnGroupItemSelectedChangeListener listener
        ) {
            mDelegate = delegate;
            mListener = listener;
        }

        @Override
        public final void onItemSelectedChanged(int position, boolean selected) {
            int[] gip = mDelegate.calculateGroupItemPosition(position);
            if (mDelegate.isGroup(gip)) {
                mListener.onGroupSelectedChanged(gip[0], selected);
            } else {
                mListener.onGroupItemSelectedChanged(gip[0], gip[1], selected);
            }
        }
    }
}
