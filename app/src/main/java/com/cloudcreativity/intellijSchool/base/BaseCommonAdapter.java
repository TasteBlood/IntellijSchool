package com.cloudcreativity.intellijSchool.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cloudcreativity.intellijSchool.R;
import com.cloudcreativity.intellijSchool.entity.CommonEntity;

import java.util.List;

public class BaseCommonAdapter extends BaseAdapter {

    private List<CommonEntity> commonEntities;
    private Context context;

    public BaseCommonAdapter(List<CommonEntity> commonEntities, Context context) {
        this.commonEntities = commonEntities;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commonEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return commonEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = new ViewHolder().getView(convertView);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CommonEntity entity = commonEntities.get(position);
        holder.tv_content.setText(entity.getCommonContent());
        if(entity.getToId()<=0&&TextUtils.isEmpty(entity.getToUserName())){
            //单评论
            holder.tv_to.setVisibility(View.GONE);
            holder.tvFromName.setText(entity.getFromUserName());
            holder.tvToName.setText("");
        }else{
            //多评论
            holder.tv_to.setVisibility(View.VISIBLE);
            holder.tvFromName.setText(entity.getFromUserName());
            holder.tvToName.setText(entity.getToUserName());
        }
        return convertView;
    }

    class ViewHolder{
        TextView tvToName;
        TextView tvFromName;
        TextView tv_content;
        TextView tv_to;

        View getView(View v){
            ViewHolder holder;
            if(v==null){
                holder = new ViewHolder();
                v = View.inflate(context, R.layout.layout_item_common,null);
                holder.tv_content = v.findViewById(R.id.tv_content);
                holder.tv_to = v.findViewById(R.id.tv_to);
                holder.tvFromName = v.findViewById(R.id.tv_fromName);
                holder.tvToName = v.findViewById(R.id.tv_toName);
                v.setTag(holder);
            }
            return v;
        }

    }
}
