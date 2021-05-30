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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private static final int REQUEST_CODE = 12;
    private DrawerLayout drawer;
    private Type currentFragment=Type.FRAGMENT_HOME;
    private Object user;
    private String role;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            role = (String) data.getExtras().get("ROLE");
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

        //checklogin
        setNavigationView();



        }

    private void setNavigationView(){

        currentFragment= Type.FRAGMENT_HOME;

        Menu nav_Menu = navigationView.getMenu();
        //nav_Menu.findItem(R.id.nav_join).setVisible(false);

        replaceFragment(new HomeFragment(user));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                switch (id){
                    case R.id.nav_home:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_HOME){
                            replaceFragment(new HomeFragment(user));
                            currentFragment=Type.FRAGMENT_HOME;
                        }
                        break;
                    case R.id.nav_assignment:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_ASSIGNMENT){
                            replaceFragment(new AssignmentFragment());
                            currentFragment=Type.FRAGMENT_ASSIGNMENT;
                        }
                        break;
                    case R.id.nav_class:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_CLASS){
                            replaceFragment(new ClassFragment());
                            currentFragment=Type.FRAGMENT_CLASS;
                        }
                        break;
                    case R.id.nav_module:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_MODULE){
                            replaceFragment(new ModuleFragment());
                            currentFragment=Type.FRAGMENT_MODULE;
                        }
                        break;
                    case R.id.nav_enrollment:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_ENROLLMENT){
                            replaceFragment(new EnrollmentFragment());
                            currentFragment=Type.FRAGMENT_ENROLLMENT;
                        }
                        break;
                    case R.id.nav_feedback:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_FEEDBACK){
                            replaceFragment(new FeedbackFragment());
                            currentFragment=Type.FRAGMENT_FEEDBACK;
                        }
                        break;
                    case R.id.nav_result:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_RESULT){
                            replaceFragment(new ResultFragment());
                            currentFragment=Type.FRAGMENT_RESULT;
                        }
                        break;
                    case R.id.nav_question:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_QUESTION){
                            replaceFragment(new QuestionFragment());
                            currentFragment=Type.FRAGMENT_QUESTION;
                        }
                        break;
                    case R.id.nav_contact:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_CONTACT){
                            replaceFragment(new ContactFragment());
                            currentFragment=Type.FRAGMENT_CONTACT;
                        }
                        break;
                    case R.id.nav_join:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_JOIN){
                            replaceFragment(new JoinFragment());
                            currentFragment=Type.FRAGMENT_JOIN;
                        }
                        break;

                    case R.id.nav_log_out:
                        DataLocal.setIsLogin(false);
                        checkLogin();
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

    private Object getUser(String role){
        Object myuser = new Object();
        if (role == Role.ADMIN.name()) {
            try {
                myuser = ((Object) ApiService.apiService.loginAdmin(DataLocal.getUserLogin(), DataLocal.getUserPassword()).execute());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else  if (role == Role.TRAINER.name()) {
            try {
                myuser = ((Object) ApiService.apiService.loginTrainer(DataLocal.getUserLogin(), DataLocal.getUserPassword()).execute());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (role == Role.TRAINEE.name()) {
            try {
                myuser = ((Object) ApiService.apiService.loginTrainee(DataLocal.getUserLogin(), DataLocal.getUserPassword()).execute());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myuser;
    }

    private boolean checkLogin() {
        if(DataLocal.getIsLogin()==false){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
        else if(user==null ){
            try{
                //user=NoteDatabase.getInstance(MainActivity.this).userDAO().getUser(DataLocal.getUserLogin()).get(0) ;

                role = DataLocal.getUserRole();
                user = getUser(role);
            }
            catch (Exception ex){
                Log.e("ERROR_USER",ex.getMessage());
                DataLocal.setIsLogin(false);
                checkLogin();
            }
        }
        if(DataLocal.getIsLogin()==true){
            Date date=DataLocal.getDateLogin();
            boolean rememberMe=DataLocal.getRememberMe();

            long distance=Calendar.getInstance().getTime().getTime()-date.getTime();
            if(rememberMe==false && distance>3000000){
                DataLocal.setIsLogin(false);
                checkLogin();
            }
            else if(rememberMe==true && distance>604800000){
                DataLocal.setIsLogin(false);
                checkLogin();
            }
            return true;
        }
        return false;

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