package com.zhangxin.study.activity.table;

import android.os.Bundle;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnClickListener;
import com.zhangxin.study.CustomDialogActivity;
import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.bean.UserInfo;
import com.zhangxin.study.utils.ToastUtil;
import com.zhangxin.study.view.CustomTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangxin
 * @date 2019/3/27
 * @desc
 **/
public class TableActivity extends BaseActivity {


    @BindView(R.id.table)
    SmartTable table;

    @BindView(R.id.titleBar)
    CustomTitleBar titleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_table;
    }

    @Override
    protected void initView() {
        initTable();
        initTitleBar();
    }

    private void initTitleBar() {
        setSupportActionBar(titleBar.toolbar);
        getSupportActionBar().setTitle("");
        titleBar.setRightBtnText("弹窗");
        titleBar.setOnCustomTitleBarListener(new CustomTitleBar.OnCustomTitleBarListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                startIntent(CustomDialogActivity.class);

            }

            @Override
            public void onTitleClick() {

            }
        });
    }

    private void initTable() {

        List<UserInfo> list = new ArrayList<>();
        list.add(new UserInfo("萧锋","降龙十八掌",30));
        list.add(new UserInfo("虚竹","天山折梅手",27));
        list.add(new UserInfo("段誉","六脉神剑",24));
        list.add(new UserInfo("天山童姥","八荒六合唯我独尊功",96));
        table.setData(list);
        table.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {

            }
        });
        table.getTableData().setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                ToastUtil.showTextToast(value);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
