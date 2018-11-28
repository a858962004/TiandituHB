package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.EditPasswordPresenter;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-10-18
 */

public class EditPasswordActivity extends BaseActivity implements BaseView {
    @BindView(R.id.ed_oldpassword)
    EditText edOldpassword;
    @BindView(R.id.ed_newpassword)
    EditText edNewpassword;
    @BindView(R.id.ed_renewpassword)
    EditText edRenewpassword;
//    @BindView(R.id.img_yzm)
//    ImageView imgYzm;
//    @BindView(R.id.ed_yzm)
//    EditText edYzm;
    @BindView(R.id.bt_editpassword)
    Button btEditpassword;
//    private CodeUtils instance;
    private BasePresenter presenter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_editpassword);
        setToolbarTitle("修改密码");
        setToolbarRightVisible(false);
        presenter = new EditPasswordPresenter(this);
//        instance = CodeUtils.getInstance();
//        Bitmap bitmap = instance.createBitmap();
//        imgYzm.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_editpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.img_yzm:
//                Bitmap bitmap = instance.createBitmap();
//                imgYzm.setImageBitmap(bitmap);
//                break;
            case R.id.bt_editpassword:
                String oldpassword = String.valueOf(edOldpassword.getText());
                String newpassword = String.valueOf(edNewpassword.getText());
                String renewpassword = String.valueOf(edRenewpassword.getText());
//                String yzm = String.valueOf(edYzm.getText());
//                String code = instance.getCode();
                UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
                if (oldpassword.equals("")) {
                    ShowToast("请输入原始密码");
                    return;
                }
                if (newpassword.equals("")) {
                    ShowToast("请输入新密码");
                    return;
                }
                if (renewpassword.equals("")) {
                    ShowToast("请再次输入新密码");
                    return;
                }
//                if (yzm.equals("")) {
//                    ShowToast("请输入验证码");
//                    return;
//                }
//                if (!code.equals(yzm)) {
//                    ShowToast("您输入的验证码有误");
//                    return;
//                }
                Map<String,Object> parameter=new HashMap<>();
                parameter.put("loginName",user.getLoginname());
                parameter.put("oldPassword",oldpassword);
                parameter.put("newPassword",newpassword);
                presenter.setRequest(parameter);
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        ShowToast(msg);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        dismissProcessDialog();
    }

    @Override
    public void setData(Object data) {
        if (data instanceof SoapObject) {
            SoapObject object=(SoapObject)data;
            String result = RequestUtil.getSoapObjectValue(object, "result");
            String okString = RequestUtil.getSoapObjectValue(object, "okString");
            String errReason = RequestUtil.getSoapObjectValue(object, "errReason");
            if (result.equals("ok")){
                showMsg("修改成功");
                finish();
            }else {
                showMsg(errReason);
            }
        }

    }
}
