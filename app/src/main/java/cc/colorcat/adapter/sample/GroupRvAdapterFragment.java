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

import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cc.colorcat.adapter.GroupChoiceRvAdapter;
import cc.colorcat.adapter.RvHolder;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
public class GroupRvAdapterFragment extends BaseRvAdapterFragment<GroupChoiceRvAdapter> {
    private final ArrayMap<Integer, List<Integer>> mData = new ArrayMap<>();
    private int mCount = 0;
    private Random mRandom = new Random();

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(requireContext(), 5);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isGroup(position) ? 5 : 1;
            }
        });
        return manager;
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mData.clear();
        mCount = 0;
        for (int i = 0; i < 15; ++i) {
            List<Integer> data = new ArrayList<>();
            for (int j = 0; j < mRandom.nextInt(8) + 1; ++j) {
                data.add(mCount++);
            }
            mData.put(i, data);
        }
        mAdapter.notifyDataSetChanged();
        setRefreshing(false);
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        List<Integer> items = Arrays.asList(mCount++, mCount++, mCount++, mCount++);
        Log.d("LoadMore", items.toString());
        int size = mData.size();
        mData.put(size, items);
        mAdapter.notifyGroupInserted(size, items.size());
    }

    @Override
    protected GroupChoiceRvAdapter getAdapter() {
        return new GroupChoiceRvAdapter() {
            private ArrayMap<String, Boolean> selectStatus = new ArrayMap<>();

            {
                setOnGroupItemSelectedChangeListener(new OnGroupItemSelectedChangeListener() {
                    @Override
                    public void onGroupSelectedChanged(int groupPosition, boolean selected) {
                        log("onGroupSelectedChanged " + groupPosition + ", " + selected);
                    }

                    @Override
                    public void onGroupItemSelectedChanged(int groupPosition, int groupItemPosition, boolean selected) {
                        log("onGroupItemSelectedChanged" + groupPosition + ", " + groupItemPosition + ", " + selected);
                    }
                });
                setChoiceMode(ChoiceMode.MULTIPLE);
                registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        selectStatus.clear();
                        log("onChanged");
                    }

                    @Override
                    public void onItemRangeChanged(int positionStart, int itemCount) {
                        super.onItemRangeChanged(positionStart, itemCount);
                        log("onItemRangeChanged");
                    }

                    @Override
                    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                        super.onItemRangeChanged(positionStart, itemCount, payload);
                        log("onItemRangeChanged");
                    }

                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        super.onItemRangeInserted(positionStart, itemCount);
                        log("onItemRangeInserted");
                    }

                    @Override
                    public void onItemRangeRemoved(int positionStart, int itemCount) {
                        super.onItemRangeRemoved(positionStart, itemCount);
                        log("onItemRangeRemoved");
                    }

                    @Override
                    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                        log("onItemRangeMoved");
                    }
                });
            }

            @Override
            public int getGroupViewType(int groupPosition) {
                log("getGroupViewTyp");
                return 1;
            }

            @Override
            public int getGroupItemViewType(int groupPosition, int groupItemPosition) {
                return 2;
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
            public void updateGroup(int groupPosition, boolean selected) {
                String key = groupPosition + "_NO";
                selectStatus.put(key, selected);
            }

            @Override
            public void updateGroupItem(int groupPosition, int groupItemPosition, boolean selected) {
                String key = groupPosition + "_" + groupItemPosition;
                selectStatus.put(key, selected);
            }


            @Override
            public boolean isGroupSelected(int groupPosition) {
                Boolean selected = selectStatus.get(groupPosition + "_NO");
                return selected != null ? selected : false;
            }

            @Override
            public boolean isGroupItemSelected(int groupPosition, int groupItemPosition) {
                Boolean selected = selectStatus.get(groupPosition + "_" + groupItemPosition);
                return selected != null ? selected : false;
            }

            @Override
            protected int getLayoutResId(int viewType) {
                switch (viewType) {
                    case 1:
                        return R.layout.item_title;
                    case 2:
                        return R.layout.item_content;
                    default:
                        throw new IllegalArgumentException("illegal viewType " + viewType);
                }
            }

            private void log(String msg) {
                Log.v("GroupRv", msg);
            }
        };
    }
}
