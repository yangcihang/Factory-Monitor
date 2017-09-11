package hrsoft.monitor_android.mine.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/11.
 * email yangcihang@hrsoft.net
 */

public class MobileModel implements Serializable {
    private String mobile;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
}
