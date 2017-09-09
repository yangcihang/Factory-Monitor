package hrsoft.monitor_android.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class WorkerListResponse implements Serializable {
    private List<WorkerModel> record;
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public void setRecord(List<WorkerModel> record) {
        this.record = record;
    }

    public int getCount() {
        return count;
    }

    public List<WorkerModel> getRecord() {
        return record;
    }
}
