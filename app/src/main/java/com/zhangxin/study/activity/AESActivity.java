package com.zhangxin.study.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import io.reactivex.functions.Consumer;

/**
 * @author zhangxin
 * @date 2019/6/26
 * @desc
 **/
public class AESActivity extends BaseActivity {


    @BindView(R.id.btnEncryption)
    TextView btnEncryption;

    @BindView(R.id.btnDecryption)
    TextView btnDecryption;

    @BindView(R.id.photoLayout)
    BGASortableNinePhotoLayout photoLayout;

    private String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AESImage/";
    private static final int ADD_PHOTO_ITEM = 1;//添加图片
    private static final int LOOK_PHOTO_ITEM = 2;//查看图片


    @Override
    protected int getLayoutId() {
        return R.layout.activity_aes;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {

        photoLayout.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
                RxPermissions rxPermissions = new RxPermissions(AESActivity.this);
                rxPermissions.request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) throws Exception {
                                if (granted) {
                                    addImage();
                                } else {
                                    ToastUtil.showTextToast("您拒绝了权限，将无法正常使用");
                                }
                            }
                        });
            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                photoLayout.removeItem(position);
            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                startActivityForResult(new BGAPhotoPickerPreviewActivity.IntentBuilder(AESActivity.this)
                        .maxChooseCount(photoLayout.getMaxItemCount())
                        .currentPosition(position)
                        .selectedPhotos(models)
                        .previewPhotos(models)
                        .isFromTakePhoto(false)
                        .build(), LOOK_PHOTO_ITEM);
            }

            @Override
            public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

            }
        });
    }

    private void addImage() {
        File file = new File(SD_PATH);
        startActivityForResult(new BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(file)
                .maxChooseCount(photoLayout.getMaxItemCount() - photoLayout.getItemCount())
                .pauseOnScroll(false)
                .selectedPhotos(null)
                .build(), ADD_PHOTO_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOOK_PHOTO_ITEM && resultCode == Activity.RESULT_OK) {
            photoLayout.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }

        if (requestCode == ADD_PHOTO_ITEM && resultCode == Activity.RESULT_OK) {
            photoLayout.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnEncryption, R.id.btnDecryption})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEncryption:
                break;
            case R.id.btnDecryption:
                break;
        }
    }
}
