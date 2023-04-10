package com.example.login_register_firebase2.farmer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register_firebase2.R;
import com.example.login_register_firebase2.admin.DocumentDetailsActivity;

import java.util.ArrayList;

public class AppliedPermitAdapter extends RecyclerView.Adapter<AppliedPermitAdapter.AppliedPermitViewHolder> implements Parcelable {

    Context context;
    ArrayList<AppliedPermitModel> appliedPermitModels;

    public AppliedPermitAdapter(Context context, ArrayList<AppliedPermitModel> appliedPermitModels) {
        this.context = context;
        this.appliedPermitModels = appliedPermitModels;
    }

    protected AppliedPermitAdapter(Parcel in) {
        appliedPermitModels = in.createTypedArrayList(AppliedPermitModel.CREATOR);
    }

    public static final Creator<AppliedPermitAdapter> CREATOR = new Creator<AppliedPermitAdapter>() {
        @Override
        public AppliedPermitAdapter createFromParcel(Parcel in) {
            return new AppliedPermitAdapter(in);
        }

        @Override
        public AppliedPermitAdapter[] newArray(int size) {
            return new AppliedPermitAdapter[size];
        }
    };

    @NonNull
    @Override
    public AppliedPermitAdapter.AppliedPermitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.permit_list,parent,false);
        return new AppliedPermitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppliedPermitAdapter.AppliedPermitViewHolder holder, int position) {

        AppliedPermitModel appliedPermitModel = appliedPermitModels.get(position);
        holder.fullName.setText(appliedPermitModel.getFullName());
        holder.userEmail.setText(appliedPermitModel.getUserEmail());
        holder.phoneNumber.setText(String.valueOf(appliedPermitModel.getPhoneNumber()));
        holder.fieldNumber.setText(String.valueOf(appliedPermitModel.getFieldNumber()));
        holder.subLocation.setText(appliedPermitModel.getSubLocation());
        holder.area.setText(appliedPermitModel.getArea());
        holder.cropRotation.setText(appliedPermitModel.getCropRotation());
        holder.caneAge.setText(String.valueOf(appliedPermitModel.getCaneAge()));
        holder.estimatedTonnes.setText(String.valueOf(appliedPermitModel.getEstimatedTonnes()));
        holder.acreAge.setText(String.valueOf(appliedPermitModel.getAcreAge()));
        holder.bankName.setText(appliedPermitModel.getBankName());
        holder.bankAccountNumber.setText(String.valueOf(appliedPermitModel.getBankAccountNumber()));
        holder.selectedDate.setText(String.valueOf(appliedPermitModel.getSelectedDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DocumentDetailsActivity.class);
                intent.putExtra("selectedAppliedPermitModel", appliedPermitModels.get(holder.getBindingAdapterPosition()));
                //intent.putExtra("documentId",appliedPermitModel.getDocumentId());
                //intent.putExtra(DocumentDetailsActivity.EXTRA_DOCUMENT_ID,appliedPermitModel.getDocumentId());
                v.getContext().startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return appliedPermitModels.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedList(appliedPermitModels);
    }

    public static class AppliedPermitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView fullName,userEmail,phoneNumber,fieldNumber,subLocation,area,cropRotation,caneAge,estimatedTonnes,acreAge,
                bankName,bankAccountNumber,selectedDate;


        public AppliedPermitViewHolder(@NonNull View itemView) {
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
        }

        @Override
        public void onClick(View v) {

        }
    }
}
