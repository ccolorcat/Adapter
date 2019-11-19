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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
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

    public static ViewHolder from(@NonNull Context context, @LayoutRes int layoutId) {
        return from(LayoutInflater.from(context), layoutId);
    }

    public static ViewHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        return from(inflater.inflate(layoutId, null));
    }

    public static ViewHolder from(@LayoutRes int layoutId, @NonNull ViewGroup container) {
        return from(layoutId, container, false);
    }

    public static ViewHolder from(@LayoutRes int layoutId, @NonNull ViewGroup container, boolean attachToRoot) {
        return from(LayoutInflater.from(container.getContext()).inflate(layoutId, container, attachToRoot));
    }

    public static ViewHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        return from(inflater, layoutId, container, false);
    }

    public static ViewHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container, boolean attachToRoot) {
        return from(inflater.inflate(layoutId, container, attachToRoot));
    }

    public static ViewHolder from(@NonNull Activity activity) {
        return new ViewHolder(activity.getWindow().getDecorView());
    }

    @NonNull
    public static ViewHolder from(@NonNull View root) {
        return new ViewHolder(root);
    }


    private final SparseArray<View> mViews = new SparseArray<>();
    @NonNull
    protected final View mRoot;
    protected final Context mContext;
    protected final Resources mResources;

    protected ViewHolder(@NonNull View root) {
        mRoot = Utils.requireNonNull(root, "root == null");
        mContext = mRoot.getContext();
        mResources = mRoot.getResources();
    }

    @NonNull
    public final View getRoot() {
        return mRoot;
    }

    /**
     * @param viewId the ID to search for
     * @return a view with given ID
     * @throws NullPointerException if can't find view with given ID
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public final <V extends View> V get(@IdRes int viewId) {
        View view = getOrNull(viewId);
        if (view == null) {
            throw new NullPointerException("Can't find view by viewId: " + mResources.getResourceName(viewId));
        }
        return (V) view;
    }

    /**
     * @param viewId the ID to search for
     * @return a view with given ID if found, or {@code null} otherwise
     * @see View#findViewById(int)
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public final <V extends View> V getOrNull(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mRoot.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, view);
            }
        }
        return (V) view;
    }

    @NonNull
    public ViewHolder setClick(@IdRes int viewId, View.OnClickListener listener) {
        get(viewId).setOnClickListener(listener);
        return this;
    }

    @NonNull
    public ViewHolder batchClick(View.OnClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setOnClickListener(listener);
        }
        return this;
    }

    @NonNull
    public ViewHolder setLongClick(@IdRes int viewId, View.OnLongClickListener listener) {
        get(viewId).setOnLongClickListener(listener);
        return this;
    }

    @NonNull
    public ViewHolder batchLongClick(View.OnLongClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setOnLongClickListener(listener);
        }
        return this;
    }

    @NonNull
    public ViewHolder setFocusChange(@IdRes int viewId, View.OnFocusChangeListener listener) {
        get(viewId).setOnFocusChangeListener(listener);
        return this;
    }

    @NonNull
    public ViewHolder batchFocusChange(View.OnFocusChangeListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setOnFocusChangeListener(listener);
        }
        return this;
    }

    @NonNull
    public ViewHolder setVisibility(@IdRes int viewId, @Visibility int visibility) {
        get(viewId).setVisibility(visibility);
        return this;
    }

    @NonNull
    public ViewHolder batchVisibility(@Visibility int visibility, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setVisibility(visibility);
        }
        return this;
    }

    @Visibility
    public int getVisibility(@IdRes int viewId) {
        return get(viewId).getVisibility();
    }

    @NonNull
    public ViewHolder setEnabled(@IdRes int viewId, boolean enabled) {
        get(viewId).setEnabled(enabled);
        return this;
    }

    @NonNull
    public ViewHolder batchEnabled(boolean enabled, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setEnabled(enabled);
        }
        return this;
    }

    public boolean isEnabled(@IdRes int viewId) {
        return get(viewId).isEnabled();
    }

    @NonNull
    public ViewHolder setSelected(@IdRes int viewId, boolean selected) {
        get(viewId).setSelected(selected);
        return this;
    }

    @NonNull
    public ViewHolder batchSelected(boolean selected, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setSelected(selected);
        }
        return this;
    }

    public boolean isSelected(@IdRes int viewIds) {
        return get(viewIds).isSelected();
    }

    /**
     * @param padding the padding in pixels
     */
    @NonNull
    public ViewHolder setPadding(@IdRes int viewId, int padding) {
        return setPadding(viewId, padding, padding, padding, padding);
    }

    /**
     * @param left   the left padding in pixels
     * @param top    the top padding in pixels
     * @param right  the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    @NonNull
    public ViewHolder setPadding(@IdRes int viewId, int left, int top, int right, int bottom) {
        get(viewId).setPadding(left, top, right, bottom);
        return this;
    }

    @NonNull
    public ViewHolder setPaddingWithDip(@IdRes int viewId, int padding) {
        return setPaddingWithDip(viewId, padding, padding, padding, padding);
    }

    @NonNull
    public ViewHolder setPaddingWithDip(@IdRes int viewId, int left, int top, int right, int bottom) {
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        int l = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, metrics);
        int t = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, metrics);
        int r = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, metrics);
        int b = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, metrics);
        return setPadding(viewId, l, t, r, b);
    }

    @NonNull
    public ViewHolder setMargin(@IdRes int viewId, int margin) {
        return setMargin(viewId, margin, margin, margin, margin);
    }

    /**
     * @param left   the left margin in pixels
     * @param top    the top margin in pixels
     * @param right  the right margin in pixels
     * @param bottom the bottom margin in pixels
     */
    @NonNull
    public ViewHolder setMargin(@IdRes int viewId, int left, int top, int right, int bottom) {
        View view = get(viewId);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            mlp.leftMargin = left;
            mlp.topMargin = top;
            mlp.rightMargin = right;
            mlp.bottomMargin = bottom;
            view.setLayoutParams(mlp);
        }
        return this;
    }

    @NonNull
    public ViewHolder setMarginWithDip(@IdRes int viewId, int margin) {
        return setMarginWithDip(viewId, margin, margin, margin, margin);
    }

    @NonNull
    public ViewHolder setMarginWithDip(@IdRes int viewId, int left, int top, int right, int bottom) {
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        int l = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, metrics);
        int t = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, metrics);
        int r = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, metrics);
        int b = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, metrics);
        return setMargin(viewId, l, t, r, b);
    }

    @NonNull
    public ViewHolder setBackground(@IdRes int viewId, Drawable background) {
        ViewCompat.setBackground(get(viewId), background);
        return this;
    }

    @NonNull
    public ViewHolder setBackground(@IdRes int viewId, @DrawableRes int drawableId) {
        get(viewId).setBackgroundResource(drawableId);
        return this;
    }

    @NonNull
    public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        get(viewId).setBackgroundColor(color);
        return this;
    }

    @NonNull
    public ViewHolder setTag(@IdRes int viewId, Object tag) {
        get(viewId).setTag(tag);
        return this;
    }

    @NonNull
    public ViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        get(viewId).setTag(key, tag);
        return this;
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T getTag(@IdRes int viewId) {
        return (T) get(viewId).getTag();
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T getTag(@IdRes int viewId, int key) {
        return (T) get(viewId).getTag(key);
    }

    @NonNull
    public ViewHolder setLayoutParams(@IdRes int viewId, @NonNull ViewGroup.LayoutParams params) {
        get(viewId).setLayoutParams(params);
        return this;
    }

    public ViewGroup.LayoutParams getLayoutParams(@IdRes int viewId) {
        return get(viewId).getLayoutParams();
    }

    @NonNull
    public ViewHolder setText(@IdRes int textViewId, CharSequence text) {
        TextView view = get(textViewId);
        view.setText(text);
        return this;
    }

    @NonNull
    public ViewHolder setText(@IdRes int textViewId, @StringRes int stringId) {
        TextView view = get(textViewId);
        view.setText(stringId);
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

    @NonNull
    public ViewHolder setError(@IdRes int textViewId, @StringRes int stringId) {
        return setError(textViewId, mResources.getText(stringId));
    }

    @NonNull
    public ViewHolder setError(@IdRes int textViewId, CharSequence error) {
        TextView view = get(textViewId);
        view.setError(error);
        return this;
    }

    @NonNull
    public ViewHolder setError(@IdRes int textViewId, @StringRes int stringId, @DrawableRes int drawableId) {
        return setError(textViewId, mResources.getText(stringId), drawableId);
    }

    @NonNull
    public ViewHolder setError(@IdRes int textViewId, CharSequence error, @DrawableRes int drawableId) {
        return setError(textViewId, error, ContextCompat.getDrawable(mContext, drawableId));
    }

    @NonNull
    public ViewHolder setError(@IdRes int textViewId, @StringRes int stringId, Drawable icon) {
        return setError(textViewId, mResources.getText(stringId), icon);
    }

    @NonNull
    public ViewHolder setError(@IdRes int textViewId, CharSequence error, Drawable icon) {
        TextView view = get(textViewId);
        view.setError(error, icon);
        return this;
    }

    public CharSequence getError(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getError();
    }

    @NonNull
    public ViewHolder setTextColorWithRes(@IdRes int textViewId, @ColorRes int colorId) {
        ColorStateList stateList = ContextCompat.getColorStateList(mContext, colorId);
        if (stateList != null) {
            return setTextColor(textViewId, stateList);
        }
        return setTextColor(textViewId, ContextCompat.getColor(mContext, colorId));
    }

    @NonNull
    public ViewHolder setTextColor(@IdRes int textViewId, @ColorInt int color) {
        TextView view = get(textViewId);
        view.setTextColor(color);
        return this;
    }

    @NonNull
    public ViewHolder setTextColor(@IdRes int textViewId, @NonNull ColorStateList colors) {
        TextView view = get(textViewId);
        view.setTextColor(colors);
        return this;
    }

    @NonNull
    public ViewHolder batchTextColor(@ColorInt int color, @IdRes int... textViewIds) {
        for (int id : textViewIds) {
            setTextColor(id, color);
        }
        return this;
    }

    @NonNull
    public ViewHolder batchTextColor(@NonNull ColorStateList colors, @IdRes int... textViewIds) {
        for (int id : textViewIds) {
            setTextColor(id, colors);
        }
        return this;
    }

    @NonNull
    public ViewHolder batchTextColorWithRes(@ColorRes int colorId, @IdRes int... textViewIds) {
        ColorStateList stateList = ContextCompat.getColorStateList(mContext, colorId);
        if (stateList != null) {
            for (int id : textViewIds) {
                setTextColor(id, stateList);
            }
            return this;
        }
        int color = ContextCompat.getColor(mContext, colorId);
        for (int id : textViewIds) {
            setTextColor(id, color);
        }
        return this;
    }

    /**
     * Sets flags on the Paint being used to display the text and
     * reflows the text if they are different from the old flags.
     *
     * @see android.graphics.Paint#setFlags(int)
     */
    @NonNull
    public ViewHolder setPaintFlags(@IdRes int textViewId, int flags) {
        TextView view = get(textViewId);
        view.setPaintFlags(flags);
        return this;
    }

    public int getPaintFlags(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getPaintFlags();
    }

    @NonNull
    public ViewHolder setChecked(@IdRes int checkableId, boolean checked) {
        Checkable checkable = get(checkableId);
        checkable.setChecked(checked);
        return this;
    }

    @NonNull
    public ViewHolder batchChecked(boolean checked, @IdRes int... checkableIds) {
        for (int id : checkableIds) {
            setChecked(id, checked);
        }
        return this;
    }

    public boolean isChecked(@IdRes int checkableId) {
        Checkable checkable = get(checkableId);
        return checkable.isChecked();
    }

    @NonNull
    public ViewHolder toggle(@IdRes int checkableId) {
        Checkable checkable = get(checkableId);
        checkable.toggle();
        return this;
    }

    @NonNull
    public ViewHolder setCheckedChange(@IdRes int compoundButtonId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton cb = get(compoundButtonId);
        cb.setOnCheckedChangeListener(listener);
        return this;
    }

    @NonNull
    public ViewHolder batchCheckedChange(CompoundButton.OnCheckedChangeListener listener, @IdRes int... compoundButtonIds) {
        for (int id : compoundButtonIds) {
            setCheckedChange(id, listener);
        }
        return this;
    }

    @NonNull
    public ViewHolder setImageResource(@IdRes int imageViewId, @DrawableRes int drawableId) {
        ImageView view = get(imageViewId);
        view.setImageResource(drawableId);
        return this;
    }

    @NonNull
    public ViewHolder setImageDrawable(@IdRes int imageViewId, Drawable drawable) {
        ImageView view = get(imageViewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @NonNull
    public ViewHolder setImageUri(@IdRes int imageViewId, Uri uri) {
        ImageView view = get(imageViewId);
        view.setImageURI(uri);
        return this;
    }

    @NonNull
    public ViewHolder setImageFile(@IdRes int imageViewId, @NonNull File file) {
        return setImageUri(imageViewId, Uri.fromFile(file));
    }

    @NonNull
    public ViewHolder setImagePath(@IdRes int imageViewId, @NonNull String path) {
        return setImageFile(imageViewId, new File(path));
    }

    @NonNull
    public ViewHolder setImageBitmap(@IdRes int imageViewId, Bitmap bitmap) {
        ImageView view = get(imageViewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    @NonNull
    public ViewHolder setProgress(@IdRes int progressBarId, int progress) {
        ProgressBar pb = get(progressBarId);
        pb.setProgress(progress);
        return this;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ViewHolder setProgress(@IdRes int progressBarId, int progress, boolean animate) {
        ProgressBar pb = get(progressBarId);
        pb.setProgress(progress, animate);
        return this;
    }

    @NonNull
    public ViewHolder setProgressBarColor(@IdRes int progressBarId, @ColorInt int color) {
        ProgressBar pb = get(progressBarId);
        Drawable pd = pb.getProgressDrawable();
        if (pd != null) {
            pd.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
        return this;
    }
}
