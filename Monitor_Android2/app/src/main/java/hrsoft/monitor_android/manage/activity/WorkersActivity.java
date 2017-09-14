package hrsoft.monitor_android.manage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.manage.adapter.WorkerListAdapter;
import hrsoft.monitor_android.mine.model.WorkerModel;

/**
 * 记录的工人列表
 *
 * @author YangCihang
 * @since 17/9/10.
 * email yangcihang@hrsoft.net
 */

public class WorkersActivity extends ToolbarActivity {
    @BindView(R.id.rec_record_procedure_workers) RecyclerView workersRec;
    private ArrayList<WorkerModel> workerModels;
    private WorkerListAdapter adapter;

    /**
     * 静态启动
     */
    public static void start(Context context, ArrayList<WorkerModel> models) {
        Intent intent = new Intent(context, WorkersActivity.class);
        intent.putExtra(KeyValue.KEY_WORKER_LIST, models);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_workers;
    }

    @Override
    protected void initVariable() {
        adapter = new WorkerListAdapter(this);
        //noinspection unchecked
        workerModels = (ArrayList<WorkerModel>) getIntent().getSerializableExtra(KeyValue.KEY_WORKER_LIST);
    }

    @Override
    protected void initView() {
        setActivityTitle(getString(R.string.title_toolbar_worker_detail));
        adapter.setData(workerModels);
        workersRec.setLayoutManager(new LinearLayoutManager(this));
        workersRec.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

}
