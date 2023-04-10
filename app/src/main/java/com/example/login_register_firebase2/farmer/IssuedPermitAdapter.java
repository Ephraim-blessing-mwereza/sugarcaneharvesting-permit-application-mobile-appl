package com.example.login_register_firebase2.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register_firebase2.R;
import com.example.login_register_firebase2.admin.IssuePermitModel;

import java.util.ArrayList;


public class IssuedPermitAdapter extends RecyclerView.Adapter<com.example.login_register_firebase2.farmer.IssuedPermitAdapter.MyViewHolder>  {
    Context context;
    ArrayList<IssuedPermitModel> issuedPermitModels;

    public IssuedPermitAdapter(Context context, ArrayList<IssuedPermitModel> issuedPermitModels) {
        this.context = context;
        this.issuedPermitModels = issuedPermitModels;
    }


    @NonNull
    @Override
    public com.example.login_register_firebase2.farmer.IssuedPermitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.issue_permit_item_view,parent,false);

        return new com.example.login_register_firebase2.farmer.IssuedPermitAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.login_register_firebase2.farmer.IssuedPermitAdapter.MyViewHolder holder, int position) {

        IssuedPermitModel model = issuedPermitModels.get(position);
        holder.fullName.setText(model.getFullName());
        holder.userEmail.setText(model.getUserEmail());
        holder.phoneNumber.setText(String.valueOf(model.getPhoneNumber()));
        holder.fieldNumber.setText(String.valueOf(model.getFieldNumber()));
        holder.subLocation.setText(model.getSubLocation());
        holder.area.setText(model.getArea());
        holder.cropRotation.setText(model.getCropRotation());
        holder.caneAge.setText(String.valueOf(model.getCaneAge()));
        holder.estimatedTonnes.setText(String.valueOf(model.getEstimatedTonnes()));
        holder.acreAge.setText(String.valueOf(model.getAcreAge()));
        holder.bankName.setText(model.getBankName());
        holder.bankAccountNumber.setText(String.valueOf(model.getBankAccountNumber()));
        holder.selectedDate.setText(String.valueOf(model.getSelectedDate()));
        holder.allocatedHarvestingDate.setText(model.getIssuedDate());
        holder.trucksIssued.setText(String.valueOf(model.getTrucksIssued()));


    }

    @Override
    public int getItemCount() {
        return issuedPermitModels.size();
    }


    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullName,userEmail,phoneNumber,fieldNumber,subLocation,area,cropRotation,caneAge,estimatedTonnes,acreAge,
                bankName,bankAccountNumber,selectedDate, allocatedHarvestingDate, trucksIssued;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName =itemView.findViewById(R.id.fullName);
            userEmail = itemView.findViewById(R.id.userEmail);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            fieldNumber =itemView.findViewById(R.id.fieldNumber);
            subLocation = itemView.findViewById(R.id.subLocation);
            area = itemView.findViewById(R.id.area);
            cropRotation =itemView.findViewById(R.id.cropRotation);
            caneAge = itemView.findViewById(R.id.caneAge);
            estimatedTonnes = itemView.findViewById(R.id.estimatedTonnes);
            acreAge =itemView.findViewById(R.id.acreAge);
            bankName = itemView.findViewById(R.id.bankName);
            bankAccountNumber = itemView.findViewById(R.id.bankAccountNumber);
            selectedDate = itemView.findViewById(R.id.selectedDate);
            allocatedHarvestingDate = itemView.findViewById(R.id.dateTextView);
            trucksIssued = itemView.findViewById(R.id.trailersIssuedForTransport);

        }
    }
}