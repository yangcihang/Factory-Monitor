package hrsoft.monitor_android.account.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */


public class LoginResponse implements Serializable {
    private String token;
    private Account user;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Account getUser() {
        return user;
    }
}