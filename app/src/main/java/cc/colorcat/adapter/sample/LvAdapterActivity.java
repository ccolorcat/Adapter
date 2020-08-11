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
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cc.colorcat.adapter.LvAdapter;
import cc.colorcat.adapter.LvHolder;
import cc.colorcat.adapter.SimpleLvAdapter;
import cc.colorcat.adapter.VHolder;

/**
 * Author: cxx
 * Date: 2018-6-4
 * GitHub: https://github.com/ccolorcat
 */
public class LvAdapterActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private final List<String> mData = new ArrayList<>(30);
    private final LvAdapter mAdapter = new SimpleLvAdapter<String>(mData, R.layout.item_sample) {
        @Override
        protected void bindView(@NonNull LvHolder holder, String s) {
            holder.setImageResource(R.id.iv_icon, R.mipmap.ic_launcher_round)
                    .setText(R.id.tv_content, s);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_adapter);

        VHolder holder = VHolder.from(this);

        ListView listView = holder.get(R.id.lv_items);
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
        for (int i = 0; i < size; ++i) {
            mData.add("item " + i);
        }
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }
}
