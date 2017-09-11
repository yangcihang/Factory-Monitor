package hrsoft.monitor_android.procedure.model;

import java.util.List;

import hrsoft.monitor_android.App;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.activity.WorkerActivity;
import hrsoft.monitor_android.mine.model.WorkerListResponse;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;
import hrsoft.monitor_android.procedure.activity.ProcedureAddWorksActivity;
import hrsoft.monitor_android.procedure.activity.ProcedureDetailActivity;
import hrsoft.monitor_android.procedure.activity.SchedulingActivity;
import hrsoft.monitor_android.procedure.fragment.ProcedureContentFragment;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureHelper {
    public static void requestProcedureList(String page, String size, String status, final ProcedureContentFragment callback) {
        NetWork.getService().requestProcedureList(String.valueOf(User.getGroupId()), page, size, status).enqueue(new ResponseCallback<ProcedureListResponse>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                ProcedureListResponse response = (ProcedureListResponse) data;
                callback.onDataLoadedSuccess(response.getData(), response.getCount());
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    public static void getProcedureNumList(String procedureId, final ProcedureDetailActivity callback) {
        NetWork.getService().requestProcedureNum(procedureId).enqueue(new ResponseCallback<ProcedureRecordListResponse>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                ProcedureRecordListResponse response = (ProcedureRecordListResponse) data;
                if (response != null) {
                    callback.onDataLoadedSuccess(response);
                }

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
    public static void requestProcedureWorkers(String procedureId, String groupId, final SchedulingActivity callback) {
        NetWork.getService().requestProcedureWorkersList(procedureId, groupId).enqueue(new ResponseCallback<List<WorkerModel>>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                List<WorkerModel> list = (List<WorkerModel>) data;
                callback.onDataLoadedSuccess(list);
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    /**
     * 请求工人列表
     */
    public static void requestWorkerList(String groupId, String page, String size, final ProcedureAddWorksActivity callback) {
        NetWork.getService().requestWorkerList(page, size, groupId).enqueue(new ResponseCallback<WorkerListResponse>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                WorkerListResponse response = (WorkerListResponse) data;
                callback.onDataLoadedSuccess(response.getRecord(), response.getCount());
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    /**
     * 工人排班
     */
    public static void scheduleWorkers(ProcedureScheduleRequest request, ProcedureAddWorksActivity callback) {
        NetWork.getService().scheduleWorkers(request).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {

            }

            @Override
            public void onDataFailed(int errorCode) {

            }
        }));
    }

    public static void deleteProcedureWorkers(DeleteProcedureWorkersRequest request, final int pos, final SchedulingActivity callback) {
        NetWork.getService().deleteScheduleWorkers(request).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onDeleteWorkerSuccess(pos);
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDeleteWorkerFailed();
            }
        }));
    }
}
