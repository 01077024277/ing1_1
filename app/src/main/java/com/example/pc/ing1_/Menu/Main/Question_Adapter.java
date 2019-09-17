package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Question_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> img_path;
    Context context;

    public ImageClickLinstener imageClickListener;
    public interface ImageClickLinstener{
        void Delete_image (View v,int position);
    }
    public void setImage_Delete(ImageClickLinstener imageClickListener){
        this.imageClickListener=imageClickListener;
    }
    public Question_Adapter(ArrayList<String> img_path, Context context) {
        this.img_path = img_path;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);

        Question_View_Holder viewHolder=new Question_View_Holder (view);

        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(img_path.get(position)).thumbnail(0.3f).centerCrop().into(((Question_View_Holder)holder).imageView);
        ((Question_View_Holder)holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageClickListener.Delete_image(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return img_path.size();
    }
    class Question_View_Holder extends RecyclerView.ViewHolder{
ImageView imageView;
ImageView delete;
        public Question_View_Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
