package com.ustinchenko.yevhen.aoc.analysisofcomments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String[] scope = new String[] {};
    private JSONArray array;
    private SendingData data;
    private EditText linkToThePost;
    private Button buttonGetLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKSdk.login(this,scope);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res){
                execute();

// Пользователь успешно авторизовался
            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void execute(){
        buttonGetLink = (Button) findViewById(R.id.buttonGetLink);
        linkToThePost = (EditText) findViewById(R.id.linkToThePost);
        buttonGetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = linkToThePost.getText().toString().substring(19, linkToThePost.getText().toString().length());
                String owner_id = link.split("_")[0];
                String post_id = link.split("_")[1];
                        /*hile(true){
                            break;
                        }*/
                VKRequest request = VKApi.wall().getComments(VKParameters.from(VKApiConst.OWNER_ID, owner_id ,VKApiConst.POST_ID, post_id, VKApiConst.COUNT, 100, VKApiConst.FIELDS, "text", "id"));
                request.executeWithListener(new VKRequest.VKRequestListener(){
                    @Override
                    public void onComplete(VKResponse response) {

                        super.onComplete(response);
                        //VKList<VKApiComment> list = (VKList<VKApiComment>) response.parsedModel;
                        try {
                            array = new JSONObject(response.responseString).getJSONObject("response").getJSONArray("items");
                            data = new SendingData();
                            data.preparationDataToSending(array);
                            /* Открываем соединение. Открытие должно происходить в отдельном потоке от ui */
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("-----------------------------SEND");
                                    try {
                                        data.sendData();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                       /* for (int i=0; i<list.size(); i++) {
                            System.out.println(list.get(i).fields);
                        }*/

                    }
                });
                Toast.makeText(getApplicationContext(), "DONE!", Toast.LENGTH_LONG).show();
            }
        });



        Toast.makeText(getApplicationContext(), "VKAccessToken", Toast.LENGTH_LONG).show();
    }
}
