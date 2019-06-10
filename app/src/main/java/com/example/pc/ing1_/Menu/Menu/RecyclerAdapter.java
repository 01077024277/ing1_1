package com.example.pc.ing1_.Menu.Menu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.aaa.MainActivity;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    int aa=-1;
    int wlr=-1;
    // adapter에 들어갈 list 입니다.
//    private ArrayList<Data> listData = new ArrayList<>();
    private ArrayList<Food_info> food_infos= new ArrayList<>();
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    public Itemclick Click;
    public interface Itemclick{
        public void onClick(View v,int Position,String title,int size,double cal,double carb,double protein,double fat,double chol,double fiber,double salt,double protass);

    }
    public void  ButtonClick(Itemclick itemclick){
        this.Click=itemclick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(food_infos.get(position), position);
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return food_infos.size();
    }

    void addItem(Food_info data) {
        // 외부에서 item을 추가시킬 함수입니다.
        food_infos.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayout;
        private TextView textView1;
        private TextView g,textView2,info,cal,git1,git2,git3,git4,git5,git6,git7;
        private ImageView imageView1;
        EditText editText;
//        MaterialSpinner niceSpinner;
        Spinner niceSpinner;
        Button button;
//        com.jaredrummler.materialspinner.MaterialSpinner niceSpinner;
        ConstraintLayout constraintLayout;

//        private Data data;
        private Food_info data;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.button);
            textView1 = itemView.findViewById(R.id.textView1);
            linearLayout=itemView.findViewById(R.id.linearItem);
            textView2 = itemView.findViewById(R.id.textView2);
            niceSpinner=itemView.findViewById(R.id.num);
            info=itemView.findViewById(R.id.info);
                imageView1 = itemView.findViewById(R.id.imageView1);
                constraintLayout=itemView.findViewById(R.id.cons);
                cal=itemView.findViewById(R.id.cal);
                git1=itemView.findViewById(R.id.git1);
            git2=itemView.findViewById(R.id.git2);
            git3=itemView.findViewById(R.id.git3);
            git4=itemView.findViewById(R.id.git4);
            git5=itemView.findViewById(R.id.git5);
            git6=itemView.findViewById(R.id.git6);
            git7=itemView.findViewById(R.id.git7);
            editText=itemView.findViewById(R.id.size);
            g=itemView.findViewById(R.id.g);

        }

        void onBind(Food_info data1, final int position) {
            this.data = data1;
            this.position = position;
            ArrayList<String> arrayList= new ArrayList<>();
            for(int i=1;i<11;i++){
                arrayList.add(i+" 회");
            }

            if(data.getNum()==1&&(data.getSize().equals("ml")||data.getSize().equals("g"))){
                textView1.setText("직접입력");
                textView2.setText(data.getCal() + "kcal");
                wlr=position;
            }else {
                textView1.setText(data.getNum() + "" + data.getSize());
                textView2.setText(data.getCal() + "kcal");
            }
            if(position==aa){
                imageView1.setImageResource(R.drawable.sort_up);
            }
            else{
                imageView1.setImageResource(R.drawable.caret_down);
            }
//            info.setText("1회 제공량 : "+data.getNum()+data.getSize()+"당 "+(int)data.getCal()+"kcal");
            if(position!=wlr&&!data.getSize().contains("g")&&!data.getSize().contains("ml")){
                niceSpinner.setVisibility(View.VISIBLE);
                ArrayAdapter spinnerAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                final double thtn = Math.round((food_infos.get(position).getCal()/food_infos.get(wlr).getCal())*100)/100.0;
                niceSpinner.setAdapter(spinnerAdapter);
                info.setText("1회 제공량 : "+data.getNum()+data.getSize()+"당  "+(int)data.getCal()+"kcal");
//                info.append("   약("+Math.round((food_infos.get(position).getCal()/food_infos.get(wlr)ㅊ.getCal())*100)/100.0+"g)");
                info.append("   약 ( "+thtn+" g)");
                git1.setText("단백질 : "+data.getProtein()+"g");
                git2.setText("탄수화물 : "+data.getCarb()+"g");
                git3.setText("지방 : "+data.getFat()+"g");
                git4.setText("콜레스테롤 : "+data.getChol()+"mg");
                git5.setText("식이섬유 : "+data.getFiber()+"g");
                git6.setText("나트륨 : "+data.getSalt()+"mg");
                git7.setText("칼륨 : "+data.getPotass()+"mg");

                cal.setText(""+(int)data.getCal()+"kcal"+" 약 ( "+thtn+" g)");
                niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int no = position+1;
                        cal.setText(""+(no*(int)data.getCal())+"kcal"+" 약 ( "+(no*thtn)+" g)");
                        git1.setText("단백질 : "+Math.round(data.getProtein()*no*100)/100.0+"g");
                        git2.setText("탄수화물 : "+Math.round(data.getCarb()*no*100)/100.0+"g");
                        git3.setText("지방 : "+Math.round(data.getFat()*no*100)/100.0+"g");
                        git4.setText("콜레스테롤 : "+Math.round(data.getChol()*no*100)/100.0+"mg");
                        git5.setText("식이섬유 : "+Math.round(data.getFiber()*no*100)/100.0+"g");
                        git6.setText("나트륨 : "+Math.round(data.getSalt()*no*100)/100.0+"mg");
                        git7.setText("칼륨 : "+Math.round(data.getPotass()*no*100)/100.0+"mg");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }else if (position!=wlr){
                if(data.getSize().contains("g")){

                }else if(data.getSize().contains("ml")){

                }
                info.setText("1회 제공량 : "+data.getNum()+data.getSize()+"당  "+(int)data.getCal()+"kcal");
                git1.setText("단백질 : "+data.getProtein()+"g");
                git2.setText("탄수화물 : "+data.getCarb()+"g");
                git3.setText("지방 : "+data.getFat()+"g");
                git4.setText("콜레스테롤 : "+data.getChol()+"mg");
                git5.setText("식이섬유 : "+data.getFiber()+"g");
                git6.setText("나트륨 : "+data.getSalt()+"mg");
                git7.setText("칼륨 : "+data.getPotass()+"mg");
                cal.setText(""+(int)data.getCal()+"kcal");
                niceSpinner.setVisibility(View.VISIBLE);


                ArrayAdapter spinnerAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,arrayList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                niceSpinner.setAdapter(spinnerAdapter);
                niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int no = position+1;
                        cal.setText(""+((int)data.getCal()*no)+"kcal");
                        git1.setText("단백질 : "+Math.round(data.getProtein()*no*100)/100.0+"g");
                        git2.setText("탄수화물 : "+Math.round(data.getCarb()*no*100)/100.0+"g");
                        git3.setText("지방 : "+Math.round(data.getFat()*no*100)/100.0+"g");
                        git4.setText("콜레스테롤 : "+Math.round(data.getChol()*no*100)/100.0+"mg");
                        git5.setText("식이섬유 : "+Math.round(data.getFiber()*no*100)/100.0+"g");
                        git6.setText("나트륨 : "+Math.round(data.getSalt()*no*100)/100.0+"mg");
                        git7.setText("칼륨 : "+Math.round(data.getPotass()*no*100)/100.0+"mg");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



            }else{
                final double thtn = Math.round(data.getCal()*100)/100.0;
                info.setText("1회 제공량 : "+data.getNum()+data.getSize()+"당  "+(Math.round(data.getCal()*100)/100.0)+"kcal");
                cal.setText(""+thtn+"kcal");
                niceSpinner.setVisibility(View.GONE);
                g.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                editText.setText("1");
                git1.setText("단백질 : "+Math.round(data.getProtein()*100)/100.0+"g");
                git2.setText("탄수화물 : "+Math.round(data.getCarb()*100)/100.0+"g");
                git3.setText("지방 : "+Math.round(data.getFat()*100)/100.0+"g");
                git4.setText("콜레스테롤 : "+Math.round(data.getChol()*100)/100.0+"mg");
                git5.setText("식이섬유 : "+Math.round(data.getFiber()*100)/100.0+"g");
                git6.setText("나트륨 : "+Math.round(data.getSalt()*100)/100.0+"mg");
                git7.setText("칼륨 : "+Math.round(data.getPotass()*100)/100.0+"mg");
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!s.toString().equals("")) {
                            int no = Integer.valueOf(s.toString());
                            cal.setText("" + Math.round(data.getCal()*100*no)/100.0 + "kcal");
                            git1.setText("단백질 : " + Math.round(data.getProtein() * no * 100) / 100.0 + "g");
                            git2.setText("탄수화물 : " + Math.round(data.getCarb() * no * 100) / 100.0 + "g");
                            git3.setText("지방 : " + Math.round(data.getFat() * no * 100) / 100.0 + "g");
                            git4.setText("콜레스테롤 : " + Math.round(data.getChol() * no * 100) / 100.0 + "mg");
                            git5.setText("식이섬유 : " + Math.round(data.getFiber() * no * 100) / 100.0 + "g");
                            git6.setText("나트륨 : " + Math.round(data.getSalt() * no * 100) / 100.0 + "mg");
                            git7.setText("칼륨 : " + Math.round(data.getPotass() * no * 100) / 100.0 + "mg");
                        }else{
                            cal.setText(""+thtn+"kcal");
                            git1.setText("단백질 : "+Math.round(data.getProtein()*100)/100.0+"g");
                            git2.setText("탄수화물 : "+Math.round(data.getCarb()*100)/100.0+"g");
                            git3.setText("지방 : "+Math.round(data.getFat()*100)/100.0+"g");
                            git4.setText("콜레스테롤 : "+Math.round(data.getChol()*100)/100.0+"mg");
                            git5.setText("식이섬유 : "+Math.round(data.getFiber()*100)/100.0+"g");
                            git6.setText("나트륨 : "+Math.round(data.getSalt()*100)/100.0+"mg");
                            git7.setText("칼륨 : "+Math.round(data.getPotass()*100)/100.0+"mg");
                        }
                    }
                });

            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Click!=null){
                        if(niceSpinner.getVisibility()==View.VISIBLE) {
                            Click.onClick(v, position, "" + textView1.getText().toString(),
                                    niceSpinner.getSelectedItemPosition()+1,
                                    Double.parseDouble(cal.getText().toString().split("k")[0]),
                                    Double.parseDouble(git1.getText().toString().split("단백질 : ")[1].split("g")[0]),
                                    Double.parseDouble(git2.getText().toString().split("탄수화물 : ")[1].split("g")[0]),
                                    Double.parseDouble(git3.getText().toString().split("지방 : ")[1].split("g")[0]),
                                    Double.parseDouble(git4.getText().toString().split("콜레스테롤 : ")[1].split("mg")[0]),
                                    Double.parseDouble(git5.getText().toString().split("식이섬유 : ")[1].split("g")[0]),
                                    Double.parseDouble(git6.getText().toString().split("나트륨 : ")[1].split("mg")[0]),
                                    Double.parseDouble(git7.getText().toString().split("칼륨 : ")[1].split("mg")[0])
                            );
                        }else{
                            Click.onClick(v, position, "" + textView1.getText().toString(),
                                    Integer.parseInt(editText.getText().toString()),
                                    Double.parseDouble(cal.getText().toString().split("k")[0]),
                                    Double.parseDouble(git1.getText().toString().split("단백질 : ")[1].split("g")[0]),
                                    Double.parseDouble(git2.getText().toString().split("탄수화물 : ")[1].split("g")[0]),
                                    Double.parseDouble(git3.getText().toString().split("지방 : ")[1].split("g")[0]),
                                    Double.parseDouble(git4.getText().toString().split("콜레스테롤 : ")[1].split("mg")[0]),
                                    Double.parseDouble(git5.getText().toString().split("식이섬유 : ")[1].split("g")[0]),
                                    Double.parseDouble(git6.getText().toString().split("나트륨 : ")[1].split("mg")[0]),
                                    Double.parseDouble(git7.getText().toString().split("칼륨 : ")[1].split("mg")[0])
                            );
                        }
                    }
                }
            });

            changeVisibility(selectedItems.get(position));

//            itemView.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
//            textView1.setOnClickListener(this);
//            textView2.setOnClickListener(this);
//            imageView1.setOnClickListener(this);
//            imageView2.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:

                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                        aa=-1;

                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                        aa=position;
                        editText.setText("1");
                    }

                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;


                    break;

//                case R.id.textView1:
//                    Toast.makeText(context, data.getTitle(), Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.textView2:
//                    Toast.makeText(context, data.getContent(), Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.imageView2:
//                    Toast.makeText(context, data.getTitle() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
//                    break;
            }
        }

        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 250;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
//                    imageView2.getLayoutParams().height = value;
                    constraintLayout.getLayoutParams().height = value;
                    constraintLayout.requestLayout();
//                    imageView2.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
//                    imageView2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    constraintLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                }
            });
            // Animation start
            va.start();
        }
    }
}