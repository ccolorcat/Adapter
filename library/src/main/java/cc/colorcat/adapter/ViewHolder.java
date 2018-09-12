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
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/ccolorcat
 */
public class ViewHolder {
    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public static ViewHolder from(@NonNull Context context, @LayoutRes int resId) {
        return from(LayoutInflater.from(context), resId);
    }

    public static ViewHolder from(@NonNull LayoutInflater inflater, @LayoutRes int resId) {
        return from(inflater.inflate(resId, null));
    }

    public static ViewHolder from(@LayoutRes int resId, @NonNull ViewGroup container) {
        return from(resId, container, false);
    }

    public static ViewHolder from(@LayoutRes int resId, @NonNull ViewGroup container, boolean attachToRoot) {
        return from(LayoutInflater.from(container.getContext()).inflate(resId, container, attachToRoot));
    }

    public static ViewHolder from(@NonNull LayoutInflater inflater, @LayoutRes int resId, ViewGroup container) {
        return from(inflater, resId, container, false);
    }

    public static ViewHolder from(@NonNull LayoutInflater inflater, @LayoutRes int resId, ViewGroup container, boolean attachToRoot) {
        return from(inflater.inflate(resId, container, attachToRoot));
    }

    public static ViewHolder from(@NonNull Activity activity) {
        return from(activity.getWindow().getDecorView());
    }

    @NonNull
    public static ViewHolder from(View root) {
        if (root == null) {
            throw new NullPointerException("root == null");
        }
        return new ViewHolder(root);
    }


    private final SparseArray<View> mViews = new SparseArray<>();
    protected final View mRoot;

    protected ViewHolder(@NonNull View root) {
        mRoot = root;
    }

    @NonNull
    public final View getRoot() {
        return mRoot;
    }

    /**
     * @param id the ID to search for
     * @return a view with given ID
     * @throws NullPointerException if can't find view with given ID
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public final <V extends View> V get(@IdRes int id) {
        View view = getOrNull(id);
        if (view == null) {
            throw new NullPointerException("Can't find view, id=" + id);
        }
        return (V) view;
    }

    /**
     * @param id the ID to search for
     * @return a view with given ID if found, or {@code null} otherwise
     * @see View#findViewById(int)
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public final <V extends View> V getOrNull(@IdRes int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mRoot.findViewById(id);
            if (view != null) {
                mViews.put(id, view);
            }
        }
        return (V) view;
    }

    public ViewHolder setOnClickListener(@IdRes int id, View.OnClickListener listener) {
        get(id).setOnClickListener(listener);
        return this;
    }

    /**
     * @see #batchOnClick
     */
    @Deprecated
    public ViewHolder setOnClickListener(View.OnClickListener listener, @IdRes int... ids) {
        return batchOnClick(listener, ids);
    }

    public ViewHolder batchOnClick(View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
        return this;
    }

    public ViewHolder setOnLongClickListener(@IdRes int id, View.OnLongClickListener listener) {
        get(id).setOnLongClickListener(listener);
        return this;
    }

    /**
     * @see #batchOnLongClick
     */
    @Deprecated
    public ViewHolder setOnLongClickListener(View.OnLongClickListener listener, @IdRes int... ids) {
        return batchOnLongClick(listener, ids);
    }

    public ViewHolder batchOnLongClick(View.OnLongClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            get(id).setOnLongClickListener(listener);
        }
        return this;
    }

    public ViewHolder setOnFocusChangeListener(@IdRes int id, View.OnFocusChangeListener listener) {
        get(id).setOnFocusChangeListener(listener);
        return this;
    }

    /**
     * @see #batchOnFocusChange
     */
    @Deprecated
    public ViewHolder setOnFocusChangeListener(View.OnFocusChangeListener listener, @IdRes int... ids) {
        return batchOnFocusChange(listener, ids);
    }

    public ViewHolder batchOnFocusChange(View.OnFocusChangeListener listener, @IdRes int... ids) {
        for (int id : ids) {
            get(id).setOnFocusChangeListener(listener);
        }
        return this;
    }

    public ViewHolder setVisibility(@IdRes int id, @Visibility int visibility) {
        get(id).setVisibility(visibility);
        return this;
    }

    /**
     * @see #batchVisibility
     */
    @Deprecated
    public ViewHolder setVisibility(@Visibility int visibility, @IdRes int... ids) {
        return batchVisibility(visibility, ids);
    }

    public ViewHolder batchVisibility(@Visibility int visibility, @IdRes int... ids) {
        for (int id : ids) {
            get(id).setVisibility(visibility);
        }
        return this;
    }

    @Visibility
    public int getVisibility(@IdRes int id) {
        return get(id).getVisibility();
    }

    public ViewHolder setEnabled(@IdRes int id, boolean enabled) {
        get(id).setEnabled(enabled);
        return this;
    }

    /**
     * @see #batchEnabled
     */
    @Deprecated
    public ViewHolder setEnabled(boolean enabled, @IdRes int... ids) {
        return batchEnabled(enabled, ids);
    }

    public ViewHolder batchEnabled(boolean enabled, @IdRes int... ids) {
        for (int id : ids) {
            get(id).setEnabled(enabled);
        }
        return this;
    }

    public boolean isEnabled(@IdRes int id) {
        return get(id).isEnabled();
    }

    public ViewHolder setSelected(@IdRes int id, boolean selected) {
        get(id).setSelected(selected);
        return this;
    }

    public boolean isSelected(@IdRes int id) {
        return get(id).isSelected();
    }

    public ViewHolder setPadding(@IdRes int id, int padding) {
        return setPadding(id, padding, padding, padding, padding);
    }

    public ViewHolder setPadding(@IdRes int id, int left, int top, int right, int bottom) {
        get(id).setPadding(left, top, right, bottom);
        return this;
    }

    public ViewHolder setBackground(@IdRes int id, Drawable background) {
        View view = get(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(background);
        }
        return this;
    }

    public ViewHolder setBackground(@IdRes int id, @DrawableRes int resId) {
        get(id).setBackgroundResource(resId);
        return this;
    }

    public ViewHolder setBackgroundColor(@IdRes int id, @ColorInt int color) {
        get(id).setBackgroundColor(color);
        return this;
    }

    public ViewHolder setTag(@IdRes int id, final Object tag) {
        get(id).setTag(tag);
        return this;
    }

    public ViewHolder setTag(@IdRes int id, int key, final Object tag) {
        get(id).setTag(key, tag);
        return this;
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T getTag(@IdRes int id) {
        return (T) get(id).getTag();
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T getTag(@IdRes int id, int key) {
        return (T) get(id).getTag(key);
    }

    public ViewHolder setLayoutParams(@IdRes int id, ViewGroup.LayoutParams params) {
        get(id).setLayoutParams(params);
        return this;
    }

    public ViewGroup.LayoutParams getLayoutParams(@IdRes int id) {
        return get(id).getLayoutParams();
    }

    public ViewHolder setText(@IdRes int textViewId, CharSequence text) {
        TextView view = get(textViewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setText(@IdRes int textViewId, @StringRes int resId) {
        TextView view = get(textViewId);
        view.setText(resId);
        return this;
    }

    public CharSequence getText(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getText();
    }

    public String getString(@IdRes int textViewId) {
        return getText(textViewId).toString();
    }

    public String getTrimmedString(@IdRes int textViewId) {
        return getText(textViewId).toString().trim();
    }

    public ViewHolder setError(@IdRes int textViewId, CharSequence error) {
        TextView view = get(textViewId);
        view.setError(error);
        return this;
    }

    public ViewHolder setError(@IdRes int textViewId, @StringRes int resId) {
        TextView view = get(textViewId);
        CharSequence error = view.getResources().getText(resId);
        view.setError(error);
        return this;
    }

    public ViewHolder setError(@IdRes int textViewId, CharSequence error, Drawable icon) {
        TextView view = get(textViewId);
        view.setError(error, icon);
        return this;
    }

    public ViewHolder setError(@IdRes int textViewId, @StringRes int resId, Drawable icon) {
        TextView view = get(textViewId);
        CharSequence error = view.getResources().getText(resId);
        view.setError(error, icon);
        return this;
    }

    public CharSequence getError(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getError();
    }

    public ViewHolder setTextColor(@IdRes int textViewId, @ColorInt int color) {
        TextView view = get(textViewId);
        view.setTextColor(color);
        return this;
    }

    public ViewHolder setTextColor(@IdRes int textViewId, @NonNull ColorStateList colors) {
        TextView view = get(textViewId);
        view.setTextColor(colors);
        return this;
    }

    /**
     * @see #batchTextColor(int, int...)
     */
    @Deprecated
    public ViewHolder setTextColor(@ColorInt int color, @IdRes int... textViewIds) {
        return batchTextColor(color, textViewIds);
    }

    public ViewHolder batchTextColor(@ColorInt int color, @IdRes int... textViewIds) {
        for (int id : textViewIds) {
            setTextColor(id, color);
        }
        return this;
    }

    /**
     * @see #batchTextColor(ColorStateList, int...)
     */
    @Deprecated
    public ViewHolder setTextColor(@NonNull ColorStateList colors, @IdRes int... textViewIds) {
        return batchTextColor(colors, textViewIds);
    }

    public ViewHolder batchTextColor(@NonNull ColorStateList colors, @IdRes int... textViewIds) {
        for (int id : textViewIds) {
            setTextColor(id, colors);
        }
        return this;
    }

    /**
     * Sets flags on the Paint being used to display the text and
     * reflows the text if they are different from the old flags.
     *
     * @see android.graphics.Paint#setFlags(int)
     */
    public ViewHolder setPaintFlags(@IdRes int textViewId, int flags) {
        TextView view = get(textViewId);
        view.setPaintFlags(flags);
        return this;
    }

    public int getPaintFlags(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getPaintFlags();
    }

    public ViewHolder setChecked(@IdRes int checkableId, boolean checked) {
        Checkable checkable = get(checkableId);
        checkable.setChecked(checked);
        return this;
    }

    public boolean isChecked(@IdRes int checkableId) {
        Checkable checkable = get(checkableId);
        return checkable.isChecked();
    }

    public ViewHolder toggle(@IdRes int checkableId) {
        Checkable checkable = get(checkableId);
        checkable.toggle();
        return this;
    }

    public ViewHolder setOnCheckedChangeListener(@IdRes int compoundButtonId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton cb = get(compoundButtonId);
        cb.setOnCheckedChangeListener(listener);
        return this;
    }

    /**
     * @see #batchOnCheckedChange
     */
    @Deprecated
    public ViewHolder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener, @IdRes int... compoundButtonIds) {
        return batchOnCheckedChange(listener, compoundButtonIds);
    }

    public ViewHolder batchOnCheckedChange(CompoundButton.OnCheckedChangeListener listener, @IdRes int... compoundButtonIds) {
        for (int id : compoundButtonIds) {
            setOnCheckedChangeListener(id, listener);
        }
        return this;
    }

    public ViewHolder setImageResource(@IdRes int imageViewId, @DrawableRes int resId) {
        ImageView view = get(imageViewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageDrawable(@IdRes int imageViewId, Drawable drawable) {
        ImageView view = get(imageViewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setImageUri(@IdRes int imageViewId, Uri uri) {
        ImageView view = get(imageViewId);
        view.setImageURI(uri);
        return this;
    }

    public ViewHolder setImageBitmap(@IdRes int imageViewId, File file) {
        return setImageBitmap(imageViewId, file.getAbsolutePath());
    }

    public ViewHolder setImageBitmap(@IdRes int imageViewId, String path) {
        return setImageBitmap(imageViewId, BitmapFactory.decodeFile(path));
    }

    public ViewHolder setImageBitmap(@IdRes int imageViewId, Bitmap bitmap) {
        ImageView view = get(imageViewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setProgress(@IdRes int progressBarId, int progress) {
        ProgressBar pb = get(progressBarId);
        pb.setProgress(progress);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ViewHolder setProgress(@IdRes int progressBarId, int progress, boolean animate) {
        ProgressBar pb = get(progressBarId);
        pb.setProgress(progress, animate);
        return this;
    }

    public ViewHolder setProgressBarColor(@IdRes int progressBarId, @ColorInt int color) {
        ProgressBar pb = get(progressBarId);
        Drawable pd = pb.getProgressDrawable();
        if (pd != null) {
            pd.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
        return this;
    }
}
