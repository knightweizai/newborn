package com.zaozhuang.newborn.chart;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class MyFillFormatter implements IFillFormatter {
    @Override
    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
        LineData data = dataProvider.getLineData();
        float y = data.getYMin(); // 返回当前节点的 y 值
        return y;
    }
}
