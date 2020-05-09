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

package cc.colorcat.adapter.sample;


import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.List;

import cc.colorcat.adapter.GroupChoiceRvAdapter;
import cc.colorcat.adapter.RvHolder;

/**
 * Author: cxx
 * Date: 2020-05-09
 * GitHub: https://github.com/ccolorcat
 */
public class GroupChoiceDemoRvAdapter extends GroupChoiceRvAdapter {
    public static final int TYPE_GROUP = 1;
    public static final int TYPE_GROUP_ITEM = 2;

    private final ArrayMap<Integer, List<Integer>> mData;
    private final ArrayMap<String, Boolean> mSelectedStatus = new ArrayMap<>();

    public GroupChoiceDemoRvAdapter(@NonNull ArrayMap<Integer, List<Integer>> data) {
        mData = data;
    }

    @Override
    public int getGroupViewType(int groupPosition) {
        return TYPE_GROUP;
    }

    @Override
    public int getGroupItemViewType(int groupPosition, int groupItemPosition) {
        return TYPE_GROUP_ITEM;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getGroupItemCount(int groupPosition) {
        return mData.valueAt(groupPosition).size();
    }

    @Override
    public void bindGroupView(@NonNull RvHolder holder, int groupPosition) {
        holder.getHelper().setText(R.id.tv_title, "group(" + groupPosition + "): " + mData.keyAt(groupPosition));
    }

    @Override
    public void bindGroupItemView(@NonNull RvHolder holder, int groupPosition, int groupItemPosition) {
        holder.getHelper().setText(R.id.tv_content, "(" + groupPosition + ", " + groupItemPosition + "): " + mData.valueAt(groupPosition).get(groupItemPosition));
    }

    @Override
    protected int getLayoutResId(int viewType) {
        switch (viewType) {
            case TYPE_GROUP:
                return R.layout.item_title;
            case TYPE_GROUP_ITEM:
                return R.layout.item_content;
            default:
                throw new IllegalArgumentException("illegal viewType: " + viewType);
        }
    }

    @Override
    public void updateGroup(int groupPosition, boolean selected) {
        String key = groupPosition + "_NO";
        mSelectedStatus.put(key, selected);
    }

    @Override
    public void updateGroupItem(int groupPosition, int groupItemPosition, boolean selected) {
        String key = groupPosition + "_" + groupItemPosition;
        mSelectedStatus.put(key, selected);
    }


    @Override
    public boolean isGroupSelected(int groupPosition) {
        Boolean selected = mSelectedStatus.get(groupPosition + "_NO");
        return selected != null ? selected : false;
    }

    @Override
    public boolean isGroupItemSelected(int groupPosition, int groupItemPosition) {
        Boolean selected = mSelectedStatus.get(groupPosition + "_" + groupItemPosition);
        return selected != null ? selected : false;
    }

    @Override
    public boolean isGroupSelectable(int groupPosition) {
        return false;
    }
}
