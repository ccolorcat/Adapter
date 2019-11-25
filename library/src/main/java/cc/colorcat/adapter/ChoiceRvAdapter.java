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

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 提供实现了单选和多选功能的 {@link RecyclerView.Adapter}
 * <p>
 * Author: cxx
 * Date: 2018-5-31
 * GitHub: https://github.com/ccolorcat
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ChoiceRvAdapter extends RvAdapter {
    @IntDef({ChoiceMode.NONE, ChoiceMode.SINGLE, ChoiceMode.MULTIPLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChoiceMode {
        /**
         * Does not indicate choices
         */
        int NONE = 0;

        /**
         * Allows up to one choice
         */
        int SINGLE = 1;

        /**
         * Allows multiple choices
         */
        int MULTIPLE = 2;
    }

    @ChoiceMode
    private int mChoiceMode = ChoiceMode.NONE;
    private int mSelectedPosition = RecyclerView.NO_POSITION;
    private OnItemSelectedChangeListener mSelectedListener;
    private RecyclerView mRecyclerView;
    private RvSelectHelper mSelectHelper;

    @Override
    public final void onBindViewHolder(@NonNull RvHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (inChoiceMode() && isSelectable(position)) {
            updateItemView(holder, isSelectedWithChoiceMode(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        if (mSelectHelper == null) {
            mSelectHelper = new RvSelectHelper();
        }
        mRecyclerView.addOnItemTouchListener(mSelectHelper);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView.removeOnItemTouchListener(mSelectHelper);
        mRecyclerView = null;
    }

    /**
     * 设置选择模式
     *
     * @param choiceMode {@link ChoiceMode}
     * @throws IllegalArgumentException 如果 choiceMode 非法将抛出此异常
     * @see ChoiceMode#NONE 不可选，即禁用选择功能
     * @see ChoiceMode#SINGLE 单选
     * @see ChoiceMode#MULTIPLE 多选
     */
    public void setChoiceMode(@ChoiceMode int choiceMode) {
        Utils.checkChoiceMode(choiceMode);
        mChoiceMode = choiceMode;
    }

    @ChoiceMode
    public int getChoiceMode() {
        return mChoiceMode;
    }

    public void setOnItemSelectedChangeListener(OnItemSelectedChangeListener listener) {
        mSelectedListener = listener;
    }

    public OnItemSelectedChangeListener getOnItemSelectedChangeListener() {
        return mSelectedListener;
    }

    /**
     * 设置当前选中的 item
     *
     * @param position item 的位置
     */
    public void setSelection(int position) {
        if (inChoiceMode()
                && checkPosition(position)
                && isSelectable(position)
                && !isSelectedWithChoiceMode(position)) {
            dispatchSelect(position, true);
        }
    }

    /**
     * 仅单选模式下才有意义
     *
     * @return 选中的 item 的位置，如果没有 item 被选中返回 {@link RecyclerView#NO_POSITION}
     */
    public int getSelection() {
        return mSelectedPosition;
    }

    /**
     * 如果需要自己设置 item 选中和未选中的效果，可覆盖此方法实现。
     * 默认实现为 {@link View#setSelected(boolean)}
     *
     * @param holder   {@link RvHolder}
     * @param selected {@code true} 为选中，否则为未选中
     * @see View#setSelected(boolean)
     */
    protected void updateItemView(@NonNull RvHolder holder, boolean selected) {
        holder.itemView.setSelected(selected);
    }

    /**
     * 多选模式下需覆盖此方法，以判断每个位置对应的 item 是否被选中。
     * 默认实现仅针对单选模式。
     *
     * @see ChoiceRvAdapter#updateItem(int, boolean)
     */
    protected boolean isSelected(int position) {
        return mChoiceMode == ChoiceMode.SINGLE
                && mSelectedPosition != RecyclerView.NO_POSITION
                && mSelectedPosition == position;
    }

    /**
     * 可覆盖此方法记录 item 选中与否的状态。
     *
     * @see ChoiceRvAdapter#isSelected(int)
     */
    protected void updateItem(int position, boolean selected) {

    }

    /**
     * @return 如果 position 所对应的 item 可以被选择就返回 true, 否则返回 false.
     */
    protected boolean isSelectable(int position) {
        return position != RecyclerView.NO_POSITION;
    }

    private void dispatchSelect(int position, boolean selected) {
        if (mChoiceMode == ChoiceMode.SINGLE) {
            if (selected) {
                final int last = mSelectedPosition;
                mSelectedPosition = position;
                if (checkPosition(last)) {
                    dispatchSelect(last, false);
                }
                notifySelectedChanged(mSelectedPosition, true);
            } else {
                notifySelectedChanged(position, false);
            }
        } else {
            notifySelectedChanged(position, selected);
        }
    }

    private void notifySelectedChanged(int position, boolean selected) {
        updateItem(position, selected);
        RvHolder holder = (RvHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            updateItemView(holder, selected);
        } else {
            notifyItemChanged(position);
        }
        if (mSelectedListener != null) {
            mSelectedListener.onItemSelectedChanged(position, selected);
        }
    }

    private boolean inChoiceMode() {
        return mChoiceMode == ChoiceMode.SINGLE || mChoiceMode == ChoiceMode.MULTIPLE;
    }

    private boolean checkPosition(int position) {
        return position >= 0 && position < getItemCount();
    }

    private boolean isSelectedWithChoiceMode(int position) {
        if (mChoiceMode == ChoiceMode.SINGLE) {
            return mSelectedPosition != RecyclerView.NO_POSITION && mSelectedPosition == position;
        }
        return mChoiceMode == ChoiceMode.MULTIPLE && isSelected(position);
    }


    private class RvSelectHelper extends RecyclerView.SimpleOnItemTouchListener {
        private GestureDetectorCompat mDetector;
        private RecyclerView mRv;

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            mRv = rv;
            dispatchTouchEvent(e);
            return false;
        }

        private void dispatchTouchEvent(@NonNull MotionEvent e) {
            if (mDetector == null) {
                mDetector = new GestureDetectorCompat(mRv.getContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        if (inChoiceMode()) {
                            RecyclerView.ViewHolder holder = findViewHolder(e);
                            if (holder != null) {
                                final int position = holder.getAdapterPosition();
                                if (isSelectable(position)) {
                                    boolean selected = isSelectedWithChoiceMode(position);
                                    if (mChoiceMode == ChoiceMode.MULTIPLE) {
                                        dispatchSelect(position, !selected);
                                    } else if (!selected) {
                                        dispatchSelect(position, true);
                                    }
                                }
                            }
                        }
                        return false;
                    }
                });
            }
            mDetector.onTouchEvent(e);
        }

        private RecyclerView.ViewHolder findViewHolder(MotionEvent e) {
            View child = mRv.findChildViewUnder(e.getX(), e.getY());
            return child != null ? mRv.getChildViewHolder(child) : null;
        }
    }


    public interface OnItemSelectedChangeListener {
        void onItemSelectedChanged(int position, boolean selected);
    }
}
