package hrsoft.monitor_android.account;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.account.model.AccountHelper;
import hrsoft.monitor_android.account.model.LoginRequest;
import hrsoft.monitor_android.base.activity.NoBarActivity;
import hrsoft.monitor_android.main.MainActivity;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */

public class LoginActivity extends NoBarActivity {
    @BindView(R.id.edit_account)
    EditText accountEdit;
    @BindView(R.id.edit_psw)
    EditText pswEdit;
    @BindView(R.id.btn_to_login)
    Button loginBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        accountEdit.addTextChangedListener(textWatcher);
        pswEdit.addTextChangedListener(textWatcher);
        loginBtn.setSelected(false);
        loginBtn.setClickable(false);
    }

    @Override
    protected void loadData() {

    }

    /**
     * 点击登录
     */
    @OnClick(R.id.btn_to_login)
    void toLogin() {
        String account = accountEdit.getText().toString().trim();
        String psw = pswEdit.getText().toString().trim();
        LoginRequest request = new LoginRequest(account, psw);
        showProgressDialog(R.string.dialog_loading);
        AccountHelper.login(request, this);
    }

    /**
     * 登录成功时
     */
    public void onLoginSuccess() {
        AccountHelper.requestGroupMsg(this);
    }

    /**
     * 登录失败时
     */
    public void onLoginFailed() {
        disMissProgressDialog();
    }

    /**
     * 获取班组消息成功时
     */
    public void onLoadedGroupSuccess() {
        disMissProgressDialog();
        this.finish();
        startActivity(new Intent(this, MainActivity.class));

    }

    /**
     * 获取班组信息失败
     */
    public void onLoadedGroupFailed() {
        disMissProgressDialog();
    }

    /**
     * 文字状态监听
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String account = accountEdit.getText().toString().trim();
            String psw = pswEdit.getText().toString().trim();
            if (account.length() > 10 && psw.length() > 5) {
                loginBtn.setSelected(true);
                loginBtn.setClickable(true);
            } else {
                loginBtn.setSelected(false);
                loginBtn.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
}
