package com.shuyu.apprecycler.bind.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.shuyu.apprecycler.R;
import com.shuyu.apprecycler.bind.holder.BindClickHolder;
import com.shuyu.apprecycler.bind.holder.BindImageHolder;
import com.shuyu.apprecycler.bind.holder.BindMutliHolder;
import com.shuyu.apprecycler.bind.holder.BindNoDataHolder;
import com.shuyu.apprecycler.bind.holder.BindTextHolder;
import com.shuyu.apprecycler.bind.model.BindClickModel;
import com.shuyu.apprecycler.bind.model.BindImageModel;
import com.shuyu.apprecycler.bind.model.BindMutliModel;
import com.shuyu.apprecycler.bind.model.BindTextModel;
import com.shuyu.apprecycler.bind.utils.BindDataUtils;
import com.shuyu.bind.decoration.BindDecorationBuilder;
import com.shuyu.bind.BindRecyclerAdapter;
import com.shuyu.bind.BindSuperAdapterManager;
import com.shuyu.bind.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 利用BindRecyclerAdapter实现，不带上下拉
 */
public class BindNormalActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;


    private List datas = new ArrayList<>();

    private BindRecyclerAdapter adapter;

    private BindSuperAdapterManager normalAdapterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_recycler_layout);
        ButterKnife.bind(this);
        init();
        initDatas();
    }

    public void init() {

        normalAdapterManager = new BindSuperAdapterManager();
        normalAdapterManager
                .bind(BindImageModel.class, BindImageHolder.ID, BindImageHolder.class)
                .bind(BindTextModel.class, BindTextHolder.ID, BindTextHolder.class)
                .bind(BindMutliModel.class, BindMutliHolder.ID, BindMutliHolder.class)
                .bind(BindClickModel.class, BindClickHolder.ID, BindClickHolder.class)
                .bindEmpty(BindNoDataHolder.NoDataModel.class, BindNoDataHolder.ID, BindNoDataHolder.class)
                .setNeedAnimation(true)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Context context, int position) {
                        Toast.makeText(context, "点击了！！　" + position, Toast.LENGTH_SHORT).show();
                    }
                });


        adapter = new BindRecyclerAdapter(this, normalAdapterManager, datas);

        recycler.setLayoutManager(new LinearLayoutManager(this));


        //间隔线
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.material_deep_teal_200));
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);
        paint.setStrokeWidth(dip2px(this, 5));
        recycler.addItemDecoration(new BindDecorationBuilder(adapter).setPaint(paint).setSpace(dip2px(this, 5)).builder());

        recycler.setAdapter(adapter);

    }

    /**
     * dip转为PX
     */
    public static int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }


    public void initDatas() {
        List list = BindDataUtils.getRefreshData();
        this.datas = list;
        if (adapter != null) {
            adapter.setListData(datas);
        }
    }

}
