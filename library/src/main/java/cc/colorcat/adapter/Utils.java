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

import android.support.annotation.NonNull;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: cxx
 * Date: 2019-11-19
 */
final class Utils {
    static int toIntPx(@NonNull DisplayMetrics metrics, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }

    @NonNull
    static Integer[] toIntegerPx(@NonNull DisplayMetrics metrics, @NonNull Integer[] dips) {
        int size = dips.length;
        Integer[] pxs = new Integer[size];
        for (int i = 0; i < size; ++i) {
            Integer dp = dips[i];
            if (dp != null) {
                pxs[i] = toIntPx(metrics, dp);
            }
        }
        return pxs;
    }

    @NonNull
    static String toString(Object object) {
        return object != null ? object.toString() : "";
    }

    @NonNull
    static String toTrimmedString(Object object) {
        return toString(object).trim();
    }

    static <T> void check(List<? extends T> oldData, List<? extends T> newData) {
        if (newData == null) {
            throw new NullPointerException("newData == null");
        }
        if (oldData == newData) {
            throw new IllegalArgumentException("The data source and data destination must be different.");
        }
    }

    static <T> void replaceNull(T[] out, T[] in) {
        for (int i = 0, size = out.length; i < size; ++i) {
            if (out[i] == null) {
                out[i] = in[i];
            }
        }
    }

    static <T> T requireNonNull(T t, String msg) {
        if (t == null) {
            throw new NullPointerException(msg);
        }
        return t;
    }

    static void checkPaddingOrMargin(Integer[] integers) {
        if (integers == null) {
            throw new NullPointerException("the value is null");
        }
        if (integers.length != 4) {
            throw new IllegalArgumentException("the value must contains 4 element.");
        }
    }

    static Integer[] getPadding(@NonNull View view) {
        return new Integer[]{
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom()
        };
    }

    static Integer[] getRelativePadding(@NonNull View view) {
        return new Integer[]{
                ViewCompat.getPaddingStart(view),
                view.getPaddingTop(),
                ViewCompat.getPaddingEnd(view),
                view.getPaddingBottom()
        };
    }

    static Integer[] getMargin(@NonNull ViewGroup.MarginLayoutParams mlp) {
        return new Integer[]{
                mlp.leftMargin,
                mlp.topMargin,
                mlp.rightMargin,
                mlp.bottomMargin
        };
    }

    static Integer[] getRelativeMargin(@NonNull ViewGroup.MarginLayoutParams mlp) {
        return new Integer[]{
                MarginLayoutParamsCompat.getMarginStart(mlp),
                mlp.topMargin,
                MarginLayoutParamsCompat.getMarginEnd(mlp),
                mlp.bottomMargin
        };
    }

    static void checkChoiceMode(@ChoiceRvAdapter.ChoiceMode int mode) {
        if (mode < ChoiceRvAdapter.ChoiceMode.NONE || mode > ChoiceRvAdapter.ChoiceMode.MULTIPLE) {
            throw new IllegalArgumentException("illegal choice mode: " + mode);
        }
    }

    static <T> List<T> immutableList(@NonNull List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    private Utils() {
        throw new AssertionError("no instance");
    }
}
