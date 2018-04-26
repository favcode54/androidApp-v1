package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.favcode54.home.HomeActivity;
import org.favcode54.utils.PersistentStorageUtils;
import org.favcode54.utils.QuickNetUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;


public class LoginActivity extends BaseActivity {

    private Button recover, create, signin;
    private EditText email, password;
    private PersistentStorageUtils cache = new PersistentStorageUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        applyFont(findViewById(R.id.rootView));

        recover = findViewById(R.id.recoverpass);
        create = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        recover.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), RecoverActivity.class);
            startActivity(cal);
        });

        create.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(cal);
        });

        signin.setOnClickListener(v -> SignIn());

    }

    private void SignIn() {

        String _email = email.getText().toString(),
                _password = password.getText().toString();

        if(_email.isEmpty() || _password.isEmpty()){
            Toast.makeText(this, "Please no field must be left empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!_email.contains("@") || !_email.contains(".")){
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            RequestBody params = new FormBody.Builder()
                    .add("email", _email)
                    .add("password", _password)
                    .build();

            loading();

            QuickNetUtils.makePostRequest(getString(R.string.endpoint_root)+"login.php", params).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    stopLoading();
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                     ResponseBody raw = response.body();
                    if(raw == null){
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    String raw_response = raw.string();

                    Timber.i(raw_response);

                    if(!raw_response.contains("{")){

                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    JSONObject response_object;
                    try {

                        response_object = new JSONObject(raw_response);


                        if(!response_object.getBoolean("error")){
                            //Login successful
                            //cache details then start asctivity

                            String user_id = String.valueOf(response_object.getLong("user_id"));
                            cache.quick_store("user_id", user_id);
                            getUser(user_id);

                        }
                        else{
                            Snackbar.make(email, response_object.getString("msg"), Snackbar.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            stopLoading();
            runOnUiThread(() -> Toast.makeText(this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());

            e.printStackTrace();
        }
    }

    private void getUser(String user_id) {

        try {
            QuickNetUtils.makeGetRequest(getString(R.string.endpoint_root) + "user.php?action=get_one&user_id=" + user_id)
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if(response.isSuccessful()){

                        ResponseBody raw = response.body();
                        if(raw == null){
                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());
                            return;
                        }

                        JsonObject user_object = new JsonParser().parse(raw.string()).getAsJsonObject();
                        cache.cacheUserDetails(user_object.get("details").getAsJsonObject());

                        //DONE start home activity
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();

                    }else{
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Unable to log in. Please retry", Toast.LENGTH_SHORT).show());
                    }
                }
            });
        } catch (IOException e) {
            Toast.makeText(this, "Unable to login. Please retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void stopLoading() {
        runOnUiThread(() -> {
            signin.setVisibility(View.VISIBLE);
            findViewById(R.id.progress).setVisibility(View.GONE);
        });

    }
    private void loading(){

        runOnUiThread(() -> {
            signin.setVisibility(View.GONE);
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
        });

    }
}