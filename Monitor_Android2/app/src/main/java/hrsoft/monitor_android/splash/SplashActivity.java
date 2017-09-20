package hrsoft.monitor_android.splash;

import android.content.Intent;

import hrsoft.monitor_android.R;
import hrsoft.monitor_android.account.LoginActivity;
import hrsoft.monitor_android.base.activity.NoBarActivity;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.main.MainActivity;
import hrsoft.monitor_android.util.Utility;


/**
 * @author YangCihang
 * @since 17/8/31.
 * email yangcihang@hrsoft.net
 */

public class SplashActivity extends NoBarActivity {
    private final static long SPLASH_TIME = 2000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (User.isLogin()) {
            Utility.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, SPLASH_TIME);
        } else {
            Utility.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, SPLASH_TIME);
        }
    }
}
