package com.example.android_etpj;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.example.android_etpj.models.Class;
import com.example.android_etpj.models.Module;
import com.example.android_etpj.ui.AssignmentFragment;
import com.example.android_etpj.ui.ClassFragment;
import com.example.android_etpj.ui.CommentResultFragment;
import com.example.android_etpj.ui.ContactFragment;
import com.example.android_etpj.ui.EnrollmentFragment;
import com.example.android_etpj.ui.FeedbackFragment;
import com.example.android_etpj.ui.FeedbackTraineeFragment;
import com.example.android_etpj.ui.FeedbackTraineeReviewFragment;
import com.example.android_etpj.ui.HomeFragment;
import com.example.android_etpj.ui.JoinFragment;
import com.example.android_etpj.ui.ModuleFragment;
import com.example.android_etpj.ui.QuestionFragment;
import com.example.android_etpj.ui.ResultFragment;
import com.example.android_etpj.ui.add.*;
import com.example.android_etpj.ui.edit.*;
import com.example.android_etpj.ui.view.ViewClassFragment;
import com.example.android_etpj.ui.view.ViewEnrollmentDetailFragment;
import com.google.android.material.navigation.NavigationView;
import com.example.android_etpj.models.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Type currentFragment=Type.FRAGMENT_HOME;
    private Object user;
    private Trainer trainer;
    private Trainee trainee;
    private Admin admin;

    public int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);

        //checklogin
        trainer = new Trainer();
        trainer.setUsername("trainer1");

        trainee = new Trainee();
        trainee.setUserId("trainee1");

        admin = new Admin();

        user=admin;
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
                        if(currentFragment!=Type.FRAGMENT_ASSIGNMENT){
                            replaceFragment(new AssignmentFragment());
                            currentFragment=Type.FRAGMENT_ASSIGNMENT;
                        }
                        break;
                    case R.id.nav_class:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_CLASS){
                            replaceFragment(new ClassFragment(user));
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
                            Trainee trainee=new Trainee("trainee1","Thuỷ Tiên","tientien","0971966126","nnnn",true,"hhhh","hhhhh","jjj");

                            replaceFragment(new FeedbackFragment());
                            currentFragment=Type.FRAGMENT_FEEDBACK;
                        }
                        break;
                    case R.id.nav_result:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_RESULT){
                            replaceFragment(new ResultFragment(user));
                            currentFragment=Type.FRAGMENT_RESULT;
                        }
                        break;
                    case R.id.nav_question:
                        checkLogin();
                        if(currentFragment!=Type.FRAGMENT_QUESTION){
                            replaceFragment(new QuestionFragment(user));
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





    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();
    }

    private boolean checkLogin() {

        return true;
    }

    public void backFeedbackFragment(){
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
    }

    public void backFeedbackFragmentRV(){

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
    }

    public void addModuleFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddModuleFragment addModuleFragment=new AddModuleFragment();

        fragmentTransaction.replace(R.id.content_frame,addModuleFragment);
        fragmentTransaction.addToBackStack(AddModuleFragment.TAG);
        fragmentTransaction.commit();
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

    public void addFeedbackFragment() {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddFeedbackFragment addFeedbackFragment=new AddFeedbackFragment();

        fragmentTransaction.replace(R.id.content_frame,addFeedbackFragment);
        fragmentTransaction.addToBackStack(AddFeedbackFragment.TAG);
        fragmentTransaction.commit();
    }

    public void reviewAddFeedbackFragment(Feedback feedback){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        ReviewAddFeedbackFragment reviewFeedbackFragment=new ReviewAddFeedbackFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("FEEDBACKREVIEW",feedback);
        reviewFeedbackFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,reviewFeedbackFragment);
        fragmentTransaction.addToBackStack(ReviewAddFeedbackFragment.TAG);
        fragmentTransaction.commit();
    }

    public void reviewEditFeedbackFragment(Feedback feedback, int type){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        ReviewEditFeedbackFragment reviewFeedbackFragment=new ReviewEditFeedbackFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("FEEDBACKREVIEW",feedback);
        bundle.putInt("TYPEREVIEW",type);
        reviewFeedbackFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,reviewFeedbackFragment);
        fragmentTransaction.addToBackStack(ReviewEditFeedbackFragment.TAG);
        fragmentTransaction.commit();
    }

    public void editFeedbackFragment(Feedback feedback,int type) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EditFeedbackFragment editFeedbackFragment=new EditFeedbackFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("FEEDBACK_EDIT",feedback);
        bundle.putInt("TYPE",type);
        editFeedbackFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,editFeedbackFragment);
        fragmentTransaction.addToBackStack(EditFeedbackFragment.TAG);
        fragmentTransaction.commit();
    }

    public void reviewFeedbackFragment(Feedback feedback){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        ReviewFeedbackFragment reviewFeedbackFragment=new ReviewFeedbackFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("FEEDBACKREVIEW",feedback);
        reviewFeedbackFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,reviewFeedbackFragment);
        fragmentTransaction.addToBackStack(ReviewFeedbackFragment.TAG);
        fragmentTransaction.commit();
    }

    public void addFeedbackTraineeReviewFragment(Assignment assignment, Trainee trainee){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        FeedbackTraineeReviewFragment feedbackTraineeReviewFragment=new FeedbackTraineeReviewFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("ASSIGNMENT",assignment);
        bundle.putSerializable("TRAINEE",trainee);
        feedbackTraineeReviewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,feedbackTraineeReviewFragment);
        fragmentTransaction.addToBackStack(FeedbackTraineeReviewFragment.TAG);
        fragmentTransaction.commit();
    }

    public void addClassFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddClassFragment addClassFragment=new AddClassFragment();

        fragmentTransaction.replace(R.id.content_frame,addClassFragment);
        fragmentTransaction.addToBackStack(AddClassFragment.TAG);
        fragmentTransaction.commit();
    }

    public void editClassFragment(Class clss) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EditClassFragment editClassFragment=new EditClassFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("CLASS",clss);
        editClassFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,editClassFragment);
        fragmentTransaction.addToBackStack(EditModuleFragment.TAG);
        fragmentTransaction.commit();
    }

    public void viewClassFragment(Class clss) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        ViewClassFragment viewClassFragment=new ViewClassFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("VIEWCLASS",clss);
        viewClassFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,viewClassFragment);
        fragmentTransaction.addToBackStack(EditModuleFragment.TAG);
        fragmentTransaction.commit();
    }


    public void addQuestionFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        AddQuestionFragment addQuestionFragment=new AddQuestionFragment();

        fragmentTransaction.replace(R.id.content_frame,addQuestionFragment);
        fragmentTransaction.addToBackStack(AddQuestionFragment.TAG);
        fragmentTransaction.commit();
    }

    public void editQuestionFragment(Question question) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EditQuestionFragment editQuestionFragment=new EditQuestionFragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("QUESTION",question);
        editQuestionFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,editQuestionFragment);
        fragmentTransaction.addToBackStack(EditQuestionFragment.TAG);
        fragmentTransaction.commit();
    }

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

    public void viewCommentResultFragment(Object user) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        CommentResultFragment commentResultFragment=new CommentResultFragment(user);


        fragmentTransaction.replace(R.id.content_frame,commentResultFragment);
        fragmentTransaction.addToBackStack(CommentResultFragment.TAG);
        fragmentTransaction.commit();
    }

}