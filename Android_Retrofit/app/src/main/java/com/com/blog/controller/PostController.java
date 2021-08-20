package com.com.blog.controller;

import android.content.Intent;
import android.util.Log;

import com.com.blog.config.SessionUser;
import com.com.blog.controller.dto.PostUpdateDto;
import com.com.blog.model.Post;
import com.com.blog.service.PostService;
import com.com.blog.controller.dto.CMRespDto;
import com.com.blog.view.post.PostDetailActivity;
import com.com.blog.view.post.PostUpdateActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//View와 통신하는 중개자
public class PostController {

    private static final String TAG = "PostController";
    private PostService postService = PostService.service;
    //private String token = SessionUser.token;
    //전역변수에 걸어놓는 순간 의존성이 심해진다, 의존성을 최대한 제거 -> 지역변수로 처리
    public Call<CMRespDto<List<Post>>> findAll(){
        return postService.findAll();
    }
    public Call<CMRespDto<Post>> findById(int postId){
        return postService.findById(postId);
    }
    public Call<CMRespDto<Post>> updateById(PostUpdateDto postUpdateDto, int postId) {return postService.updateById(postUpdateDto, postId);}
    public Call<CMRespDto> deleteById(int postId){return postService.deleteById(postId);}

    public void initPost() {
        Log.d(TAG, "initPost: ");
        //Call<CMRespDto<List<Post>>> call = postService.initPost();
        //enqueue: Call이라는 상자가 비어있는데 데이터 통신이 끝나면 onResponse 함수를 콜백해라는 의미
        postService.initPost().enqueue(new Callback<CMRespDto<List<Post>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Post>>> call, Response<CMRespDto<List<Post>>> response) {
                CMRespDto<List<Post>> cm = response.body();
                Log.d(TAG, "onResponse: title: "+cm.getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Post>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
