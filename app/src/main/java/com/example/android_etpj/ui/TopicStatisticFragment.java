package com.example.android_etpj.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android_etpj.R;
import com.example.android_etpj.SpinnerAdapter;
import com.example.android_etpj.api.ApiService;
import com.example.android_etpj.models.Answer;
import com.example.android_etpj.models.Class;
import com.example.android_etpj.models.Module;
import com.example.android_etpj.models.Question;
import com.example.android_etpj.models.Topic;
import com.example.android_etpj.models.TopicAnswers;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicStatisticFragment extends Fragment {

    private Spinner spClassSearch;
    private Spinner spModuleSearch;

    private TextView tvClassSpinner;
    private TextView tvModuleSpinner;
    private TextView tvStatisticTitle;

    private Class clss;
    private Module module;

    private LinearLayout layoutMainChart;
    private List<LinearLayout> layoutChildChart;

    public TopicStatisticFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_topic_statistic, container, false);

        clss = new Class();
        module = new Module();

        tvClassSpinner = view.findViewById(R.id.tv_title_search_1);
        tvModuleSpinner = view.findViewById(R.id.tv_title_search_2);
        tvStatisticTitle = view.findViewById(R.id.tv_title_statistic);

        tvClassSpinner.setText("Class:");
        tvModuleSpinner.setText("Course:");
        tvStatisticTitle.setText("Statistic By Topic");

        spClassSearch = view.findViewById(R.id.sp_search_1);
        spModuleSearch = view.findViewById(R.id.sp_search_2);

        layoutMainChart = view.findViewById(R.id.layout_topic_chart);

        setClassSpinnerSearch();
        setModuleSpinnerSearch();

        setTopicChart();
        return  view;

    }
    private void setClassSpinnerSearch() {
        ApiService.apiService.getClasses().enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                List<Class> arrayClassList=(ArrayList<Class>) response.body();
                List<Object> classes = new ArrayList<>();
                classes.addAll(arrayClassList);

                SpinnerAdapter spClassAdapter = new SpinnerAdapter(getContext(),R.layout.item_sp_selected,classes);
                try {
                    spClassSearch.setAdapter(spClassAdapter);
                } catch (Exception e) {
                    Log.e("a",e.getMessage());
                }


                if(arrayClassList.size()>=2) {
                    spClassSearch.setSelection(spClassAdapter.getPosition(arrayClassList.get(0)));
                }

                spClassSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object object=spClassAdapter.getItem(position);
                        clss = (Class) object;
                        setTopicChart();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

            }
        });
    }

    private void setModuleSpinnerSearch() {
        ApiService.apiService.getModules().enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                List<Module> arrayModuleList=(ArrayList<Module>) response.body();
                List<Object> modules = new ArrayList<>();
                modules.addAll(arrayModuleList);

                SpinnerAdapter spModuleAdapter = new SpinnerAdapter(getContext(),R.layout.item_sp_selected,modules);
                try {
                    spModuleSearch.setAdapter(spModuleAdapter);
                } catch (Exception e) {
                    Log.e("a",e.getMessage());
                }


                if(arrayModuleList.size()>=2) {
                    spModuleSearch.setSelection(spModuleAdapter.getPosition(arrayModuleList.get(0)));
                }

                spModuleSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object object=spModuleAdapter.getItem(position);
                        module = (Module)object;
                        setTopicChart();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });
    }

    private void setTopicChart (){
        layoutMainChart.removeAllViews();
        ApiService.apiService.getTopicAnswersByClassModule(clss.getClassID(),module.getModuleID()).enqueue(new Callback<List<TopicAnswers>>() {
            @Override
            public void onResponse(Call<List<TopicAnswers>> call, Response<List<TopicAnswers>> response) {
                ArrayList<TopicAnswers> topicAnswersList = (ArrayList<TopicAnswers>) response.body();
                Log.e("A",String.valueOf(topicAnswersList.size()));
                for (int i = 0; i < topicAnswersList.size(); i++) {
                    View viewChildChart;
                    viewChildChart = getLayoutInflater().inflate(R.layout.item_topic_chart,null,false);

                    PieChart pieChart1;
                    TextView tvChart1;

                    tvChart1 = viewChildChart.findViewById(R.id.tv_title_chart_1);
                    pieChart1 = viewChildChart.findViewById(R.id.child_chart_1);

                    tvChart1.setText(topicAnswersList.get(i).getTopic().getTopicName());

                    List<String> titleList = new ArrayList<>();
                    List<Float> countList = new ArrayList<>();

                    titleList.add(new String("Strongly Disagree"));
                    titleList.add(new String("Disagree"));
                    titleList.add(new String("Neural"));
                    titleList.add(new String("Agree"));
                    titleList.add(new String("Strong Agree"));

                    for (int j = 0; j < 5 ; j++){
                        countList.add(new Float(0));
                    }

                    for (Answer answer:topicAnswersList.get(i).getAnswers()) {
                        switch (answer.getValue()) {
                            case 0:
                                countList.set(0,countList.get(0)+1);
                                break;
                            case 1:
                                countList.set(1,countList.get(1)+1);
                                break;
                            case 2:
                                countList.set(2,countList.get(2)+1);
                                break;
                            case 3:
                                countList.set(3,countList.get(3)+1);
                                break;
                            case 4:
                                countList.set(4,countList.get(4)+1);
                                break;
                        }
                    }
//                    if (clss.getClassID()==3&&module.getModuleID()==4) {
//                        Log.e("0",String.valueOf(countList.get(0)));
//                        Log.e("1",String.valueOf(countList.get(1)));
//                        Log.e("2",String.valueOf(countList.get(2)));
//                        Log.e("3",String.valueOf(countList.get(3)));
//                        Log.e("4",String.valueOf(countList.get(4)));
//                    }

                    ArrayList<PieEntry> pieEntries1 = new ArrayList<>();
                    for(int m=0; m<countList.size(); m++){
                        if (countList.get(m) != 0) {
                            pieEntries1.add(new PieEntry(countList.get(m), titleList.get(m) + " (%)"));
                        }
                    }

                    pieChart1.setDrawHoleEnabled(false);
                    pieChart1.clear();
                    PieDataSet pieDataSet1 = new PieDataSet(pieEntries1,"Class Statistic");
                    pieDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet1.setValueTextColor(Color.WHITE);
                    pieDataSet1.setValueTextSize(15f);

                    PieData pieData1 = new PieData(pieDataSet1);
                    pieChart1.setData(pieData1);
                    pieChart1.getDescription().setEnabled(false);
                    pieChart1.animate();

                    i++;

                    if (i < topicAnswersList.size()) {

                        PieChart pieChart2;
                        TextView tvChart2;

                        tvChart2 = viewChildChart.findViewById(R.id.tv_title_chart_2);
                        pieChart2 = viewChildChart.findViewById(R.id.child_chart_2);

                        tvChart2.setText(topicAnswersList.get(i).getTopic().getTopicName());

                        titleList.clear();
                        countList.clear();

                        titleList.add(new String("Strongly Disagree"));
                        titleList.add(new String("Disagree"));
                        titleList.add(new String("Neural"));
                        titleList.add(new String("Agree"));
                        titleList.add(new String("Strong Agree"));

                        for (int j = 0; j < 5; j++) {
                            countList.add(new Float(0));
                        }

                        for (Answer answer : topicAnswersList.get(i).getAnswers()) {
                            switch (answer.getValue()) {
                                case 0:
                                    countList.set(0, countList.get(0) + 1);
                                    break;
                                case 1:
                                    countList.set(1, countList.get(1) + 1);
                                    break;
                                case 2:
                                    countList.set(2, countList.get(2) + 1);
                                    break;
                                case 3:
                                    countList.set(3, countList.get(3) + 1);
                                    break;
                                case 4:
                                    countList.set(4, countList.get(4) + 1);
                                    break;
                            }
                        }
//                    if (clss.getClassID()==3&&module.getModuleID()==4) {
//                        Log.e("0",String.valueOf(countList.get(0)));
//                        Log.e("1",String.valueOf(countList.get(1)));
//                        Log.e("2",String.valueOf(countList.get(2)));
//                        Log.e("3",String.valueOf(countList.get(3)));
//                        Log.e("4",String.valueOf(countList.get(4)));
//                    }

                        ArrayList<PieEntry> pieEntries2 = new ArrayList<>();
                        for (int m = 0; m < countList.size(); m++) {
                            if (countList.get(m) != 0) {
                                pieEntries2.add(new PieEntry(countList.get(m), titleList.get(m) + " (%)"));
                            }
                        }

                        pieChart2.setDrawHoleEnabled(false);
                        pieChart2.clear();
                        PieDataSet pieDataSet2 = new PieDataSet(pieEntries2, "Class Statistic");
                        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
                        pieDataSet2.setValueTextColor(Color.WHITE);
                        pieDataSet2.setValueTextSize(15f);

                        PieData pieData2 = new PieData(pieDataSet2);
                        pieChart2.setData(pieData2);
                        pieChart2.getDescription().setEnabled(false);
                        pieChart2.animate();
                    } else {
                        LinearLayout linearLayout = viewChildChart.findViewById(R.id.layout_chart_2);
                        linearLayout.setVisibility(View.INVISIBLE);
                    }

                    layoutMainChart.addView(viewChildChart);
                }
            }

            @Override
            public void onFailure(Call<List<TopicAnswers>> call, Throwable t) {

            }
        });
    }

    private void setTopicChartTemp() {
        layoutMainChart.removeAllViews();
        ApiService.apiService.getTopics().enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                List<Topic> arrayTopicList=(ArrayList<Topic>) response.body();
//                for (int x = 0; x < arrayTopicList.size(); x++) {
//                    for (int y = 0; y < arrayTopicList.get(x).getQuestions().size(); y++) {
//                        arrayTopicList.get(x).getQuestions().get(y).setTopicID(arrayTopicList.get(x).getTopicID());
//                    }
//                }
                List<Object> topics = new ArrayList<>();
                topics.addAll(arrayTopicList);
                List<Topic> arrayUseTopic = new ArrayList<>();

                for (int i = 0; i < arrayTopicList.size(); i++) {
                    Topic topic = arrayTopicList.get(i);
                    ApiService.apiService.getAnswersByClassModuleTopic(arrayTopicList.get(i).getTopicID(), clss.getClassID(), module.getModuleID())
                            .enqueue(new Callback<List<Answer>>() {
                                @Override
                                public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                                    List<Answer> answerList = (ArrayList<Answer>) response.body();
                                    if (answerList.size() > 0) {
                                        arrayUseTopic.add(topic);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Answer>> call, Throwable t) {

                                }
                            });
                }
                for (int i = 0; i < arrayUseTopic.size(); i++) {
                    View viewChildChart;
                    viewChildChart = getLayoutInflater().inflate(R.layout.item_topic_chart,null,false);
                    ApiService.apiService.getAnswersByClassModuleTopic(arrayUseTopic.get(i).getTopicID(), clss.getClassID(), module.getModuleID())
                            .enqueue(new Callback<List<Answer>>() {
                                @Override
                                public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                                    List<Answer> arrayNeedAnswerList = (ArrayList<Answer>) response.body();

                                    PieChart pieChart1;


                                    pieChart1 = viewChildChart.findViewById(R.id.child_chart_1);

                                    List<String> titleList = new ArrayList<>();
                                    List<Float> countList = new ArrayList<>();

                                    titleList.add(new String("Strongly Disagree"));
                                    titleList.add(new String("Disagree"));
                                    titleList.add(new String("Neural"));
                                    titleList.add(new String("Agree"));
                                    titleList.add(new String("Strong Agree"));

                                    for (int j = 0; j < 5 ; j++){
                                        countList.add(new Float(0));
                                    }

                                    for (Answer answer:arrayNeedAnswerList) {
                                        switch (answer.getValue()) {
                                            case 0:
                                                countList.set(0,countList.get(0)+1);
                                                break;
                                            case 1:
                                                countList.set(1,countList.get(1)+1);
                                                break;
                                            case 2:
                                                countList.set(2,countList.get(2)+1);
                                                break;
                                            case 3:
                                                countList.set(3,countList.get(3)+1);
                                                break;
                                            case 4:
                                                countList.set(4,countList.get(4)+1);
                                                break;
                                        }
                                    }
                                    if (clss.getClassID()==3&&module.getModuleID()==4) {
                                        Log.e("0",String.valueOf(countList.get(0)));
                                        Log.e("1",String.valueOf(countList.get(1)));
                                        Log.e("2",String.valueOf(countList.get(2)));
                                        Log.e("3",String.valueOf(countList.get(3)));
                                        Log.e("4",String.valueOf(countList.get(4)));
                                        Log.e("a",String.valueOf(arrayNeedAnswerList.size()));
                                    }

                                    ArrayList<PieEntry> pieEntries=new ArrayList<>();
                                    for(int i=0; i<countList.size(); i++){
                                        if (countList.get(i) != 0) {
                                            pieEntries.add(new PieEntry(((countList.get(i)*100 / arrayNeedAnswerList.size())), titleList.get(i) + " (%)"));
                                        }
                                    }

                                    pieChart1.clear();
                                    PieDataSet pieDataSet=new PieDataSet(pieEntries,"Class Statistic");
                                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                    pieDataSet.setValueTextColor(Color.WHITE);
                                    pieDataSet.setValueTextSize(25f);

                                    PieData pieData=new PieData(pieDataSet);
                                    pieChart1.setData(pieData);
                                    pieChart1.getDescription().setEnabled(false);
                                    pieChart1.animate();
                                }


                                @Override
                                public void onFailure(Call<List<Answer>> call, Throwable t) {

                                }
                            });
                    i++;
                    ApiService.apiService.getAnswersByClassModuleTopic(arrayUseTopic.get(i).getTopicID(), clss.getClassID(), module.getModuleID())
                            .enqueue(new Callback<List<Answer>>() {
                                @Override
                                public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                                    List<Answer> arrayNeedAnswerList = (ArrayList<Answer>) response.body();

                                    PieChart pieChart2;


                                    pieChart2 = viewChildChart.findViewById(R.id.child_chart_1);

                                    List<String> titleList = new ArrayList<>();
                                    List<Float> countList = new ArrayList<>();

                                    titleList.add(new String("Strongly Disagree"));
                                    titleList.add(new String("Disagree"));
                                    titleList.add(new String("Neural"));
                                    titleList.add(new String("Agree"));
                                    titleList.add(new String("Strong Agree"));

                                    for (int j = 0; j < 5 ; j++){
                                        countList.add(new Float(0));
                                    }

                                    for (Answer answer:arrayNeedAnswerList) {
                                        switch (answer.getValue()) {
                                            case 0:
                                                countList.set(0,countList.get(0)+1);
                                                break;
                                            case 1:
                                                countList.set(1,countList.get(1)+1);
                                                break;
                                            case 2:
                                                countList.set(2,countList.get(2)+1);
                                                break;
                                            case 3:
                                                countList.set(3,countList.get(3)+1);
                                                break;
                                            case 4:
                                                countList.set(4,countList.get(4)+1);
                                                break;
                                        }
                                    }
                                    if (clss.getClassID()==3&&module.getModuleID()==4) {
                                        Log.e("0",String.valueOf(countList.get(0)));
                                        Log.e("1",String.valueOf(countList.get(1)));
                                        Log.e("2",String.valueOf(countList.get(2)));
                                        Log.e("3",String.valueOf(countList.get(3)));
                                        Log.e("4",String.valueOf(countList.get(4)));
                                        Log.e("a",String.valueOf(arrayNeedAnswerList.size()));
                                    }

                                    ArrayList<PieEntry> pieEntries=new ArrayList<>();
                                    for(int i=0; i<countList.size(); i++){
                                        if (countList.get(i) != 0) {
                                            pieEntries.add(new PieEntry(((countList.get(i)*100 / arrayNeedAnswerList.size())), titleList.get(i) + " (%)"));
                                        }
                                    }

                                    pieChart2.clear();
                                    PieDataSet pieDataSet=new PieDataSet(pieEntries,"Class Statistic");
                                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                    pieDataSet.setValueTextColor(Color.WHITE);
                                    pieDataSet.setValueTextSize(25f);

                                    PieData pieData=new PieData(pieDataSet);
                                    pieChart2.setData(pieData);
                                    pieChart2.getDescription().setEnabled(false);
                                    pieChart2.animate();
                                }


                                @Override
                                public void onFailure(Call<List<Answer>> call, Throwable t) {

                                }
                            });
                    layoutMainChart.addView(viewChildChart);
                }
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {

            }
        });
    }
}