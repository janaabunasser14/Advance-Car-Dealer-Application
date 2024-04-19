package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class ListCarAdapter2 extends ArrayAdapter<ListCarRes> {
    public ListCarAdapter2(@NonNull FragmentActivity context, ArrayList<ListCarRes> dataArrayList) {
        super(context, R.layout.list_item2, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListCarRes listData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item2, parent, false);
        }

        ImageView listImage = view.findViewById(R.id.listImage);
        TextView listType = view.findViewById(R.id.Model);
        TextView StartDay = view.findViewById(R.id.StartDay);
        TextView EndDay=view.findViewById(R.id.EndDay);
        TextView price = view.findViewById(R.id.Cost);
        listType.setText(listData.getTypec());
        price.setText("Rent :"+ listData.getCost()+"$");
        StartDay.setText("StartDay :"+listData.getStartDay());
        EndDay.setText("EndDay :"+listData.getEndDate());
        return view;
    }
}