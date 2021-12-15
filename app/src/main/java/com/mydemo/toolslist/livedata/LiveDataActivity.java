package com.mydemo.toolslist.livedata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.mydemo.toolslist.R;

import java.util.ArrayList;
import java.util.List;

public class LiveDataActivity extends AppCompatActivity {

    Button btnAddData, btnDefaultAddData, btnOne;
    TextView tvData;
    Gson gson = new Gson();
    MutableLiveData<List<Person>> personLiveData = new MutableLiveData<>();
    List<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        btnAddData = findViewById(R.id.btnAddData);
        btnDefaultAddData = findViewById(R.id.btnDefaultAddData);
        btnOne = findViewById(R.id.btnOne);
        tvData = findViewById(R.id.tvData);

        int flag = getIntent().getFlags();

        Log.e("TAGTAG", "启动模式的flag=== 0x" + Integer.toHexString(flag));

        setBtnAddData();
        observe();
    }

    private void observe() {
        personLiveData.observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                String mPeople = gson.toJson(people);
                tvData.setText(mPeople);
            }
        });
    }

    private void setBtnAddData() {
        btnDefaultAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i < 6; i++) {
                    Person person = new Person("默认的：" + (i * i), i);
                    personList.add(person);
                }
                personLiveData.postValue(personList);
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 20; i < 25; i++) {
                    Person person = new Person("默认的：" + (i * i), i);
                    personList.add(person);
                }
                List<Person> personList00 = personLiveData.getValue();
                personList00.addAll(personList);
                personLiveData.postValue(personList00);
            }
        });

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                List<Person>  list = personLiveData.getValue();
                //                Person person = list.get(0);
                //                person.setName("秋水浮萍任缥缈");
                //                personList.add(0, person);
                //                personLiveData.postValue(personList);
                personLiveData.getValue().get(0).setName("秋水浮萍任缥缈");
            }
        });

        //        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        LiveData<String> liveData;
    }


}