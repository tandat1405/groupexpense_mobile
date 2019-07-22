package com.datnt.groupexpense.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.datnt.groupexpense.ModelRetrofit.ExpenseResult;
import com.datnt.groupexpense.R;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends BaseAdapter {
    private List<ExpenseResult> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChartAdapter(Context aContext, List<ExpenseResult> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_chart_view, null);
            holder = new ViewHolder();
            holder.ownerName = convertView.findViewById(R.id.tv_chart_owner);
            holder.anyChartView = convertView.findViewById(R.id.any_chart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExpenseResult expenseResult = this.listData.get(position);
        holder.ownerName.setText(expenseResult.getUsername());

        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();
        for(int j=0;j<expenseResult.getExpenseResponses().size();j++){
            data.add(new ValueDataEntry(expenseResult.getExpenseResponses().get(j).getName(), expenseResult.getExpenseResponses().get(j).getQuantity()));
        }
        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");
        cartesian.animation(true);
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Expense");
        cartesian.yAxis(0).title("VNĐ");
        cartesian.xScroller(true);
        cartesian.xZoom(true);
        holder.anyChartView.setChart(cartesian);

        return convertView;
    }

    static class ViewHolder {
        TextView ownerName;
        AnyChartView anyChartView;
    }
}
