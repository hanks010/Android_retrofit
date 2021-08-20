package com.com.blog.view.post;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.com.blog.R;
import com.com.blog.controller.PostController;
import com.com.blog.controller.dto.CMRespDto;
import com.com.blog.controller.dto.PostUpdateDto;
import com.com.blog.model.Post;
import com.com.blog.view.CustomAppBarActivity;
import com.com.blog.view.InitActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostUpdateActivity extends CustomAppBarActivity implements InitActivity {

    private PostController postController;
    private TextInputEditText tvTitle, tvContent;
    private MaterialButton btnUpdate;
    private Post post;
    private PostUpdateActivity mContext = PostUpdateActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);

        init();
        initLr();
        initSetting();
        initData();
    }

    @Override
    public void init() {
        postController = new PostController();
        tvContent = findViewById(R.id.tvContent);
        tvTitle = findViewById(R.id.tvTitle);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    @Override
    public void initLr() {
        btnUpdate.setOnClickListener(v -> {
            String title = tvTitle.getText().toString();
            String content = tvContent.getText().toString();
            postController.updateById(new PostUpdateDto(title,content),post.getId()).enqueue(new Callback<CMRespDto<Post>>() {
                @Override
                public void onResponse(Call<CMRespDto<Post>> call, Response<CMRespDto<Post>> response) {
                    CMRespDto<Post> cm = response.body();
                    if(cm.getCode()==1) {
                        //postId, intent 하면 post.getId 가져가야
//                        Intent intent = new Intent(mContext, PostDetailActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.putExtra("postId", post.getId());
//                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CMRespDto<Post>> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public void initSetting() {
        onAppBarSettings(true, "Update");
    }

    @Override
    public void initData() {
        Intent getIntent = getIntent();
//        initTitle = getIntent.getStringExtra("title");
//        initContent = getIntent.getStringExtra("content");
//        postId = getIntent.getIntExtra("postId",0);
        post = (Post) getIntent.getSerializableExtra("post");
        tvTitle.setText(post.getTitle());
        tvContent.setText(post.getContent());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}