package hrsoft.monitor_android.manage.model;

import java.util.List;

import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.manage.ManageFragment;
import hrsoft.monitor_android.manage.activity.ProcedureListActivity;
import hrsoft.monitor_android.manage.activity.RecordActivity;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;
import hrsoft.monitor_android.procedure.model.ProcedureListResponse;
import hrsoft.monitor_android.procedure.model.ProcedureModel;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class ManageHelper {
    /**
     * 请求记录的列表
     */
    public static void requestRecordList(String groupId, String page, String size, final ManageFragment callback) {
        NetWork.getService().requestManageList(groupId, page, size).enqueue(new ResponseCallback<RecordListResponse>(new ResponseCallback.DataCallback<RecordListResponse>() {
            @Override
            public void onDataSuccess(RecordListResponse data) {
                callback.onDataLoadedSuccess(data.getRecords(), data.getCount());
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    /**
     * 获取工序中员工信息
     */
    public static void requestProcedureWorkers(String procedureId, String groupId, final RecordActivity callback) {
        NetWork.getService().requestProcedureWorkersList(procedureId, groupId).enqueue(new ResponseCallback<>(new ResponseCallback.DataCallback<List<WorkerModel>>() {
            @Override
            public void onDataSuccess(List<WorkerModel> data) {
                callback.onWorkersLoadedSuccess(data);
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onWorkersLoadedFailed();
            }
        }));
    }

    /**
     * 获取工序列表
     */
    public static void requestProcedureList(final ProcedureListActivity callback) {
        String page = "1";
        String size = "10000";
        NetWork.getService().requestProcedureList(String.valueOf(User.getGroupId()), page, size, ProcedureModel.TYPE_PROCESSING)
                .enqueue(new ResponseCallback<>(new ResponseCallback.DataCallback<ProcedureListResponse>() {
                    @Override
                    public void onDataSuccess(ProcedureListResponse data) {
                        callback.onDataLoadedSuccess(data.getData());
                    }

                    @Override
                    public void onDataFailed(int errorCode) {
                        callback.onDataLoadedFailed();
                    }
                }));

    }

    /**
     * 添加记录
     */
    public static void addRecord(RecordModel requestModel, final RecordActivity callback) {
        NetWork.getService().addRecord(requestModel).enqueue(new ResponseCallback<>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onAddRecordSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onAddRecordFailed();
            }
        }));
    }

    /**
     * 修改记录
     */
    public static void changeRecord(RecordModel requestModel, int recordId, final RecordActivity callback) {
        NetWork.getService().changedRecord(requestModel, recordId).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onChangeRecordSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onChangeRecordFailed();
            }
        }));
    }

    /**
     * 删除工序计件
     */
    public static void deleteRecord(int recordId, final RecordActivity callback) {
        NetWork.getService().deleteRecord(recordId).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onDeleteRecordSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDeleteRecordFailed();
            }
        }));
    }
}
