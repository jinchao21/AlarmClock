package com.example.ljc.alarmclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ljc.alarmclock.R;

/**
 * Created by ljc on 17-10-17.
 */

public class AlarmAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    public AlarmAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
    }

//    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
//
//    /*构造函数*/
//    public MyAdapter(Context context) {
//        this.mInflater = LayoutInflater.from(context);
//    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.alarm_item, null);
            holder = new ViewHolder();
            holder.alarmTime = (TextView)convertView.findViewById(R.id.alarmTime);
            holder.ringOrvibrate = (TextView)convertView.findViewById(R.id.ringOrvibrate);
            holder.repeat = (TextView)convertView.findViewById(R.id.repeat);
            holder.alarmState = (Switch)convertView.findViewById(R.id.alarmState);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    public final class ViewHolder{
        public TextView alarmTime;
        public TextView ringOrvibrate;
        public TextView repeat;
        public Switch alarmState;
    }

//    @Override
//    public View getView(finalint position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        //观察convertView随ListView滚动情况
//        Log.v("MyListViewBase", "getView " + position + " " + convertView);
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.item,
//                    null);
//            holder = new ViewHolder();
//                    /*得到各个控件的对象*/
//            holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
//            holder.text = (TextView) convertView.findViewById(R.id.ItemText);
//            holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
//            convertView.setTag(holder);//绑定ViewHolder对象
//        }
//        else{
//            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
//        }
//            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
//        holder.title.setText(getDate().get(position).get("ItemTitle").toString());
//        holder.text.setText(getDate().get(position).get("ItemText").toString());
//
//            /*为Button添加点击事件*/
//        holder.bt.setOnClickListener(new OnClickListener() {
//
//            @Override
//            publicvoid onClick(View v) {
//                Log.v("MyListViewBase", "你点击了按钮" + position);                                //打印Button的点击信息
//
//            }
//        });
//
//        return convertView;
//    }
//
//    /*存放控件*/
//    public final class ViewHolder{
//public TextView title;
//public TextView text;
//public Button   bt;
//        }
}
