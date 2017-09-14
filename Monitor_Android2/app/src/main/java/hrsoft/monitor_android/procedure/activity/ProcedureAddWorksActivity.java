package hrsoft.monitor_android.procedure.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.App;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.base.adapter.RecyclerScrollListener;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.procedure.adapter.ProcedureAddWorkersAdapter;
import hrsoft.monitor_android.procedure.model.ProcedureHelper;
import hrsoft.monitor_android.procedure.model.ProcedureModel;
import hrsoft.monitor_android.procedure.model.ProcedureScheduleRequest;
import hrsoft.monitor_android.util.CacheUtil;
import hrsoft.monitor_android.widget.SimpleRecyclerView;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureAddWorksActivity extends ToolbarActivity {
    @BindView(R.id.rec_procedure_add_worker)
    SimpleRecyclerView workersRec;
    @BindView(R.id.refresh_worker)
    SwipeRefreshLayout workerRefresh;
    @BindView(R.id.empty)
    View emptyView;
    private ProcedureAddWorkersAdapter adapter;
    private RecyclerScrollListener scrollListener;
    private ArrayList<WorkerModel> schedulingWorkers;
    private int page = 1;
    private int procedureId;
    // TODO: 17/9/10 默认加载全部成员，后期有时间来优化
    private final static int size = -1;//默认获取全部班组成员(方便判断)
    private boolean isFirstLoad = true; //是否从第一页开始加载
    private boolean isLastPage = false;//是不是最后一页

    /**
     * 静态启动
     */
    public static void start(Context context, ArrayList<WorkerModel> models, int procedureId) {
        Intent intent = new Intent(context, ProcedureAddWorksActivity.class);
        intent.putExtra(KeyValue.KEY_PROCEDURE_ID, procedureId);
        intent.putExtra(KeyValue.KEY_WORKER_LIST, models);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_procedure_add_workers;
    }

    @Override
    protected void initVariable() {
        procedureId = getIntent().getIntExtra(KeyValue.KEY_PROCEDURE_ID, -1);
        schedulingWorkers = (ArrayList<WorkerModel>) getIntent().getSerializableExtra(KeyValue.KEY_WORKER_LIST);
        adapter = new ProcedureAddWorkersAdapter(this);
        scrollListener = new RecyclerScrollListener();
        initListVariable();
    }


    @Override
    protected void initView() {
        setActivityTitle("选择工人");
        workersRec.setAdapter(adapter);
        workersRec.setLayoutManager(new LinearLayoutManager(this));
        workersRec.setOnScrollListener(scrollListener);
        workersRec.setEmptyView(emptyView);
    }


    @Override
    protected void loadData() {
        showProgressDialog(R.string.dialog_loading);
        getDataSource();
    }

    @OnClick(R.id.btn_procedure_add_worker)
    void onSubmitWorkers() {
        ProcedureScheduleRequest request = new ProcedureScheduleRequest();
        if (!adapter.getSelectedModels().isEmpty()) {
            request.setWorkerIds(adapter.getSelectedModels());
            if (procedureId != -1) {
                request.setProcedureId(procedureId);
                ProcedureHelper.scheduleWorkers(request, this);
                this.finish();
            } else {
                logicError();
            }
        } else {
            this.finish();
        }
    }

    /**
     * 加载数据源
     */
    public void getDataSource() {
        String p = String.valueOf(page);
        String s = String.valueOf(size);
        ProcedureHelper.requestWorkerList(String.valueOf(User.getGroupId()), p, s, this);
    }

    /**
     * 数据加载成功时回调
     */
    public void onDataLoadedSuccess(List<WorkerModel> models, int pageSum) {
        disMissProgressDialog();
        adapter.setData(models, schedulingWorkers);
//        page += 1;
//        if (isFirstLoad) {
//            isFirstLoad = false;
//            adapter.setData(models);
//        } else {
//            adapter.addAll(models);
//        }
//        if (adapter.getListData().size() == pageSum) {
//            isLastPage = true;
//        }
//        workerRefresh.setRefreshing(false);
    }

    /**
     * 数据加载失败时回调
     */
    public void onDataLoadedFailed() {
        disMissProgressDialog();
    }

    /**
     * 初始化列表变量
     */
    private void initListVariable() {
        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClicked<WorkerModel>() {
            @Override
            public void onItemClicked(WorkerModel workerModel, RecyclerViewAdapter.ViewHolder holder) {

            }
        });
//        scrollListener.setScrolledToLastListener(new RecyclerScrollListener.OnScrolledToLast() {
//            @Override
//            public void onScrolledToLast(int position) {
//                if (!workerRefresh.isRefreshing()) {
//                    if (!isLastPage) {
//                        getDataSource();
//                    }
//                }
//            }
//        });
        workerRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                page = 1;
//                isLastPage = false;
//                isFirstLoad = true;
                getDataSource();
            }
        });
    }

}
