package hrsoft.monitor_android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.util.CacheUtil;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */
public class App extends Application {
    private static App instance;//实例对象
    private static List<Activity> activityList = new ArrayList<>();
    private static CacheUtil cacheUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        User.load(this);
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        //RichText.initCacheDir(this);
    }

    /**
     * 缓存初始化
     */
    public CacheUtil getCacheUtil() {
        if (cacheUtil == null) {
            cacheUtil = CacheUtil.get(instance.getFilesDir());
        }
        return cacheUtil;
    }

    /**
     * 外部获取实例对象
     *
     * @return NUEDCApplication
     */
    public static App getInstance() {
        return instance;
    }

    private static android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new android.app.Application.ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            activityList.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityList.remove(activity);
        }
    };

    /**
     * 移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 清除所有Activity
     */
    public void removeAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null && !activity.isFinishing())
                activity.finish();
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        removeAllActivity();
        // TODO: 17/8/25 退出的后续操作
    }

    /**
     * 退出账户
     */
    public void exitAccount() {
        // TODO: 17/9/7 退出登录，清除班组信息
        User.exitLogin(this);
        removeAllActivity();
    }
}
