package com.leary.littlefairy.beautifulapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.model.Entity.PhotoSetInfo;
import com.leary.littlefairy.beautifulapp.model.HttpProcessor.HttpCallback;
import com.leary.littlefairy.beautifulapp.model.HttpProcessor.HttpHelper;
import com.leary.littlefairy.beautifulapp.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkTestActivity extends AppCompatActivity {

    @BindView(R.id.tv_json)
    TextView tvJson;

    private static final String TAG = NetworkTestActivity.class.getSimpleName();
    private String url = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";
    private Map<String, Object> mParams = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);
    }

    public void loadData(View view) {
        HttpHelper.getInstance().get(url, mParams, new HttpCallback<PhotoSetInfo>() {
            @Override
            public void onSuccess(PhotoSetInfo result) {
                StringBuilder sb = new StringBuilder();
                if (result != null) {
                    sb.append("来源：").append(result.getSource()).append("\r\n")
                            .append("Tag：").append(result.getSettag()).append("\r\n")
                            .append("天气描述：").append(result.getDesc());
                }
                tvJson.setText(sb.toString());
            }

            @Override
            public void onFail(String e) {
                super.onFail(e);
                ToastUtils.with(NetworkTestActivity.this).show("网络请求失败啦出错啦");
                Log.e("leary", "网络请求失败啦出错啦");
            }
        });
    }
}
