package hrsoft.monitor_android.account.model;

import hrsoft.monitor_android.account.LoginActivity;
import hrsoft.monitor_android.common.Config;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;
import hrsoft.monitor_android.util.ToastUtil;

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
        NetWork.getService().login(request).enqueue(new ResponseCallback<>(new ResponseCallback.DataCallback<LoginResponse>() {
            @Override
            public void onDataSuccess(LoginResponse data) {
                Account user = data.getUser();
                if (!user.getRole().equals(Account.LEADER)) {
                    onDataFailed(Config.FLAG_ROLE);
                } else {
                    User.login(user, data.getToken());
                    callback.onLoginSuccess();
                }
            }

            @Override
            public void onDataFailed(int errorCode) {
                if (errorCode == Config.FLAG_ROLE) {
                    ToastUtil.showToast("登录权限错误");
                }
                callback.onLoginFailed();
            }
        }));
    }

    public static void requestGroupMsg(final LoginActivity callback) {
        NetWork.getService().getGroupMsg().enqueue(new ResponseCallback<>(new ResponseCallback.DataCallback<GroupModel>() {
            @Override
            public void onDataSuccess(GroupModel data) {
                if (data != null) {
                    User.saveTeam(data.getDescription(), data.getName(), data.getId());
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
