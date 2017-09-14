package hrsoft.monitor_android.manage.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/11.
 * email yangcihang@hrsoft.net
 */

public class CreateGroupResponse implements Serializable {
    private int groupId;

    private int code;

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
