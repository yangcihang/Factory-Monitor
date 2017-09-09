package hrsoft.monitor_android.procedure.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.procedure.adapter.ProcedureWorksListAdapter;
import hrsoft.monitor_android.procedure.model.DeleteProcedureWorkersRequest;
import hrsoft.monitor_android.procedure.model.ProcedureHelper;
import hrsoft.monitor_android.procedure.model.ProcedureModel;
import hrsoft.monitor_android.util.DialogUtils;
import hrsoft.monitor_android.util.TimeUtil;


/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class SchedulingActivity extends ToolbarActivity {
    @BindView(R.id.txt_procedure_id) TextView idTxt;
    @BindView(R.id.txt_procedure_title) TextView procedureTitleTxt;
    @BindView(R.id.txt_procedure_center_title) TextView centerTitleTxt;
    @BindView(R.id.txt_procedure_total) TextView totalTxt;
    @BindView(R.id.txt_procedure_success_count) TextView successCountTxt;
    @BindView(R.id.progress_procedure) ProgressBar procedureProgress;
    @BindView(R.id.txt_procedure_create_time) TextView createTimeTxt;
    @BindView(R.id.txt_procedure_end_time) TextView endTimeTxt;
    @BindView(R.id.btn_procedure_add_worker) Button scheduleBtn;
    @BindView(R.id.rec_procedure_workers_list) RecyclerView workersRec;

    private ProcedureModel procedureModel;
    private ProcedureWorksListAdapter adapter;

    public static void start(Context context, ProcedureModel model) {
        Intent intent = new Intent(context, SchedulingActivity.class);
        intent.putExtra(KeyValue.KEY_PROCEDURE_MODEL, model);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheduling;
    }

    @Override
    protected void initVariable() {
        procedureModel = (ProcedureModel) getIntent().getSerializableExtra(KeyValue.KEY_PROCEDURE_MODEL);
    }

    @Override
    protected void initView() {
        setActivityTitle("排班信息");
        initHeadView();
        initListView();
    }

    @Override
    protected void loadData() {
        showProgressDialog(R.string.dialog_loading);
        ProcedureHelper.requestProcedureWorkers(String.valueOf(procedureModel.getId()),
                String.valueOf(User.getGroupId()), this);
    }

    /**
     * 点击添加工人
     */
    @OnClick(R.id.btn_procedure_add_worker)
    void addWorker() {
        startActivity(new Intent(this, ProcedureAddWorksActivity.class));
    }

    /**
     * 加载工人列表
     */
    public void onDataLoadedSuccess(List<WorkerModel> response) {
        disMissProgressDialog();
        adapter.setData(response);
    }

    /**
     * 加载工人列表失败
     */
    public void onDataLoadedFailed() {
        disMissProgressDialog();
    }

    /**
     * 删除工人成功
     */
    public void onDeleteWorkerSuccess() {
        disMissProgressDialog();
    }

    /**
     * 删除工人失败
     */
    public void onDeleteWorkerFailed() {
        disMissProgressDialog();
    }

    /**
     * 初始化Head信息
     */
    private void initHeadView() {
        int percent = (int) (((float) procedureModel.getSuccessCount() / (float) procedureModel.getTotalCount()) * 100);
        String createTime =
                TimeUtil.setStampToString(TimeUtil.setStringToStamp(procedureModel.getStartTime(),
                        TimeUtil.DATE_DEFAULT_FORMAT), TimeUtil.DATE_DEFAULT_FORMAT);
        String endTime =
                TimeUtil.setStampToString(TimeUtil.setStringToStamp(procedureModel.getEndTime(),
                        TimeUtil.DATE_DEFAULT_FORMAT), TimeUtil.DATE_DEFAULT_FORMAT);

        idTxt.setText(String.valueOf(procedureModel.getId()));
        procedureTitleTxt.setText(procedureModel.getName());
        totalTxt.setText(String.valueOf(procedureModel.getTotalCount()));
        successCountTxt.setText(String.valueOf(procedureModel.getSuccessCount()));
        procedureProgress.setProgress(percent);
        createTimeTxt.setText(createTime);
        endTimeTxt.setText(endTime);
        switch (procedureModel.getStatus()) {
            case 0://未开始
                centerTitleTxt.setText("未开始");
                break;
            case 1://进行中
                centerTitleTxt.setText("进行中");
                break;
            case 2://已结束
                centerTitleTxt.setText("已结束");
                break;
            default:
                break;
        }
    }

    /**
     * 初始化工人列表的信息
     */
    private void initListView() {
        adapter = new ProcedureWorksListAdapter(this);
        workersRec.setAdapter(adapter);
        adapter.setOnDeletedWorkerListener(new ProcedureWorksListAdapter.DeleteWorker() {
            @Override
            public void onWorkerDelete(final WorkerModel workerModel, int pos) {
                new DialogUtils(SchedulingActivity.this)
                        .setCancelable(false)
                        .setTitleText("确认要删除吗？")
                        .setPositiveButton(new DialogUtils.OnButtonListener() {
                            @Override
                            public void onButtonClicked(DialogUtils dialogUtils) {
                                showProgressDialog(R.string.dialog_loading);
                                DeleteProcedureWorkersRequest request = new DeleteProcedureWorkersRequest();
                                request.setProcedureId(procedureModel.getId());
                                request.setWorkerId(workerModel.getId());
                                ProcedureHelper.deleteProcedureWorkers(request, SchedulingActivity.this);
                            }
                        })
                        .setNegativeButton(null)
                        .showAlertDialog();
            }
        });
    }
}
