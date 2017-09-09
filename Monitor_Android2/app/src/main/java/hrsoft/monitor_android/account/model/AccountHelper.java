package hrsoft.monitor_android.account.model;

import hrsoft.monitor_android.account.LoginActivity;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */

public class AccountHelper {
    /**
     * 登录
     */
    public static void login(final LoginRequest request, final LoginActivity callback) {
        NetWork.getService().login(request).enqueue(new ResponseCallback<LoginResponse>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                LoginResponse response = (LoginResponse) data;
                Account user = response.getUser();
                User.login(user, response.getToken());
                callback.onLoginSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onLoginFailed();
            }
        }));
    }

    public static void requestGroupMsg(final LoginActivity callback) {
        NetWork.getService().getGroupMsg().enqueue(new ResponseCallback<GroupModel>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                GroupModel groupModel = (GroupModel) data;
                if (data != null) {
                    User.saveTeam(groupModel.getDescription(), groupModel.getName(), groupModel.getId());
                }
                callback.onLoadedGroupSuccess();
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onLoadedGroupFailed();
            }
        }));
    }
}
