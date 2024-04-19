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

public class ListCarAdapter4 extends ArrayAdapter<ListCarResUser> {
    public ListCarAdapter4(@NonNull FragmentActivity context, ArrayList<ListCarResUser> dataArrayList) {
        super(context, R.layout.list_item3, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListCarResUser listData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item3, parent, false);
        }

        ImageView listImage = view.findViewById(R.id.listImage);
        TextView listType = view.findViewById(R.id.Model);
        TextView StartDay = view.findViewById(R.id.StartDay);
        TextView EndDay=view.findViewById(R.id.EndDay);
        TextView user = view.findViewById(R.id.user);
        listType.setText(listData.getModel());
        user.setText(listData.getuser());
        StartDay.setText("StartDay :"+listData.getStartDay());
        EndDay.setText("EndDay :"+listData.getEndDate());
        return view;
    }
}