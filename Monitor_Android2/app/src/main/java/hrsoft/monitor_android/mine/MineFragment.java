package hrsoft.monitor_android.mine;

import android.content.Intent;

import butterknife.OnClick;
import hrsoft.monitor_android.App;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.account.LoginActivity;
import hrsoft.monitor_android.base.fragment.BaseFragment;

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
}
