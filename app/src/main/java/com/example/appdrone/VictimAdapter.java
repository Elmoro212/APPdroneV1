package com.example.appdrone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VictimAdapter extends RecyclerView.Adapter<VictimAdapter.VictimViewHolder> {

    private Context context;
    private List<Victim> victims;
    private OnItemClickListener onItemClickListener;
    private FirebaseFirestore db; // FirebaseFirestore instance


    public interface OnItemClickListener {
        void onItemClick(Victim victim);
    }

    public VictimAdapter(Context context, List<Victim> victims) {
        this.context = context;
        this.victims = victims;
        db = FirebaseFirestore.getInstance(); // Initialize FirebaseFirestore
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public VictimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_victim, parent, false);
        return new VictimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VictimViewHolder holder, int position) {
        Victim victim = victims.get(position);
        holder.bind(victim);

        // Set the initial state of the toggle button based on the "découvert" value
        holder.toggleButton.setChecked(victim.isDecouvert()); // Assuming you have a boolean isDecouvert() method in your Victim class

        // Set click listener for the toggle button
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the "découvert" status in Firebase
                DocumentReference victimRef = db.collection("victimes").document(String.valueOf(victim.getId())); // Corrected collection name: "victimes"
                victimRef.update("status", isChecked) // Corrected field name: "découvert"
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Update successful
                                // Update the victim object in your list as well
                                victim.setDecouvert(isChecked); // You might need to adjust this based on your Victim class
                                //victimAdapter.notifyItemChanged(victimList.indexOf(victim));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle error
                                // Revert the toggle button state and display an error message
                                holder.toggleButton.setChecked(!isChecked);
                                Log.e("VictimListActivity", "Error updating découvert status", e); // Log the error
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return victims.size();
    }

    public void setVictims(List<Victim> victims) {
        this.victims = victims;
    }

    public class VictimViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvLatitude;
        private TextView tvLongitude;
        private TextView tvAltitude;
        private TextView tvTime;
        private TextView tvStatut;
        private TextView tvId;
        public ToggleButton toggleButton;

        public VictimViewHolder(View itemView) {
            super(itemView);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            tvLongitude = itemView.findViewById(R.id.tvLongitude);
            tvAltitude = itemView.findViewById(R.id.tvAltitude);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatut = itemView.findViewById(R.id.tvStatut);
            tvId = itemView.findViewById(R.id.tvId);
            toggleButton = itemView.findViewById(R.id.toggleButton);
            itemView.setOnClickListener(this);
        }

        public void bind(Victim victim) {
            tvId.setText("Id: " + victim.getId());
            tvLatitude.setText("Latitude: " + victim.getLatitude());
            tvLongitude.setText("Longitude: " + victim.getLongitude());
            tvAltitude.setText("Altitude: " + victim.getAltitude());
            Long timestamp = victim.getTime_of_discovery();
            if (timestamp != null) {
                Date date = new Date(timestamp);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedTime = sdf.format(date);
                tvTime.setText("Time: " + formattedTime);
            } else {
                tvTime.setText("Time: N/A");
            }
            tvStatut.setText("Status: " + victim.getStatus());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(victims.get(position));
                }
            }
        }
    }
}