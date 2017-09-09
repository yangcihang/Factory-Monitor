package hrsoft.monitor_android.procedure.model;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class DeleteProcedureWorkersRequest implements Serializable {
    private int procedureId;
    private Integer workerId;

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public int getWorkerId() {
        return workerId;
    }
}
