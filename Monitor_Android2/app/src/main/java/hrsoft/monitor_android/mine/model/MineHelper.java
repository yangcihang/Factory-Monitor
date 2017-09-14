package hrsoft.monitor_android.mine.model;

import hrsoft.monitor_android.account.model.GroupModel;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.manage.model.CreateGroupResponse;
import hrsoft.monitor_android.mine.MineFragment;
import hrsoft.monitor_android.mine.activity.AddWorkerActivity;
import hrsoft.monitor_android.mine.activity.PersonalActivity;
import hrsoft.monitor_android.mine.activity.WorkerActivity;
import hrsoft.monitor_android.network.GlobalAPIErrorHandler;
import hrsoft.monitor_android.network.HttpStateCode;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;
import hrsoft.monitor_android.network.RspCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        NetWork.getService().createGroup(groupModel).enqueue(new Callback<CreateGroupResponse>() {
            @Override
            public void onResponse(Call<CreateGroupResponse> call, Response<CreateGroupResponse> response) {
                if (response.code() <= HttpStateCode.REQUEST_SUCCESS) {
                    if (response.body().getCode() == RspCode.SUCCEED) {
                        int id = response.body().getGroupId();
                        User.saveTeam(groupModel.getDescription(), groupModel.getName(), id);
                        callback.onDataLoadedSuccess();
                    } else {
                        GlobalAPIErrorHandler.handle(response.body().getCode());
                        callback.onDataLoadedFailed();
                    }
                } else {
                    GlobalAPIErrorHandler.handle(response.code());
                    callback.onDataLoadedFailed();
                }
            }

            @Override
            public void onFailure(Call<CreateGroupResponse> call, Throwable t) {
                callback.onDataLoadedFailed();
            }
        });
    }

    /**
     * 更新班组信息
     */
    public static void updateGroupInfo(final GroupModel groupModel, final PersonalActivity callback) {
        NetWork.getService().updateGroup(groupModel).enqueue(new ResponseCallback<>(new ResponseCallback.DataCallback() {
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
        NetWork.getService().requestWorkerList(page, size, groupId).enqueue(new ResponseCallback<WorkerListResponse>(new ResponseCallback.DataCallback<WorkerListResponse>() {
            @Override
            public void onDataSuccess(WorkerListResponse data) {
                callback.onDataLoadedSuccess(data.getRecord(), data.getCount());
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

    public static void updatePassword(String password, final MineFragment callback) {
        PasswordModel model = new PasswordModel();
        model.setPassword(password);
        NetWork.getService().updateUserPassword(String.valueOf(User.getId()), model).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onUpdatePasswordSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                //失败时基类处理
                callback.onUpdatePasswordFailed();
            }
        }));
    }

    /**
     * 更新手机号
     */
    public static void updateMobile(String mobile, final MineFragment callback) {
        MobileModel requestBody = new MobileModel();
        requestBody.setMobile(mobile);
        NetWork.getService().updateUserInfo(String.valueOf(User.getId()), requestBody).enqueue(new ResponseCallback(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                callback.onUpadateMobileSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onUpdateMobileFailed();
            }
        }));
    }

}
