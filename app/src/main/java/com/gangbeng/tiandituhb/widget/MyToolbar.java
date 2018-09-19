package com.gangbeng.tiandituhb.widget;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gangbeng.tiandituhb.R;


/**
 * @author zhanghao
 * @fileName MyToolbar
 * @date 2018-02-02
 */

public class MyToolbar extends Toolbar {

    private EditText toolbar_editText;
    private TextView toolbar_search;
    private TextView toolbar_textView;
    private TextView toolbar_imgBtn;
    private ImageButton toolbar_LeftimgBtn;
    private MyToolBarBtnListenter btnListenter;
    private MyToolBarEditTextListener editTextListener;

    public MyToolbar(Context context) {
        this(context, null);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(0,0);

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.MyToolBar, defStyleAttr, 0);
//        Drawable drawableRight = a.getDrawable(R.styleable.MyToolBar_RightImgButtonIcon);
        Drawable drawableLeft = a.getDrawable(R.styleable.MyToolBar_LeftImgButtonIcon);
        Drawable drawableText = a.getDrawable(R.styleable.MyToolBar_MiddleImgTextIcon);
        boolean aBoolean = a.getBoolean(R.styleable.MyToolBar_isShowEditText, false);
        String hint = a.getString(R.styleable.MyToolBar_editHint);
        if (!TextUtils.isEmpty(hint)) {
            toolbar_editText.setHint(hint);
        }
//        if (drawableRight != null) {
//            setRightImageBtnDrawable(drawableRight);
//        }
        if (drawableLeft != null) {
            setLeftImageBtnDrawable(drawableLeft);
        }
        if (drawableText != null) {
            setTextImageDrawable(drawableText);
        }

        /**
         * 如果显示editText为true，则把editText 设为显示，TextView设为隐藏
         */
        if (aBoolean) {
            showEditText();
            hintTextView();
        } else {
            hintEditText();
        }

    }


    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_mytoolbar, null);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, params);

        toolbar_editText = (EditText) this.findViewById(R.id.toolbar_editText);
        toolbar_textView = (TextView) this.findViewById(R.id.toolbar_textView);
        toolbar_imgBtn = (TextView) this.findViewById(R.id.toolbar_imgBtn);
        toolbar_LeftimgBtn = (ImageButton) this.findViewById(R.id.toolbar_leftImgBtn);
        toolbar_search = (TextView) this.findViewById(R.id.toolbar_search);
//        toolbar_editText.addTextChangedListener(this);
        toolbar_imgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != btnListenter) {
                    btnListenter.ImageRightBtnclick();
                }
            }
        });

        toolbar_LeftimgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != btnListenter) {
                    btnListenter.ImageLeftBtnclick();
                }

            }
        });
        toolbar_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != btnListenter) {
                    if (TextUtils.isEmpty(toolbar_editText.getText())) {
                        Toast.makeText(getContext(), "输入为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    btnListenter.searchBtnclick(toolbar_editText.getText().toString());
                }
            }
        });
        toolbar_editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.e("editText", "失去焦点");
                    // 失去焦点
                    toolbar_editText.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(toolbar_editText.getWindowToken(), 0);
                } else {
                    Log.e("editText", "获得焦点");
                    toolbar_editText.setCursorVisible(true);
                }
            }
        });

    }

    public void showTextView() {
        toolbar_textView.setVisibility(View.VISIBLE);
    }

    public void showEditText() {
        toolbar_editText.setVisibility(View.VISIBLE);
    }

    public void hideEdiText(){
        toolbar_editText.setVisibility(View.GONE);
    }

    public void showRightBtnIcon() {
        toolbar_imgBtn.setVisibility(View.VISIBLE);
    }

    public void showLeftBtnIcon() {
        toolbar_LeftimgBtn.setVisibility(View.VISIBLE);
    }

    public void hintLeftBtnIcon() {
        toolbar_LeftimgBtn.setVisibility(View.GONE);
    }

    public void showToolbar_search() {
        toolbar_search.setVisibility(View.VISIBLE);
    }

    public void hintToolbar_search() {
        toolbar_search.setVisibility(View.GONE);
    }

    public void hintTextView() {
        toolbar_textView.setVisibility(View.GONE);
    }

    public void hintEditText() {
        toolbar_editText.setVisibility(View.GONE);
    }

    public void hintRightBtnIcon() {
        toolbar_imgBtn.setVisibility(View.GONE);
    }


    public String getEditText() {
        return String.valueOf(toolbar_editText.getText());
    }

    @Override
    public void setTitle(@StringRes int resId) {
        showTextView();
        if (resId != 0) {
            toolbar_textView.setText(resId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        showTextView();
        if (title != null) {
            toolbar_textView.setText(title);
        }
    }

    public void setTextImageDrawable(Drawable resId) {
        showTextView();
        //使用setCompoundDrawables时，必须调用setBounds方法，否则图片不显示
        resId.setBounds(0, 0, resId.getMinimumWidth(), resId.getMinimumHeight());
        toolbar_textView.setCompoundDrawables(resId, null, null, null);
    }

    public void setLeftImageBtnDrawable(Drawable resId) {
        showLeftBtnIcon();
        toolbar_LeftimgBtn.setImageDrawable(resId);
    }

    public void setLeftImageBtnDrawable(int resId) {
        showLeftBtnIcon();
        toolbar_LeftimgBtn.setImageResource(resId);
    }

    public void setRightImageBtnText(String text) {
        showRightBtnIcon();
        toolbar_imgBtn.setText(text);
    }


    public void setMyToolBarBtnListenter(MyToolBarBtnListenter btnListenter) {
        this.btnListenter = btnListenter;
    }

    public void setMyToolBarEditTextListener(MyToolBarEditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    public interface MyToolBarBtnListenter {
        void ImageRightBtnclick();

        void ImageLeftBtnclick();

        void searchBtnclick(String content);
    }

    public interface MyToolBarEditTextListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }


}
