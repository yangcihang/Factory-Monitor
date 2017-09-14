package hrsoft.monitor_android.network;

import android.text.TextUtils;

import java.net.ConnectException;

import hrsoft.monitor_android.App;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.network.convert.ResultException;
import hrsoft.monitor_android.util.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 统一的响应回调
 *
 * @author YangCihang
 * @since 17/8/20.
 * email yangcihang@hrsoft.net
 */

public class ResponseCallback<T> implements Callback<RspModel<T>> {
    private DataCallback<T> onDataCallback;

    public ResponseCallback(DataCallback<T> callback) {
        this.onDataCallback = callback;
    }

    //成功时回调
    @Override
    public void onResponse(Call<RspModel<T>> call, Response<RspModel<T>> response) {
        //前两句在Converter中已判断，在次做二次判断,所以以下的else都在failure中执行
        if (response.raw().code() <= HttpStateCode.REQUEST_SUCCESS) {
            onDataCallback.onDataSuccess(response.body().getData());

        }
    }


    //失败时回调(解析错误时的处理)
    @Override
    public void onFailure(Call<RspModel<T>> call, Throwable t) {
        if (t instanceof ResultException) {
            if (TextUtils.isEmpty(((ResultException) t).getMsg())) {
                GlobalAPIErrorHandler.handle(((ResultException) t).getCode());
            } else {
                if (((ResultException) t).getCode() == RspCode.ERROR_ACCOUNT_LOGIN) {
                    App.getInstance().exitAccount();
                }
                ToastUtil.showToast(((ResultException) t).getMsg(), ((ResultException) t).getCode());
            }
            onDataCallback.onDataFailed(((ResultException) t).getCode());
        } else if (t instanceof ConnectException) {
            // TODO: 17/8/21 网络连接错误,前者的toast做测试开发用
            ToastUtil.showToast(R.string.toast_net_work_error);
            onDataCallback.onDataFailed(-1);
        } else {
            ToastUtil.showToast(R.string.toast_unknow_error);
            onDataCallback.onDataFailed(-1);
        }
    }

    /**
     * 内部获取model的接口
     */
    public interface DataCallback<T> {
        //数据加载成功时回调
        void onDataSuccess(T data);

        //数据加载失败时回调
        void onDataFailed(int errorCode);
    }
}


