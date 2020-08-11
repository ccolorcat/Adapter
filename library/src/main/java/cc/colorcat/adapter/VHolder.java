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

package cc.colorcat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * Author: cxx
 * Date: 2020-08-11
 * GitHub: https://github.com/ccolorcat
 */
public final class VHolder extends ViewHolder<VHolder> {
    @NonNull
    public static VHolder from(@NonNull Context context, @LayoutRes int layoutId) {
        return from(LayoutInflater.from(context), layoutId);
    }

    @NonNull
    public static VHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        return from(inflater.inflate(layoutId, null));
    }

    @NonNull
    public static VHolder from(@LayoutRes int layoutId, @NonNull ViewGroup container) {
        return from(layoutId, container, false);
    }

    @NonNull
    public static VHolder from(@LayoutRes int layoutId, @NonNull ViewGroup container, boolean attachToRoot) {
        return from(LayoutInflater.from(container.getContext()).inflate(layoutId, container, attachToRoot));
    }

    @NonNull
    public static VHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        return from(inflater, layoutId, container, false);
    }

    @NonNull
    public static VHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container, boolean attachToRoot) {
        return from(inflater.inflate(layoutId, container, attachToRoot));
    }

    @NonNull
    public static VHolder from(@NonNull Activity activity) {
        return new VHolder(activity.getWindow().getDecorView());
    }

    @NonNull
    public static VHolder from(@NonNull View root) {
        return new VHolder(root);
    }

    private VHolder(@NonNull View root) {
        super(root);
    }
}
