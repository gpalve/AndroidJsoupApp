package com.ucccwr.jsoupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });


    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://indianrailways.gov.in/railwayboard//view_section.jsp?lang=0&id=0,1,304,366,550,1234").get();
                    String title = doc.title();
                    Elements links = doc.select("a.linkn");
//                    Elements tds = doc.select("td[]");

                    //builder.append(title).append("\n");
//                    link.attr("href");
//                    link.text()

                    ArrayList<String> tdlist = new ArrayList<>();
                    Element table = doc.select("table").get(0); //select the first table.
                    Elements rows = table.select("tr");

                    for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                        Element row = rows.get(i);
                        Elements cols = row.select("td");

                       String str =  cols.get(i).text() + " ";
                       builder.append(str);



                    }



//                    for (Element link : links) {
//                        String str = link.attr("href") + " ";
////                        builder.append("\n").append("Link : ").append(link.attr("href"))
////                                .append("\n").append("Text : ").append(link.text()).append("\n\n");
//                        builder.append(str);
//                    }




                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        result.setText(builder.toString());
                        ListView listView = (ListView) findViewById(R.id.listview);

                        String[] players = builder.toString().split(" ");
                        List<String> Players_list = new ArrayList<String>(Arrays.asList(players));
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Players_list);
                        listView.setAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                result.setText("You selected " + selectedItem);
                            }
                        });

                    }
                });
            }
        }).start();
    }
}