package com.ucccwr.jsoupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class press_release extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_releas);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        getWebsite();

    }

    private void getWebsite() {

        final StringBuilder builder = new StringBuilder();

        try {
            Document doc = Jsoup.connect("https://pib.gov.in/Allrel.aspx").get();
            String extractor = "div.content-area > ul";  // h3 and ul want to extract
            Elements ULs = doc.select(extractor);
        for (int i=0;i<ULs.size();i++) {
            Element oneUL = ULs.get(i);

            Elements liHead = oneUL.select("li");
            String heading = liHead.select("h3").text();
            builder.append("\n \uD83C\uDDEE\uD83C\uDDF3 " + heading + " \n\n");

            Elements innerUl = liHead.select("ul");
            Elements innerlis = innerUl.select("li");


            for (Element oneInnerLi : innerlis) {
                builder.append("➡️ " +oneInnerLi.select("a").text() + "\n\n");
            }
            builder.append("--sep--");
        }


//            for ( int i =0; i<TRs.size();i++) {
//                Element oneRow = TRs.get(i);
//                Elements cols = oneRow.select("td");
////                    Log.d("sizeofCol", String.valueOf(cols.size()));
////
////                    Log.d("oneCol", cols.get(0).getElementsByTag("a").attr("href").toString()); // get link for download
////                    Log.d("twoCol", cols.get(1).text());  // get heading
////                    Log.d("threeCol", cols.get(2).text()); // get date
//
//                int sizeCols = cols.size(); // 3
//                if (sizeCols >= 3) {
//                    String link = cols.get(0).getElementsByTag("a").attr("href").toString();
//                    String headings = cols.get(1).text();
//                    String date = cols.get(2).text();
//
//                    builder.append("\n").append("Circular : ").append(headings)
//                            .append("\n\n").append("Dated : ").append(date).append("\n\n")
//                            .append("Link : https://indianrailways.gov.in").append(link).append("\n")
//                            .append("--sep--");
//
//                } else {
//                    continue;
//                }
//            }
//
            ListView listView = (ListView) findViewById(R.id.listview);

            String[] players = builder.toString().split("--sep--");
            List<String> Players_list = new ArrayList<String>(Arrays.asList(players));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(press_release.this, android.R.layout.simple_list_item_1, Players_list);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    //t.setText("You selected " + selectedItem);
                    // pass https://pib.gov.in/Allrel.aspx this url to go to site
                    Log.d("selected",selectedItem);
                }
            });

//

//
//                    ArrayList<String> tdlist = new ArrayList<>();
//
//                    Elements rows = table.select("td");
//
//                    for (int i = 1; i < rows.size(); i++) {
//
//
//                        Element row = rows.get(i);
//                        Elements cols = row.select("td");
//
//                        String str =  cols.get(i).text();
//                        builder.append(str);
//
//
//
//                    }





//                    for (Element link : links) {
//                        String str = link.attr("href") + " ";
////                        builder.append("\n").append("Link : ").append(link.attr("href"))
////                                .append("\n").append("Text : ").append(link.text()).append("\n\n");
//                        builder.append(str);
//                    }




        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Log.d("mydata",builder.toString());
//
//                        t.setText(builder.toString());
//
//
//
////                        result.setText(builder.toString());

//
//                    }
//                });
    }

}