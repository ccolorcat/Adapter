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

import android.util.Log;
import android.util.SparseArray;

import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;

/**
 * Author: cxx
 * Date: 2020-05-06
 * GitHub: https://github.com/ccolorcat
 */
public class Utils {
    public static <K, V> void insert(ArrayMap<K, V> map, K k, V v, int insertPosition) {
        SimpleArrayMap<K, V> removed;
        if (insertPosition <= 0) {
            removed = new ArrayMap<>();
            removed.putAll(map);
            map.clear();
            map.put(k, v);
            map.putAll(removed);
            return;
        }

        if (insertPosition >= map.size()) {
            map.put(k, v);
            return;
        }

        removed = new ArrayMap<>();
//        removeAfterPosition(insertPosition, map, (ArrayMap<K, V>) removed);
        Log.v("Utils", "before remove " + toString(map));
        while (insertPosition < map.size()) {
            removed.put(map.keyAt(insertPosition), map.removeAt(insertPosition));
        }
        Log.v("Utils", "removed: " + toString((ArrayMap<?, ?>) removed));
        map.put(k, v);
        Log.v("Utils", "after inserted: " + toString(map));
//        map.putAll(removed);
        for (int i = 0; i < removed.size(); i++) {
            K key = removed.keyAt(i);
            V value = removed.valueAt(i);
            Log.v("Utils", "restore " + key + ", " + value);
            map.put(key, value);
            Log.v("Utils", "restore " + toString(map));
//            map.put(removed.keyAt(i), removed.valueAt(i));
        }
        Log.v("Utils", "after completed: " + toString(map));
    }

    public static <K, V> void insert(LinkedMap<K, V> map, K key, V v, int insertPosition) {
        LinkedMap<K, V> removed;
        if (insertPosition <= 0) {
            removed = new LinkedMap<>();
            removed.putAll(map);
            map.clear();
            map.put(key, v);
            map.putAll(removed);
            return;
        }

        if (insertPosition >= map.size()) {
            map.put(key, v);
            return;
        }

        removed = new LinkedMap<>();
        while (insertPosition < map.size()) {
            removed.put(map.keyAt(insertPosition), map.valueAt(insertPosition));
            map.removeAt(insertPosition);
        }
        map.put(key, v);
        map.putAll(removed);
    }

    public static <K, V> void insertBatch(LinkedMap<K, V> map, LinkedMap<K, V> newData, int insertPosition) {
        LinkedMap<K, V> removed;
        if (insertPosition <= 0) {
            removed = new LinkedMap<>();
            removed.putAll(map);
            map.clear();
            map.putAll(newData);
            map.putAll(removed);
            return;
        }

        if (insertPosition >= map.size()) {
            map.putAll(newData);
            return;
        }

        removed = new LinkedMap<>();
        while (insertPosition < map.size()) {
            removed.put(map.keyAt(insertPosition), map.valueAt(insertPosition));
            map.removeAt(insertPosition);
        }
        map.putAll(newData);
        map.putAll(removed);
    }

    private static String toString(ArrayMap<?, ?> data) {
        StringBuilder build = new StringBuilder().append('{');
        for (int i = 0; i < data.size(); ++i) {
            if (i != 0) {
                build.append(", ");
            }
            build.append(data.keyAt(i)).append('=').append(data.valueAt(i));
        }
        build.append('}');
        return build.toString();
    }

    public static <K, V> void insertBatch(ArrayMap<K, V> map, ArrayMap<K, V> newData, int insertPosition) {
        SimpleArrayMap<K, V> removed;
        if (insertPosition <= 0) {
            removed = new ArrayMap<>();
            removed.putAll(map);
            map.clear();
            map.putAll((SimpleArrayMap<? extends K, ? extends V>) newData);
            map.putAll(removed);
            return;
        }

        if (insertPosition >= map.size()) {
            map.putAll((SimpleArrayMap<? extends K, ? extends V>) newData);
            return;
        }

        removed = new ArrayMap<>();
        removeAfterPosition(insertPosition, map, (ArrayMap<K, V>) removed);
//        while (insertPosition < map.size()) {
//            removed.put(map.keyAt(insertPosition), map.removeAt(insertPosition));
//        }
        map.putAll((SimpleArrayMap<? extends K, ? extends V>) newData);
        map.putAll(removed);
    }

    private static <K, V> void removeAfterPosition(int position, ArrayMap<K, V> original, ArrayMap<K, V> removed) {
        while (position < original.size()) {
            if (removed != null) {
                removed.put(original.keyAt(position), original.removeAt(position));
            }
        }
    }

    public static <V> void append(SparseArray<V> original, SparseArray<V> newData) {
        for (int i = 0, size = newData.size(); i < size; i++) {
            original.put(newData.keyAt(i), newData.valueAt(i));
        }
    }

    private Utils() {
        throw new AssertionError("no instance");
    }
}
