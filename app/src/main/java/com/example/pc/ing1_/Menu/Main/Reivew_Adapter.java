package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.Review_Item;

import java.util.ArrayList;

public class Reivew_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Review_Item> review_items;
    private Reivew_Adapter.ItemClickeListener itemClickeListener;
    public interface ItemClickeListener{
        void onItemclicke(View v,int position);
    }
    public void setOnclikListener(Reivew_Adapter.ItemClickeListener listener){
        this.itemClickeListener=listener;
    }
    public class Review_Holder extends RecyclerView.ViewHolder {
        TextView nick, content, date,img_add;
        ImageView profile,review_img;
        RatingBar ratingBar;

        public Review_Holder(@NonNull View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.nick);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
            profile = itemView.findViewById(R.id.profile);
            ratingBar=itemView.findViewById(R.id.user_rating);
            review_img=itemView.findViewById(R.id.reivew_img);
            img_add=itemView.findViewById(R.id.img_add);
        }
    }

    public Reivew_Adapter() {
    }
    public Reivew_Adapter(Context content, ArrayList<Review_Item> review_items){
        context=content;
        this.review_items=review_items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_recycle, viewGroup, false);
        Review_Holder review_holder= new Review_Holder(view);
        return review_holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if(review_items.get(i).getProfile().equals("")){
            Glide.with(context).load(R.drawable.ic_user_1).into(((Review_Holder) viewHolder).profile);

        }else {
            Glide.with(context).load(review_items.get(i).getProfile()).centerCrop().into(((Review_Holder) viewHolder).profile);
            ((Review_Holder)viewHolder).profile.setBackground(new ShapeDrawable(new OvalShape()));
            ((Review_Holder)viewHolder).profile.setClipToOutline(true);
        }
        ((Review_Holder)viewHolder).content.setText(review_items.get(i).getContent());
        ((Review_Holder)viewHolder).content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        ((Review_Holder)viewHolder).content.setMovementMethod(new ScrollingMovementMethod());

        ((Review_Holder)viewHolder).nick.setText(review_items.get(i).getNick());
        ((Review_Holder)viewHolder).ratingBar.setRating(review_items.get(i).getPoint());
        ((Review_Holder)viewHolder).ratingBar.setIsIndicator(false);
        if(review_items.get(i).getImg().size()==0){
            ((Review_Holder)viewHolder).review_img.setVisibility(View.GONE);
            ((Review_Holder)viewHolder).img_add.setVisibility(View.GONE);
        }else if(review_items.get(i).getImg().size()==1){
            ((Review_Holder)viewHolder).review_img.setVisibility(View.VISIBLE);
            ((Review_Holder)viewHolder).img_add.setVisibility(View.GONE);
            Glide.with(context).load(review_items.get(i).getImg().get(0)).centerCrop().into(((Review_Holder)viewHolder).review_img);
        }else {
            ((Review_Holder)viewHolder).review_img.setVisibility(View.VISIBLE);
            ((Review_Holder)viewHolder).img_add.setVisibility(View.VISIBLE);
            ((Review_Holder)viewHolder).img_add.setText("+ "+(review_items.get(i).getImg().size()-1));
            Glide.with(context).load(review_items.get(i).getImg().get(0)).centerCrop().into(((Review_Holder)viewHolder).review_img);

        }
        ((Review_Holder)viewHolder).review_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickeListener.onItemclicke(v,i);
            }
        });
        ((Review_Holder)viewHolder).img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickeListener.onItemclicke(v,i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return review_items.size();
    }
}
