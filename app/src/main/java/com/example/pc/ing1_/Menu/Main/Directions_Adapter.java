package com.example.pc.ing1_.Menu.Main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.ing1_.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Directions_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<JsonObject> paths;
    static int check;
    private PathClickListener itemClickListener;
    public interface PathClickListener{
        void onItemClicke_Path(View view,int position);
    }
    public void setOnClickListener_Path(PathClickListener listener){
        this.itemClickListener=listener;
    }

    public Directions_Adapter(    ArrayList<JsonObject> paths) {
        this.paths=paths;
        check=0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.directions_item,viewGroup,false);

        Directions_ViewHolder viewHolder=new Directions_ViewHolder (view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ((Directions_ViewHolder)viewHolder).path_info.setText("");
        if(i==check){
            ((Directions_ViewHolder)viewHolder).check.setVisibility(View.VISIBLE);
        }else{
            ((Directions_ViewHolder)viewHolder).check.setVisibility(View.GONE);

        }
        ((Directions_ViewHolder)viewHolder).pathnum.setText("경로 "+(i+1));
        JsonObject jsonObject=paths.get(i).getAsJsonObject().get("summary").getAsJsonObject();
        int minutes=(int)(jsonObject.get("duration").getAsDouble()/(1000*60)%60);
        double meters = jsonObject.get("distance").getAsDouble();
        ((Directions_ViewHolder)viewHolder).path_distance.setText(Math.round(meters/1000*10)/10.0+" km");
        ((Directions_ViewHolder)viewHolder).path_time.setText(minutes+" 분");
        ((Directions_ViewHolder)viewHolder).path_taxi.setText(jsonObject.get("taxiFare").toString()+"원");
        ((Directions_ViewHolder)viewHolder).path_oil.setText(jsonObject.get("fuelPrice").toString()+"원");

        for(int j=0; j<paths.get(i).get("section").getAsJsonArray().size();j++){
            if(j!=paths.get(i).get("section").getAsJsonArray().size()-1) {
                ((Directions_ViewHolder) viewHolder).path_info.append(paths.get(i).get("section").getAsJsonArray().get(j).getAsJsonObject().get("name").toString().replace("\"", "") + " ->");
            }else{
                ((Directions_ViewHolder) viewHolder).path_info.append(paths.get(i).get("section").getAsJsonArray().get(j).getAsJsonObject().get("name").toString().replace("\"", ""));

            }
//            Log.d("중복",paths.get(i).get("section").getAsJsonArray().get(j).getAsJsonObject().get("name").toString().replace("\"","")+" ");
        }
        ((Directions_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    if(check!=i) {
                        itemClickListener.onItemClicke_Path(v, i);
                        check = i;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }
    public class Directions_ViewHolder extends RecyclerView.ViewHolder{

        TextView pathnum,path_time,path_distance,path_info,path_taxi,path_oil;
        ImageView check;
        public Directions_ViewHolder(@NonNull View itemView) {
            super(itemView);
            pathnum=itemView.findViewById(R.id.pathnum);
            path_time=itemView.findViewById(R.id.pathtime);
            path_distance=itemView.findViewById(R.id.path_distance);
            path_info=itemView.findViewById(R.id.path_info);
            path_taxi=itemView.findViewById(R.id.path_taxi);
            path_oil=itemView.findViewById(R.id.path_oil);
            check=itemView.findViewById(R.id.check_mark);
        }
    }
}
