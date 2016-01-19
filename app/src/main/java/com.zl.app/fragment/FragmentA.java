package com.zl.app.fragment;

import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zl.app.BaseFragment;
import com.zl.app.R;


/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_a)
public class FragmentA extends BaseFragment {

    private final String TAG = "FragmentA";
    @ViewById(R.id.container)
    LinearLayout linearLayout;

    List<View> views;

    @AfterViews
    void afterViews() {
        views = new ArrayList<View>();
        String viewstr = getViews();
        Log.i(TAG, viewstr);
        Gson gson = new Gson();
        Map<String, ArrayList<ViewProp>> map = gson.fromJson(viewstr, new TypeToken<Map<String, ArrayList<ViewProp>>>() {
        }.getType());
        ArrayList<ViewProp> list = map.get("views");
        Log.i(TAG, list.size() + "");
        //initView(list);
    }

    public void initView(@NonNull ArrayList<ViewProp> list) {
        for (ViewProp viewProp : list) {
            if (viewProp.getType().equals("editview")) {
                EditText editText = new EditText(getActivity());
                editText.setId(viewProp.getId());
                editText.setTag(viewProp.getTag());
                views.add(editText);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                if (viewProp.getWidth() == 0) {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (viewProp.getWidth() == -1) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                if (viewProp.getHeight() == 0) {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (viewProp.getHeight() == -1) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                params.setMargins(viewProp.getMaginLeft(), viewProp.getMarinTop(), viewProp.getMarginRight(), viewProp.getMarginBottom());
                editText.setLayoutParams(params);
                editText.setHint(viewProp.getHint());
                editText.setPadding(viewProp.getPadding(), viewProp.getPadding(), viewProp.getPadding(), viewProp.getPadding());
                linearLayout.addView(editText);
            }
            if (viewProp.getType().equals("pwview")) {
                EditText editText = new EditText(getActivity());
                editText.setId(viewProp.getId());
                editText.setTag(viewProp.getTag());
                views.add(editText);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                if (viewProp.getWidth() == 0) {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (viewProp.getWidth() == -1) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                if (viewProp.getHeight() == 0) {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (viewProp.getHeight() == -1) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                params.setMargins(viewProp.getMaginLeft(), viewProp.getMarinTop(), viewProp.getMarginRight(), viewProp.getMarginBottom());
                editText.setLayoutParams(params);
                editText.setHint(viewProp.getHint());
                editText.setPadding(viewProp.getPadding(), viewProp.getPadding(), viewProp.getPadding(), viewProp.getPadding());
                linearLayout.addView(editText);
            }
            if (viewProp.getType().equals("button")) {
                Button button = new Button(getActivity());
                views.add(button);
                button.setId(viewProp.getId());
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                if (viewProp.getWidth() == 0) {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (viewProp.getWidth() == -1) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                if (viewProp.getHeight() == 0) {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else if (viewProp.getHeight() == -1) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                if(viewProp.getMaginLeft()==0 && viewProp.getMarginBottom()==0
                        && viewProp.getMarginRight() == 0 && viewProp.getMarinTop() == 0){
                    params.setMargins(viewProp.getMargin(),viewProp.getMargin(),viewProp.getMargin(),viewProp.getMargin());
                }else{
                    params.setMargins(viewProp.getMaginLeft(), viewProp.getMarinTop(), viewProp.getMarginRight(), viewProp.getMarginBottom());
                }

                button.setLayoutParams(params);
                button.setText(viewProp.getText());
                button.setPadding(viewProp.getPadding(), viewProp.getPadding(), viewProp.getPadding(), viewProp.getPadding());
                linearLayout.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder sb = new StringBuilder();
                         for(View view: views){
                             if(view instanceof EditText){
                                 EditText editText = (EditText) view;
                                 String tag = String.valueOf(editText.getTag());
                                 sb.append(tag).append("=").append(editText.getText().toString()).append("&");
                             }
                         }
                        Toast.makeText(getActivity(),sb.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                //other views,for example,textview,radio,checkbox,imageview ...
            }
        }
    }

    public String getViews() {
        ViewProp userNameViewProp = new ViewProp();
        userNameViewProp.setId(1);
        userNameViewProp.setType("editview");
        userNameViewProp.setWidth(-1);
        userNameViewProp.setHeight(0);
        userNameViewProp.setHint("user name");
        userNameViewProp.setTag("username");
        userNameViewProp.setPadding(15);
        userNameViewProp.setMarinTop(15);
        userNameViewProp.setMaginLeft(15);
        userNameViewProp.setMarginRight(15);

        ViewProp passwordViewProp = new ViewProp();
        passwordViewProp.setId(2);
        passwordViewProp.setType("pwview");
        passwordViewProp.setWidth(-1);
        passwordViewProp.setHeight(0);
        passwordViewProp.setHint("password");
        passwordViewProp.setTag("password");
        passwordViewProp.setPadding(15);
        passwordViewProp.setMarinTop(15);
        passwordViewProp.setMaginLeft(15);
        passwordViewProp.setMarginRight(15);


        ViewProp loginBtnProp = new ViewProp();
        loginBtnProp.setId(3);
        loginBtnProp.setType("button");
        loginBtnProp.setWidth(-1);
        loginBtnProp.setHeight(0);
        loginBtnProp.setText("login");
        loginBtnProp.setPadding(15);
        loginBtnProp.setMargin(15);

        ArrayList<ViewProp> propList = new ArrayList<ViewProp>();
        propList.add(userNameViewProp);
        propList.add(passwordViewProp);
        propList.add(loginBtnProp);

        Map<String, ArrayList<ViewProp>> map = new HashMap<String, ArrayList<ViewProp>>();
        map.put("views", propList);

        Gson gson = new Gson();
        String viewstr = gson.toJson(map);
        return viewstr;
    }

    //height  width 0: wrap_content  -1:match_parent
    class ViewProp {

        private int id;
        private String type;             //textview  button  ....
        private int height;
        private int width;
        private int padding;
        private int margin;
        private int paddingLeft;
        private int paddingRight;
        private int paddingTop;
        private int paddingBottom;
        private int maginLeft;
        private int marginRight;
        private int marinTop;
        private int marginBottom;
        private String gravity;        //top bottom left right ....
        private String layoutGravity; //top bottom left right ....
        private String hint;
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private String text;
        private int textSize = 12;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getPadding() {
            return padding;
        }

        public void setPadding(int padding) {
            this.padding = padding;
        }

        public int getMargin() {
            return margin;
        }

        public void setMargin(int margin) {
            this.margin = margin;
        }

        public int getPaddingLeft() {
            return paddingLeft;
        }

        public void setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
        }

        public int getPaddingRight() {
            return paddingRight;
        }

        public void setPaddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
        }

        public int getPaddingTop() {
            return paddingTop;
        }

        public void setPaddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
        }

        public int getPaddingBottom() {
            return paddingBottom;
        }

        public void setPaddingBottom(int paddingBottom) {
            this.paddingBottom = paddingBottom;
        }

        public int getMaginLeft() {
            return maginLeft;
        }

        public void setMaginLeft(int maginLeft) {
            this.maginLeft = maginLeft;
        }

        public int getMarginRight() {
            return marginRight;
        }

        public void setMarginRight(int marginRight) {
            this.marginRight = marginRight;
        }

        public int getMarinTop() {
            return marinTop;
        }

        public void setMarinTop(int marinTop) {
            this.marinTop = marinTop;
        }

        public int getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        public String getGravity() {
            return gravity;
        }

        public void setGravity(String gravity) {
            this.gravity = gravity;
        }

        public String getLayoutGravity() {
            return layoutGravity;
        }

        public void setLayoutGravity(String layoutGravity) {
            this.layoutGravity = layoutGravity;
        }
    }
}
