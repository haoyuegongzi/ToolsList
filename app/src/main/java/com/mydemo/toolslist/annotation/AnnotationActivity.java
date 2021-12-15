package com.mydemo.toolslist.annotation;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mydemo.toolslist.R;

@Teachers
public class AnnotationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);


        setDrawable(R.drawable.ic_launcher_background);
        sex(Teachers.Man);
        setIcon(WeekDay.MONDAY);
    }


    void setIcon(@WeekDay int id){

    }

    void setDrawable(@DrawableRes int id){

    }

    void sex(@Teachers int sex){

    }
}