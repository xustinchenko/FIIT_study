package com.ustinchenko.yevhen.aoc.analysisofcomments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class SendingData {
    private LinkedList<Comment> main_data;

    public LinkedList<Comment> getMain_data() {
        return main_data;
    }

    public void setMain_data(LinkedList<Comment> main_data) {
        this.main_data = main_data;
    }

    public void preparationDataToSending(JSONArray array){
        main_data = new LinkedList<>();
        try{
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String user_id = item.getString("from_id");
                String text = item.getString("text");
                Comment comment = new Comment();
                comment.setComment(text);
                comment.setUser_id(user_id);
                main_data.add(comment);
                System.out.println(user_id + ":    " + text);
                //Log.d("VkParseLog", city.getTitle());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*public static void main(String[] args) throws Exception {
        SendingData sendingData = new SendingData();
        sendingData.sendData();
    }*/

    public void sendData() throws Exception {
        Client client = new Client();
        client.openConnection();
        client.sendData(ByteBuffer.allocate(4).putInt(main_data.size()).array());//
        client.closeConnection();

    }
}
