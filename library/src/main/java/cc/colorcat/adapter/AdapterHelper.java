/*
 * Copyright 2019 cxx
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

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public final class AdapterHelper {
    private static final SingleTypeAdapterHelper<?>[] HELPERS = {
            new SingleTypeRvAdapterHelper<>(),
            new SingleTypeLvAdapterHelper<>(),
            new SingleTypePagerAdapterHelper<>()
    };

    /**
     * @param singleTypeAdapter Must be one of
     *                          {@link android.widget.BaseAdapter},
     *                          {@link RecyclerView.Adapter},
     *                          {@link PagerAdapter}
     * @see SimpleRvAdapter
     * @see SimpleChoiceRvAdapter
     * @see SimpleAutoChoiceRvAdapter
     * @see SimpleLvAdapter
     * @see SimpleVpAdapter
     */
    @Nullable
    public static <T> SingleTypeAdapterHelper<T> of(@NonNull SingleType<T> singleTypeAdapter) {
        Utils.requireNonNull(singleTypeAdapter, "singleTypeAdapter == null");
        for (SingleTypeAdapterHelper<?> helper : HELPERS) {
            if (helper.canHandle(singleTypeAdapter)) {
                @SuppressWarnings("unchecked")
                SingleTypeAdapterHelper<T> cloned = (SingleTypeAdapterHelper<T>) helper.clone();
                if (cloned.attachAdapter(singleTypeAdapter)) {
                    return cloned;
                }
            }
        }
        return null;
    }

    public static <T> SimpleRvAdapter<T> newSimpleRvAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new SimpleRvAdapter<T>(data, itemLayout) {
            @Override
            public void bindView(@NonNull RvHolder holder, T data) {
                binder.bindView(holder.getHelper(), data);
            }
        };
    }

    public static <T> SimpleAutoChoiceRvAdapter<T> newSimpleAutoChoiceRvAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new SimpleAutoChoiceRvAdapter<T>(data, itemLayout) {
            @Override
            public void bindView(@NonNull RvHolder holder, T data) {
                binder.bindView(holder.getHelper(), data);
            }
        };
    }

    public static <T> FixedRvAdapter<T> newFixedRvAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new FixedRvAdapter<T>(data, itemLayout) {
            @Override
            public void bindView(@NonNull RvHolder holder, T data) {
                binder.bindView(holder.getHelper(), data);
            }
        };
    }

    public static <T> FixedChoiceRvAdapter<T> newFixedChoiceRvAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new FixedChoiceRvAdapter<T>(data, itemLayout) {
            @Override
            public void bindView(@NonNull RvHolder holder, T data) {
                binder.bindView(holder.getHelper(), data);
            }
        };
    }

    public static <T> SimpleLvAdapter<T> newSimpleLvAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new SimpleLvAdapter<T>(data, itemLayout) {
            @Override
            protected void bindView(@NonNull LvHolder holder, T t) {
                binder.bindView(holder, t);
            }
        };
    }

    public static <T> FixedLvAdapter<T> newFixedLvAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new FixedLvAdapter<T>(data, itemLayout) {
            @Override
            protected void bindView(@NonNull LvHolder holder, T t) {
                binder.bindView(holder, t);
            }
        };
    }

    public static <T> SimpleVpAdapter<T> newSimpleVpAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new SimpleVpAdapter<T>(data, itemLayout) {
            @Override
            protected void bindView(@NonNull VpHolder holder, T data) {
                binder.bindView(holder, data);
            }
        };
    }

    public static <T> FixedVpAdapter<T> newFixedVpAdapter(
            @NonNull List<T> data,
            @LayoutRes int itemLayout,
            @NonNull final ViewBinder<? super T> binder
    ) {
        Utils.requireNonNull(binder, "binder == null");
        return new FixedVpAdapter<T>(data, itemLayout) {
            @Override
            protected void bindView(@NonNull VpHolder holder, T data) {
                binder.bindView(holder, data);
            }
        };
    }

    private AdapterHelper() {
        throw new AssertionError("no instance");
    }
}
