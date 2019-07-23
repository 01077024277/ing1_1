package com.example.pc.ing1_.Menu.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.ing1_.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Directions_bmw_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<JSONObject> jsonObjects;
    static int check;

    Directions_bmw_Adapter (ArrayList<JSONObject> jsonObjects){
        this.jsonObjects=jsonObjects;
        this.check=0;
    }
    private PathClickListener_bmw itemClickListener;

    public interface PathClickListener_bmw{
        void onItemClicke_Path_bmw(View view,int position);
    }
    public void setOnClickListener_Path_bmw(PathClickListener_bmw listener){
        this.itemClickListener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.directions_bmw_item,viewGroup,false);

        Directions_bmw_ViewHolder viewHolder=new Directions_bmw_ViewHolder (view);

        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if(i==check){
            ((Directions_bmw_ViewHolder)viewHolder).check.setVisibility(View.VISIBLE);
        }else{
            ((Directions_bmw_ViewHolder)viewHolder).check.setVisibility(View.GONE);
        }
        try {

            JSONArray jsonArray=jsonObjects.get(i).getJSONArray("subPath");
            boolean check__=false;
            for(int j=0;j<jsonArray.length();j++){
                int type=jsonArray.getJSONObject(j).getInt("trafficType");
                if(type==1 && check__==false){
                    String linenum=jsonArray.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("name");
//                    JsonArray jsonElements=jsonArray.getJSONArray(j).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    JSONObject jsonObject= jsonArray.getJSONObject(j);
                    String start=jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    linenum=linenum.replace(" ","").replace("수도권","");
                    int index = jsonObject.getInt("stationCount");
                    String end =jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(index).getString("stationName");
                    ((Directions_bmw_ViewHolder)viewHolder).path_info.setText(start+"("+linenum+")"+" -> "+end+"("+linenum+")");

                    check__=true;
                }else if (type==1){
                    String linenum=jsonArray.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("name");
//                    JsonArray jsonElements=jsonArray.getJSONArray(j).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    JSONObject jsonObject= jsonArray.getJSONObject(j);
                    String start=jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    linenum=linenum.replace(" ","").replace("수도권","");
                    int index = jsonObject.getInt("stationCount");
                    String end =jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(index).getString("stationName");
                    ((Directions_bmw_ViewHolder)viewHolder).path_info.append(" -> "+start+"("+linenum+")"+" -> "+end+"("+linenum+")");
                }else if (type==2 && check__==false){
                    String linenum=jsonArray.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("busNo");
//                    JsonArray jsonElements=jsonArray.getJSONArray(j).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    JSONObject jsonObject= jsonArray.getJSONObject(j);
                    String start=jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    linenum=linenum.replace(" ","").replace("수도권","");
                    int index = jsonObject.getInt("stationCount");
                    String end =jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(index).getString("stationName");
                    ((Directions_bmw_ViewHolder)viewHolder).path_info.setText(""+start+"("+linenum+")"+" -> "+end+"("+linenum+")");
                    check__=true;
                }else if (type==2){
                    String linenum=jsonArray.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("busNo");
//                    JsonArray jsonElements=jsonArray.getJSONArray(j).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    JSONObject jsonObject= jsonArray.getJSONObject(j);
                    String start=jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getString("stationName");
                    linenum=linenum.replace(" ","").replace("수도권","");
                    int index = jsonObject.getInt("stationCount");
                    String end =jsonObject.getJSONObject("passStopList").getJSONArray("stations").getJSONObject(index).getString("stationName");
                    ((Directions_bmw_ViewHolder)viewHolder).path_info.append(" -> "+start+"("+linenum+")"+" -> "+end+"("+linenum+")");
                }
            }





            JSONObject jsonObject= jsonObjects.get(i).getJSONObject("info");
//            ((Directions_bmw_ViewHolder)viewHolder).path_info.setText(jsonObject.getString("mapObj").toString());
            ((Directions_bmw_ViewHolder)viewHolder).pathnum.setText("경로 "+(i+1)+"");
            ((Directions_bmw_ViewHolder)viewHolder).path_time.setText(jsonObject.getString("totalTime").toString()+" 분");
            ((Directions_bmw_ViewHolder)viewHolder).path_distance.setText(jsonObject.getString("payment").toString()+ " 원");
            int bus =jsonObject.getInt("busStationCount");
            int subway=jsonObject.getInt("subwayStationCount");
            if(bus!=0){
                ((Directions_bmw_ViewHolder)viewHolder).count.setText("버스 정류장 "+bus+"개");
                if(subway!=0){
                    ((Directions_bmw_ViewHolder)viewHolder).count.append("  +  지하철역 "+subway+"개");

                }else{

                }
            }else{
                ((Directions_bmw_ViewHolder)viewHolder).count.setText("");
                if(subway!=0){
                    ((Directions_bmw_ViewHolder)viewHolder).count.append("지하철역 "+subway+"개");

                }else{

                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((Directions_bmw_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check!=i) {
                    itemClickListener.onItemClicke_Path_bmw(v, i);
                    check = i;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }
    public class Directions_bmw_ViewHolder extends RecyclerView.ViewHolder{
        TextView pathnum,path_time,path_distance,path_info,path_taxi,path_oil,count;
        ImageView check;
        public Directions_bmw_ViewHolder(@NonNull View itemView) {
            super(itemView);
            pathnum=itemView.findViewById(R.id.pathnum);
            path_time=itemView.findViewById(R.id.pathtime);
            path_distance=itemView.findViewById(R.id.path_distance);
            path_info=itemView.findViewById(R.id.path_info);
            check=itemView.findViewById(R.id.check_mark);
            count=itemView.findViewById(R.id.path);
        }
    }
}
