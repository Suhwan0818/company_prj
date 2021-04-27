package example.test.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    Button btn_login, btn_result, btn_goto;
    EditText name, password, email;
    TextView result, output; //버튼 변수 구현


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        btn_result = findViewById(R.id.btn_check);
        btn_goto = findViewById(R.id.btn_photo);

        name = findViewById(R.id.input_name);
        password = findViewById(R.id.input_password);
        email = findViewById(R.id.input_email);
        result = findViewById(R.id.input_result);
        output = findViewById(R.id.input_output); //activity_main에 변수를 가져와서 활성화 시켰습니다.

        //로그인 화면으로 돌아가기 버튼
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //결과 버튼 크릭하면 데이터 결과 보여주도록 합니다
        btn_result.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) { resultSubmit();/*이건 밑에 함수 만들어 놨습니다.*/ }
        });
        //사진 가져오는 화면으로 이동하는 버튼
        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), PhotoActivity.class);
                startActivity(intent1);
            }
        });
    }

    //Menu of top side
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate 매뉴인데 액션바 쓰시면 활성화 가능합니다.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //아이템 뭔가를 만들고 싶다면 이 함수를 쓰시면 됩니다. 바로 쓰실 수 있도록 제가 쓰던건 지워놨습니다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 액션바 할거면 여기
        // 자동 버튼도 여기
        // parent activity에서 뭔가 끌어다 쓸려면 AndroidManifest에 추가한 지정도 여기에 추가하시면 됩니다.
        int id = item.getItemId();

        //쓰기 편하도록 추가만 하면 되게 만들어놨습니다.
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //로그인 디비 연결하기
    //기본적으로 POST MAN으로 JSON데이터 연결해서 사용하고 있습니다.
    //제가 쓴건 OKHTTP3인데 더 좋은 방식이 있다면 해당 함수를 바꾸시면 됩니다.
    public void resultSubmit(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String data = email.getText().toString();
        RequestBody body = new FormBody.Builder()
                .add("email",data)
                .build();
        Request request = new Request.Builder()
                .url("https://mysnapbook.skanaimaging.com/api/sUser/IsEmailRegistered")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        result.setText(data);
        client.newCall(request).enqueue(new Callback() {
            @Override//실패시
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override//로그 오류시
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    Log.i("tag","fail to response");
                } else {
                    Log.i("tag","success to response");
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                output.setText(responseData);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        //이 아랫 부분은 POST MAN DB가 바뀌기 전입니다. 지금은 바뀌어서 밑에 함수는 사용 못하는데 혹시나해서 남겨놨습니다.
        //필요 없으시면 이 아래부터는 다 지워도 상관 없습니다.
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        String data = email.getText().toString();
//        String data2 = password.getText().toString();
//        RequestBody body = new FormBody.Builder()
//                .add("email",data)
//                .add("password",data2)
//                .build();
//        Request request = new Request.Builder()
//                .url("https://mysnapbook.skanaimaging.com/api/sUser/Login")
//                .post(body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        result.setText(data);
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if(!response.isSuccessful()){
//                    Log.i("tag","fail to response");
//                } else {
//                    Log.i("tag","success to response");
//                    final String responseData = response.body().string();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try{
//                                JSONObject jsonObject = new JSONObject(responseData);
//                                String responseValue = jsonObject.getString("OK");
//                                JSONObject responseObject = new JSONObject(responseValue);
//                                Iterator i = responseObject.keys();
//                                System.out.println(responseValue);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }

    //포토 액티비티로 AWS S3 콘솔 넘기기 하다가 실패하고 남은 것들입니다.
    public static PhotoActivity PA = new PhotoActivity();

}
