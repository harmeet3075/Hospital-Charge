package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.user.hospitalcharge.R;

/**
 * Created by user on 18-01-2016.
 */
public class LegalDisclaimerActivity extends Activity {


    private ImageView backButton;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_legal_disclaimer_page);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
