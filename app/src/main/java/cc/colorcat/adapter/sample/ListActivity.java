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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cc.colorcat.adapter.ChoiceRvAdapter;
import cc.colorcat.adapter.RvHolder;
import cc.colorcat.adapter.ViewHolder;

/**
 * Author: cxx
 * Date: 2018/5/31
 * GitHub: https://github.com/ccolorcat
 */
public class ListActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private List<String> mData = new ArrayList<>(30);
    private SparseBooleanArray mRecord = new SparseBooleanArray(30);
    private ChoiceRvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ViewHolder holder = ViewHolder.from(this);

        RecyclerView recyclerView = holder.get(R.id.rv_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChoiceRvAdapter() {
            @Override
            public int getLayoutResId(int viewType) {
                return R.layout.item_sample;
            }

            @Override
            public void bindView(@NonNull RvHolder holder, int position) {
                holder.getHelper().setImageResource(R.id.iv_icon, R.mipmap.ic_launcher_round)
                        .setText(R.id.tv_content, mData.get(position));
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }

            @Override
            protected boolean isSelected(int position) {
                return mRecord.get(position);
            }

            @Override
            protected void updateItem(int position, boolean selected) {
                mRecord.put(position, selected);
            }
        };
        recyclerView.setAdapter(mAdapter);

        mRefreshLayout = holder.get(R.id.srl_root);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData(mData.size() + 10);
                    }
                }, 1000);
            }
        });
        refreshData(20);
    }

    private void refreshData(int size) {
        mData.clear();
        mRecord.clear();
        for (int i = 0; i < size; ++i) {
            mData.add("item " + i);
        }
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choice_mode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.single:
                mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.SINGLE);
                break;
            case R.id.multiple:
                mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.MULTIPLE);
                break;
            case R.id.none:
                mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.NONE);
                break;
            default:
                break;
        }
        mRecord.clear();
        mAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
