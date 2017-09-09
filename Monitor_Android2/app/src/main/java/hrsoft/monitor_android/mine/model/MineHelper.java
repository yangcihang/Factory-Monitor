package hrsoft.monitor_android.mine.model;

import hrsoft.monitor_android.account.model.GroupModel;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.activity.AddWorkerActivity;
import hrsoft.monitor_android.mine.activity.PersonalActivity;
import hrsoft.monitor_android.mine.activity.WorkerActivity;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class MineHelper {
    /**
     * 创建班组信息
     */
    public static void createGroupInfo(final GroupModel groupModel, final PersonalActivity callback) {
        NetWork.getService().createGroup(groupModel).enqueue(new ResponseCallback<Integer>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                int id = (Integer) data;
                User.saveTeam(groupModel.getDescription(), groupModel.getName(), id);
                callback.onDataLoadedSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    /**
     * 更新班组信息
     */
    public static void updateGroupInfo(final GroupModel groupModel, final PersonalActivity callback) {
        NetWork.getService().updateGroup(groupModel).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                User.saveTeam(groupModel.getDescription(), groupModel.getName(), User.getGroupId());
                callback.onDataLoadedSuccess();
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
    public static void requestWorkerList(String groupId, String page, String size, final WorkerActivity callback) {
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
     * 添加成员信息
     */
    public static void addWorkerInfo(WorkerModel model, final AddWorkerActivity callback) {
        NetWork.getService().addWorker(model).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onDataLoadedSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    /**
     * 更新成员信息
     */
    public static void updateWorkerInfo(WorkerModel model, final AddWorkerActivity callback) {
        NetWork.getService().updateWorker(String.valueOf(model.getId()), model).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onDataLoadedSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }

    public static void deleteWorkerInfo(final WorkerModel model, final int position, final WorkerActivity callback) {
        NetWork.getService().deleteWorker(String.valueOf(model.getId()), String.valueOf(model.getGroupId()))
                .enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
                    @Override
                    public void onDataSuccess(Object data) {
                        callback.onDeleteWorkerSuccess(position);
                    }

                    @Override
                    public void onDataFailed(int errorCode) {
                        callback.onDeleteWorkerFailed();
                    }
                }));
    }
}
