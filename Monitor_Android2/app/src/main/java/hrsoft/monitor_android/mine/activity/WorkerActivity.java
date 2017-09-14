package hrsoft.monitor_android.mine.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.activity.ToolbarActivity;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.base.adapter.RecyclerScrollListener;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.mine.adapter.WorkListAdapter;
import hrsoft.monitor_android.mine.model.MineHelper;
import hrsoft.monitor_android.mine.model.WorkerModel;
import hrsoft.monitor_android.util.DialogUtils;
import hrsoft.monitor_android.util.ToastUtil;
import hrsoft.monitor_android.util.Utility;
import hrsoft.monitor_android.widget.SimpleRecyclerView;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class WorkerActivity extends ToolbarActivity {

    @BindView(R.id.rec_mine_worker)
    SimpleRecyclerView workerRec;
    @BindView(R.id.fab_add_worker)
    FloatingActionButton addFab;
    @BindView(R.id.refresh_worker)
    SwipeRefreshLayout workerRefresh;
    @BindView(R.id.empty)
    View emptyView;

    private WorkListAdapter adapter;
    private RecyclerScrollListener scrollListener;
    private int page = 1;
    private final static int size = 15;//默认一页15个
    private boolean isFirstLoad = true; //是否从第一页开始加载
    private boolean isLastPage = false;//是不是最后一页

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker;
    }

    @Override
    protected void initVariable() {
        adapter = new WorkListAdapter(this);
        scrollListener = new RecyclerScrollListener();
        initListVariable();
    }

    @Override
    protected void initView() {
        setActivityTitle("员工信息");
        workerRec.setAdapter(adapter);
        workerRec.setLayoutManager(new LinearLayoutManager(this));
        workerRec.setOnScrollListener(scrollListener);
        workerRec.setEmptyView(emptyView);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.fab_add_worker)
    void addWorker() {
        AddWorkerActivity.start(this, AddWorkerActivity.TYPE_ADD, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressDialog(R.string.dialog_loading);
        page = 1;
        isFirstLoad = true;
        getDataSource();
    }

    /**
     * 发起获取数据源的请求
     */
    private void getDataSource() {
        String p = String.valueOf(page);
        String s = String.valueOf(size);
        MineHelper.requestWorkerList(String.valueOf(User.getGroupId()), p, s, this);
    }

    /**
     * 数据成功返回时
     */
    public void onDataLoadedSuccess(List<WorkerModel> models, int pageSum) {
        disMissProgressDialog();
        page += 1;
        if (isFirstLoad) {
            isFirstLoad = false;
            adapter.setData(models);
        } else {
            adapter.addAll(models);
        }
        if (adapter.getListData().size() == pageSum) {
            isLastPage = true;
            adapter.setToRefresh(false);
        } else {
            adapter.setToRefresh(true);
        }
        workerRefresh.setRefreshing(false);
    }

    /**
     * 数据返回失败时候
     */
    public void onDataLoadedFailed() {
        disMissProgressDialog();
        adapter.setToRefresh(false);
        workerRefresh.setRefreshing(false);
    }

    /**
     * 删除工人成功后
     */
    public void onDeleteWorkerSuccess(int position) {
        disMissProgressDialog();
        ToastUtil.showToast(R.string.toast_delete_success);
        adapter.remove(position);
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                adapter.setToRefresh(false);
            }
        }, 1000);
    }

    /**
     * 删除工人失败后
     */
    public void onDeleteWorkerFailed() {
        disMissProgressDialog();
    }

    /**
     * 初始化列表的变量
     */
    private void initListVariable() {
        adapter.setOnItemClickedListener(new RecyclerFooterAdapter.OnItemClicked<WorkerModel>() {
            @Override
            public void onItemClicked(WorkerModel workerModel, RecyclerFooterAdapter.ViewHolder holder) {
                AddWorkerActivity.start(WorkerActivity.this, AddWorkerActivity.TYPE_CHANGE, workerModel);
            }
        });
        adapter.setOnDeletedWorkerListener(new WorkListAdapter.DeleteWorker() {
            @Override
            public void onWorkerDelete(final WorkerModel workerModel, final int pos) {
                new DialogUtils(WorkerActivity.this)
                        .setCancelable(false)
                        .setTitleText("确认要删除" + workerModel.getName() + "吗？")
                        .setPositiveButton(new DialogUtils.OnButtonListener() {
                            @Override
                            public void onButtonClicked(DialogUtils dialogUtils) {
                                WorkerActivity.this.showProgressDialog(R.string.dialog_loading);
                                MineHelper.deleteWorkerInfo(workerModel, pos, WorkerActivity.this);
                            }
                        })
                        .setNegativeButton(null)
                        .showAlertDialog();
            }
        });
        workerRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isLastPage = false;
                isFirstLoad = true;
                getDataSource();
            }
        });
        scrollListener.setScrolledToLastListener(new RecyclerScrollListener.OnScrolledToLast() {
            @Override
            public void onScrolledToLast(int position) {
                if (!workerRefresh.isRefreshing()) {
                    if (!isLastPage) {
                        adapter.setToRefresh(true);
                        getDataSource();
                    } else {
                        adapter.setToRefresh(false);
                    }
                }
            }
        });
    }
}
