package com.example.android_etpj;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Admin;
import com.example.android_etpj.sharedpreference.DataLocal;
import com.google.android.material.snackbar.Snackbar;

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
    private ImageView imgvRoleMenu;
    private TextView txtErrorPassword;
    public LoginActivity() {
    }

    private Admin admin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //view1 = findViewById(R.id.layout_login);
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        txtvLogin = findViewById(R.id.btn_login);
        chkRememberMe = findViewById(R.id.remember_me);
        txtErrorUsername = findViewById(R.id.username_error);
        txtErrorPassword = findViewById(R.id.password_error);
        txtErrorUsername.setVisibility(View.INVISIBLE);
        txtErrorPassword.setVisibility(View.INVISIBLE);

        setBtnLogin();
        //setImgvRoleMenu();
        //setSignInButton();
        //setExitButton();
        //setFloatingActionButton();

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

                    //Admin admin;




                    ApiService.apiService.loginAdmin(username, password).enqueue(new Callback<Admin>() {
                        @Override
                        public void onResponse(Call<Admin> call, Response<Admin> response) {
                            //response.body().getEmail();
                            //admin = (Admin) response;
/*                            String userID = response.body().getUsername();
                            String userRole = "Admin";
                            DataLocal.setIsLogin(true);
                            DataLocal.setDateLogin(Calendar.getInstance().getTime());
                            DataLocal.setUserLogin(userID);
                            DataLocal.setUserRole(userRole);

                            DataLocal.setIsRememberMe(chkRememberMe.isChecked());

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("USER",response.body());
                            bundle.putString("ROLE","Admin");
                            Intent intent=getIntent();
                            intent.putExtras(bundle);
                            setResult(RESULT_OK,intent);
                            finish();*/
                            edtPassword.setText(response.body().getEmail());
/*                            Snackbar snackbar = Snackbar.make(edtUsername,response.body().toString(), Snackbar.LENGTH_LONG);
                            snackbar.show();*/

/*                            if (response.body()!=Null){
                                edtPassword.setText(response.body().getEmail());
                            }
                            else{
                                edtPassword.setText("None");
                            }*/



                        }

                        @Override
                        public void onFailure(Call<Admin> call, Throwable t) {
                            edtPassword.setText("fail");
                        }
                    });

/*                    List<User> userList=NoteDatabase.getInstance(LoginActivity.this).userDAO().getListUser(email,password);

                    if(userList.size()>0){
                        user=userList.get(0);
                        DataLocal.setIsLogin(true);
                        DataLocal.setDateLogin(Calendar.getInstance().getTime());
                        DataLocal.setUserLogin(user.getId());
                        DataLocal.setIsRememberMe(chkRememberMe.isChecked());

                        Bundle bundle=new Bundle();
                        bundle.putSerializable("USER",user);

                        Intent intent=getIntent();
                        intent.putExtras(bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else {
                        hideKeyboard();

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_login),"Username or password is incorrect", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }*/
                }
            }
        );
    }
}
