package com.datnt.groupexpense.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datnt.groupexpense.ModelRetrofit.ExpenseResult;
import com.datnt.groupexpense.R;

import java.util.ArrayList;
import java.util.List;

import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

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
            holder.chart = convertView.findViewById(R.id.bar_chart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExpenseResult expenseResult = this.listData.get(position);
        holder.ownerName.setText(expenseResult.getUsername());
        int tempMaxExpense = 0;
        for (int i = 1; i < expenseResult.getExpenseResponses().size(); i++) {
            if (tempMaxExpense < expenseResult.getExpenseResponses().get(i).getQuantity()) {
                tempMaxExpense = expenseResult.getExpenseResponses().get(i).getQuantity();
            }
        }
        holder.chart.setBarMaxValue(tempMaxExpense);
        List<BarChartModel> barChartModelList = new ArrayList<>();
        for (int i = 0; i < expenseResult.getExpenseResponses().size(); i++) {
            BarChartModel barChartModel = new BarChartModel();
            barChartModel.setBarValue(expenseResult.getExpenseResponses().get(i).getQuantity());
            barChartModel.setBarColor(Color.parseColor("#2bb0fc"));
            barChartModel.setBarTag(null);
            barChartModel.setBarText(expenseResult.getExpenseResponses().get(i).getName() + "\n"
                    + expenseResult.getExpenseResponses().get(i).getQuantity());
            barChartModelList.add(barChartModel);
        }
        holder.chart.addBar(barChartModelList);
        System.out.println(expenseResult.getExpenseResponses().size() + "----------------------------------");
        return convertView;
    }

    static class ViewHolder {
        TextView ownerName;
        BarChart chart;
    }
}
