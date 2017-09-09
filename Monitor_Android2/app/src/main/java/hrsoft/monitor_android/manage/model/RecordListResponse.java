package hrsoft.monitor_android.manage.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class RecordListResponse implements Serializable {
    private List<RecordModel> records;
    private int count;

    public List<RecordModel> getRecords() {
        return records;
    }

    public void setRecords(List<RecordModel> records) {
        this.records = records;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

