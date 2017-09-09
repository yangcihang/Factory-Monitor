package hrsoft.monitor_android.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hrsoft.monitor_android.App;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.account.LoginActivity;
import hrsoft.monitor_android.base.fragment.BaseFragment;
import hrsoft.monitor_android.mine.activity.PersonalActivity;
import hrsoft.monitor_android.mine.activity.WorkerActivity;

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
    }

    /**
     * 修改密码
     */
    @OnClick(R.id.rl_mine_password)
    public void onPasswordClicked() {

    }

    /**
     * 到工人详细
     */
    @OnClick(R.id.rl_mine_worker_info)
    public void onWorkerInfoClicked() {
        startActivity(new Intent(getContext(), WorkerActivity.class));
    }

}
