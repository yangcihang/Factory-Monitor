package hrsoft.monitor_android.procedure.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureRecordListResponse implements Serializable {
    private List<ProcedureRecordModel> logs;

    public void setLogs(List<ProcedureRecordModel> logs) {
        this.logs = logs;
    }

    public List<ProcedureRecordModel> getLogs() {
        return logs;
    }
}
