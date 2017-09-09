package hrsoft.monitor_android.procedure.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureModel implements Serializable {
    public static final String TYPE_ALL = "-1";
    public static final String TYPE_UNSTART = "0";
    public static final String TYPE_PROCESSING = "1";
    public static final String TYPE_FINISHED = "2";
    //        "id": 1,
//            "start_time": "2017-08-29 19:26:39",
//            "end_time": "2017-08-31 19:26:39",
//            "name": "测试工序",
//            "status": 1,
//            "order_id": 1,
//            "total_count": 100,
//            "updated_at": "2017-08-29 21:07:58",
//            "success_count": 0
    private int id;
    private String startTime;
    private String endTime;
    private String name;
    private int status;
    private int orderId;
    private int totalCount;
    private String updatedAt;
    private int successCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
