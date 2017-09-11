package hrsoft.monitor_android.mine.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/11.
 * email yangcihang@hrsoft.net
 */


public class PasswordModel implements Serializable {
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
