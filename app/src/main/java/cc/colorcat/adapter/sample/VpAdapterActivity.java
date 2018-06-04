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

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cc.colorcat.adapter.VpAdapter;
import cc.colorcat.adapter.VpHolder;

/**
 * Author: cxx
 * Date: 2018-06-04
 * GitHub: https://github.com/ccolorcat
 */
public class VpAdapterActivity extends AppCompatActivity {
    @DrawableRes
    private int[] mImage = {
            R.drawable.bg_img0,
            R.drawable.bg_img1,
            R.drawable.bg_img2,
            R.drawable.bg_img3,
            R.drawable.bg_img4
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_adapter);

        ViewPager viewPage = findViewById(R.id.vp_root);
        viewPage.setOffscreenPageLimit(mImage.length);
        viewPage.setAdapter(new VpAdapter() {
            @Override
            public int getViewType(int position) {
                return position == 2 ? 1 : 0;
            }

            @Override
            public int getLayoutResId(int viewType) {
                switch (viewType) {
                    case 0:
                        return R.layout.item_image;
                    case 1:
                        return R.layout.item_sample;
                    default:
                        throw new IllegalArgumentException("illegal viewType = " + viewType);
                }
            }

            @Override
            public void bindView(@NonNull VpHolder holder, int position) {
                if (holder.getViewType() == 0) {
                    int index = position > 2 ? position - 1 : position;
                    holder.setImageResource(R.id.iv_image, mImage[index]);
                } else {
                    holder.setImageResource(R.id.iv_icon, R.mipmap.ic_launcher_round)
                            .setText(R.id.tv_content, "this is a test");
                }
            }

            @Override
            public int getCount() {
                return mImage.length + 1;
            }
        });
    }
}
