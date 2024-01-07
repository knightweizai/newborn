package com.zaozhuang.newborn.ui.chart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.reflect.TypeToken;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.chart.ChartConst;
import com.zaozhuang.newborn.chart.ChartEntity;
import com.zaozhuang.newborn.chart.ChartUtil;
import com.zaozhuang.newborn.chart.LegendEntity;
import com.zaozhuang.newborn.data.InputEntity;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartFragment extends BaseFragment {

    private int index;
    private LineChart lineChart;

    public static ChartFragment newInstance(int index) {
        ChartFragment fragment = new ChartFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("index", index);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart;
    }

    @Override
    protected void initView(View view) {

        index = getArguments().getInt("index");

        lineChart = view.findViewById(R.id.chart);

        if (index == 0) {
            setData0();
        } else if (index == 1) {
            setData1();
        } else if (index == 2) {
            setData2();
        }

    }

    /**
     * 身长
     */
    private void setData0() {
        List<ChartEntity> list = new ArrayList<>();
        ChartEntity entity1 = new ChartEntity();
        entity1.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.twentyFourNums));
        if (BabyManager.isBoy()) {
            entity1.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.lowBoyHeight));
        } else {
            entity1.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.lowGirlHeight));
        }

        entity1.curveLable = "低于此线需就医";
        entity1.color = R.color.text_yellow;

        ChartEntity entity2 = new ChartEntity();
        entity2.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.twentyFourNums));
        if (BabyManager.isBoy()) {
            entity2.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.highBoyHeight));
        } else {
            entity2.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.highGirlHeight));
        }

        entity2.curveLable = "高于此线需就医";
        entity2.color = R.color.goods_price_color;


        list.add(entity1);
        list.add(entity2);

        //获取用户手动输入的信息
        //这里获取身长
        String height = BabyManager.getHeight();
        if (!TextUtils.isEmpty(height)) {
            ChartEntity entity3 = new ChartEntity();
            entity3.xDataList = new ArrayList<>();
            entity3.yDataList = new ArrayList<>();

            List<InputEntity> inputList = GSON.fromJson(height, new TypeToken<List<InputEntity>>() {
            }.getType());
            for (int i = 0; i < inputList.size(); i++) {
                InputEntity inputEntity = inputList.get(i);
                entity3.xDataList.add(inputEntity.key);
                entity3.yDataList.add(inputEntity.value);
            }
            entity3.curveLable = "宝宝身长";
            if (BabyManager.isBoy()) {
                entity3.color = R.color.primary_color_boy;
            } else {
                entity3.color = R.color.primary_color_girl;
            }
            list.add(entity3);
        }

        List<LegendEntity> legendEntityList = new ArrayList<>();
//        LegendEntity legendEntity = new LegendEntity();
//        legendEntity.label = "低于此线需就医";
//        legendEntity.color = R.color.black;
//
//
//        LegendEntity legendEntity1 = new LegendEntity();
//        legendEntity1.label = "高于此线需就医";
//        legendEntity1.color = R.color.black;
//
//        legendEntityList.add(legendEntity);
//        legendEntityList.add(legendEntity1);

        ChartUtil.showChart2(getActivity(), lineChart, BabyManager.isBoy() ? ChartConst.boyHeightTitle : ChartConst.girlHeightTitle, "cm", list, legendEntityList);
    }

    //体重
    private void setData1() {

        List<ChartEntity> list = new ArrayList<>();
        ChartEntity entity1 = new ChartEntity();
        entity1.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.twelveNums));
        if (BabyManager.isBoy()) {
            entity1.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.lowBoyWeight));
        } else {
            entity1.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.lowGirlWeight));
        }
        entity1.curveLable = "低于此线需就医";
        entity1.color = R.color.text_yellow;

        ChartEntity entity2 = new ChartEntity();
        entity2.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.twelveNums));
        if (BabyManager.isBoy()) {
            entity2.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.highBoyWeight));
        } else {
            entity2.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.highGirlWeight));
        }
        entity2.curveLable = "高于此线需就医";
        entity2.color = R.color.goods_price_color;

        list.add(entity1);
        list.add(entity2);


        //获取用户手动输入的信息
        //这里获取体重
        String weight = BabyManager.getWeight();
        if (!TextUtils.isEmpty(weight)) {
            ChartEntity entity3 = new ChartEntity();
            entity3.xDataList = new ArrayList<>();
            entity3.yDataList = new ArrayList<>();

            List<InputEntity> inputList = GSON.fromJson(weight, new TypeToken<List<InputEntity>>() {
            }.getType());
            for (int i = 0; i < inputList.size(); i++) {
                InputEntity inputEntity = inputList.get(i);
                entity3.xDataList.add(inputEntity.key);
                entity3.yDataList.add(inputEntity.value);
            }
            entity3.curveLable = "宝宝体重";
            if (BabyManager.isBoy()) {
                entity3.color = R.color.primary_color_boy;
            } else {
                entity3.color = R.color.primary_color_girl;
            }
            list.add(entity3);
        }


        List<LegendEntity> legendEntityList = new ArrayList<>();
//        LegendEntity legendEntity = new LegendEntity();
//        legendEntity.label = "低于此线需就医";
//        legendEntity.color = R.color.black;
//
//
//        LegendEntity legendEntity1 = new LegendEntity();
//        legendEntity1.label = "高于此线需就医";
//        legendEntity1.color = R.color.black;
//
//        legendEntityList.add(legendEntity);
//        legendEntityList.add(legendEntity1);
        //显示图表,参数（ 上下文，图表对象， X轴数据，Y轴数据，图表标题，曲线图例名称，坐标点击弹出提示框中数字单位）
        ChartUtil.showChart2(getActivity(), lineChart, BabyManager.isBoy() ? ChartConst.boyWeightTitle : ChartConst.girlWeightTitle, "kg", list, legendEntityList);
    }

    private void setData2() {
        List<ChartEntity> list = new ArrayList<>();
        ChartEntity entity1 = new ChartEntity();
        entity1.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.huangDanHours));
        entity1.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.lowhuangdans));
        entity1.curveLable = "(暂观察)中低危风险线";
        entity1.color = R.color.huangdan_high;

        ChartEntity entity2 = new ChartEntity();
        entity2.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.huangDanHours));
        entity2.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.middlehuangdans));
        entity2.curveLable = "(就医)高中危风险线";
        entity2.color = R.color.huangdan_middle;


        ChartEntity entity3 = new ChartEntity();
        entity3.xDataList = new ArrayList<String>(Arrays.asList(ChartConst.huangDanHours));
        entity3.yDataList = new ArrayList<String>(Arrays.asList(ChartConst.highHuangdans));
        entity3.curveLable = "(就医)高危风险线";
        entity3.color = R.color.huangdan_low;

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);

        //获取用户手动输入的信息
        //这里获取体重
        String huangdan = BabyManager.getHuangdan();
        if (!TextUtils.isEmpty(huangdan)) {
            ChartEntity entity4 = new ChartEntity();
            entity4.xDataList = new ArrayList<>();
            entity4.yDataList = new ArrayList<>();

            List<InputEntity> inputList = GSON.fromJson(huangdan, new TypeToken<List<InputEntity>>() {
            }.getType());
            for (int i = 0; i < inputList.size(); i++) {
                InputEntity inputEntity = inputList.get(i);
                entity4.xDataList.add(inputEntity.key);
                entity4.yDataList.add(inputEntity.value);
            }
            entity4.curveLable = "宝宝黄疸";
            if (BabyManager.isBoy()) {
                entity4.color = R.color.primary_color_boy;
            } else {
                entity4.color = R.color.primary_color_girl;
            }
            list.add(entity4);
        }


        List<LegendEntity> legendEntityList = new ArrayList<>();
//        LegendEntity legendEntity = new LegendEntity();
//        legendEntity.label = "(暂观察)中低危风险线";
//        legendEntity.color = R.color.huangdan_high;
//        LegendEntity legendEntity1 = new LegendEntity();
//        legendEntity1.label = "(就医)高中危风险线";
//        legendEntity1.color = R.color.huangdan_middle;
//        LegendEntity legendEntity2 = new LegendEntity();
//        legendEntity2.label = "(就医)高危风险线";
//        legendEntity2.color = R.color.huangdan_low;
//
//        legendEntityList.add(legendEntity);
//        legendEntityList.add(legendEntity1);
//        legendEntityList.add(legendEntity2);

        ChartUtil.showChart2(getActivity(), lineChart, ChartConst.huangdanTitle, "mg/dl", list, legendEntityList);
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    protected void clear() {

    }
}
