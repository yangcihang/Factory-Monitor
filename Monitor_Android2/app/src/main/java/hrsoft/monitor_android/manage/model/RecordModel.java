package hrsoft.monitor_android.manage.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class RecordModel implements Serializable {
    //     "procedure_id": 1,
//             "start_time": "2017-08-30 16:25:15",
//             "end_time": "2017-08-30 16:25:15",
//             "leader_id": 1,
//             "work_group_id": 1,
//             "total_count": 100,
//             "success_count": 10,
//             "status": 0,
//             "add_on": null,
//             "created_at": "2017-09-01 08:38:43",
//             "updated_at": "2017-09-01 08:38:43"
    private int procedureId;
    private String startTime;
    private String endTime;
    private int leaderId;
    private int workGroupId;
    private int totalCount;
    private int successCount;
    private int status;
    private String addOn;
    private String createdAt;
    private String procedureName;

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
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

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public int getWorkGroupId() {
        return workGroupId;
    }

    public void setWorkGroupId(int workGroupId) {
        this.workGroupId = workGroupId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddOn() {
        return addOn;
    }

    public void setAddOn(String addOn) {
        this.addOn = addOn;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String updatedAt;
}
