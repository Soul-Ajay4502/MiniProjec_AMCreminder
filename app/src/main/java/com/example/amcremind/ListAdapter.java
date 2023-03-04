package com.example.amcremind;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<Items> itemsList;
    public ListAdapter(Activity mContext, List<Items> itemsList){
        super(mContext,R.layout.list_item,itemsList);
        this.mContext=mContext;
        this.itemsList=itemsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView=inflater.inflate(R.layout.list_item,null,true);

        TextView id=listItemView.findViewById(R.id.id);
        TextView name=listItemView.findViewById(R.id.name);
        TextView price=listItemView.findViewById(R.id.pr);
        TextView renew=listItemView.findViewById(R.id.rene);
        TextView tot=listItemView.findViewById(R.id.total);
        TextView loc=listItemView.findViewById(R.id.location);

        Items item=itemsList.get(position);
        id.setText(item.getid());
        name.setText(item.getItemname());
        price.setText(item.getItemprice());
        renew.setText(item.getRenewal());
        tot.setText(item.getTotalno());
        loc.setText(item.getLocation());
        return listItemView;
    }
}
