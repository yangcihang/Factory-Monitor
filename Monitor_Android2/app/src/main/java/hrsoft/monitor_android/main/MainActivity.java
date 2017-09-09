package hrsoft.monitor_android.main;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.NoBarActivity;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.manage.ManageFragment;
import hrsoft.monitor_android.mine.MineFragment;
import hrsoft.monitor_android.mine.activity.PersonalActivity;
import hrsoft.monitor_android.procedure.ProcedureFragment;
import hrsoft.monitor_android.util.FragmentUtil;

public class MainActivity extends NoBarActivity {

    @BindView(R.id.img_tab_menu_procedure) ImageView procedureImg;
    @BindView(R.id.txt_tab_menu_procedure) TextView procedureTxt;
    @BindView(R.id.img_tab_menu_manager) ImageView managerImg;
    @BindView(R.id.txt_tab_menu_manager) TextView managerTxt;
    @BindView(R.id.img_tab_menu_mine) ImageView mineImg;
    @BindView(R.id.txt_tab_menu_mine) TextView mineTxt;

    private static final String PROCEDURE_TAG = "procedure";
    private static final String MANAGER_TAG = "manager";
    private static final String MINE_TAG = "mine";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        //登录后获取班组长列表，内容为空则强制其填写班组长信息
        if (!User.isComplete()) {
            startActivity(new Intent(this, PersonalActivity.class));
        }
    }

    @Override
    protected void initView() {
        onProcedureClicked();
    }

    @Override
    protected void loadData() {

    }

    /**
     * 点击工序
     */
    @OnClick(R.id.ll_tab_menu_procedure)
    public void onProcedureClicked() {
        changeProcedureStatus();
        replaceFragment(R.id.ll_tab_menu_procedure);

    }

    /**
     * 点击产能管理
     */
    @OnClick(R.id.ll_tab_menu_manager)
    public void onManagerClicked() {
        changeManagerStatus();
        replaceFragment(R.id.ll_tab_menu_manager);
    }

    /**
     * 点击我的页面
     */
    @OnClick(R.id.ll_tab_menu_mine)
    public void onMineClicked() {
        changeMineStatus();
        replaceFragment(R.id.ll_tab_menu_mine);
    }

    /**
     * 展示当前的fragment
     */
    private void replaceFragment(int resId) {
        ManageFragment manageFragment = (ManageFragment) getSupportFragmentManager().findFragmentByTag(MANAGER_TAG);
        MineFragment mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag(MINE_TAG);
        ProcedureFragment ProcedureFragment = (ProcedureFragment) getSupportFragmentManager().findFragmentByTag(PROCEDURE_TAG);
        if (mineFragment != null) {
            FragmentUtil.hideFragment(this, mineFragment);
        }
        if (manageFragment != null) {
            FragmentUtil.hideFragment(this, manageFragment);
        }
        if (ProcedureFragment != null) {
            FragmentUtil.hideFragment(this, ProcedureFragment);
        }
        switch (resId) {
            case R.id.ll_tab_menu_procedure:
                if (ProcedureFragment == null) {
                    ProcedureFragment = new ProcedureFragment();
                    FragmentUtil.add(this, R.id.fl_content, ProcedureFragment, PROCEDURE_TAG);
                } else {
                    FragmentUtil.showFragment(this, ProcedureFragment);
                }
                break;
            case R.id.ll_tab_menu_manager:
                if (manageFragment == null) {
                    manageFragment = new ManageFragment();
                    FragmentUtil.add(this, R.id.fl_content, manageFragment, MANAGER_TAG);
                } else {
                    FragmentUtil.showFragment(this, manageFragment);
                }
                break;
            case R.id.ll_tab_menu_mine:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    FragmentUtil.add(this, R.id.fl_content, mineFragment, MINE_TAG);
                } else {
                    FragmentUtil.showFragment(this, mineFragment);
                }
                break;
            default:
                logicError();
                break;
        }
    }

    /**
     * 改变首页状态
     */
    private void changeProcedureStatus() {
        clearAllStatus();
        procedureTxt.setSelected(true);
        procedureImg.setSelected(true);
    }

    /**
     * 改变订单列表状态
     */
    private void changeManagerStatus() {
        clearAllStatus();
        managerTxt.setSelected(true);
        managerImg.setSelected(true);
    }

    /**
     * 改变我的页面状态
     */
    private void changeMineStatus() {
        clearAllStatus();
        mineImg.setSelected(true);
        mineTxt.setSelected(true);
    }

    /**
     * 清除所有状态
     */
    private void clearAllStatus() {
        procedureTxt.setSelected(false);
        mineTxt.setSelected(false);
        managerTxt.setSelected(false);
        procedureImg.setSelected(false);
        mineImg.setSelected(false);
        managerImg.setSelected(false);
    }
}
