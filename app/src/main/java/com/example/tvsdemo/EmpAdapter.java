package com.example.tvsdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.EmpAdapterViewHolder> {

    private static final String TAG = EmpAdapter.class.getSimpleName();
    private ArrayList<EmpDetails> empDetails = new ArrayList<>();
    private Context context;
    final private EmpAdapterClickHandler mClickHandler;

    public interface EmpAdapterClickHandler{
        void onClickForEmpDetails(EmpDetails details);
    }

    public EmpAdapter(Context context, EmpAdapterClickHandler emp){
        this.context = context;
        mClickHandler = emp;
    }

    public void setEmpObject(ArrayList<EmpDetails> employees){
        empDetails = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmpAdapter.EmpAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId =R.layout.emp_name_adapter;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, viewGroup, false);
        return new EmpAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpAdapter.EmpAdapterViewHolder empAdapterViewHolder, int i) {
        empAdapterViewHolder.mTextView.setText(empDetails.get(i).getEmpName());
    }

    @Override
    public int getItemCount() {
        if(empDetails.size() == 0)
            return 0;
        else
            return empDetails.size();
    }

    public class EmpAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView mTextView;

        public EmpAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.emp_names);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mClickHandler.onClickForEmpDetails(empDetails.get(getAdapterPosition()));
        }
    }

    public void viewSearchResult(ArrayList<EmpDetails> filteredList){
        empDetails = filteredList;
        notifyDataSetChanged();
    }
}
