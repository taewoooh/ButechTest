package com.example.butechtest;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.CustomViewHolder> {

    private ArrayList<ListViewItem> items = null;
    private ArrayList<ListViewItem> arrayList;

    TWPreference twPreference;

    Context context;

    int a;
    int b;
    int c;

    String searchString;



    public RvAdapter(ArrayList<ListViewItem> items, Context context) {
        this.context = context;
        this.items = items;
        arrayList = new ArrayList<ListViewItem>();
        arrayList.addAll(items);


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        int safePosition = holder.getAdapterPosition();
        int count = safePosition + 1;
        try {

            holder.price.setTextColor(Color.parseColor("#000000")); // 기본색
            holder.highicon.setVisibility(View.INVISIBLE); // 신고가 아이콘


            holder.minusprice.setText("");
            holder.name.setTextColor(Color.parseColor("#000000")); // 검정색
            holder.pyeungsu.setText("");
            holder.pyeungdang.setText("");
            holder.gap.setText("");


            holder.number.setText(String.valueOf(count) + "위"); //신고가율


//            a = Integer.parseInt(items.get().getPrice().replaceAll(",", "").replaceAll("\\p{Z}", ""));
//            b = Integer.parseInt(items.get().getHightprice().replaceAll(",", "").replaceAll("\\p{Z}", ""));


            a = items.get(safePosition).getChaik();


//            Log.e("taewoooh"+"\n\n" + " 거래 금액 - > " + a + "\n" + "최고가 - > " + b, "");


            if (a > 0) {

                count++;

                holder.highicon.setVisibility(View.VISIBLE);// 신고가 아이콘
                holder.price.setTextColor(Color.parseColor("#F44336")); // 분홍색

                int d = a - b;
                holder.minusprice.setText(new Util().Priceedit(String.valueOf(a)));


            } else {
                holder.highicon.setVisibility(View.INVISIBLE);// 신고가 아이콘
                holder.price.setTextColor(Color.parseColor("#000000")); // 기본색
                holder.minusprice.setText("");


            }


        } catch (NumberFormatException e) {


        } catch (Exception e) {

        }


        String price_s = new Util().Priceedit(String.valueOf(items.get(safePosition).getPrice())); // 금액에 억 붙히기
        String price_o = new Util().Priceedit(items.get(safePosition).getHightprice()); // 금액에 억 붙히기(최고가)


        String highday = new Util().Daygagong(items.get(safePosition).getHightyear()
                , items.get(safePosition).getHightmonth(), items.get(safePosition).getHightday());


//        }

        String c = items.get(safePosition).getChongsedaesu();
        String j = items.get(safePosition).getPyungeunjucha();
        String ji = items.get(safePosition).getJihachul();
        String yong = items.get(safePosition).getYongjeukryul();
        String gun = items.get(safePosition).getGunpaeyul();


        holder.Name.setText(items.get(safePosition).getName()); //단지이름

        holder.Area.setText(items.get(safePosition).getArea()); //면적
        holder.Bupjungdong.setText(items.get(safePosition).getBupjungdong()); // 주소


        holder.rentprice.setText(items.get(safePosition).getMart());
        holder.rentprice.setText(new Util(context).Priceedit(items.get(safePosition).getMart()));
        holder.rentvalue.setText(" / " + items.get(safePosition).getHospital() + "건");


        //holder.gap.setText(items.get(safePosition).getPark());

        if (Integer.parseInt(items.get(safePosition).getPark()) == 10000000) {

            holder.gap.setText("");


        } else {

            holder.gap.setText(new Util(context).Priceedit(items.get(safePosition).getPark()));

        }


        String n = items.get(safePosition).getName();


        String name = n.toLowerCase(Locale.getDefault());

        String a = items.get(safePosition).getArea();
        String area = a.toLowerCase(Locale.getDefault());

        String b = items.get(safePosition).getBupjungdong();
        String bup = b.toLowerCase(Locale.getDefault());


        try {
            if (searchString.length() == 0) {
                Log.e("searchString", "");

            } else if (name.contains(searchString)) {

                int startPos = name.indexOf(searchString);
                int endPos = startPos + searchString.length();

                Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.Name.getText());
                spanString.setSpan(new BackgroundColorSpan(0xFFFFFF00), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.Name.setText(spanString);

            } else if (area.contains(searchString)) {

                int startPos = area.indexOf(searchString);
                int endPos = startPos + searchString.length();

                Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.Area.getText());
                spanString.setSpan(new BackgroundColorSpan(0xFFFFFF00), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.Area.setText(spanString);

            } else if (bup.contains(searchString)) {

                int startPos = bup.indexOf(searchString);
                int endPos = startPos + searchString.length();

                Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.Bupjungdong.getText());
                spanString.setSpan(new BackgroundColorSpan(0xFFFFFF00), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.Bupjungdong.setText(spanString);

            }


        } catch (Exception e) {


        }


        holder.ymd.setText(items.get(safePosition).getYmd()); //계약일
        holder.high.setText(String.valueOf(items.get(safePosition).getHigh())); // 층수
        //holder.Areac.setText(items.get(safePosition).getAreac()); // 평형
        holder.CREATE_YYYY.setText(items.get(safePosition).getGunchukyear()); // 평형
        holder.hight_day.setText(highday); // 계약 날짜
        holder.price.setText(price_s); ///거래 금액
        holder.previ_pricenumber.setText(price_o); // 최고가


        holder.high2.setText(items.get(safePosition).getHighhigh() + "층");


        holder.chongsedaesu.setText(items.get(safePosition).getChongsedaesu()); // 총세대수
        holder.pyungeunjucha.setText(items.get(safePosition).getPyungeunjucha()); // 펑균주차대수
        holder.pyungeunjucha.setText(items.get(safePosition).getYongjeukryul()); // 용적률
        holder.pyungeunjucha.setText(items.get(safePosition).getGunpaeyul()); // 건페율


        try {
            //items.get(safePosition).getMart().equals("분양권/입주권") &&
            if (items.get(safePosition).getGunchukyear().equals("0")) {


                holder.name.setTextColor(Color.parseColor("#2196F3"));
            }
        } catch (Exception e) {

        }

        try {

            if (Integer.parseInt(items.get(safePosition).getPyungmyuendo()) > 0 && items.get(safePosition).getPyungmyuendo() != "1000") {

                Log.e("pyungmyuendo", " -->> " + items.get(safePosition).getName() + " / " + items.get(safePosition).getArea() + " / " + items.get(safePosition).getPyungmyuendo() +
                        " / " + items.get(safePosition).getYongjeukryul() + " / " + items.get(safePosition).getGunpaeyul());
                //Log.e("pyungmyuendo2"," / "+items.get(safePosition).getName()+" / "+items.get(safePosition).getArea()+" / "+items.get(safePosition).getPyungmyuendo());


                holder.pyeungsu.setText(" / " + items.get(safePosition).getPyungmyuendo() + "평");

                int i = items.get(safePosition).getPrice() / Integer.parseInt(items.get(safePosition).getPyungmyuendo());


                holder.pyeungdang.setText(" / " + i);


            }
        } catch (Exception e) {


        }


        if (ji.equals("")) {
            ji = "-";
            holder.jihachul.setText(ji);
        } else {

            Log.e("jihachul", " / " + ji);
            ji = items.get(safePosition).getJihachul().replace("\n", "");
            Log.e("jihachul2", " / " + ji);

            holder.jihachul.setText(ji);

        }
        if (c.equals("")) {

            c = "-";
            holder.chongsedaesu.setText(c);


        } else {


            holder.chongsedaesu.setText(c);

        }
        if (j.equals("")) {
            j = "-";
            holder.pyungeunjucha.setText(j);

        } else {

            holder.pyungeunjucha.setText(j);

        }
        if (yong.equals("") || yong.equals("0")) {
            yong = "-";
            holder.yongjeukryul.setText(yong);

        } else {

            holder.yongjeukryul.setText(yong);

        }
        if (gun.equals("") || gun.equals("0")) {
            gun = "-";
            holder.gunpaeyul.setText(gun);

        } else {

            holder.gunpaeyul.setText(gun);

        }

        if (twPreference.getInt("value", 0) == 0) {


        } else if (twPreference.getInt("value", 0) == 1) {
            holder.minusprice.setBackgroundColor(0xFFFFFF00);

        } else if (twPreference.getInt("value", 0) == 2) {
            holder.Area.setBackgroundColor(0xFFFFFF00);

        } else if (twPreference.getInt("value", 0) == 3) {
            holder.ymd.setBackgroundColor(0xFFFFFF00);

        } else if (twPreference.getInt("value", 0) == 4) {
            holder.CREATE_YYYY.setBackgroundColor(0xFFFFFF00);

        } else if (twPreference.getInt("value", 0) == 5) {
            holder.CREATE_YYYY.setBackgroundColor(0xFFFFFF00);

        } else if (twPreference.getInt("value", 0) == 6) {
            holder.pyeungdang.setBackgroundColor(0xFFFFFF00);

        } else if (twPreference.getInt("value", 0) == 7) {
            holder.gap.setBackgroundColor(0xFFFFFF00);


        }else if(twPreference.getInt("value", 0) == 8){

            holder.rentvalue.setBackgroundColor(0xFFFFFF00);
        }

    }




    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(String charText) { // 리사이클러뷰 검색

        this.searchString = charText;

        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();


        if (charText.length() == 0) {
            TWPreference twPreference = new TWPreference(context);

            if (twPreference.getInt("refresh", 0) == 1) {

            } else {
                items.addAll(arrayList);
            }

        } else {
            for (ListViewItem news : arrayList) {
                if (news.getName().toLowerCase(Locale.getDefault()).contains(charText) //타이틀 or 주소 검색 가능
                        || news.getBupjungdong().toLowerCase(Locale.getDefault()).contains(charText) ||
                        news.getAreac().toLowerCase(Locale.getDefault()).contains(charText) ||
                        news.getArea().toLowerCase(Locale.getDefault()).contains(charText) ||
                        news.getMonth().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(news);
                }
            }
        }
        notifyDataSetChanged();


    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        TextView Name;
        TextView Area;
        TextView Bupjungdong;
        TextView ymd;
        TextView CREATE_YYYY;
        TextView high;
        TextView Areac;
        TextView minusprice;
        TextView previ_pricenumber;
        TextView hight_day;
        TextView high2;
        TextView rentprice;
        TextView rentvalue;
        TextView gap;


        TextView chongsedaesu;
        TextView pyungeunjucha;
        TextView jihachul;
        TextView pyeungsu;
        TextView number;
        TextView pyeungdang;


        ImageView highicon;
        ImageView crown;

        TextView yongjeukryul;
        TextView gunpaeyul;
        TextView name;

        public CustomViewHolder(View itemView) {
            super(itemView);


            // title = itemView.findViewById(R.id.item_tv_title);
            high2 = itemView.findViewById(R.id.high2);
            yongjeukryul = itemView.findViewById(R.id.yongjeukryul);
            gunpaeyul = itemView.findViewById(R.id.gunpaeyul);
            previ_pricenumber = itemView.findViewById(R.id.Previ_pricenumber);
            hight_day = itemView.findViewById(R.id.high_day);
            minusprice = itemView.findViewById(R.id.minusprice);
            highicon = itemView.findViewById(R.id.highicon);
            price = itemView.findViewById(R.id.price);
            Name = itemView.findViewById(R.id.Name);
            pyeungdang = itemView.findViewById(R.id.pyeungdang);
            Area = itemView.findViewById(R.id.Area);
            Bupjungdong = itemView.findViewById(R.id.Bupjungdong);
            ymd = itemView.findViewById(R.id.ymd);
            CREATE_YYYY = itemView.findViewById(R.id.CREATE_YYYY);
            high = itemView.findViewById(R.id.high);
            chongsedaesu = itemView.findViewById(R.id.chongsedaesu);
            pyungeunjucha = itemView.findViewById(R.id.pyungeunjucha);
            jihachul = itemView.findViewById(R.id.jihachul);
            pyeungsu = itemView.findViewById(R.id.pyeungsu);

            number = itemView.findViewById(R.id.number);
            name = itemView.findViewById(R.id.Name);
            rentprice = itemView.findViewById(R.id.rentprice);
            rentvalue = itemView.findViewById(R.id.rentvalue);
            gap = itemView.findViewById(R.id.gap);

            twPreference = new TWPreference(context);

        }


    }

}