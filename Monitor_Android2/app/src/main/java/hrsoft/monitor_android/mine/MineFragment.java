package hrsoft.monitor_android.mine;

import android.content.Intent;
import android.text.InputType;

import butterknife.OnClick;
import hrsoft.monitor_android.App;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.account.LoginActivity;
import hrsoft.monitor_android.base.fragment.BaseFragment;
import hrsoft.monitor_android.mine.activity.PersonalActivity;
import hrsoft.monitor_android.mine.activity.WorkerActivity;
import hrsoft.monitor_android.mine.model.MineHelper;
import hrsoft.monitor_android.mine.model.MobileModel;
import hrsoft.monitor_android.util.DialogUtils;
import hrsoft.monitor_android.util.RegexUtil;
import hrsoft.monitor_android.util.ToastUtil;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */

public class MineFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    /**
     * 退出登录
     */
    @OnClick(R.id.txt_exit_login)
    void toAccount() {
        App.getInstance().exitAccount();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    /**
     * 到班组信息
     */
    @OnClick(R.id.rl_mine_my_account)
    void toPersonalAccount() {
        startActivity(new Intent(getContext(), PersonalActivity.class));
    }

    /**
     * 修改手机号
     */
    @OnClick(R.id.rl_mine_mobile)
    public void onMobileClicked() {
        new DialogUtils(getContext())
                .setTitleText(getString(R.string.text_title_dialog_change_mobile))
                .setCancelable(false)
                .setInputEditView(InputType.TYPE_CLASS_NUMBER)
                .setNegativeButton(null)
                .setPositiveButton(new DialogUtils.OnButtonListener() {
                    @Override
                    public void onButtonClicked(DialogUtils dialogUtils) {
                        String mobile = dialogUtils.getInputText();
                        if (!RegexUtil.checkMobile(mobile)) {
                            ToastUtil.showToast(R.string.toast_mobile_error);
                        } else {
                            showProgressDialog(R.string.dialog_loading);
                            MineHelper.updateMobile(mobile, MineFragment.this);
                        }
                    }
                })
                .showAlertDialog();
    }

    /**
     * 修改密码
     */
    @OnClick(R.id.rl_mine_password)
    public void onPasswordClicked() {
        new DialogUtils(getContext())
                .setTitleText(getString(R.string.text_title_dialog_update_password))
                .setCancelable(false)
                .setInputEditView(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .setNegativeButton(null)
                .setPositiveButton(new DialogUtils.OnButtonListener() {
                    @Override
                    public void onButtonClicked(DialogUtils dialogUtils) {
                        String password = dialogUtils.getInputText();
                        if (password.length() < 6 || password.length() > 20) {
                            // TODO: 17/9/6 加入中文字符的判断
                            ToastUtil.showToast(R.string.toast_password_error);
                        } else {
                            showProgressDialog(R.string.dialog_loading);
                            MineHelper.updatePassword(password, MineFragment.this);
                        }
                    }
                })
                .showAlertDialog();
    }

    /**
     * 到工人详细
     */
    @OnClick(R.id.rl_mine_worker_info)
    public void onWorkerInfoClicked() {
        startActivity(new Intent(getContext(), WorkerActivity.class));
    }

    /**
     * 修改密码成功时候回调
     */
    public void onUpdatePasswordSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_change_success);
    }

    /**
     * 修改密码失败时候回调
     */
    public void onUpdatePasswordFailed() {
        disMissProgressDialog();
    }

    /**
     * 修改手机号成功回调
     */
    public void onUpadateMobileSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_change_success);
    }

    /**
     * 修改手机号失败回调
     */
    public void onUpdateMobileFailed() {
        disMissProgressDialog();
    }
}
