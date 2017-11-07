package com.example.administrator.getrssi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager wm;
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new mThread().start();
    }

    private class mThread extends Thread{
        @Override
        public void run(){
            while(true){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        obtainListInfo();
                    }
                });
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void obtainListInfo(){

        editText1 = (EditText) findViewById(R.id.et1);
        editText2 = (EditText) findViewById(R.id.et2);
        //显示扫描到的所有wifi信息
        wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();

        int strength = wi.getRssi();
        int speed = wi.getLinkSpeed();
        String designation = wi.getSSID();

        String addr = wi.getBSSID();
        String unit = WifiInfo.LINK_SPEED_UNITS;

        if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            StringBuilder listinfo = new StringBuilder();
            //搜索到的wifi列表信息
            List<ScanResult> scanResults = wm.getScanResults();

            for (ScanResult sr:scanResults){
                listinfo.append("wifi网络ID：");
                listinfo.append(sr.SSID);
                listinfo.append("\nwifi MAC地址：");
                listinfo.append(sr.BSSID);
                listinfo.append("\nwifi信号强度：");
                listinfo.append(sr.level+"\n\n");
            }
            editText2.setText(listinfo.toString());
            String curr_connected_wifi=null;
            curr_connected_wifi="Currently connecting WiFi \'"+designation+"\' \nRssi: "+strength+
                    "\nMac addr: "+addr+"\nspeed: "+speed+" "+unit;
            editText1.setText(curr_connected_wifi.toString());
        }
    }
}
