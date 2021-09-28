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

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public abstract class ViewHolder<VH extends ViewHolder<VH>> {
    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }


    private SparseArray<Object> mExtras;
    private final SparseArray<View> mViews = new SparseArray<>();
    @NonNull
    protected final View mRoot;
    @NonNull
    protected final Context mContext;
    @NonNull
    protected final Resources mResources;

    protected ViewHolder(@NonNull View root) {
        mRoot = Utils.requireNonNull(root, "root == null");
        mContext = mRoot.getContext();
        mResources = mRoot.getResources();
    }

    public final VH putExtra(int key, Object data) {
        if (mExtras == null) mExtras = new SparseArray<>();
        mExtras.put(key, data);
        return self();
    }

    @SuppressWarnings("unchecked")
    public final <T> T getExtra(int key) {
        return mExtras == null ? null : (T) mExtras.get(key);
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

    @SuppressWarnings("unchecked")
    @NonNull
    private <T> T cast(@IdRes int viewId) {
        return (T) get(viewId);
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
    public VH setClick(@IdRes int viewId, View.OnClickListener listener) {
        get(viewId).setOnClickListener(listener);
        return self();
    }

    @NonNull
    public VH batchClick(View.OnClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setOnClickListener(listener);
        }
        return self();
    }

    @NonNull
    public VH setLongClick(@IdRes int viewId, View.OnLongClickListener listener) {
        get(viewId).setOnLongClickListener(listener);
        return self();
    }

    @NonNull
    public VH batchLongClick(View.OnLongClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setOnLongClickListener(listener);
        }
        return self();
    }

    @NonNull
    public VH setFocusChange(@IdRes int viewId, View.OnFocusChangeListener listener) {
        get(viewId).setOnFocusChangeListener(listener);
        return self();
    }

    @NonNull
    public VH batchFocusChange(View.OnFocusChangeListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setOnFocusChangeListener(listener);
        }
        return self();
    }

    @NonNull
    public VH setVisibility(@IdRes int viewId, @Visibility int visibility) {
        get(viewId).setVisibility(visibility);
        return self();
    }

    @NonNull
    public VH batchVisibility(@Visibility int visibility, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setVisibility(visibility);
        }
        return self();
    }

    @Visibility
    public int getVisibility(@IdRes int viewId) {
        return get(viewId).getVisibility();
    }

    @NonNull
    public VH setEnabled(@IdRes int viewId, boolean enabled) {
        get(viewId).setEnabled(enabled);
        return self();
    }

    @NonNull
    public VH batchEnabled(boolean enabled, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setEnabled(enabled);
        }
        return self();
    }

    public boolean isEnabled(@IdRes int viewId) {
        return get(viewId).isEnabled();
    }

    @NonNull
    public VH setSelected(@IdRes int viewId, boolean selected) {
        get(viewId).setSelected(selected);
        return self();
    }

    @NonNull
    public VH batchSelected(boolean selected, @IdRes int... viewIds) {
        for (int id : viewIds) {
            get(id).setSelected(selected);
        }
        return self();
    }

    public boolean isSelected(@IdRes int viewIds) {
        return get(viewIds).isSelected();
    }

    /**
     * @param padding the padding in pixels
     */
    @NonNull
    public VH setPadding(@IdRes int viewId, int padding) {
        return setPadding(viewId, padding, padding, padding, padding);
    }

    /**
     * @param left   the left padding in pixels
     * @param top    the top padding in pixels
     * @param right  the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    @NonNull
    public VH setPadding(@IdRes int viewId, int left, int top, int right, int bottom) {
        get(viewId).setPadding(left, top, right, bottom);
        return self();
    }

    /**
     * @param ltrb Padding in pixels for left, top, right, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setPadding(@IdRes int viewId, @NonNull Integer[] ltrb) {
        Utils.checkPaddingOrMargin(ltrb);
        Utils.replaceNull(ltrb, Utils.getPadding(get(viewId)));
        return setPadding(viewId, ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
    }

    /**
     * @param padding the padding in dip
     */
    @NonNull
    public VH setPaddingWithDip(@IdRes int viewId, int padding) {
        int p = Utils.toIntPx(mResources.getDisplayMetrics(), padding);
        return setPadding(viewId, p, p, p, p);
    }

    /**
     * @param left   the left padding in dip
     * @param top    the top padding in dip
     * @param right  the right padding in dip
     * @param bottom the bottom padding in dip
     */
    @NonNull
    public VH setPaddingWithDip(@IdRes int viewId, int left, int top, int right, int bottom) {
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        int l = Utils.toIntPx(metrics, left);
        int t = Utils.toIntPx(metrics, top);
        int r = Utils.toIntPx(metrics, right);
        int b = Utils.toIntPx(metrics, bottom);
        return setPadding(viewId, l, t, r, b);
    }

    /**
     * @param ltrb Padding in dip for left, top, right, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setPaddingWithDip(@IdRes int viewId, @NonNull Integer[] ltrb) {
        Utils.checkPaddingOrMargin(ltrb);
        Integer[] pxs = Utils.toIntegerPx(mResources.getDisplayMetrics(), ltrb);
        return setPadding(viewId, pxs);
    }


    /**
     * @param padding The padding in pixels.
     */
    @NonNull
    public VH setPaddingRelative(@IdRes int viewId, int padding) {
        return setPaddingRelative(viewId, padding, padding, padding, padding);
    }

    /**
     * @param start  the start padding in pixels
     * @param top    the top padding in pixels
     * @param end    the end padding in pixels
     * @param bottom the bottom padding in pixels
     */
    @NonNull
    public VH setPaddingRelative(@IdRes int viewId, int start, int top, int end, int bottom) {
        ViewCompat.setPaddingRelative(get(viewId), start, top, end, bottom);
        return self();
    }

    /**
     * @param steb Padding in pixels for start, top, end, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setPaddingRelative(@IdRes int viewId, @NonNull Integer[] steb) {
        Utils.checkPaddingOrMargin(steb);
        Utils.replaceNull(steb, Utils.getRelativePadding(get(viewId)));
        return setPaddingRelative(viewId, steb[0], steb[1], steb[2], steb[3]);
    }

    @NonNull
    public VH setPaddingRelativeWithDip(@IdRes int viewId, int padding) {
        int p = Utils.toIntPx(mResources.getDisplayMetrics(), padding);
        return setPaddingRelative(viewId, p, p, p, p);
    }

    @NonNull
    public VH setPaddingRelativeWithDip(@IdRes int viewId, int start, int top, int end, int bottom) {
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        int s = Utils.toIntPx(metrics, start);
        int t = Utils.toIntPx(metrics, top);
        int e = Utils.toIntPx(metrics, end);
        int b = Utils.toIntPx(metrics, bottom);
        return setPaddingRelative(viewId, s, t, e, b);
    }

    /**
     * @param steb Padding in dip for start, top, end, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setPaddingRelativeWithDip(@IdRes int viewId, @NonNull Integer[] steb) {
        Utils.checkPaddingOrMargin(steb);
        Integer[] pxs = Utils.toIntegerPx(mResources.getDisplayMetrics(), steb);
        return setPaddingRelative(viewId, pxs);
    }


    @NonNull
    public VH setMargin(@IdRes int viewId, int margin) {
        return setMargin(viewId, margin, margin, margin, margin);
    }

    /**
     * @param left   the left margin in pixels
     * @param top    the top margin in pixels
     * @param right  the right margin in pixels
     * @param bottom the bottom margin in pixels
     */
    @NonNull
    public VH setMargin(@IdRes int viewId, int left, int top, int right, int bottom) {
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
        return self();
    }

    /**
     * @param ltrb Margin in pixels for left, top, right, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setMargin(@IdRes int viewId, @NonNull Integer[] ltrb) {
        Utils.checkPaddingOrMargin(ltrb);
        ViewGroup.LayoutParams lp = get(viewId).getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            Utils.replaceNull(ltrb, Utils.getMargin(mlp));
            setMargin(viewId, ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
        }
        return self();
    }

    @NonNull
    public VH setMarginWithDip(@IdRes int viewId, int margin) {
        int m = Utils.toIntPx(mResources.getDisplayMetrics(), margin);
        return setMargin(viewId, m, m, m, m);
    }

    @NonNull
    public VH setMarginWithDip(@IdRes int viewId, int left, int top, int right, int bottom) {
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        int l = Utils.toIntPx(metrics, left);
        int t = Utils.toIntPx(metrics, top);
        int r = Utils.toIntPx(metrics, right);
        int b = Utils.toIntPx(metrics, bottom);
        return setMargin(viewId, l, t, r, b);
    }

    /**
     * @param ltrb Margin in dip for left, top, right, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setMarginWithDip(@IdRes int viewId, @NonNull Integer[] ltrb) {
        Utils.checkPaddingOrMargin(ltrb);
        Integer[] pxs = Utils.toIntegerPx(mResources.getDisplayMetrics(), ltrb);
        return setMargin(viewId, pxs);
    }

    @NonNull
    public VH setMarginRelative(@IdRes int viewId, int margin) {
        return setMarginRelative(viewId, margin, margin, margin, margin);
    }

    @NonNull
    public VH setMarginRelative(@IdRes int viewId, int start, int top, int end, int bottom) {
        View view = get(viewId);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            MarginLayoutParamsCompat.setMarginStart(mlp, start);
            mlp.topMargin = top;
            MarginLayoutParamsCompat.setMarginEnd(mlp, end);
            mlp.bottomMargin = bottom;
            view.setLayoutParams(mlp);
        }
        return self();
    }

    /**
     * @param steb Margin in pixels for start, top, end, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setMarginRelative(@IdRes int viewId, @NonNull Integer[] steb) {
        Utils.checkPaddingOrMargin(steb);
        ViewGroup.LayoutParams lp = get(viewId).getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            Utils.replaceNull(steb, Utils.getRelativeMargin(mlp));
            setMarginRelative(viewId, steb[0], steb[1], steb[2], steb[3]);
        }
        return self();
    }

    @NonNull
    public VH setMarginRelativeWithDip(@IdRes int viewId, int margin) {
        int m = Utils.toIntPx(mResources.getDisplayMetrics(), margin);
        return setMarginRelative(viewId, m, m, m, m);
    }

    @NonNull
    public VH setMarginRelativeWithDip(@IdRes int viewId, int start, int top, int end, int bottom) {
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        int s = Utils.toIntPx(metrics, start);
        int t = Utils.toIntPx(metrics, top);
        int e = Utils.toIntPx(metrics, end);
        int b = Utils.toIntPx(metrics, bottom);
        return setMarginRelative(viewId, s, t, e, b);
    }

    /**
     * @param steb Margin in dip for start, top, end, and bottom.
     *             Use {@code null} if you want ignore the padding there.
     */
    @NonNull
    public VH setMarginRelativeWithDip(@IdRes int viewId, @NonNull Integer[] steb) {
        Utils.checkPaddingOrMargin(steb);
        Integer[] pxs = Utils.toIntegerPx(mResources.getDisplayMetrics(), steb);
        return setMarginRelative(viewId, pxs);
    }

    @NonNull
    public VH setBackground(@IdRes int viewId, Drawable background) {
        ViewCompat.setBackground(get(viewId), background);
        return self();
    }

    @NonNull
    public VH setBackgroundResource(@IdRes int viewId, @DrawableRes int drawableId) {
        get(viewId).setBackgroundResource(drawableId);
        return self();
    }

    @NonNull
    public VH setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        get(viewId).setBackgroundColor(color);
        return self();
    }

    @NonNull
    public VH setTag(@IdRes int viewId, Object tag) {
        get(viewId).setTag(tag);
        return self();
    }

    @NonNull
    public VH setTag(@IdRes int viewId, int key, Object tag) {
        get(viewId).setTag(key, tag);
        return self();
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
    public VH setLayoutParams(@IdRes int viewId, @NonNull ViewGroup.LayoutParams params) {
        get(viewId).setLayoutParams(params);
        return self();
    }

    public ViewGroup.LayoutParams getLayoutParams(@IdRes int viewId) {
        return get(viewId).getLayoutParams();
    }

    @NonNull
    public VH setText(@IdRes int textViewId, CharSequence text) {
        TextView view = get(textViewId);
        view.setText(text);
        return self();
    }

    @NonNull
    public VH setText(@IdRes int textViewId, @StringRes int stringId) {
        TextView view = get(textViewId);
        view.setText(stringId);
        return self();
    }

    public CharSequence getText(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getText();
    }

    @NonNull
    public String getString(@IdRes int textViewId) {
        return Utils.toString(getText(textViewId));
    }

    @NonNull
    public String getTrimmedString(@IdRes int textViewId) {
        return Utils.toTrimmedString(getText(textViewId));
    }

    @NonNull
    public VH setError(@IdRes int textViewId, @StringRes int stringId) {
        return setError(textViewId, mResources.getText(stringId));
    }

    @NonNull
    public VH setError(@IdRes int textViewId, CharSequence error) {
        TextView view = get(textViewId);
        view.setError(error);
        return self();
    }

    @NonNull
    public VH setError(@IdRes int textViewId, @StringRes int stringId, @DrawableRes int drawableId) {
        return setError(textViewId, mResources.getText(stringId), drawableId);
    }

    @NonNull
    public VH setError(@IdRes int textViewId, CharSequence error, @DrawableRes int drawableId) {
        return setError(textViewId, error, ContextCompat.getDrawable(mContext, drawableId));
    }

    @NonNull
    public VH setError(@IdRes int textViewId, @StringRes int stringId, Drawable icon) {
        return setError(textViewId, mResources.getText(stringId), icon);
    }

    @NonNull
    public VH setError(@IdRes int textViewId, CharSequence error, Drawable icon) {
        TextView view = get(textViewId);
        view.setError(error, icon);
        return self();
    }

    public CharSequence getError(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getError();
    }

    @NonNull
    public VH setTextColorWithRes(@IdRes int textViewId, @ColorRes int colorId) {
        ColorStateList stateList = ContextCompat.getColorStateList(mContext, colorId);
        if (stateList != null) {
            return setTextColor(textViewId, stateList);
        }
        return setTextColor(textViewId, ContextCompat.getColor(mContext, colorId));
    }

    @NonNull
    public VH setTextColor(@IdRes int textViewId, @ColorInt int color) {
        TextView view = get(textViewId);
        view.setTextColor(color);
        return self();
    }

    @NonNull
    public VH setTextColor(@IdRes int textViewId, @NonNull ColorStateList colors) {
        TextView view = get(textViewId);
        view.setTextColor(colors);
        return self();
    }

    @NonNull
    public VH batchTextColor(@ColorInt int color, @IdRes int... textViewIds) {
        for (int id : textViewIds) {
            setTextColor(id, color);
        }
        return self();
    }

    @NonNull
    public VH batchTextColor(@NonNull ColorStateList colors, @IdRes int... textViewIds) {
        for (int id : textViewIds) {
            setTextColor(id, colors);
        }
        return self();
    }

    @NonNull
    public VH batchTextColorWithRes(@ColorRes int colorId, @IdRes int... textViewIds) {
        ColorStateList stateList = ContextCompat.getColorStateList(mContext, colorId);
        if (stateList != null) {
            return batchTextColor(stateList, textViewIds);
        }
        return batchTextColor(ContextCompat.getColor(mContext, colorId), textViewIds);
    }

    /**
     * @see android.graphics.Paint#setFlags(int)
     */
    @NonNull
    public VH setPaintFlags(@IdRes int textViewId, int flags) {
        TextView view = get(textViewId);
        view.setPaintFlags(flags);
        return self();
    }

    public int getPaintFlags(@IdRes int textViewId) {
        TextView view = get(textViewId);
        return view.getPaintFlags();
    }

    @NonNull
    public VH setChecked(@IdRes int checkableId, boolean checked) {
        Checkable checkable = cast(checkableId);
        checkable.setChecked(checked);
        return self();
    }

    @NonNull
    public VH batchChecked(boolean checked, @IdRes int... checkableIds) {
        for (int id : checkableIds) {
            setChecked(id, checked);
        }
        return self();
    }

    public boolean isChecked(@IdRes int checkableId) {
        Checkable checkable = cast(checkableId);
        return checkable.isChecked();
    }

    @NonNull
    public VH toggle(@IdRes int checkableId) {
        Checkable checkable = cast(checkableId);
        checkable.toggle();
        return self();
    }

    @NonNull
    public VH setCheckedChange(@IdRes int compoundButtonId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton cb = get(compoundButtonId);
        cb.setOnCheckedChangeListener(listener);
        return self();
    }

    @NonNull
    public VH batchCheckedChange(CompoundButton.OnCheckedChangeListener listener, @IdRes int... compoundButtonIds) {
        for (int id : compoundButtonIds) {
            setCheckedChange(id, listener);
        }
        return self();
    }

    @NonNull
    public VH setImageResource(@IdRes int imageViewId, @DrawableRes int drawableId) {
        ImageView view = get(imageViewId);
        view.setImageResource(drawableId);
        return self();
    }

    @NonNull
    public VH setImageDrawable(@IdRes int imageViewId, Drawable drawable) {
        ImageView view = get(imageViewId);
        view.setImageDrawable(drawable);
        return self();
    }

    @NonNull
    public VH setImageUri(@IdRes int imageViewId, Uri uri) {
        ImageView view = get(imageViewId);
        view.setImageURI(uri);
        return self();
    }

    @NonNull
    public VH setImageFile(@IdRes int imageViewId, @NonNull File file) {
        return setImageUri(imageViewId, Uri.fromFile(file));
    }

    @NonNull
    public VH setImagePath(@IdRes int imageViewId, @NonNull String path) {
        return setImageFile(imageViewId, new File(path));
    }

    @NonNull
    public VH setImageBitmap(@IdRes int imageViewId, Bitmap bitmap) {
        ImageView view = get(imageViewId);
        view.setImageBitmap(bitmap);
        return self();
    }

    @NonNull
    public VH setProgress(@IdRes int progressBarId, int progress) {
        ProgressBar pb = get(progressBarId);
        pb.setProgress(progress);
        return self();
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N)
    public VH setProgress(@IdRes int progressBarId, int progress, boolean animate) {
        ProgressBar pb = get(progressBarId);
        pb.setProgress(progress, animate);
        return self();
    }

    @NonNull
    public VH setProgressBarColor(@IdRes int progressBarId, @ColorInt int color) {
        ProgressBar pb = get(progressBarId);
        Drawable pd = pb.getProgressDrawable();
        if (pd != null) {
//            pd.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            pd.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        }
        return self();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    public VH setForeground(@IdRes int viewId, Drawable foreground) {
        get(viewId).setForeground(foreground);
        return self();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    public VH setForegroundResource(@IdRes int viewId, @DrawableRes int drawableId) {
        Drawable foreground = ContextCompat.getDrawable(mContext, drawableId);
        return setForeground(viewId, foreground);
    }

    @SuppressWarnings("unchecked")
    private VH self() {
        return (VH) this;
    }
}
