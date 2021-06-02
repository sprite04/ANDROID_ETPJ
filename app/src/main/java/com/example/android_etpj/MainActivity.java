package com.example.android_etpj;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.android_etpj.models.Admin;
import com.example.android_etpj.models.Assignment;
import com.example.android_etpj.models.Enrollment;
import com.example.android_etpj.models.Module;
import com.example.android_etpj.models.Trainer;
import com.example.android_etpj.ui.AccessForbiddenHomePageFragment;
import com.example.android_etpj.ui.AccessForbiddenLoginFragment;
import com.example.android_etpj.ui.AssignmentFragment;
import com.example.android_etpj.ui.ClassFragment;
import com.example.android_etpj.ui.CommentResultFragment;
import com.example.android_etpj.ui.ContactFragment;
import com.example.android_etpj.ui.EnrollmentFragment;
import com.example.android_etpj.ui.FeedbackFragment;
import com.example.android_etpj.ui.HomeFragment;
import com.example.android_etpj.ui.JoinFragment;
import com.example.android_etpj.ui.QuestionFragment;
import com.example.android_etpj.ui.ResultFragment;
import com.example.android_etpj.ui.add.*;
import com.example.android_etpj.ui.edit.*;
import com.example.android_etpj.ui.view.ViewEnrollmentDetailFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements AccessForbiddenHomePageFragment.AcessForBiddenHF {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Type currentFragment=Type.FRAGMENT_HOME;
    private Object user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Admin trainer=new Admin();
        trainer.setUsername("trainer1");
        user=trainer;

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

        replaceFragment(new HomeFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                switch (id){
                    case R.id.nav_home:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_HOME){
                            replaceFragment(new HomeFragment());
                            currentFragment=Type.FRAGMENT_HOME;
                        }
                        break;
                    case R.id.nav_assignment:
                        checkLogin();
                        if(user !=null){
                            if(user instanceof Admin ||user instanceof Trainer) {
                                if (currentFragment != Type.FRAGMENT_ASSIGNMENT) {
                                    replaceFragment(new AssignmentFragment(user));
                                    currentFragment = Type.FRAGMENT_ASSIGNMENT;
                                }
                            }else{
                                replaceFragment(new AccessForbiddenHomePageFragment());
                            }
                        }else{
                            replaceFragment(new AccessForbiddenLoginFragment());
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
                        if(user!=null){
                            if(user instanceof Admin||user instanceof Trainer){
                                if(currentFragment!=Type.FRAGMENT_MODULE){
                                    replaceFragment(new CommentResultFragment(user));
                                    currentFragment=Type.FRAGMENT_MODULE;
                                }
                            }else{
                                replaceFragment(new AccessForbiddenHomePageFragment());
                            }
                        }else{
                            replaceFragment(new AccessForbiddenLoginFragment());
                        }
                        /*if(currentFragment!=Type.FRAGMENT_MODULE){
                            replaceFragment(new ModuleFragment());
                            currentFragment=Type.FRAGMENT_MODULE;
                        }*/
                        break;
                    case R.id.nav_enrollment:
                        checkLogin();
                        if(user!= null)
                        {
                            if(user instanceof Admin){
                                if(currentFragment!=Type.FRAGMENT_ENROLLMENT){
                                    replaceFragment(new EnrollmentFragment());
                                    currentFragment=Type.FRAGMENT_ENROLLMENT;
                                }
                            }else{
                                replaceFragment(new AccessForbiddenHomePageFragment());
                            }
                        }else{
                            replaceFragment(new AccessForbiddenLoginFragment());
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

    private boolean checkLogin() {

        return true;
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


    //Assignment
    public void addAssignmentFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddAssignmentFragment addAssignmentFragment=new AddAssignmentFragment();

        fragmentTransaction.replace(R.id.content_frame,addAssignmentFragment);
        fragmentTransaction.addToBackStack(AddAssignmentFragment.TAG);
        fragmentTransaction.commit();
    }
    public void editAssignmentFragment(Assignment assignment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EditAssignmentFragment editAssignmentFragment=new EditAssignmentFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("ASSIGNMENT",assignment);
        editAssignmentFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,editAssignmentFragment);
        fragmentTransaction.addToBackStack(EditAssignmentFragment.TAG);
        fragmentTransaction.commit();
    }
    //Enrollment
    public void addEnrollmentFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddEnrollmentFragment addEnrollmentFragment=new AddEnrollmentFragment();

        fragmentTransaction.replace(R.id.content_frame,addEnrollmentFragment);
        fragmentTransaction.addToBackStack(AddEnrollmentFragment.TAG);
        fragmentTransaction.commit();
    }
    public void editEnrollmentFragment(Enrollment enrollment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EditEnrollmentFragment editEnrollmentFragment=new EditEnrollmentFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("ENROLLMENT",enrollment);
        editEnrollmentFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,editEnrollmentFragment);
        fragmentTransaction.addToBackStack(EditEnrollmentFragment.TAG);
        fragmentTransaction.commit();
    }

    public void viewEnrollmentFragment(Enrollment enrollment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        ViewEnrollmentDetailFragment viewEnrollmentFragment=new ViewEnrollmentDetailFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("ENROLLMENT",enrollment);
        viewEnrollmentFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,viewEnrollmentFragment);
        fragmentTransaction.addToBackStack(EditEnrollmentFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void fragmentHome() {
        replaceFragment(new HomeFragment());
        currentFragment=Type.FRAGMENT_HOME;
    }
}