package com.example.android_etpj;

import com.example.android_etpj.models.Module;
import com.example.android_etpj.models.Question;

public interface ExchangeQuestion {
    void loadData(int idTopic);

    void editData(Question question);
}
