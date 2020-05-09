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

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cc.colorcat.adapter.ChoiceRvAdapter;
import cc.colorcat.adapter.GroupChoiceRvAdapter;
import cc.colorcat.adapter.RvHolder;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
public class GroupRvAdapterFragment extends BaseRvAdapterFragment<GroupChoiceRvAdapter> {
    private static final int ITEM_TYPE_TITLE = 1;
    private static final int ITEM_TYPE_CONTENT = 2;

    private final LinkedMap<Integer, List<Integer>> mData = new LinkedMap<>();
    private int mCount = 0;
    private Random mRandom = new Random();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecoration(new StickyItemDecoration(new StickyView() {
            @Override
            public boolean isStickyView(RecyclerView.ViewHolder holder) {
                return mAdapter.isGroup(holder.getAdapterPosition());
            }

//            @Override
//            public boolean isStickyView(View view) {
//                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
//                return mAdapter.isGroup(holder.getAdapterPosition());
//            }

//            @Override
//            public int getStickViewType() {
//                return ITEM_TYPE_TITLE;
//            }
        }));
    }

    @Override
    protected RecyclerView.RecycledViewPool getRecycledViewPool() {
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(ITEM_TYPE_TITLE, 12);
        pool.setMaxRecycledViews(ITEM_TYPE_CONTENT, 60);
        return pool;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.group_adapter_test, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int groupPosition = mRandom.nextInt(mData.size());
        switch (item.getItemId()) {
            case R.id.test_group_changed: {
                changeGroup(groupPosition);
                toast("notifyGroupChanged " + groupPosition);
                mAdapter.notifyGroupChanged(groupPosition);
                break;
            }
            case R.id.test_group_range_changed: {
                int n = mData.size() / 3;
                int groupPositionStart = mRandom.nextInt(n);
                int groupCount = n;
                changeRangeGroup(groupPositionStart, groupCount);
                toast("notifyGroupRangeChanged groupPositionStart=" + groupPositionStart + ", count=" + groupCount);
                mAdapter.notifyGroupRangeChanged(groupPositionStart, groupCount);
                break;
            }
            case R.id.test_group_inserted: {
                insertGroup(groupPosition);
                toast("notifyGroupInserted, groupPosition=" + groupPosition);
                mAdapter.notifyGroupInserted(groupPosition);
                break;
            }
            case R.id.test_group_range_inserted: {
                int groupCount = mRandom.nextInt(5) + 1;
                insertRangeGroup(groupPosition, groupCount);
                toast("notifyGroupRangeInserted, groupPosition=" + groupPosition + ", groupCount=" + groupCount);
                mAdapter.notifyGroupRangeInserted(groupPosition, groupCount);
                break;
            }
            case R.id.test_group_appended: {
                mData.put(nextKey(), generateGroupItem(mRandom.nextInt(5) + 1));
                toast("notifyGroupAppended");
                mAdapter.notifyGroupAppended();
                break;
            }
            case R.id.test_group_range_appended: {
                LinkedMap<Integer, List<Integer>> groups = generateGroup(mRandom.nextInt(3) + 1);
                mData.putAll(groups);
                toast("notifyGroupRangeAppended, size=" + groups.size());
                mAdapter.notifyGroupRangeAppended(groups.size());
                break;
            }
            case R.id.test_group_removed: {
                List<Integer> values = mData.removeAt(groupPosition);
                toast("notifyGroupRemoved, groupPosition=" + groupPosition + ", groupItemCount=" + values.size());
                mAdapter.notifyGroupRemoved(groupPosition, values.size());
                break;
            }
            case R.id.test_group_range_removed: {
                int n = mData.size() / 3;
                int groupPositionStart = mRandom.nextInt(n);
                int groupCount = mRandom.nextInt(n) + 1;
                int itemCount = mAdapter.calculateSize(groupPositionStart, groupCount);
                for (int i = groupPositionStart + groupCount - 1; i >= groupPositionStart; --i) {
                    mData.removeAt(i);
                }
                toast("notifyGroupRangeRemoved, groupPositionStart=" + groupPositionStart + ", groupCount=" + groupCount + ", itemCount=" + itemCount);
                mAdapter.notifyGroupRangeRemoved(groupPositionStart, itemCount);
                break;
            }
            case R.id.test_group_item_changed: {
                List<Integer> values = mData.valueAt(groupPosition);
                int groupItemPosition = mRandom.nextInt(values.size());
                values.set(groupItemPosition, values.get(groupItemPosition) + 1);
                toast("notifyGroupItemChanged, groupPosition=" + groupPosition + ", groupItemPosition=" + groupItemPosition);
                mAdapter.notifyGroupItemChanged(groupPosition, groupItemPosition);
                break;
            }
            case R.id.test_group_item_range_changed: {
                List<Integer> values = mData.valueAt(groupPosition);
                int n = values.size() / 2;
                int groupItemPositionStart = mRandom.nextInt(n);
                int groupItemCount = n;
                for (int i = groupItemPositionStart; i < groupItemPositionStart + groupItemCount; i++) {
                    values.set(i, values.get(i) + 1);
                }
                toast("notifyGroupItemRangeChanged, groupPosition=" + groupPosition + ", groupItemPositionStart=" + groupItemPositionStart + ", groupItemCount=" + groupItemCount);
                mAdapter.notifyGroupItemRangeChanged(groupPosition, groupItemPositionStart, groupItemCount);
                break;
            }
            case R.id.test_group_item_inserted: {
                List<Integer> value = mData.valueAt(groupPosition);
                int groupItemPosition = mRandom.nextInt(value.size());
                int newItem = mRandom.nextInt();
                List<Integer> headList = new ArrayList<>(value.subList(0, groupItemPosition));
                List<Integer> tailList = new ArrayList<>(value.subList(groupItemPosition, value.size()));
                value.clear();
                value.addAll(headList);
                value.add(newItem);
                value.addAll(tailList);
                toast("notifyGroupItemInserted, groupPosition=" + groupPosition + ", groupItemPosition=" + groupItemPosition + ", newGroupItem=" + newItem);
                mAdapter.notifyGroupItemInserted(groupPosition, groupItemPosition);
                break;
            }
            case R.id.test_group_item_range_inserted: {
                List<Integer> value = mData.valueAt(groupPosition);
                int groupItemPositionStart = mRandom.nextInt(value.size());
                List<Integer> newItems = generateGroupItem(mRandom.nextInt(5) + 1);
                List<Integer> headList = new ArrayList<>(value.subList(0, groupItemPositionStart));
                List<Integer> tailList = new ArrayList<>(value.subList(groupItemPositionStart, value.size()));
                value.clear();
                value.addAll(headList);
                value.addAll(newItems);
                value.addAll(tailList);
                toast("notifyGroupItemRangeInserted, groupPosition=" + groupPosition + ", groupItemPositionStart=" + groupItemPositionStart + ", groupItemCount=" + newItems.size() + ", " + newItems);
                mAdapter.notifyGroupItemRangeInserted(groupPosition, groupItemPositionStart, newItems.size());
                break;
            }
            case R.id.test_group_item_appended: {
                int newItem = mRandom.nextInt();
                mData.valueAt(groupPosition).add(newItem);
                toast("notifyGroupItemAppended, groupPosition=" + groupPosition + ", newItem=" + newItem);
                mAdapter.notifyGroupItemAppended(groupPosition);
                break;
            }
            case R.id.test_group_item_range_appended: {
                List<Integer> newData = generateGroupItem(mRandom.nextInt(5) + 1);
                mData.valueAt(groupPosition).addAll(newData);
                toast("notifyGroupItemRangeAppended, groupPosition=" + groupPosition + ", groupItemCount=" + newData.size() + ", " + newData);
                mAdapter.notifyGroupItemRangeAppended(groupPosition, newData.size());
                break;
            }
            case R.id.test_group_item_removed: {
                List<Integer> oldData = mData.valueAt(groupPosition);
                int groupItemPosition = mRandom.nextInt(oldData.size());
                oldData.remove(groupItemPosition);
                toast("notifyGroupItemRemoved, groupPosition=" + groupPosition + ", groupItemPosition=" + groupItemPosition);
                mAdapter.notifyGroupItemRemoved(groupPosition, groupItemPosition);
                break;
            }
            case R.id.test_group_item_range_removed: {
                List<Integer> oldData = mData.valueAt(groupPosition);
                int n = oldData.size() / 2;
                int groupItemPositionStart = mRandom.nextInt(n);
                int groupItemCount = mRandom.nextInt(n) + 1;
                List<Integer> subList = oldData.subList(groupItemPositionStart, groupItemPositionStart + groupItemCount);
                toast("notifyGroupItemRangeRemoved, groupPosition=" + groupPosition + ", groupItemPositionStart=" + groupItemPositionStart + ", groupItemCount=" + groupItemCount + ", " + subList);
                subList.clear();
                mAdapter.notifyGroupItemRangeRemoved(groupPosition, groupItemPositionStart, groupItemCount);
                break;
            }
            case R.id.test_group_item_moved: {
                int fromGroupPosition = groupPosition;
                List<Integer> from = mData.valueAt(fromGroupPosition);
                int fromGroupItemPosition = mRandom.nextInt(from.size());
                int toGroupPosition = mRandom.nextInt(mData.size());
                while (toGroupPosition == fromGroupPosition) {
                    toGroupPosition = mRandom.nextInt(mData.size());
                }
                List<Integer> to = mData.valueAt(toGroupPosition);
                int toGroupItemPosition = mRandom.nextInt(to.size());
                Integer value = from.remove(fromGroupItemPosition);
                List<Integer> head = new ArrayList<>(to.subList(0, toGroupItemPosition));
                List<Integer> tail = new ArrayList<>(to.subList(toGroupItemPosition, to.size()));
                to.clear();
                to.addAll(head);
                to.add(value);
                to.addAll(tail);
                toast("notifyGroupItemMoved, frp=" + fromGroupPosition + ", fgip=" + fromGroupItemPosition + ", tgp=" + toGroupPosition + ", tgip=" + toGroupItemPosition + ", " + value);
                mAdapter.notifyGroupItemMoved(fromGroupPosition, fromGroupItemPosition, toGroupPosition, toGroupItemPosition);
                break;
            }
            case R.id.test_group_none_choice:
                mAdapter.disableChoice();
                break;
            case R.id.test_group_single_choice:
                mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.SINGLE);
                break;
            case R.id.test_group_multiple_choice:
                mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.MULTIPLE);
                break;
            case R.id.test_group_clear_selection: {
                mAdapter.clearSelection();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeGroup(int groupPosition) {
        List<Integer> items = mData.valueAt(groupPosition);
        for (int i = 0, size = items.size(); i < size; ++i) {
            int value = items.get(i);
            items.set(i, value + 1);
        }
    }

    private void changeRangeGroup(int groupPositionStart, int groupCount) {
        for (int i = groupPositionStart; i < groupPositionStart + groupCount; ++i) {
            changeGroup(i);
        }
    }

    private void insertGroup(int groupPosition) {
        int key = nextKey();
        List<Integer> value = generateGroupItem(mRandom.nextInt(4) + 1);
        log("before: " + mData.toString());
        Utils.insert(mData, key, value, groupPosition);
        log("insert: position=" + groupPosition + ", key=" + key + ", value=" + value);
        log("after: " + mData);
    }

    private void insertRangeGroup(int groupPosition, int groupCount) {
        LinkedMap<Integer, List<Integer>> newData = generateGroup(groupCount);
        Utils.insertBatch(mData, newData, groupPosition);
    }

    private int nextKey() {
        int key = mRandom.nextInt();
        while (mData.containsKey(key)) {
            key = mRandom.nextInt();
        }
        return key;
    }

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
        LinkedMap<Integer, List<Integer>> data = generateGroup(mRandom.nextInt(9) + 3);
        mData.putAll(data);
        mAdapter.notifyDataSetChanged();
        setRefreshing(false);
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        List<Integer> items = generateGroupItem(mRandom.nextInt(4) + 1);
        Log.d("LoadMore", items.toString());
        int size = mData.size();
        mData.put(size, items);
        mAdapter.notifyGroupAppended();
    }

    private LinkedMap<Integer, List<Integer>> generateGroup(int groupCount) {
        LinkedMap<Integer, List<Integer>> data = new LinkedMap<>();
        for (int i = 0; i < groupCount; i++) {
            List<Integer> items = generateGroupItem(mRandom.nextInt(4) + 3);
            data.put(nextKey(), items);
        }
        return data;
    }

    private List<Integer> generateGroupItem(int groupItemCount) {
        List<Integer> data = new ArrayList<>(groupItemCount);
        for (int i = 0; i < groupItemCount; i++) {
            data.add(mCount++);
        }
        return data;
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
                return ITEM_TYPE_TITLE;
            }

            @Override
            public int getGroupItemViewType(int groupPosition, int groupItemPosition) {
                return ITEM_TYPE_CONTENT;
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
            public boolean isGroupSelectable(int groupPosition) {
                return false;
            }

            @Override
            protected int getLayoutResId(int viewType) {
                switch (viewType) {
                    case ITEM_TYPE_TITLE:
                        return R.layout.item_title;
                    case ITEM_TYPE_CONTENT:
                        return R.layout.item_content;
                    default:
                        throw new IllegalArgumentException("illegal viewType " + viewType);
                }
            }
        };
    }

    private void log(String msg) {
        Log.v("GroupRv", msg);
    }

    private void toast(String txt) {
        Log.d("GroupRv", txt);
//        Toast.makeText(requireContext(), txt, Toast.LENGTH_LONG).show();
    }
}
