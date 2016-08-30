package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apple.myapplication.R;

import java.util.List;

/**
 * Created by fazhao on 16/8/30.
 */
public class DragAdapter extends BaseAdapter {
    private List<String> name;
    private Context context;

    public DragAdapter(Context context,List<String>name){
        this.name = name;
        this.context = context;
    }


    @Override
    public int getCount() {
        return name.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return name.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder  = new ViewHolder();
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.drag_adapter,null);
            holder.title = (TextView) view.findViewById(R.id.drag_tv);
            view.setTag(holder);
        }else
            holder = (ViewHolder) view.getTag();
        if(i == 0) {
            holder.title.setText("(" + DragGridView.x + "," + DragGridView.y + ")"
                    + "   position:" + DragGridView.position);
            holder.title.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        holder.title.setText(name.get(i - 1));
        notifyDataSetChanged();
        return view;
    }

    class ViewHolder{
        TextView title;
    }
}
