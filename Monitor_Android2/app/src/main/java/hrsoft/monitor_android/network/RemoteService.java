package hrsoft.monitor_android.network;


import java.util.ArrayList;
import java.util.List;

import hrsoft.monitor_android.account.model.LoginRequest;
import hrsoft.monitor_android.account.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络接口请求类
 *
 * @author: Vzer.
 * @date: 2017/7/25. 15:54
 * @email: vzer@qq.com
 */

public interface RemoteService {
    @POST("user/login")
    Call<RspModel<LoginResponse>> login(@Body LoginRequest request);
}
