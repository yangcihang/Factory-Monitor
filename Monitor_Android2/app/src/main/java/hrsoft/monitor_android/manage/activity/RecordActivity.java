package hrsoft.monitor_android.manage.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.manage.model.ManageHelper;
import hrsoft.monitor_android.manage.model.RecordModel;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.procedure.model.ProcedureModel;
import hrsoft.monitor_android.util.DialogUtils;
import hrsoft.monitor_android.util.FloatUtil;
import hrsoft.monitor_android.util.TimeUtil;
import hrsoft.monitor_android.util.ToastUtil;

import static android.view.View.GONE;

/**
 * @author YangCihang
 * @since 17/9/10.
 * email yangcihang@hrsoft.net
 */

public class RecordActivity extends ToolbarActivity {
    public static final int RECORD_CHANGE = 1;//修改
    public static final int RECORD_ADD = 0; //增加
    public static final int REQUEST_CODE = 3;
    private static final int LOAD_MODEL_RECORD = 4;
    private static final int LOAD_MODEL_PROCEDURE = 5;
    @BindView(R.id.txt_record_current) TextView recordCurrentTxt;
    @BindView(R.id.btn_record_select) Button selectBtn;
    @BindView(R.id.edit_record_count) EditText totalCountEdit;
    @BindView(R.id.edit_record_qualified) EditText qualifiedEdit;
    @BindView(R.id.txt_record_percent) TextView percentTxt;
    @BindView(R.id.txt_record_start) TextView currentTimeTxt;
    @BindView(R.id.txt_record_workers_num) TextView workersNumTxt;
    @BindView(R.id.btn_record_submit) Button submitBtn;
    @BindView(R.id.txt_record_time_label) TextView timeLabelTxt;
    @BindView(R.id.txt_record_workers_detail) TextView workerDetailTxt;
    @BindView(R.id.btn_record_delete) Button deleteBtn;
    private int type;
    private RecordModel recordModel;//记录的model
    private ProcedureModel procedureModel; //工序的model
    private ArrayList<WorkerModel> workerModels;

    /**
     * 静态启动
     */
    public static void start(Context context, RecordModel model, int type) {
        Intent intent = new Intent(context, RecordActivity.class);
        intent.putExtra(KeyValue.KEY_RECORD_MODEL, model);
        intent.putExtra(KeyValue.KEY_RECORD_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initVariable() {
        recordModel = (RecordModel) getIntent().getSerializableExtra(KeyValue.KEY_RECORD_MODEL);
        type = getIntent().getIntExtra(KeyValue.KEY_RECORD_TYPE, -1);
    }

    @Override
    protected void initView() {
        totalCountEdit.addTextChangedListener(textWatcher);
        qualifiedEdit.addTextChangedListener(textWatcher);
        switch (type) {
            case RECORD_ADD:
                setActivityTitle("日常记");
                deleteBtn.setVisibility(GONE);
                timeLabelTxt.setText(R.string.text_record_current_label);
                currentTimeTxt.setText(TimeUtil.getCurrentTime());
                break;
            case RECORD_CHANGE:
                setActivityTitle("修改记录");
                float total = recordModel.getTotalCount();
                selectBtn.setVisibility(GONE);//隐藏选择工序的button
                float success = recordModel.getSuccessCount();
                float percent = FloatUtil.getFloat((success / total) * 100, 2);
                timeLabelTxt.setText(R.string.text_record_changed_time);
                totalCountEdit.setText(String.valueOf(recordModel.getTotalCount()));
                qualifiedEdit.setText(String.valueOf(recordModel.getSuccessCount()));
                percentTxt.setText(String.valueOf(percent) + "%");
                currentTimeTxt.setText(recordModel.getCreatedAt());
                break;
            default:
                logicError();
                break;
        }
        //第一次进来时候加载，判断记录的model
        if (recordModel == null) {
            workerDetailTxt.setVisibility(GONE);
            recordCurrentTxt.setText("请选择当前工序");
            workersNumTxt.setText(R.string.text_worker_empty);
        } else {
            workerDetailTxt.setVisibility(View.VISIBLE);
            recordCurrentTxt.setText(recordModel.getProcedureName());
            loadWorkers(LOAD_MODEL_RECORD);
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.btn_record_delete)
    void onDeleteRecord() {
        new DialogUtils(this)
                .setCancelable(false)
                .setTitleText("确认要删除这条记录吗？")
                .setNegativeButton(null)
                .setPositiveButton(new DialogUtils.OnButtonListener() {
                    @Override
                    public void onButtonClicked(DialogUtils dialogUtils) {
                        ManageHelper.deleteRecord(recordModel.getId(), RecordActivity.this);
                    }
                })
                .showAlertDialog();
    }

    /**
     * 点击提交
     */
    // TODO: 17/9/10 提交时候的判断(record还是procedure)
    @OnClick(R.id.btn_record_submit)
    void onSubmit() {
        switch (type) {
            case RECORD_ADD:
                if (procedureModel != null) {
                    if (TextUtils.isEmpty(totalCountEdit.getText().toString().trim())) {
                        ToastUtil.showToast(R.string.toast_record_total_count_empty);
                    } else if (TextUtils.isEmpty(qualifiedEdit.getText().toString().trim())) {
                        ToastUtil.showToast(R.string.toast_record_success_count_empty);
                    } else {
                        int total = Integer.valueOf(totalCountEdit.getText().toString().trim());
                        int successCount = Integer.valueOf(qualifiedEdit.getText().toString().trim());
                        if (total < successCount) {
                            ToastUtil.showToast(R.string.toast_record_num_error);
                        } else {
                            final RecordModel requestModel = new RecordModel();
                            requestModel.setProcedureId(procedureModel.getId());
                            requestModel.setLeaderId(User.getId());
                            requestModel.setWorkGroupId(User.getGroupId());
                            requestModel.setTotalCount(total);
                            requestModel.setSuccessCount(successCount);
                            //requestModel.setEndTime(null);
                            new DialogUtils(RecordActivity.this)
                                    .setTitleText("请确认此次记录")
                                    .setInputHintText("总产量:" + total + "   合格品" + successCount)
                                    .setCancelable(false)
                                    .setNegativeButton(null)
                                    .setPositiveButton(new DialogUtils.OnButtonListener() {
                                        @Override
                                        public void onButtonClicked(DialogUtils dialogUtils) {
                                            showProgressDialog(R.string.dialog_loading);
                                            ManageHelper.addRecord(requestModel, RecordActivity.this);
                                        }
                                    })
                                    .showAlertDialog();
                        }
                    }
                } else {
                    ToastUtil.showToast(R.string.toast_record_procedure_empty);
                }
                break;
            case RECORD_CHANGE:
                if (recordModel != null) {
                    if (TextUtils.isEmpty(totalCountEdit.getText().toString().trim())) {
                        ToastUtil.showToast(R.string.toast_record_total_count_empty);
                    } else if (TextUtils.isEmpty(qualifiedEdit.getText().toString().trim())) {
                        ToastUtil.showToast(R.string.toast_record_success_count_empty);
                    } else {
                        int total = Integer.valueOf(totalCountEdit.getText().toString().trim());
                        int successCount = Integer.valueOf(qualifiedEdit.getText().toString().trim());
                        if (total < successCount) {
                            ToastUtil.showToast(R.string.toast_record_num_error);
                        } else {
                            final RecordModel requestModel = new RecordModel();
                            requestModel.setProcedureId(recordModel.getProcedureId());
                            requestModel.setLeaderId(User.getId());
                            requestModel.setWorkGroupId(User.getGroupId());
                            requestModel.setTotalCount(total);
                            requestModel.setSuccessCount(successCount);
                            new DialogUtils(RecordActivity.this)
                                    .setTitleText("确认修改此工序的计件信息吗？")
                                    .setCancelable(false)
                                    .setNegativeButton(null)
                                    .setPositiveButton(new DialogUtils.OnButtonListener() {
                                        @Override
                                        public void onButtonClicked(DialogUtils dialogUtils) {
                                            showProgressDialog(R.string.dialog_loading);
                                            ManageHelper.changeRecord(requestModel, recordModel.getId(), RecordActivity.this);
                                        }
                                    })
                                    .showAlertDialog();
                        }
                    }

                }
                break;
            default:
                logicError();
                break;
        }
    }

    /**
     * 选择工序
     */
    @OnClick(R.id.btn_record_select)
    void onSelectProcedure() {
        startActivityForResult(new Intent(this, ProcedureListActivity.class), REQUEST_CODE);
    }

    /**
     * 查看工序的工人
     */
    @OnClick(R.id.txt_record_workers_detail)
    void onCheckWorkerDetail() {
        WorkersActivity.start(this, workerModels);
    }

    /**
     * 加载工人信息(通过记录或者工序来加载)
     */
    private void loadWorkers(int loadType) {
        showProgressDialog(R.string.dialog_loading);
        switch (loadType) {
            case LOAD_MODEL_PROCEDURE:
                if (procedureModel != null) {
                    ManageHelper.requestProcedureWorkers(String.valueOf(procedureModel.getId()),
                            String.valueOf(User.getGroupId()), this);
                }
                break;
            case LOAD_MODEL_RECORD:
                if (recordModel != null) {
                    ManageHelper.requestProcedureWorkers(String.valueOf(recordModel.getProcedureId()),
                            String.valueOf(User.getGroupId()), this);
                }
                break;
            default:
                logicError();
                break;
        }
    }

    /**
     * 工人信息加载成功
     */
    public void onWorkersLoadedSuccess(List<WorkerModel> modelList) {
        disMissProgressDialog();
        if (modelList.isEmpty()) {
            workersNumTxt.setText(R.string.text_worker_empty);
            workerDetailTxt.setVisibility(GONE);
        } else {
            workerModels = (ArrayList<WorkerModel>) modelList;
            workerDetailTxt.setVisibility(View.VISIBLE);
            workersNumTxt.setText(String.valueOf(modelList.size()));
        }

    }

    /**
     * 工人信息加载失败
     */
    public void onWorkersLoadedFailed() {
        disMissProgressDialog();
    }

    /**
     * 添加记录成功
     */
    public void onAddRecordSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_add_record_success);
        this.finish();
    }

    /**
     * 添加记录失败
     */
    public void onAddRecordFailed() {
        disMissProgressDialog();
    }

    /**
     * 修改记录成功
     */
    public void onChangeRecordSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_change_record_success);
        this.finish();
    }

    /**
     * 修改记录失败
     */
    public void onChangeRecordFailed() {
        disMissProgressDialog();
    }

    /**
     * 修改记录成功
     */
    public void onDeleteRecordSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_delete_record_success);
        this.finish();
    }

    /**
     * 修改记录失败
     */
    public void onDeleteRecordFailed() {
        disMissProgressDialog();
    }

    /**
     * 监听edit的动作,显示合格率
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!TextUtils.isEmpty(totalCountEdit.getText().toString())
                    && !TextUtils.isEmpty(qualifiedEdit.getText().toString())) {
                float total = Float.valueOf(totalCountEdit.getText().toString().trim());
                float success = Float.valueOf(qualifiedEdit.getText().toString().trim());
                int percent = (int) ((success / total) * 100);
                percentTxt.setText(String.valueOf(percent) + "%");
            }
        }
    };

    /**
     * 选择工序后回调到此处
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            procedureModel = (ProcedureModel) data.getSerializableExtra(KeyValue.KEY_PROCEDURE_MODEL);
            //返回值后回调
            if (procedureModel != null) {
                loadWorkers(LOAD_MODEL_PROCEDURE);
                recordCurrentTxt.setText(procedureModel.getName());
            } else {
                workerDetailTxt.setVisibility(GONE);
                workersNumTxt.setText(R.string.text_worker_empty);
            }
        }
    }

}

