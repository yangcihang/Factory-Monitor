package hrsoft.monitor_android.procedure.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureListResponse implements Serializable {
    private List<ProcedureModel> data;
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public void setData(List<ProcedureModel> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public List<ProcedureModel> getData() {
        return data;
    }
}
