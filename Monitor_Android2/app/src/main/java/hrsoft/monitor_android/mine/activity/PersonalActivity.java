package hrsoft.monitor_android.mine.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.account.model.GroupModel;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.model.MineHelper;
import hrsoft.monitor_android.util.ToastUtil;

/**
 * 个人的班组信息
 *
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class PersonalActivity extends ToolbarActivity {
    //班组长名字
    @BindView(R.id.txt_personal_name)
    TextView nameTxt;
    //班组长电话
    @BindView(R.id.txt_personal_mobile)
    TextView mobileTxt;
    //班组简介
    @BindView(R.id.edt_personal_description)
    EditText descriptionEdt;
    //班组名称
    @BindView(R.id.edt_personal_group_name)
    EditText groupNameEdt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        setActivityTitle("我的信息");
        String name = User.getName();
        String mobile = User.getMobile();
        String teamName = User.getTeamName();
        String des = User.getDes();
        if (!TextUtils.isEmpty(name)) {
            nameTxt.setText(name);
        }
        if (!TextUtils.isEmpty(mobile)) {
            mobileTxt.setText(mobile);
        }
        if (!TextUtils.isEmpty(teamName)) {
            groupNameEdt.setText(teamName);
            //游标设置在末尾
            groupNameEdt.setSelection(teamName.length());
        }
        if (!TextUtils.isEmpty(des)) {
            descriptionEdt.setText(des);
        }
    }

    @Override
    protected void loadData() {

    }

    /**
     * 点击提交
     */
    @OnClick(R.id.btn_personal_submit)
    void submit() {
        String des = descriptionEdt.getText().toString();
        String name = groupNameEdt.getText().toString();
        if (TextUtils.isEmpty(des) || TextUtils.isEmpty(name)) {
            ToastUtil.showToast(R.string.toast_group_info_empty);
        } else {
            GroupModel groupModel = new GroupModel(User.getId(), name, des, User.getName());
            showProgressDialog(R.string.dialog_loading);
            if (User.isComplete()) {
                if (name.equals(User.getTeamName()) && des.equals(User.getDes())) {
                    this.finish();
                } else {
                    MineHelper.updateGroupInfo(groupModel, this);
                }
            } else {
                MineHelper.createGroupInfo(groupModel, this);
            }
        }
    }

    /**
     * 数据加载成功时
     */
    public void onDataLoadedSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_operation_success);
        this.finish();
    }

    /**
     * 数据加载失败时
     */
    public void onDataLoadedFailed() {
        disMissProgressDialog();
    }

    /**
     * 实体键的back监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //如果班组信息未完善，不能退出
            if (!User.isComplete()) {
                ToastUtil.showToast(R.string.toast_group_info_empty);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * toolbar的返回监听
     */
    @Override
    protected void onBackBtnOnclick() {
        //如果班组信息未完善，不能退出
        if (!User.isComplete()) {
            ToastUtil.showToast(R.string.toast_group_info_empty);
        } else {
            super.onBackBtnOnclick();
        }
    }
}
