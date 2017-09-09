package hrsoft.monitor_android.manage.model;

import hrsoft.monitor_android.manage.ManageFragment;
import hrsoft.monitor_android.network.NetWork;
import hrsoft.monitor_android.network.ResponseCallback;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class MangeHelper {
    public static void requestManageList(String groupId, String page, String size, final ManageFragment callback) {
        NetWork.getService().requestManageList(groupId, page, size).enqueue(new ResponseCallback<RecordListResponse>(new ResponseCallback.DataCallback() {
            @Override
            public void onDataSuccess(Object data) {
                RecordListResponse response = (RecordListResponse) data;
                callback.onDataLoadedSuccess(response.getRecords(), response.getCount());
            }

            @Override
            public void onDataFailed(int errorCode) {
                callback.onDataLoadedFailed();
            }
        }));
    }
}
