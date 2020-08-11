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

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: cxx
 * Date: 2020-04-27
 * GitHub: https://github.com/ccolorcat
 */
public class RvPreloadMoreHelper extends RecyclerView.OnScrollListener {
    public static RvPreloadMoreHelper newHelper(int preSize, @NonNull OnLoadMoreListener listener) {
        return new RvPreloadMoreHelper(preSize, listener);
    }

    private int mPreSize;
    private OnLoadMoreListener mListener;
    private boolean mLoadMoreEnabled = true;
    /**
     * drag up (ScrollBar down) is true, otherwise is false
     */
    private boolean mUpOnLast = false;

    private RvPreloadMoreHelper(int preSize, OnLoadMoreListener listener) {
        mPreSize = preSize;
        mListener = listener;
    }

    public void setLoadMoreEnabled(boolean enabled) {
        mLoadMoreEnabled = enabled;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (mLoadMoreEnabled && mUpOnLast) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager != null) {
                View view = manager.findViewByPosition(manager.getItemCount() - mPreSize);
                if (view != null) {
                    mListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mUpOnLast = dy > 0;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
