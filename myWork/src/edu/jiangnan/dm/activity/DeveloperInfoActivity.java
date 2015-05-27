package edu.jiangnan.dm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.jiangnan.dm.R;

/**
 * Created by Jelly Joe on 2015/4/20.
 */
public class DeveloperInfoActivity extends Activity {

    private View tutor, student;
    public final static String TUTOR = "tutor";
    public final static String STUDENT = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_dialog);
        tutor = findViewById(R.id.tutor);
        student = findViewById(R.id.student);
        tutor.setOnClickListener(onClickListener);
        student.setOnClickListener(onClickListener);
        View container = findViewById(R.id.container);
        container.setOnClickListener(onClickListener);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        showCard(id);
    }

    private void showCard(String id) {
        tutor.setVisibility(View.GONE);
        student.setVisibility(View.GONE);

        if ( id.equals(TUTOR) ) {
            tutor.setVisibility(View.VISIBLE);
        } else {
            student.setVisibility(View.VISIBLE);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if( v.getId() == R.id.container ) {
                finish();
            }
        }
    };


}
