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

public class adv_jsoup extends AppCompatActivity {
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_jsoup);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        getWebsite();

    }

    private void getWebsite() {

                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://indianrailways.gov.in/railwayboard/view_section.jsp?id=0%2C1%2C304%2C366%2C508%2C511").get();
                    String extractor = "table#table38 > tbody > tr:gt(2)";
                    Elements TRs = doc.select(extractor);
//                    Log.d("oneTr",TRs.get(0).toString());

                for ( int i =0; i<TRs.size();i++) {
                    Element oneRow = TRs.get(i);
                    Elements cols = oneRow.select("td");
//                    Log.d("sizeofCol", String.valueOf(cols.size()));
//
//                    Log.d("oneCol", cols.get(0).getElementsByTag("a").attr("href").toString()); // get link for download
//                    Log.d("twoCol", cols.get(1).text());  // get heading
//                    Log.d("threeCol", cols.get(2).text()); // get date

                    int sizeCols = cols.size(); // 3
                    if (sizeCols >= 3) {
                        String link = cols.get(0).getElementsByTag("a").attr("href").toString();
                        String headings = cols.get(1).text();
                        String date = cols.get(2).text();

                        builder.append("\n").append("\uD83D\uDCD2 Circular : ").append(headings)
                                .append("\n\n").append("\uD83D\uDCC5 Dated : ").append(date).append("\n\n")
                                .append("\uD83D\uDD17 Link : https://indianrailways.gov.in").append(link).append("\n")
                                .append("--sep--");

                    } else {
                        continue;
                    }
                }

                    ListView listView = (ListView) findViewById(R.id.listview);

                    String[] players = builder.toString().split("--sep--");
                    Log.d("mydata",players[0].toString());
                    List<String> Players_list = new ArrayList<String>(Arrays.asList(players));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(adv_jsoup.this, android.R.layout.simple_list_item_1, Players_list);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            //t.setText("You selected " + selectedItem);
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