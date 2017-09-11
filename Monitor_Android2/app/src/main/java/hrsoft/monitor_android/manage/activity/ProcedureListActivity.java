package hrsoft.monitor_android.manage.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.manage.adapter.ManageProcedureListAdapter;
import hrsoft.monitor_android.manage.model.ManageHelper;
import hrsoft.monitor_android.procedure.model.ProcedureHelper;
import hrsoft.monitor_android.procedure.model.ProcedureModel;

/**
 * @author YangCihang
 * @since 17/9/10.
 * email yangcihang@hrsoft.net
 */

public class ProcedureListActivity extends ToolbarActivity {
    @BindView(R.id.rec_manage_procedure_list) RecyclerView procedureRec;
    private ManageProcedureListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_procedure_list;
    }

    @Override
    protected void initVariable() {
        adapter = new ManageProcedureListAdapter(this);
        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClicked<ProcedureModel>() {
            @Override
            public void onItemClicked(ProcedureModel model, RecyclerViewAdapter.ViewHolder holder) {
                Intent intent = new Intent();
                intent.putExtra(KeyValue.KEY_PROCEDURE_MODEL, model);
                ProcedureListActivity.this.setResult(RESULT_OK, intent);
                ProcedureListActivity.this.finish();
            }
        });
    }

    @Override
    protected void initView() {
        setActivityTitle("进行中的工序");
        procedureRec.setLayoutManager(new LinearLayoutManager(this));
        procedureRec.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        showProgressDialog(R.string.dialog_loading);
        ManageHelper.requestProcedureList(this);
    }


    public void onDataLoadedSuccess(List<ProcedureModel> procedureModels) {
        disMissProgressDialog();
        adapter.setData(procedureModels);
    }

    /**
     * 数据加载失败
     */
    public void onDataLoadedFailed() {
        disMissProgressDialog();
    }
}
