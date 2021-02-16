package com.example.temperature;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TemperatureAdapter extends BaseAdapter {
   private List<TemperatureBean> mlist;
   private Context mContext;
   private LayoutInflater mlayoutInflater;
   public TemperatureAdapter(Context context, List<TemperatureBean> list){
       mContext=context;
       mlist=list;
       mlayoutInflater=LayoutInflater.from(context);
   }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=mlayoutInflater.inflate(R.layout.list_items,null);
            viewHolder.mTvName=convertView.findViewById(R.id.main_tv_name);
            viewHolder.mTvTime=convertView.findViewById(R.id.main_tv_time);
            viewHolder.mTvTemperature=convertView.findViewById(R.id.main_tv_temperature);
            viewHolder.mTvWhere=convertView.findViewById(R.id.main_tv_there);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        TemperatureBean bean=mlist.get(position);
        viewHolder.mTvName.setText(bean.n_ame);
        viewHolder.mTvTime.setText(bean.timeTv);
        viewHolder.mTvTemperature.setText(bean.temperature);
        viewHolder.mTvWhere.setText(bean.where);
        return convertView;
    }
    private static class ViewHolder{
        public TextView mTvName;
        public TextView mTvTemperature;
        public TextView mTvTime;
        public TextView mTvWhere;
    }
}
