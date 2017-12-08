package com.leary.littlefairy.beautifulapp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.model.Entity.HomeWork;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;
import com.leary.littlefairy.beautifulapp.model.Entity.TipInfo;
import com.leary.littlefairy.beautifulapp.model.network.OnAppDialogClickListener;
import com.leary.littlefairy.beautifulapp.model.network.api.ApiClient;
import com.leary.littlefairy.beautifulapp.model.util.GlobalVariable;
import com.leary.littlefairy.beautifulapp.ui.adapter.HomeworkAdapter;
import com.leary.littlefairy.beautifulapp.ui.persenter.contracts.IMainPresenter;
import com.leary.littlefairy.beautifulapp.ui.persenter.implement.MainPresenter;
import com.leary.littlefairy.beautifulapp.ui.view.IMainView;
import com.leary.littlefairy.beautifulapp.utils.ActivityAlertDialogManager;
import com.leary.littlefairy.beautifulapp.utils.LogUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements IMainView{
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Subscription mSubscription;
    private List<HomeWork> mList;
    private HomeworkAdapter adapter;
    private IMainPresenter mainPresenter;
    /**
     * app需要开通的运行时权限
     */
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private static final String[] PERMISSIONS_STR = new String[]{
            "读写手机存储",
            "获取手机信息"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        ButterKnife.bind(this);
        requestPermission(PERMISSIONS);
        initData();
    }

    private void requestPermission(String[] permissions) {
        boolean isMinSdkM = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
        if (isMinSdkM || permissions.length == 0) {
            //低于6。0或无权限，则直接通过
            initWidget();
            return;
        }
        ActivityCompat.requestPermissions(this, PERMISSIONS, GlobalVariable.REQUEST_CODE_PERMISSION);
    }

    private void initData() {

    }

    Observer<Result<List<HomeWork>>> mObservable = new Observer<Result<List<HomeWork>>>(){
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            LogUtils.e(TAG + " " + e.getMessage());
        }

        @Override
        public void onNext(Result<List<HomeWork>> result) {
            if (result.isSuccess()) {
                adapter.initData(result.getDetail());
            }
        }
    };
    private void initWidget() {
        mainPresenter = new MainPresenter(this, this, mSubscription);
        toolbar.setTitle("Hello My Fairy");
        toolbar.setNavigationIcon(R.drawable.lepay_icon_menu_white);
        mList = new ArrayList<>();
        adapter = new HomeworkAdapter(this,mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mainPresenter.loadMainData();
    }

    @Override
    public void loadMainDataOk() {
        unsubscribe();
        mSubscription = ApiClient.service
                .getMainData("leary", 272, 8, 1, 100/*,");50"
                        , String.valueOf(System.currentTimeMillis() / 1000)
                        ,"2BC3D5AB-5E71-191C-46B8-4E24E6DD4F70","0","10001"*/)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObservable);

    }
    @Override
    public void loadMainDataStart() {

    }

    @Override
    public void loadMainDataFinish() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GlobalVariable.REQUEST_CODE_PERMISSION) {
            for (int i = 0; i < grantResults.length; i++) {
                boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (isTip) {//表明用户没有彻底禁止弹出权限请求
                        requestPermission(new String[]{permissions[i]});
                    } else {
                        //表明用户已经彻底禁止弹出权限请求
                        TipInfo tipInfo = TipInfo.createTipInfo("重要提示", String.format("请开启【%s】权限", PERMISSIONS_STR[i]));
                        tipInfo.setSureBtnText("去设置");
                        AlertDialog ad = ActivityAlertDialogManager.displayOneBtnDialog(this, tipInfo, new OnAppDialogClickListener() {
                            @Override
                            protected void onAppClick(DialogInterface dialogInterface, int i) {
                                getAppDetailSettingIntent(MainActivity.this);
                                finish();
                            }
                        });
                        ad.setCancelable(false);
                        ad.show();
                    }
                    return;
                }
            }
            initWidget();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }
}
