package com.example.ui.person;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.data.database.AppDatabase;
import com.example.data.model.PersonModel;

import java.util.List;

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {

    private List<PersonModel> mPersonList;
    private View.OnLongClickListener longClickListener;

    public PersonAdaptor(List<PersonModel> mPersonList, View.OnLongClickListener longClickListener) {

        this.mPersonList=mPersonList;
        this.longClickListener=longClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(mPersonList.get(i).getName());
        myViewHolder.email.setText(mPersonList.get(i).getEmail());
        myViewHolder.number.setText(mPersonList.get(i).getNumber());
        myViewHolder.pincode.setText(mPersonList.get(i).getPincode());
        myViewHolder.city.setText(mPersonList.get(i).getCity());
        myViewHolder.itemView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }

    public void setTasks(List<PersonModel> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<PersonModel> getTasks() {

        return mPersonList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, pincode, number, city;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.person_name);
            email = itemView.findViewById(R.id.person_email);
            pincode = itemView.findViewById(R.id.person_pincode);
            number = itemView.findViewById(R.id.person_number);
            city = itemView.findViewById(R.id.person_city);

        }

    }

}
