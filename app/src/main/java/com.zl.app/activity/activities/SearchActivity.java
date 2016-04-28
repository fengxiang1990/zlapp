package com.zl.app.activity.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.util.ToastUtil;

/**
 * Created by fengxiang on 2016/4/28.
 */
public class SearchActivity extends BaseActivityWithToolBar {

    EditText editText;
    TextView text_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activty);
        setTitle("搜索活动");
        setBtnLeft1Enable(true);
        editText = (EditText) findViewById(R.id.editText2);
        text_search = (TextView) findViewById(R.id.text_search);
        text_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                String keyword = String.valueOf(editText.getText());
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtil.show(SearchActivity.this, "搜索内容不能为空");
                    return;
                }
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });
    }
}
