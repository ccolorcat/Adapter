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

import java.util.LinkedHashMap;

/**
 * Author: cxx
 * Date: 2020-05-06
 * GitHub: https://github.com/ccolorcat
 */
public class LinkedMap<K, V> extends LinkedHashMap<K, V> {

    public K keyAt(int index) {
        return entryAt(index).getKey();
    }

    public V valueAt(int index) {
        return entryAt(index).getValue();
    }

    public V removeAt(int index) {
        K key = keyAt(index);
        return remove(key);
    }

    private Entry<K, V> entryAt(int index) {
        int i = -1;
        for (Entry<K, V> entry : this.entrySet()) {
            ++i;
            if (i == index) {
                return entry;
            }
        }
        throw new IndexOutOfBoundsException("size=" + size() + ", index=" + index);
    }
}
