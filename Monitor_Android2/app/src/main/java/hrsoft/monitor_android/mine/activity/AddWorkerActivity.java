package hrsoft.monitor_android.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.model.MineHelper;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.util.RegexUtil;
import hrsoft.monitor_android.util.ToastUtil;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class AddWorkerActivity extends ToolbarActivity {
    public static final int TYPE_ADD = 0x123;
    public static final int TYPE_CHANGE = 0x1234;

    @BindView(R.id.edt_addworker_name) EditText nameEdit;
    @BindView(R.id.edt_addworker_position) EditText positionEdit;
    @BindView(R.id.edt_addworker_no) EditText noEdit;
    @BindView(R.id.edt_addworker_mobile) EditText mobileEdit;

    private int type = -1;
    private WorkerModel workerModel;

    /**
     * 静态启动
     */
    public static void start(Context context, int type, WorkerModel model) {
        Intent intent = new Intent(context, AddWorkerActivity.class);
        intent.putExtra(KeyValue.KEY_WORKER_TYPE, type);
        if (model != null) {
            intent.putExtra(KeyValue.KEY_WORKER_MODEL, model);
        }
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_worker;
    }

    @Override
    protected void initVariable() {
        type = getIntent().getIntExtra(KeyValue.KEY_WORKER_TYPE, -1);
        workerModel = (WorkerModel) getIntent().getSerializableExtra(KeyValue.KEY_WORKER_MODEL);
    }

    @Override
    protected void initView() {
        switch (type) {
            case TYPE_ADD:
                setActivityTitle("添加组员信息");
                break;
            case TYPE_CHANGE:
                setActivityTitle("修改组员信息");
                if (workerModel != null) {
                    nameEdit.setText(workerModel.getName());
                    noEdit.setText(workerModel.getNo());
                    mobileEdit.setText(workerModel.getMobile());
                    positionEdit.setText(workerModel.getPosition());
                }
                break;
            default:
                logicError();
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    /**
     * 添加或修改信息成功
     */
    public void onDataLoadedSuccess() {
        ToastUtil.showToast("操作成功");
        this.finish();
    }

    /**
     * 添加或修改信息失败
     */
    public void onDataLoadedFailed() {
    }

    @OnClick(R.id.btn_addworker_submit)
    public void onViewClicked() {
        String name = nameEdit.getText().toString().trim();
        String position = positionEdit.getText().toString().trim();
        String no = noEdit.getText().toString().trim();
        String mobile = mobileEdit.getText().toString().trim();
        if (!RegexUtil.checkMobile(mobile)) {
            ToastUtil.showToast(R.string.toast_mobile_error);
        } else if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast(R.string.toast_name_empty);
        } else if (TextUtils.isEmpty(position)) {
            ToastUtil.showToast("请输入职称");
        } else if (TextUtils.isEmpty(no)) {
            ToastUtil.showToast("请输入工号");
        } else {
            WorkerModel workerModel;
            switch (type) {
                case TYPE_ADD:
                    workerModel = new WorkerModel();
                    workerModel.setMobile(mobile);
                    workerModel.setName(name);
                    workerModel.setPosition(position);
                    workerModel.setNo(no);
                    workerModel.setGroupId(User.getGroupId());
                    MineHelper.addWorkerInfo(workerModel, this);
                    break;
                case TYPE_CHANGE:
                    workerModel = new WorkerModel(this.workerModel.getId(), name, position, no, mobile);
                    workerModel.setGroupId(User.getGroupId());
                    if (this.workerModel.equals(workerModel)) {
                        this.finish();
                    } else {
                        MineHelper.updateWorkerInfo(workerModel, this);
                    }
                    break;
                default:
                    logicError();
                    break;
            }
        }
    }

}
