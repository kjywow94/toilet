package com.example.samsung.toiletapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends Activity {

    EditText edit;
    TextView text;
    String data;
    static double[] w = new double[260];
    static double[] g = new double[260];
    static String[] s = new String[260];
    static int w_idx = 0;
    static int g_idx = 0;
    static int s_idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
    }

    //Button을 클릭했을 때 자동으로 호출되는 callback method
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                text.setText(data); //TextView에 문자열  data 출력
                            }
                        });
                    }
                }).start();
                break;
            case R.id.next:
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                break;
        }
    }


    String getXmlData() {

        StringBuffer buffer = new StringBuffer();

        try {
            XmlPullParser toilet = getResources().getXml(R.xml.toilet);
            int eventType = toilet.getEventType();
            boolean chk = false;
            boolean w_chk = false;
            boolean g_chk = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (toilet.getName().equals("화장실명")) {
                        chk = true;
                    } else if (toilet.getName().equals("위도")) {
                        w_chk = true;
                    } else if (toilet.getName().equals("경도")) {
                        g_chk = true;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (chk == true) {
                        buffer.append(toilet.getText() + "\t");
                        s[s_idx++] = toilet.getText();
                        chk = false;
                    } else if (w_chk == true) {
                        buffer.append(toilet.getText() + "\t");
                        w[w_idx++] = Double.parseDouble(toilet.getText());
                        w_chk = false;
                    } else if (g_chk == true) {
                        buffer.append(toilet.getText() + "\t");
                        g[g_idx++] = Double.parseDouble(toilet.getText());
                        g_chk = false;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (toilet.getName().equals("Row")) {
                        buffer.append("\n");
                    }
                }
                eventType = toilet.next();
            }

        } catch (Exception e) {
            Log.d("XMLParser", e.getMessage());
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();
    }

}
