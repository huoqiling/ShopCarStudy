package com.zhangxin.study.activity.menu;

import android.os.Bundle;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.menu.ButtonData;
import com.zhangxin.study.menu.ButtonEventListener;
import com.zhangxin.study.menu.SectorMenuButton;
import com.zhangxin.study.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends BaseActivity {

    @BindView(R.id.sector_menu)
    SectorMenuButton sectorMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void initView() {
        initSectorMenuButton();
    }

    private void initSectorMenuButton() {
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        for (int i = 0; i < 4; i++) {
            //最后一个参数表示padding
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenu.setButtonDatas(buttonDatas);
        setListener(sectorMenu);
    }

    private void setListener(final SectorMenuButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                ToastUtil.showTextToast("button" + index);
            }
            @Override
            public void onExpand() {
                ToastUtil.showTextToast("onExpand");
            }
            @Override
            public void onCollapse() {
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
