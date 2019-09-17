package com.example.pc.ing1_.Menu.Menu;

//public class Menu_Activity extends AppCompatActivity{
//    ExGridView gridView;
//    Calendar calendar,calendar_today;
//    ArrayList<Grid_Item> arrayList;
//    Calendar_Adapter calendar_adapter;
//    TextView title;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.menu_activity);
//
//        title=findViewById(R.id.title);
//
//        calendar=Calendar.getInstance();
//        calendar_today=Calendar.getInstance();
//
//        setCalendat(calendar.get(Calendar.MONTH)); // 캘린더를 나타내기위한 함수
//        setCalendat(calendar.get(Calendar.MONTH)); // 캘린더를 나타내기위한 함수
//
//        title.setText(calendar.get(Calendar.YEAR)+"년"+(calendar.get(Calendar.MONTH)+1)+"월"); //타이틀에 날짜 표시
//
//
//        Button next = findViewById(R.id.cnext);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(calendar.get(Calendar.MONTH)==12){
//                    calendar.set(Calendar.YEAR-1,calendar.get(Calendar.MONTH),1);
//                    Toast.makeText(getApplicationContext(),"qweqwe",Toast.LENGTH_LONG).show();
//                }
//                setCalendat(calendar.get(Calendar.MONTH)+1);
////                title.setText(calendar.get(Calendar.YEAR)+"년"+(calendar.get(Calendar.MONTH)+1)+"월");
//
//
//
//            }
//        });
//        Button back = findViewById(R.id.cback);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setCalendat(calendar.get(Calendar.MONTH)-1);
////                title.setText(calendar.get(Calendar.YEAR)+"년"+(calendar.get(Calendar.MONTH)+1)+"월");
//
//            }
//        });
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
//                intent.putExtra("day",arrayList.get(position).getFomat());
//                startActivity(intent);
//            }
//        });
//
//    }
//
//    public void setCalendat(int month){
//        Toast.makeText(getApplicationContext(),""+month,Toast.LENGTH_SHORT).show();
//        arrayList=new ArrayList<>();
//
//
//        arrayList.add(new Grid_Item("일",1,"0"));
//        arrayList.add(new Grid_Item("월",2,"0"));
//        arrayList.add(new Grid_Item("화",3,"0"));
//        arrayList.add(new Grid_Item("수",4,"0"));
//        arrayList.add(new Grid_Item("목",5,"0"));
//        arrayList.add(new Grid_Item("금",6,"0"));
//        arrayList.add(new Grid_Item("토",7,"0"));
////        calendar.set(Calendar.MONTH,month);
//
//        calendar_today.set(calendar.get(Calendar.YEAR),month,1);
//        int startday=calendar_today.get(Calendar.DAY_OF_WEEK);
//        if(startday!=1){
//            for(int i=0;i<startday-1;i++){
//                arrayList.add(null);
//            }
//        }
//        calendar.set(Calendar.MONTH,month);
//        for( int i=0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
//            calendar_today.set(calendar.get(Calendar.YEAR),month,1+i);
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
//            SimpleDateFormat format2 = new SimpleDateFormat("-MM-dd");
//
//            String formatted = format1.format(calendar.getTime());
//            String formatted2 = format2.format(calendar_today.getTime());
//            Log.d("캘린더원",formatted);
//            Log.d("캘린더투",formatted2);
//
//            arrayList.add(new Grid_Item(String.valueOf(i+1),calendar_today.get(Calendar.DAY_OF_WEEK),formatted+""+formatted2));
//        }
//        Log.d("남는거",""+arrayList.size());
//        Log.d("남는거",""+arrayList.size()%7);
//        int j = arrayList.size()%7;
//        if(j!=0) {
//            for (int i = 0; i < 7 - j; i++) {
//                arrayList.add(null);
//                Log.d("남는거", "" + i);
//            }
//        }
////        calendar_adapter=new Calendar_Adapter(this,arrayList,);
//        gridView=findViewById(R.id.grid);
//        gridView.setAdapter(calendar_adapter);
//        title.setText(calendar.get(Calendar.YEAR)+"년"+(calendar.get(Calendar.MONTH)+1)+"월");
//
//        setDynamicHeight(gridView);
//
//    }
//
//    private void setDynamicHeight(GridView gridView) {
//        ListAdapter gridViewAdapter = gridView.getAdapter();
//        if (gridViewAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        int items = gridViewAdapter.getCount();
//        int rows = 0;
//
//        View listItem = gridViewAdapter.getView(0, null, gridView);
//        listItem.measure(0, 0);
//        totalHeight = listItem.getMeasuredHeight();
//
//        float x = 1;
//        if( items > 5 ){
//            x = items/5;
//            rows = (int) (x + 1);
//            totalHeight *= rows;
//        }
//
//        ViewGroup.LayoutParams params = gridView.getLayoutParams();
//        params.height = totalHeight;
//        gridView.setLayoutParams(params);
//    }
//
//}

