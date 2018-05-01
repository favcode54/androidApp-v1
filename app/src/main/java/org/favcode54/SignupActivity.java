package org.favcode54;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.favcode54.home.HomeActivity;
import org.favcode54.utils.PersistentStorageUtils;
import org.favcode54.utils.QuickNetUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Zfinix on 3/12/18.
 */
public class SignupActivity extends BaseActivity {

    private EditText first_name, last_name, email, password;
    private Button submit;
    private PersistentStorageUtils persistentStorageUtils = new PersistentStorageUtils(this);
    private String f_name, l_name, e_mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_signup);

        //applyFont(findViewById(R.id.rootView));

        //connect UI elements an initialize neccessary classes
        initialize();


    }

    private void initialize() {
        first_name = findViewById(R.id.firstname);
        last_name = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        submit = findViewById(R.id.signup);

        submit.setOnClickListener( v -> validateInputs());

        findViewById(R.id.signin).setOnClickListener( v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void validateInputs() {
        if(!QuickNetUtils.isConnected(this)){
            snackError("No internet connection");
            return;
        }

        loading();

        //Get content of inputs to string
               f_name  = first_name.getText().toString();
               l_name = last_name.getText().toString();
               e_mail = email.getText().toString();
               pass = email.getText().toString();

        if(f_name.isEmpty() || l_name.isEmpty() || e_mail.isEmpty() || pass.isEmpty()){
            snackError("No field must be left empty");
            return;
        }

        if(!e_mail.contains("@") || !e_mail.contains(".")){
            snackError("Invalid email address");
            return;
        }

        if(!e_mail.toLowerCase().endsWith("favcode54.org")){
            snackError("Only Favcode54 email addresses are allowed");
            return;
        }

        validateEmailAddress(e_mail);
    }

    private void validateEmailAddress(String e_mail) {
        try {
            QuickNetUtils.makeGetRequest(getString(R.string.endpoint_root) + "registration.php?action=validate_email&field=email&value="+e_mail).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> snackError("Unable to complete signup. Please retry."));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseBody raw_body = response.body();

                    if(!response.isSuccessful() || raw_body == null){
                        runOnUiThread(() -> snackError("Unable to complete signup. Please retry."));
                        return;
                    }

                    JsonObject response_json = new JsonParser().parse(raw_body.string()).getAsJsonObject();

                    if(response_json.get("error").getAsBoolean()){
                        runOnUiThread(() -> alertError(response_json.get("msg").getAsString()));
                        return;
                    }

                    //If this point is reached, email address is valid for signup
                    //so proceed with signup
                    runOnUiThread(() -> finallySignUp());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            snackError("Something went wrong. Please retry");
        }
    }

    private void finallySignUp() {
        RequestBody requestBody = new FormBody.Builder()
                .add("first_name", f_name)
                .add("last_name", l_name)
                .add("email", e_mail)
                .add("password", pass)
                .add("re_password", pass)
                .build();


        try {
            QuickNetUtils.makePostRequest(
                    getString(R.string.endpoint_root) + "registration.php?action=complete_registration",
                    requestBody

            ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(() -> snackError("Unable to complete signup. Please retry."));

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseBody raw_body = response.body();

                    if(!response.isSuccessful() || raw_body == null){
                        runOnUiThread(() -> snackError("Unable to complete signup. Please retry."));
                        return;
                    }

                    JsonObject response_json = new JsonParser().parse(raw_body.string()).getAsJsonObject();

                    if(response_json.get("error").getAsBoolean()){
                        runOnUiThread(() -> alertError(response_json.get("msg").getAsString()));
                        return;
                    }else{

                        //registration successful. Cache user_id, get user details, then start MainActivity
                        persistentStorageUtils.quick_store("user_id", response_json.get("user_id").getAsString());

                        runOnUiThread(() -> getUserDetailsThenStartMainActivity(response_json.get("user_id").getAsString()));
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            snackError("Something went wrong. Please retry.");
        }
    }

    private void getUserDetailsThenStartMainActivity(String user_id) {

        try {
            QuickNetUtils.makeGetRequest(getString(R.string.endpoint_root) + "user.php?action=get_one&user_id=" + user_id)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(() -> alertError("Unable to log in. Please retry"));

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if(response.isSuccessful()){

                                ResponseBody raw = response.body();
                                if(raw == null){
                                    runOnUiThread(() -> snackError("Unable to sign up. Please retry."));
                                    return;
                                }

                                JsonObject user_object = new JsonParser().parse(raw.string()).getAsJsonObject();
                                persistentStorageUtils.cacheUserDetails(user_object.get("details").getAsJsonObject());

                                //DONE start home activity
                                stopLoading();
                                startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();

                            }else{
                                runOnUiThread(() -> snackError("Unable to sign up. Please retry"));
                            }
                        }
                    });
        } catch (IOException e) {
            snackError("Unable to sign up. Please retry.");
            e.printStackTrace();
        }
    }

    private void stopLoading() {
        runOnUiThread(() -> {
            submit.setVisibility(View.VISIBLE);
            findViewById(R.id.progress).setVisibility(View.GONE);
        });

    }
    private void loading(){

        runOnUiThread(() -> {
            submit.setVisibility(View.GONE);
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
        });

    }

    private void snackError(String error_message){
        stopLoading();

        if(error_message == null || error_message.isEmpty()){
            return;
        }

        Snackbar.make(submit, error_message, Snackbar.LENGTH_LONG).show();
    }

    private void alertError(String message){
        stopLoading();

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage(message);
        ab.setPositiveButton("OK", null);
        ab.show();
    }
}