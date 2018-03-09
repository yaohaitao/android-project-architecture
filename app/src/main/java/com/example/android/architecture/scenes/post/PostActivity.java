package com.example.android.architecture.scenes.post;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.architecture.R;
import com.example.android.architecture.services.PostService;
import com.example.android.architecture.utils.ActivityUtils;

/**
 * Created by YaoHaitao on 2018/3/9.
 */

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        PostFragment postFragment = (PostFragment) getSupportFragmentManager().findFragmentById(R.id.post_fragment_container);
        if (postFragment == null) {
            postFragment = PostFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), postFragment, R.id.post_fragment_container);
        }

        new PostPresenter(new PostService(), postFragment);
    }
}
