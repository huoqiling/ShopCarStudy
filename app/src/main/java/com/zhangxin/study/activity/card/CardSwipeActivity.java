package com.zhangxin.study.activity.card;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lin.cardlib.CardLayoutManager;
import com.lin.cardlib.CardSetting;
import com.lin.cardlib.CardTouchHelperCallback;
import com.lin.cardlib.OnSwipeCardListener;
import com.lin.cardlib.utils.ReItemTouchHelper;
import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.utils.LogUtil;
import com.zhangxin.study.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhangxin
 * @date 2020/5/18
 * @desc 滑动
 **/
public class CardSwipeActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<Integer> list = new ArrayList<>();
    private MyAdapter cardAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_swipe;
    }

    @Override
    protected void initView() {
        getData();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());
        CardSetting setting = new CardSetting();
        setting.setSwipeListener(new OnSwipeCardListener<Integer>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                switch (direction) {
                    case ReItemTouchHelper.LEFT:
                        myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
                        LogUtil.e("aaa", "swiping direction=left");
                        break;
                    case ReItemTouchHelper.RIGHT:
                        myHolder.likeImageView.setAlpha(Math.abs(ratio));
                        LogUtil.e("aaa", "swiping direction=right");
                        break;
                    default:
                        myHolder.dislikeImageView.setAlpha(0f);
                        myHolder.likeImageView.setAlpha(0f);
                        break;
                }
            }


            @Override
            public void onSwipedOut(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                switch (direction) {
                    case ReItemTouchHelper.LEFT:
                        ToastUtil.showTextToast("swipe left out");
                        break;
                    case ReItemTouchHelper.RIGHT:
                        ToastUtil.showTextToast("swipe right out");
                        break;
                }
            }

            @Override
            public void onSwipedClear() {
                ToastUtil.showTextToast("cards are consumed");
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        cardAdapter.notifyDataSetChanged();
                    }
                }, 2000L);
            }
        });

        CardTouchHelperCallback helperCallback = new CardTouchHelperCallback(recyclerView, list, setting);
        ReItemTouchHelper mReItemTouchHelper = new ReItemTouchHelper(helperCallback);
        CardLayoutManager layoutManager = new CardLayoutManager(mReItemTouchHelper, setting);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new MyAdapter();
        recyclerView.setAdapter(cardAdapter);
    }


    private void getData() {
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);
    }


    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            avatarImageView.setImageResource(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            ImageView likeImageView;
            ImageView dislikeImageView;

            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
                dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
            }

        }
    }

}
