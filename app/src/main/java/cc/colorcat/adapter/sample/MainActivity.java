package cc.colorcat.adapter.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cc.colorcat.adapter.ViewHolder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewHolder.from(this)
                .batchClick(
                        mClick,
                        R.id.btn_rv_adapter,
                        R.id.btn_lv_adapter,
                        R.id.btn_vp_adapter
                ).setBackground(R.id.btn_rv_adapter, R.color.colorAccent);
    }

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_lv_adapter:
                    navigateTo(LvAdapterActivity.class);
                    break;
                case R.id.btn_rv_adapter:
                    navigateTo(ChoiceRvAdapterActivity.class);
                    break;
                case R.id.btn_vp_adapter:
                    navigateTo(VpAdapterActivity.class);
                default:
                    break;
            }
        }
    };

    private void navigateTo(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
