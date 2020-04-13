package com.zhangxin.study.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.zhangxin.study.R;
import com.zhangxin.study.activity.menu.MenuActivity;
import com.zhangxin.study.activity.store.SplashActivity;
import com.zhangxin.study.activity.table.TableActivity;
import com.zhangxin.study.androidannotations.AndroidAnnotationsActivity;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.mvp.ui.MVPLoginActivity;
import com.zhangxin.study.view.CustomTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudyListActivity extends BaseActivity {


    @BindView(R.id.titleBar)
    CustomTitleBar titleBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private StudyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_study_list;
    }

    @Override
    protected void initView() {
        setSupportActionBar(titleBar.toolbar);
        getSupportActionBar().setTitle("");

        adapter = new StudyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(getData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startIntent(SplashActivity.class);
                        break;
                    case 1:
                        startIntent(TableActivity.class);
                        break;
                    case 2:
                        startIntent(MenuActivity.class);
                        break;
                    case 3:
                        startIntent(AndroidAnnotationsActivity.class);
                        break;
                    case 4:
                        startIntent(CanvasActivity.class);
                        break;
                    case 5:
                        startIntent(WebSocketActivity.class);
                        break;
                    case 6:
                        startIntent(RSAActivity.class);
                        break;
                    case 7:
                        startIntent(AESActivity.class);
                        break;
                    case 8:
                        startIntent(AppUpdateActivity.class);
                        break;
                    case 9:
                        startIntent(MVPLoginActivity.class);
                        break;
                }
            }
        });

    }


    @Override
    public boolean doubleExitAppEnable() {
        return true;
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<>();
        dataList.add("商城");
        dataList.add("仿Excel表格效果");
        dataList.add("扇形菜单按钮");
        dataList.add("AndroidAnnotations");
        dataList.add("canvas");
        dataList.add("webSocket");
        dataList.add("rsa加密解密");
        dataList.add("aes文件加解密");
        dataList.add("app版本更新");
        dataList.add("mvp学习");
        return dataList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @author zhangxin
     * @date 2019/3/27
     * @desc 适配器
     **/
    private class StudyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public StudyAdapter() {
            super(R.layout.item_study_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tvTitle, item);

        }
    }

}
