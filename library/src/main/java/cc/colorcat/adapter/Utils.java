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

import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
final class Utils {
    static <T> void check(List<? extends T> oldData, List<? extends T> newData) {
        if (newData == null) {
            throw new NullPointerException("newData == null");
        }
        if (oldData == newData) {
            throw new IllegalArgumentException("The data source and data destination must be different.");
        }
    }

    static <T> T requireNonNull(T t, String msg) {
        if (t == null) {
            throw new NullPointerException(msg);
        }
        return t;
    }

    static <T> T requireNonNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    private Utils() {
        throw new AssertionError("no instance");
    }
}
