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

import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
public final class AdapterHelper {
    private static final List<SingleTypeAdapterHelper<?>> HELPERS = Arrays.<SingleTypeAdapterHelper<?>>asList(
            new SingleTypeRvAdapterHelper<>(),
            new SingleTypeLvAdapterHelper<>(),
            new SingleTypePagerAdapterHelper<>()
    );

    @Nullable
    public static <T> SingleTypeAdapterHelper<T> of(SingleType<T> singleTypeAdapter) {
        Utils.requireNonNull(singleTypeAdapter, "singleTypeAdapter == null");
        SingleTypeAdapterHelper<?> helper;
        for (int i = 0, size = HELPERS.size(); i < size; ++i) {
            helper = HELPERS.get(i);
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

    private AdapterHelper() {
        throw new AssertionError("no instance");
    }
}
