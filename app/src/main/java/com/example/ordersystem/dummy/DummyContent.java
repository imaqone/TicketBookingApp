package com.example.ordersystem.dummy;

import android.widget.ListView;
import android.widget.Toast;

import com.example.ordersystem.MainActivity;
import com.example.ordersystem.R;
import com.example.ordersystem.requestOrder;
import com.example.ordersystem.servlet.servlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    private static OkHttpClient okhttpClient;
    private static String serviceURL = "http://10.21.105.141:8080/TomcatTest/servletTest";
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
//    private static final int COUNT = 25;

    static {
//         Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("messageType", "requestOrder");
                String[] temp = {"anqi"};
                map.put("message", temp);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = jsonObject.toString();
                RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, jsonString);
                okhttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(serviceURL)
                        .post(body)
                        .build();
                Call call = okhttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("连接失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {


                        System.out.println("登录数据库连接成功");
                        String res = response.body().string();
                        System.out.println(res);
                        try {
                            JSONArray jsonAllTicket = new JSONArray(res);
                            for (int i = 0; i < jsonAllTicket.length(); i++) {
                                JSONObject SingleTicket = jsonAllTicket.getJSONObject(i);
                                String DepartStation = SingleTicket.getString("DepartStation");
                                String ArriveStation = SingleTicket.getString("ArriveStation");
                                String DepartTime = SingleTicket.getString("DepartTime");
                                String ArriveTime = SingleTicket.getString("ArriveTime");
                                String TrainNum = SingleTicket.getString("trainNum");
                                String DepartDate = SingleTicket.getString("DepartDate");
                                DummyItem responeOrder = new DummyItem(DepartStation, ArriveStation, TrainNum, DepartDate, DepartTime, ArriveTime);
                                addItem(responeOrder);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
    public void update(){

    }
    public static void addItem(DummyItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.trainNum, item);
    }

//    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
//    public static class DummyItem {
//        public final String id;
//        public final String content;
//        public final String details;
//
//        public DummyItem(String id, String content, String details) {
//            this.id = id;
//            this.content = content;
//            this.details = details;
//        }
//
//        @Override
//        public String toString() {
//            return content;
//        }
//    }
    public static class DummyItem {
        public String DepartStation;
        public String ArriveStation;
        public String trainNum;
        public String DepartDate;
        public String DepartTime;
        public String ArriveTime;

        public DummyItem(String DepartStation, String ArriveStation, String trainNum, String DepartDate, String DepartTime, String ArriveTime) {
            this.DepartStation = DepartStation;
            this.ArriveStation = ArriveStation;
            this.trainNum = trainNum;
            this.DepartDate = DepartDate;
            this.DepartTime = DepartTime;
            this.ArriveTime = ArriveTime;
        }

        @Override
        public String toString() {
            return trainNum;
        }
    }
}
