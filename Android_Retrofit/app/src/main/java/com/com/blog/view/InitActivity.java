package com.com.blog.view;

public interface InitActivity {
    void init();    //findViewById, new
    void initLr();  //리스너 등록
    void initSetting(); //각종 기타 세팅
    default void initAdapter(){} //어댑터와 관련된 new
    default void initData(){}    //화면 초기화
}
