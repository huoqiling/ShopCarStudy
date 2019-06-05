package com.zhangxin.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.zhangxin.study.R;

/**
 * @author zhangxin
 * @date 2019/4/24
 * @desc 自定义图片的TextView
 **/
public class DrawableTextView extends AppCompatTextView {

    private final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;
    private Drawable drawableImage;
    private int drawableWidth;
    private int drawableHeight;
    private int drawableLocation;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DrawableTextView, 0, 0);
        drawableImage = typedArray.getDrawable(R.styleable.DrawableTextView_drawableImage);
        drawableWidth = (int) typedArray.getDimension(R.styleable.DrawableTextView_drawableWidth, 0);
        drawableHeight = (int) typedArray.getDimension(R.styleable.DrawableTextView_drawableHeight, 0);
        drawableLocation = typedArray.getInt(R.styleable.DrawableTextView_drawableLocation, LEFT);
        typedArray.recycle();

        try {
            drawDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDrawableImage(Drawable drawableImage) {
        this.drawableImage = drawableImage;
        try {
            drawDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Drawable的宽高和位置
     */
    private void drawDrawable() throws Exception {
        if (drawableImage != null) {
            Bitmap bitmap = ((BitmapDrawable) drawableImage).getBitmap();
            Drawable drawable;
            if (drawableWidth != 0 && drawableHeight != 0) {
                drawable = new BitmapDrawable(getResources(), getScaleBitmap(bitmap, drawableWidth, drawableHeight));
            } else {
                drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap,
                        bitmap.getWidth(), bitmap.getHeight(), true));
            }
            drawable.setBounds(0, 0, drawableWidth, drawableHeight);
            switch (drawableLocation) {
                case LEFT:
                    setCompoundDrawables(drawable, null, null, null);
                    break;
                case TOP:
                    setCompoundDrawables(null, drawable, null, null);
                    break;
                case RIGHT:
                    setCompoundDrawables(null, null, drawable, null);
                    break;
                case BOTTOM:
                    setCompoundDrawables(null, null, null, drawable);
                    break;
            }
        }
    }

    /**
     * 等比缩放图片
     */
    private Bitmap getScaleBitmap(Bitmap bm, int mWidth, int mHeight) throws Exception {
        // 获得图片宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) mWidth) / width;
        float scaleHeight = ((float) mHeight) / height;
        // 设置缩放Matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 返回缩放后新图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
