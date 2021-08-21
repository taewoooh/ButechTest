package com.example.butechtest;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    private final String BASE_URL = "https://taewoooh88.cafe24.com/";
    String ilbyeol_url1 = "https://taewoooh88.cafe24.com/showtables.php";

    private Retrofit retrofit;
    private RecyclerView rv;
    private LinearLayoutManager llm;


    private static String TAG = "8888888888d888";
    int hour;
    String areac;
    String ymd;
    TextView day_textview;

    int seoul_count = 0;

    TextView seoul_c;
    TextView seoul_singoga;
    TextView seoul_singogayul;

    TextView gyeungi_c;
    TextView gyeungi_singoga;
    TextView gyeungi_singogayul;

    int gyeungi_count = 0;
    int gyeungi_singocount = 0;
    EditText search_edit;
    CardView day_cardview;
    ImageView delete_textimageview;
    ArrayList<String> daylist1;
    ArrayList<String> daylist2;
    String daynum;


    TextView cardview_button;
    CardView day_cardview2;
    TextView cardview_button2;

    int total_singocount = 0;
    int seoul_singocount = 0;


    ImageView list_setup_imageview;
    ImageView cycleimageview;


    CardView b1;


    int i_highprice = 0;


    SwipeRefreshLayout swipeRefreshLayout;


    RvAdapter adapter;
    private static ArrayList<ListViewItem> itemArrayList;

    InputMethodManager imm;

    TextView cardviewbutton;


    String intentday;
    String v;
    TextView coment;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemArrayList = new ArrayList<>();
        //adapter = new RvAdapter(itemArrayList, MainActivity.this);


        findview();


        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); //키보드 내리기 초기화


        daylist1 = new ArrayList();
        daylist2 = new ArrayList();

        Calendar cal = Calendar.getInstance();

        hour = cal.get(cal.HOUR_OF_DAY); //현재 시간 구하기

        // Statusbar();


        llm = new LinearLayoutManager(this);

        try {
            new ilbyeolUi_AsyncTask1().execute(ilbyeol_url1);
        } catch (Exception e) {


        }


    }


    public class ilbyeolUi_AsyncTask1 extends AsyncTask<String, Void, String> { // DB에서 일별 테이블 이름 가져오기


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            Log.d(TAG, "dhxodn2");
        }

        @Override

        protected String doInBackground(String... strings) {

            String serverURL = strings[0];

            try {

                Log.d(TAG, "dhxodn3");
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                Log.d(TAG, "dhxodn4");

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.d(TAG, "dhxodn5" + sb.toString().trim());
                return sb.toString().trim();

            } catch (Exception e) {
                return null;
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                String i;

                Log.d(TAG, "dhxodn6" + s);


                String day = s.replaceAll("[^0-9]", ""); //json 특수문자 제거 온니 숫자만

                while (day.length() != 0) {
                    i = day.substring(0, 8);
                    day = day.substring(8, day.length());

                    daylist1.add(i);
                    //Log.e("ohtaewoo", "ilbyeolui end  /  "+i);
                    Log.e("ilbyeol 1", "" + day);
                    Collections.sort(daylist1);
                    Collections.reverse(daylist1);


                }
                daynum = daylist1.get(0);

                //intentday = daylist1.get(0);


                if (v == null) {

                    intentday = daylist1.get(0);

                }


                Tongsin("tDAY" + intentday);


                Log.d(TAG, "dhxodn6.5\n" + s + "\n" + daylist1.toString() + "\n" + new Util().Jungbok(daylist1).toString()); //배열 중복제거


            } catch (Exception e) {


                Toast.makeText(getApplicationContext(), "데이터를 가져올수 없습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }


        }


    }


    public void init() {
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void findview() {
        coment = (TextView) findViewById(R.id.coment);
        rv = (RecyclerView) findViewById(R.id.main_rv);//
        day_textview = (TextView) findViewById(R.id.day_textview);
        search_edit = (EditText) findViewById(R.id.search_edit);
        day_cardview = (CardView) findViewById(R.id.day_buy);
        delete_textimageview = (ImageView) findViewById(R.id.delete_textImageview);
        cardview_button = (TextView) findViewById(R.id.cardview_buy);
        day_cardview2 = (CardView) findViewById(R.id.day_rentprice);
        cardview_button2 = (TextView) findViewById(R.id.cardview_rentprice);
        cardviewbutton = (TextView) findViewById(R.id.cardview_buy);
        list_setup_imageview = (ImageView) findViewById(R.id.list_setup);
        cycleimageview = (ImageView) findViewById(R.id.cycleimageview);
        b1 = (CardView) findViewById(R.id.b1);

        seoul_c = (TextView) findViewById(R.id.seoul_c);
        seoul_singoga = (TextView) findViewById(R.id.seoul_singoga);
        seoul_singogayul = (TextView) findViewById(R.id.seoul_singogayul);
        gyeungi_c = (TextView) findViewById(R.id.gyeungi_c);
        gyeungi_singoga = (TextView) findViewById(R.id.gyeungi_singoga);
        gyeungi_singogayul = (TextView) findViewById(R.id.gyeungi_singogayul);


    }


    public void Tongsin(String tablecode) { // 서버 데이터를 가지고 온다 파라미터는 불러올 테이블 이름


        init();
        GitHub gitHub = retrofit.create(GitHub.class);
        Call<List<ListViewItem>> call = gitHub.contributors(tablecode);
        call.enqueue(new Callback<List<ListViewItem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            // 성공시
            public void onResponse(Call<List<ListViewItem>> call, Response<List<ListViewItem>> response) {
                total_singocount = 0;
                seoul_singocount = 0;
                seoul_count = 0;
                gyeungi_count = 0;
                gyeungi_singocount = 0;
                List<ListViewItem> contributors = response.body();
                // 받아온 리스트를 순회하면서


                // use response data and do some fancy stuff :)

                Log.e("Test888", response.body().toString());


                for (ListViewItem contributor : contributors) {


                    String name = contributor.name;
                    int price = contributor.price;
                    String area = contributor.area;
                    String year = contributor.year;
                    String month = contributor.month;
                    String day = contributor.day;
                    String high = contributor.high;
                    String doromyung = contributor.doromyung;
                    String jibun = contributor.jibun;
                    String geunmulcode = contributor.geunmulcode;
                    String jiyeokcode = contributor.jiyeokcode;
                    String bupjungdong = contributor.bupjungdong;
                    String gunchukyear = contributor.gunchukyear;
                    String hightprice = contributor.hightprice;
                    String hightyear = contributor.hightyear;
                    String hightmonth = contributor.hightmonth;
                    String hightday = contributor.hightday;
                    int chaik = contributor.chaik;
                    String pyungmyuendo = contributor.pyungmyuendo;
                    String chongdongsu = contributor.chongdongsu;
                    String chongsedaesu = contributor.chongsedaesu;
                    String juchadaesu = contributor.juchadaesu;
                    String pyungeunjucha = contributor.pyungeunjucha;
                    String yongjeukryul = contributor.yongjeukryul;
                    String gunpaeyul = contributor.gunpaeyul;
                    String ganrisamuso = contributor.ganrisamuso;
                    String nanbang = contributor.nanbang;
                    String gunseoulsa = contributor.gunseoulsa;
                    String jihachul = contributor.jihachul;
                    String mart = contributor.mart;
                    String hospital = contributor.hospital;
                    String park = contributor.park;
                    String cho = contributor.cho;
                    String jung = contributor.jung;
                    int dangiday = contributor.dangiday;
                    int daychaik = contributor.daychaik;
                    String highhigh = contributor.highhigh;

                    if (pyungmyuendo == null || pyungmyuendo.length() > 7) {


                        pyungmyuendo = "1000";
                    }

                    if (park == null || park.equals("")) {


                        park = "10000000";


                    }
                    if (hospital == null || hospital.equals("")) {


                        hospital = "0";


                    }

                    Log.e("t2", "가나다라" + " / " + name + " / " + bupjungdong + " / " + park);

                    try { // 신고가 카운트 하기
                        //i_price = Integer.parseInt(price.replaceAll(",", "").replaceAll("\\p{Z}", ""));
                        i_highprice = Integer.parseInt(hightprice.replaceAll(",", "").replaceAll("\\p{Z}", ""));


                        if (price > i_highprice) {
                            total_singocount++;

                        }
                        if (bupjungdong.contains("서울특별시") && price > i_highprice) {
                            seoul_singocount++;


                        }
                        if (bupjungdong.contains("경기도") && price > i_highprice) {
                            gyeungi_singocount++;


                        }

                    } catch (Exception e) {
                    }


                    areac = new Util().AreaChange(area);   // 평형 바꾸기
                    ymd = new Util().Ymd(year, month, day); // 년월일
                    month = month.replace(",", "");


                    if (!bupjungdong.contains("서울특별시")) {
                        if (bupjungdong.indexOf("시") > 0) {


                            String s = String.valueOf(bupjungdong.charAt(bupjungdong.indexOf("시") + 1));
                            //bupjungdong.indexOf("시") + 1)
                            if (!s.equals(" ")) {
                                Log.d("dhxodn1988", "" + bupjungdong + "/" + s);

                                bupjungdong = bupjungdong.replace(s, " " + s);

                            }
                        } else if (bupjungdong.indexOf("군") > 0) {
                            String s = String.valueOf(bupjungdong.charAt(bupjungdong.indexOf("군") + 1));
                            //bupjungdong.indexOf("시") + 1)
                            if (!s.equals(" ")) {
                                Log.d("dhxodn1988", "" + bupjungdong + "/" + s);

                                bupjungdong = bupjungdong.replace(s, " " + s);

                            }

                        }


                    }


                    itemArrayList.add(new ListViewItem(name, price, area, year, month, day,
                            high, doromyung, jibun, geunmulcode,
                            jiyeokcode, bupjungdong, gunchukyear, hightprice,
                            hightyear, hightmonth, hightday, areac, ymd, chaik, pyungmyuendo, chongdongsu, chongsedaesu, juchadaesu, pyungeunjucha,
                            yongjeukryul, gunpaeyul, ganrisamuso, nanbang, gunseoulsa, jihachul, mart, hospital, park, cho, jung, dangiday, daychaik, highhigh));
                    Collections.sort(itemArrayList);


                    Log.e("오하람", "" + name + " / " + price);
                    DataView(); //데이터 화면에 뿌리기

                    if (bupjungdong.matches(".*서울특별시.*")) {
                        seoul_count++;


                    } else {
                        gyeungi_count++;


                    }


                }

                int total_c = seoul_count + gyeungi_count;
                int total_sc = seoul_singocount + gyeungi_singocount;

                double seoul_v = (double) seoul_singocount / (double) seoul_count * 100;
                double gyeungi_v = (double) gyeungi_singocount / (double) gyeungi_count * 100;
                double total2_v = (double) total_sc / (double) total_c * 100;


                Log.e("taewoooh88", "서울특별시" + " / " + seoul_count + " / " + seoul_singocount + " / " + String.format("%.0f", seoul_v));
                Log.e("taewoooh88", "경기도" + " / " + gyeungi_count + " / " + gyeungi_singocount + " / " + String.format("%.0f", gyeungi_v));
                Log.e("taewoooh88", "서울경기,경기도" + " / " + total_c + " / " + total_sc + " / " + total2_v);


                seoul_c.setText(String.valueOf(seoul_count));
                seoul_singoga.setText(String.valueOf(seoul_singocount));
                seoul_singogayul.setText(String.format("%.0f", seoul_v));


                gyeungi_c.setText(String.valueOf(gyeungi_count));
                gyeungi_singoga.setText(String.valueOf(gyeungi_singocount));
                gyeungi_singogayul.setText(String.format("%.0f", gyeungi_v));


            }

            @Override
            // 실패시
            public void onFailure(Call<List<ListViewItem>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                        .show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void DataView() {  // itemArraylist 에 담김 데이터를 화면에 뿌려준다


        Collections.sort(itemArrayList);


        adapter = new RvAdapter(itemArrayList, MainActivity.this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);


        day_textview.setText(daynum);


        if (Integer.parseInt(new Util().Getday()) == Integer.parseInt(daynum)) {


            Log.e("cycleimageview1", "On" + new Util().Getday() + " / " + daynum);


            cycleimageview.setVisibility(View.VISIBLE);
        } else if (Integer.parseInt(new Util().Getday()) != Integer.parseInt(daynum)) {
            Log.e("cycleimageview1", "off " + new Util().Getday() + " / " + daynum);

            cycleimageview.setVisibility(View.INVISIBLE);


        }




    }


}