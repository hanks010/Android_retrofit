package com.com.blog.view.post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.com.blog.R;
import com.com.blog.controller.PostController;
import com.com.blog.controller.dto.CMRespDto;
import com.com.blog.model.Post;
import com.com.blog.view.CustomAppBarActivity;
import com.com.blog.view.InitActivity;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class   PostDetailActivity extends CustomAppBarActivity implements InitActivity {

    private static final String TAG = "PostDetailActivity";
    // 애플리케이션이 처음부터 끝까지 관리하는 어떤 클래스에 액티비티 컨텍스트 전달하면 가비지 컬렉션이 동작하지 않아서 메모리 부족, 망함
    private PostDetailActivity mContext = PostDetailActivity.this;

    private MaterialButton btnDelete,btnUpdateForm;
    private TextView tvBox;
    private int postId;
    private PostController postController;
    private Post post;//

    @Override
    protected void onResume() {
        super.onResume();
        //수정완료 되면 postId 갱신
      // initData(); // PostUpdateActivity에서 보낸
        updateData();
        Log.d(TAG, "onResume: 다시 그려짐");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Log.d(TAG, "onCreate: ");
        init();
        initData();
        initLr();
        initSetting();
    }

    @Override
    public void init() {
        postController = new PostController();
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdateForm = findViewById(R.id.btnUpdateForm);
        tvBox = findViewById(R.id.tvBox);

    }

    @Override
    public void initLr() {
        btnUpdateForm.setOnClickListener(v ->{
            Intent intent = new Intent(mContext,PostUpdateActivity.class);
            intent.putExtra("post",post); //Serializable implements 해야 객체를 통째로 넘길 수 있음
            mContext.startActivity(intent);
        });
        btnDelete.setOnClickListener(v -> {
            Log.d(TAG, "initLr: "+postId);
            postController.deleteById(postId).enqueue(new Callback<CMRespDto>() {
                @Override
                public void onResponse(Call<CMRespDto> call, Response<CMRespDto> response) {
                    Log.d(TAG, "onResponse: "+response.body());
                    CMRespDto cm = response.body();
                   if(cm.getCode() == 1){
                       //이전 페이지들 다 삭제하고 PostListActivity만 남게함

                       //finish(); + ReSume에서 새로 그림, 뒤로가기 했을 때 이전 액티비티들이 모두 남아있다.
                       Intent intent = new Intent(mContext,PostListActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
                }

                @Override
                public void onFailure(Call<CMRespDto> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }

    @Override
    public void initSetting() {
        onAppBarSettings(true, "Detail");

    }
    public void updateData(){
        Intent getIntent = getIntent();
        postId = getIntent.getIntExtra("postId",0); //디폴트값 설정 필요
        if(postId == 0) finish();
        // 트럭 데이터 받기
        //데이터 초기화하기
        postController.findById(postId).enqueue(new Callback<CMRespDto<Post>>() {
            @Override
            public void onResponse(Call<CMRespDto<Post>> call, Response<CMRespDto<Post>> response) {
                CMRespDto<Post> cm = response.body();
                if (cm.getCode() == 1) {
                    post = cm.getData();
                    if (tvBox.getText().toString() == null) {
                        tvBox.append("id: " + post.getId() + "\n");
                        tvBox.append("title: " + post.getTitle() + "\n");
                        tvBox.append("content: " + post.getContent() + "\n");
                        tvBox.append("username: " + post.getUser().getUsername() + "\n");
                        tvBox.append("created: " + post.getCreated() + "\n");
                    } else {
                        tvBox.setText(null);
                        tvBox.append("id: " + post.getId() + "\n");
                        tvBox.append("title: " + post.getTitle() + "\n");
                        tvBox.append("content: " + post.getContent() + "\n");
                        tvBox.append("username: " + post.getUser().getUsername() + "\n");
                        tvBox.append("created: " + post.getCreated() + "\n");
                    }
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
            @Override
    public void initData() {
        Intent getIntent = getIntent();
        postId = getIntent.getIntExtra("postId",0); //디폴트값 설정 필요
        if(postId == 0) finish();
        // 트럭 데이터 받기
        //데이터 초기화하기
        postController.findById(postId).enqueue(new Callback<CMRespDto<Post>>() {
            @Override
            public void onResponse(Call<CMRespDto<Post>> call, Response<CMRespDto<Post>> response) {
                CMRespDto<Post> cm = response.body();
                if(cm.getCode() ==1){
                    post = cm.getData();
                    tvBox.append("id: "+post.getId()+"\n");
                    tvBox.append("title: "+post.getTitle()+"\n");
                    tvBox.append("content: "+post.getContent()+"\n");
                    tvBox.append("username: "+post.getUser().getUsername()+"\n");
                    tvBox.append("created: "+post.getCreated()+"\n");

                }
            }

            @Override
            public void onFailure(Call<CMRespDto<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 시작");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 일시정지");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 멈춤");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 종료 ");
    }
}