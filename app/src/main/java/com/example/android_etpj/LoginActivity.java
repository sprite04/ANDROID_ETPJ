package com.example.android_etpj;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Admin;
import com.example.android_etpj.models.Trainee;
import com.example.android_etpj.models.Trainer;
import com.example.android_etpj.sharedpreference.DataLocal;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView txtvLogin;
    private Button btnExit;
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chkRememberMe;
    private TextView txtErrorUsername;
    private Spinner spRole;
    private ImageView imgvRoleMenu;
    private TextView txtErrorPassword;
    public LoginActivity() {
    }

    private Object user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        getSupportActionBar().hide();
        //view1 = findViewById(R.id.layout_login);
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        txtvLogin = findViewById(R.id.btn_login);
        chkRememberMe = findViewById(R.id.remember_me);
        txtErrorUsername = findViewById(R.id.username_error);
        txtErrorPassword = findViewById(R.id.password_error);
        spRole = findViewById(R.id.sp_role);
        txtErrorUsername.setVisibility(View.INVISIBLE);
        txtErrorPassword.setVisibility(View.INVISIBLE);
        user = new Object();
        setBtnLogin();

        setRoleSpinner();





        //setImgvRoleMenu();
        //setSignInButton();
        //setExitButton();
        //setFloatingActionButton();

    }

    private void setRoleSpinner(){
        ArrayList<String> roles= new ArrayList<String>();
        roles.add(Role.ADMIN.name());
        roles.add(Role.TRAINER.name());
        roles.add(Role.TRAINEE.name());

        SpinnerRoleAdapter spinnerAdapter=new SpinnerRoleAdapter(this,R.layout.item_sp_login_selected,roles);
        spinnerAdapter.setDropDownViewResource(R.layout.item_sp_role);
        spRole.setAdapter(spinnerAdapter);
    }

    private void roleLogin(String role, String username, String password){
        if (role == Role.ADMIN.name()){
            Callback<Admin> call_admin = new Callback<Admin>() {
                @Override
                public void onResponse(Call<Admin> call, Response<Admin> response) {
                    user = (Object)  response.body();
                    //DataLocal.getInstance();
                    DataLocal.setIsLogin(true);
                    DataLocal.setDateLogin(Calendar.getInstance().getTime());
                    DataLocal.setUserLogin(response.body().getUsername());
                    DataLocal.setUserPassword(response.body().getPassword());
                    DataLocal.setUserRole(role);
                    DataLocal.setIsRememberMe(chkRememberMe.isChecked());

                    edtPassword.setText(  ((Admin) user).getEmail());

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("USER",((Serializable) user));
                    bundle.putString("ROLE", role);
                    Intent intent=getIntent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();

                }

                @Override
                public void onFailure(Call<Admin> call, Throwable t) {
                    edtPassword.setText("fail");
                }
            };
            ApiService.apiService.loginAdmin(username, password).enqueue(call_admin);
        }
        else if (role == Role.TRAINER.name()){
            Callback<Trainer> callTrainer = new Callback<Trainer>() {
                @Override
                public void onResponse(Call<Trainer> call, Response<Trainer> response) {
                    user = (Object)  response.body();
                    //DataLocal.getInstance();
                    DataLocal.setIsLogin(true);
                    DataLocal.setDateLogin(Calendar.getInstance().getTime());
                    DataLocal.setUserLogin(response.body().getUsername());
                    DataLocal.setUserPassword(response.body().getPassword());
                    DataLocal.setUserRole(role);
                    DataLocal.setIsRememberMe(chkRememberMe.isChecked());

                    edtPassword.setText(  ((Trainer) user).getEmail());

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("USER",((Serializable) user));
                    bundle.putString("ROLE", role);
                    Intent intent=getIntent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Trainer> call, Throwable t) {
                    edtPassword.setText("fail");
                }
            };
            ApiService.apiService.loginTrainer(username, password).enqueue(callTrainer);
        }
        else{
            Callback<Trainee> callTrainee = new Callback<Trainee>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    user = (Object)  response.body();
                    //DataLocal.getInstance();
                    DataLocal.setIsLogin(true);
                    DataLocal.setDateLogin(Calendar.getInstance().getTime());
                    DataLocal.setUserLogin(response.body().getUsername());
                    DataLocal.setUserPassword(response.body().getPassword());
                    DataLocal.setUserRole(role);
                    DataLocal.setIsRememberMe(chkRememberMe.isChecked());

                    edtPassword.setText(  ((Trainee) user).getEmail());

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("USER",((Serializable) user));
                    bundle.putString("ROLE", role);
                    Intent intent=getIntent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable t) {
                    edtPassword.setText("fail");
                }
            };
            ApiService.apiService.loginTrainee(username, password).enqueue(callTrainee);
        }



    }

    private void setBtnLogin(){
        txtvLogin.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

                    if (username.isEmpty() == true && password.isEmpty() == true) {
                        txtErrorUsername.setText("Username must have at least 1 character!");
                        txtErrorPassword.setText("Password must have at least 1 character!");
                        txtErrorPassword.setVisibility(View.VISIBLE);
                        txtErrorUsername.setVisibility(View.VISIBLE);
                        return;
                    } else if (username.contains(" ") && password.isEmpty() == true) {
                        txtErrorUsername.setText("Username must have at blank space!");
                        txtErrorPassword.setText("Password must have at least 1 character!");
                        txtErrorPassword.setVisibility(View.VISIBLE);
                        txtErrorUsername.setVisibility(View.VISIBLE);
                        return;
                    }

                    txtErrorUsername.setVisibility(View.INVISIBLE);
                    txtErrorPassword.setVisibility(View.INVISIBLE);


                    roleLogin(spRole.getSelectedItem().toString(),username,password);

                }
            }
        );
    }
}
