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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cc.colorcat.adapter.VHolder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VHolder.from(this).batchClick(
                mClick,
                R.id.btn_rv_adapter,
                R.id.btn_lv_adapter,
                R.id.btn_vp_adapter,
                R.id.btn_group_adapter
        );
    }

    private final View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_lv_adapter) {
                navigateTo(LvAdapterActivity.class);
            } else if (id == R.id.btn_rv_adapter) {
                navigateTo(ChoiceRvAdapterActivity.class);
            } else if (id == R.id.btn_vp_adapter) {
                navigateTo(VpAdapterActivity.class);
            } else if (id == R.id.btn_group_adapter) {
                navigateTo(ContainerActivity.class);
            }
        }
    };

    private void navigateTo(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
