package hrsoft.monitor_android.procedure.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.base.adapter.RecyclerScrollListener;
import hrsoft.monitor_android.base.fragment.BaseFragment;
import hrsoft.monitor_android.common.KeyValue;
import hrsoft.monitor_android.common.LogicErrorException;
import hrsoft.monitor_android.procedure.activity.ProcedureDetailActivity;
import hrsoft.monitor_android.procedure.activity.SchedulingActivity;
import hrsoft.monitor_android.procedure.adapter.ProcedureListAdapter;
import hrsoft.monitor_android.procedure.model.ProcedureHelper;
import hrsoft.monitor_android.procedure.model.ProcedureModel;
import hrsoft.monitor_android.util.ToastUtil;
import hrsoft.monitor_android.widget.SimpleRecyclerView;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureContentFragment extends BaseFragment {
    public static final int TYPE_ALL = 0;
    public static final int TYPE_PROCESSING = 1;
    public static final int TYPE_FINISHED = 2;
    public static final int TYPE_UNSTART = 3;

    @BindView(R.id.rec_procedure_list) SimpleRecyclerView procedureListRec;
    @BindView(R.id.refresh_procedure_list) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.empty) View emptyView;

    private int type = -1;
    private int page = 1;
    private final static int size = 8;//默认一页8个
    private boolean isFirstLoad = true; //是否从第一页开始加载
    private boolean isLastPage = false;//是不是最后一页
    private ProcedureListAdapter adapter;
    private RecyclerScrollListener scrollListener;

    /**
     * 返回Fragment的实例
     */
    public static ProcedureContentFragment createFragment(int type) {
        ProcedureContentFragment fragment = new ProcedureContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KeyValue.KEY_ORDER_FRAGMENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_procedure_content;
    }

    @Override
    protected void initVariable() {
        type = getArguments().getInt(KeyValue.KEY_ORDER_FRAGMENT_TYPE);
        adapter = new ProcedureListAdapter(getContext());
        scrollListener = new RecyclerScrollListener();
        initListVariable();
    }

    @Override
    protected void initView() {
        procedureListRec.setAdapter(adapter);
        procedureListRec.setEmptyView(emptyView);
        procedureListRec.setLayoutManager(new LinearLayoutManager(getContext()));
        procedureListRec.setOnScrollListener(scrollListener);
    }

    @Override
    protected void loadData() {
        getDataSource();
    }

    /**
     * 发起获取数据源的请求
     */
    private void getDataSource() {
        String p = String.valueOf(page);
        String s = String.valueOf(size);
        String status;
        switch (type) {
            case TYPE_ALL:
                status = ProcedureModel.TYPE_ALL;
                ProcedureHelper.requestProcedureList(p, s, status, this);
                break;
            case TYPE_PROCESSING:
                status = ProcedureModel.TYPE_PROCESSING;
                ProcedureHelper.requestProcedureList(p, s, status, this);
                break;
            case TYPE_UNSTART:
                status = ProcedureModel.TYPE_UNSTART;
                ProcedureHelper.requestProcedureList(p, s, status, this);
                break;
            case TYPE_FINISHED:
                status = ProcedureModel.TYPE_FINISHED;
                ProcedureHelper.requestProcedureList(p, s, status, this);
                break;
            default:
                try {
                    throw new LogicErrorException();
                } catch (LogicErrorException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    /**
     * 数据成功返回时
     */
    public void onDataLoadedSuccess(List<ProcedureModel> models, int pageSum) {
        page += 1;
        if (isFirstLoad) {
            isFirstLoad = false;
            adapter.setData(models);
        } else {
            adapter.addAll(models);
        }
        if (adapter.getListData().size() == pageSum) {
            isLastPage = true;
        }
        swipeRefresh.setRefreshing(false);
        adapter.setToRefresh(false);
    }

    /**
     * 数据返回失败时候
     */
    public void onDataLoadedFailed() {
        adapter.setToRefresh(false);
        swipeRefresh.setRefreshing(false);
    }

    /**
     * 工序完成确认成功
     */
    public void onConfirmProcedureSuccess() {
        disMissProgressDialog();
        ToastUtil.showToast("工序完成确认成功");
    }

    /**
     * 工序完成确认失败
     */
    public void onConfirmProcedureFailed() {
        disMissProgressDialog();
    }

    /**
     * 初始化列表变量
     */
    private void initListVariable() {
        scrollListener.setScrolledToLastListener(new RecyclerScrollListener.OnScrolledToLast() {
            @Override
            public void onScrolledToLast(int position) {
                if (!swipeRefresh.isRefreshing()) {
                    if (!isLastPage) {
                        adapter.setToRefresh(true);
                        getDataSource();
                    } else {
                        adapter.setToRefresh(false);
                    }
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isLastPage = false;
                isFirstLoad = true;
                getDataSource();
            }
        });
        adapter.setOnItemClickedListener(new RecyclerFooterAdapter.OnItemClicked<ProcedureModel>() {
            @Override
            public void onItemClicked(ProcedureModel procedureModel, RecyclerFooterAdapter.ViewHolder holder) {
                if (procedureModel.getStatus() == 0) {
                    ToastUtil.showToast("工序还未开始");
                } else {
                    ProcedureDetailActivity.start(getContext(), procedureModel);
                }
            }
        });
        adapter.setOnScheduleClickedListener(new ProcedureListAdapter.OnScheduleClicked() {
            @Override
            public void getProcedure(ProcedureModel model) {
                SchedulingActivity.start(getContext(), model);
            }
        });
        adapter.setOnConfirmClickedListener(new ProcedureListAdapter.OnScheduleClicked() {
            @Override
            public void getProcedure(ProcedureModel model) {
                showProgressDialog(R.string.dialog_loading);
                ProcedureHelper.confirmProcedure(ProcedureContentFragment.this);
            }
        });
    }
}
