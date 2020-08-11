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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cc.colorcat.adapter.RvAdapter;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
public abstract class BaseRvAdapterFragment<Adapter extends RvAdapter> extends Fragment {
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected Adapter mAdapter;
    protected RvPreloadMoreHelper mLoadMoreHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.layout_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = getAdapter();
        mRecyclerView = view.findViewById(R.id.rv_items);
        mRecyclerView.setLayoutManager(getLayoutManager());
        RecyclerView.RecycledViewPool pool = getRecycledViewPool();
        if (pool != null) mRecyclerView.setRecycledViewPool(pool);
        mRecyclerView.setAdapter(mAdapter);
        mLoadMoreHelper = RvPreloadMoreHelper.newHelper(1, new RvPreloadMoreHelper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                BaseRvAdapterFragment.this.onLoadMore();
            }
        });
        mRecyclerView.addOnScrollListener(mLoadMoreHelper);
        mRefreshLayout = view.findViewById(R.id.srl_root);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseRvAdapterFragment.this.onRefresh();
            }
        });
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(requireContext());
    }

    protected RecyclerView.RecycledViewPool getRecycledViewPool() {
        return null;
    }

    protected abstract Adapter getAdapter();

    protected void onRefresh() {

    }

    protected void onLoadMore() {

    }

    protected final void setRefreshing(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

    protected final void setLoadMoreEnabled(boolean enabled) {
        mLoadMoreHelper.setLoadMoreEnabled(enabled);
    }
}
