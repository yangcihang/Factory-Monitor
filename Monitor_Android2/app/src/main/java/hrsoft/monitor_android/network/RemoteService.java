package hrsoft.monitor_android.network;


import java.util.List;

import hrsoft.monitor_android.account.model.GroupModel;
import hrsoft.monitor_android.account.model.LoginRequest;
import hrsoft.monitor_android.account.model.LoginResponse;
import hrsoft.monitor_android.manage.model.CreateGroupResponse;
import hrsoft.monitor_android.manage.model.RecordListResponse;
import hrsoft.monitor_android.manage.model.RecordModel;
import hrsoft.monitor_android.mine.model.MobileModel;
import hrsoft.monitor_android.mine.model.PasswordModel;
import hrsoft.monitor_android.mine.model.WorkerListResponse;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.procedure.model.DeleteProcedureWorkersRequest;
import hrsoft.monitor_android.procedure.model.ProcedureListResponse;
import hrsoft.monitor_android.procedure.model.ProcedureModel;
import hrsoft.monitor_android.procedure.model.ProcedureRecordListResponse;
import hrsoft.monitor_android.procedure.model.ProcedureScheduleRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RemoteService {
    /**
     * 登录
     */
    @POST("user/login")
    Call<RspModel<LoginResponse>> login(@Body LoginRequest request);

    /**
     * 获取班组信息
     */
    @GET("group/info")
    Call<RspModel<GroupModel>> getGroupMsg();

    /**
     * 创建班组信息
     */
    @POST("group/create")
    Call<CreateGroupResponse> createGroup(@Body GroupModel model);

    /**
     * 更新班组信息
     */
    @PUT("group/update")
    Call<RspModel> updateGroup(@Body GroupModel model);

    /**
     * 获取记录列表
     */
    @GET("group/log/{groupId}")
    Call<RspModel<RecordListResponse>> requestManageList(
            @Path("groupId") String groupId
            , @Query("page") String page
            , @Query("size") String size);

    /**
     * 获取工人列表
     */
    @GET("group/worker")
    Call<RspModel<WorkerListResponse>> requestWorkerList(
            @Query("page") String page
            , @Query("size") String size
            , @Query("groupId") String groupId);

    /**
     * 添加班组成员
     */
    @POST("group/worker")
    Call<RspModel> addWorker(@Body WorkerModel workerModel);

    /**
     * 更新成员信息
     */
    @PUT("group/worker/{workerId}")
    Call<RspModel> updateWorker(@Path("workerId") String workerId, @Body WorkerModel workerModel);

    /**
     * 删除工人信息
     */
    @DELETE("group/worker")
    Call<RspModel> deleteWorker(@Query("workerId") String workerId, @Query("groupId") String groupId);

    /**
     * 获取工序信息
     */
    @GET("procedure/list/{workGroupId}")
    Call<RspModel<ProcedureListResponse>> requestProcedureList(
            @Path("workGroupId") String groupId,
            @Query("page") String page,
            @Query("size") String size,
            @Query("status") String status);

    /**
     * 获取计件信息
     */
    @GET("procedure/{procedureId}/logs")
    Call<RspModel<ProcedureRecordListResponse>> requestProcedureNum(@Path("procedureId") String procedureId);

    /**
     * 获取工序中班组信息
     */
    @GET("group/worker/procedure")
    Call<RspModel<List<WorkerModel>>> requestProcedureWorkersList(
            @Query("procedureId") String procedureId
            , @Query("groupId") String groupId);

    @POST("group/worker/schedule")
    Call<RspModel> scheduleWorkers(@Body ProcedureScheduleRequest request);

    /**
     * 删除员工信息
     */
    @PUT("group/schedule")
    Call<RspModel> deleteScheduleWorkers(@Body DeleteProcedureWorkersRequest request);

    /**
     * 计件
     */
    @POST("group/log")
    Call<RspModel> addRecord(@Body RecordModel recordModel);

    /**
     * 修改计件
     */
    @PUT("group/log/{logId}")
    Call<RspModel> changedRecord(@Body RecordModel recordModel, @Path("logId") int logId);

    /**
     * 删除计件
     */
    @DELETE("group/log/{logId}")
    Call<RspModel> deleteRecord(@Path("logId") int logId);

    /**
     * 修改密码
     */
    @PUT("user/{id}")
    Call<RspModel> updateUserPassword(@Path("id") String id, @Body PasswordModel password);

    /**
     * 修改手机号
     */
    @PUT("user/{id}")
    Call<RspModel> updateUserInfo(@Path("id") String id, @Body MobileModel requestBody);

    @PUT("procedure/status/{procedureId}")
    Call<RspModel> confirmProcedure(@Path("procedureId") int procedureId, @Body ProcedureModel requestBody);
}
