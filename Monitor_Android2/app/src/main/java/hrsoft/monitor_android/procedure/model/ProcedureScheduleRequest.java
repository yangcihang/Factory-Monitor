package hrsoft.monitor_android.procedure.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureScheduleRequest implements Serializable {
    ////       "procedureId": 1,
//    "workerIds": [
//            1,
//            2,
//            3,
//            4,
//            5
//            ]
    private int procedureId;
    private List<Integer> workerIds;

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public void setWorkerIds(List<Integer> workerIds) {
        this.workerIds = workerIds;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public List<Integer> getWorkerIds() {
        return workerIds;
    }
}
