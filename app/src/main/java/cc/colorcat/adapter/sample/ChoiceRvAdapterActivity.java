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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cc.colorcat.adapter.AdapterHelper;
import cc.colorcat.adapter.AdapterViewHolder;
import cc.colorcat.adapter.ChoiceRvAdapter;
import cc.colorcat.adapter.SimpleAutoChoiceRvAdapter;
import cc.colorcat.adapter.SingleTypeAdapterHelper;
import cc.colorcat.adapter.VHolder;
import cc.colorcat.adapter.ViewBinder;

/**
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/ccolorcat
 */
public class ChoiceRvAdapterActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private final List<String> mData = new ArrayList<>(30);
    private final SimpleAutoChoiceRvAdapter<String> mAdapter = AdapterHelper.newSimpleAutoChoiceRvAdapter(
            mData, R.layout.item_sample, new ViewBinder<String>() {
                @Override
                public void bindView(@NonNull AdapterViewHolder<?> holder, String data) {
                    holder.setImageResource(R.id.iv_icon, R.mipmap.ic_launcher_round)
                            .setText(R.id.tv_content, data);
                }
            });
    private SingleTypeAdapterHelper<String> mHelper = AdapterHelper.require(mAdapter);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_rv_adapter);

        VHolder holder = VHolder.from(this);

        RecyclerView recyclerView = holder.get(R.id.rv_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mRefreshLayout = holder.get(R.id.srl_root);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateData(5, mData.size() > 30);
                    }
                }, 1000);
            }
        });
        updateData(10, true);
    }

    private void updateData(int size, boolean refresh) {
        List<String> newData = new ArrayList<>(size);
        int num = refresh ? 0 : mData.size();
        for (int i = 0; i < size; ++i) {
            newData.add("item " + (num + i));
        }
        if (refresh) {
            mHelper.replaceAll(newData);
        } else {
            mHelper.append(newData);
        }
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choice_mode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.single) {
            mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.SINGLE);
        } else if (itemId == R.id.multiple) {
            mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.MULTIPLE);
        } else if (itemId == R.id.none) {
            mAdapter.setChoiceMode(ChoiceRvAdapter.ChoiceMode.NONE);
        }
        mAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
