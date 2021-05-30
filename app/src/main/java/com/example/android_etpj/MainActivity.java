package com.example.android_etpj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Admin;
import com.example.android_etpj.models.Module;
import com.example.android_etpj.models.Trainer;
import com.example.android_etpj.ui.AssignmentFragment;
import com.example.android_etpj.ui.ClassFragment;
import com.example.android_etpj.ui.ContactFragment;
import com.example.android_etpj.ui.EnrollmentFragment;
import com.example.android_etpj.ui.FeedbackFragment;
import com.example.android_etpj.ui.HomeFragment;
import com.example.android_etpj.ui.JoinFragment;
import com.example.android_etpj.ui.ModuleFragment;
import com.example.android_etpj.ui.QuestionFragment;
import com.example.android_etpj.ui.ResultFragment;
import com.example.android_etpj.ui.add.*;
import com.example.android_etpj.ui.edit.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.android_etpj.sharedpreference.DataLocal;

import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private static final int REQUEST_CODE = 12;
    private DrawerLayout drawer;
    private Type currentFragment = Type.FRAGMENT_HOME;
    private Role currentRole= Role.ADMIN;
    private Object user;
    private String role;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            role = (String) data.getExtras().get("ROLE");
            currentRole = Role.valueOf(role);
            user = data.getExtras().get("USER");
            setNavigationView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DataLocal = DataLocal.getInstance();
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);

        checkLogin();

        currentRole = Role.valueOf(DataLocal.getUserRole());

        if (currentRole==Role.ADMIN){
            user = DataLocal.getAdmin();
        }
        else if (currentRole==Role.TRAINER){
            user = DataLocal.getTrainer();
        }
        else  {
            user = DataLocal.getTrainee();
        }
        setNavigationView();


    }


    private void setNavigationView(){

        currentFragment= Type.FRAGMENT_HOME;

        Menu nav_Menu = navigationView.getMenu();


        replaceFragment(new HomeFragment(user));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                switch (id){
                    case R.id.nav_home:

                        if(currentFragment!=Type.FRAGMENT_HOME){
                            replaceFragment(new HomeFragment(user));
                            currentFragment=Type.FRAGMENT_HOME;
                        }
                        break;
                    case R.id.nav_assignment:

                        if(currentFragment!=Type.FRAGMENT_ASSIGNMENT){
                            replaceFragment(new AssignmentFragment());
                            currentFragment=Type.FRAGMENT_ASSIGNMENT;
                        }
                        break;
                    case R.id.nav_class:

                        if(currentFragment!=Type.FRAGMENT_CLASS){
                            replaceFragment(new ClassFragment());
                            currentFragment=Type.FRAGMENT_CLASS;
                        }
                        break;
                    case R.id.nav_module:

                        if(currentFragment!=Type.FRAGMENT_MODULE){
                            replaceFragment(new ModuleFragment());
                            currentFragment=Type.FRAGMENT_MODULE;
                        }
                        break;
                    case R.id.nav_enrollment:

                        if(currentFragment!=Type.FRAGMENT_ENROLLMENT){
                            replaceFragment(new EnrollmentFragment());
                            currentFragment=Type.FRAGMENT_ENROLLMENT;
                        }
                        break;
                    case R.id.nav_feedback:

                        if(currentFragment!=Type.FRAGMENT_FEEDBACK){
                            replaceFragment(new FeedbackFragment());
                            currentFragment=Type.FRAGMENT_FEEDBACK;
                        }
                        break;
                    case R.id.nav_result:

                        if(currentFragment!=Type.FRAGMENT_RESULT){
                            replaceFragment(new ResultFragment());
                            currentFragment=Type.FRAGMENT_RESULT;
                        }
                        break;
                    case R.id.nav_question:

                        if(currentFragment!=Type.FRAGMENT_QUESTION){
                            replaceFragment(new QuestionFragment());
                            currentFragment=Type.FRAGMENT_QUESTION;
                        }
                        break;
                    case R.id.nav_contact:

                        if(currentFragment!=Type.FRAGMENT_CONTACT){
                            replaceFragment(new ContactFragment());
                            currentFragment=Type.FRAGMENT_CONTACT;
                        }
                        break;
                    case R.id.nav_join:

                        if(currentFragment!=Type.FRAGMENT_JOIN){
                            replaceFragment(new JoinFragment());
                            currentFragment=Type.FRAGMENT_JOIN;
                        }
                        break;

                    case R.id.nav_log_out:
                        DataLocal.setIsLogin(false);
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivityForResult(intent,REQUEST_CODE);
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void addModuleFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddModuleFragment addModuleFragment=new AddModuleFragment();
        /*Bundle bundle=new Bundle();
        bundle.putSerializable("MODULE",module);
        addModuleFragment.setArguments(bundle);*/

        fragmentTransaction.replace(R.id.content_frame,addModuleFragment);
        fragmentTransaction.addToBackStack(AddModuleFragment.TAG);
        fragmentTransaction.commit();
    }



    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();
    }


    private void checkLogin() {
        if(DataLocal.getIsLogin()==false){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }

        if(DataLocal.getIsLogin()==true){
            Date date=DataLocal.getDateLogin();
            boolean rememberMe=DataLocal.getRememberMe();

            long distance=Calendar.getInstance().getTime().getTime()-date.getTime();
            
            if(rememberMe==false && distance>30*60*1000){ //milliseconds = 30 minutes
                DataLocal.setIsLogin(false);
                checkLogin();
            }
            else if(rememberMe==true && distance>24*60*60*1000){ // 24 hours
                DataLocal.setIsLogin(false);
                checkLogin();
            }

        }


    }




    public void editModuleFragment(Module module) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EditModuleFragment editModuleFragment=new EditModuleFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("MODULE",module);
        editModuleFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,editModuleFragment);
        fragmentTransaction.addToBackStack(EditModuleFragment.TAG);
        fragmentTransaction.commit();
    }
}