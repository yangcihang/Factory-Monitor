package hrsoft.monitor_android.manage;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.base.adapter.RecyclerScrollListener;
import hrsoft.monitor_android.base.fragment.BaseFragment;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.manage.activity.RecordActivity;
import hrsoft.monitor_android.manage.adapter.RecordListAdapter;
import hrsoft.monitor_android.manage.model.RecordModel;
import hrsoft.monitor_android.manage.model.ManageHelper;
import hrsoft.monitor_android.widget.SimpleRecyclerView;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */

public class ManageFragment extends BaseFragment {
    @BindView(R.id.rec_manage) SimpleRecyclerView manageRec;
    @BindView(R.id.refresh_manage) SwipeRefreshLayout manageRefresh;
    @BindView(R.id.empty) View emptyView;
    @BindView(R.id.fab_add_record) FloatingActionButton addFab;
    private RecordListAdapter adapter;
    private RecyclerScrollListener scrollListener;
    private int page = 1;
    private final static int size = 8;//默认一页8个
    private boolean isFirstLoad = true; //是否从第一页开始加载
    private boolean isLastPage = false;//是不是最后一页

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    protected void initVariable() {
        adapter = new RecordListAdapter(getContext());
        scrollListener = new RecyclerScrollListener();
        initListVariable();
    }

    @Override
    protected void initView() {
        manageRec.setAdapter(adapter);
        manageRec.setLayoutManager(new LinearLayoutManager(getContext()));
        manageRec.setOnScrollListener(scrollListener);
        manageRec.setEmptyView(emptyView);
    }

    @Override
    protected void loadData() {
    }

    /**
     * 保证每次返回都能及时更新
     */
    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        isFirstLoad = true;
        isLastPage = false;
        getDataSource();

    }

    @OnClick(R.id.fab_add_record)
    void addRecord() {
        RecordActivity.start(getContext(), null, RecordActivity.RECORD_ADD);
    }

    /**
     * 发起获取数据源的请求
     */
    private void getDataSource() {
        String p = String.valueOf(page);
        String s = String.valueOf(size);
        ManageHelper.requestRecordList(String.valueOf(User.getGroupId()), p, s, this);
    }

    /**
     * 数据成功返回时
     */
    public void onDataLoadedSuccess(List<RecordModel> models, int pageSum) {
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
        manageRefresh.setRefreshing(false);
        adapter.setToRefresh(false);
        adapter.setToRefresh(false);
    }

    /**
     * 数据返回失败时候
     */
    public void onDataLoadedFailed() {
        adapter.setToRefresh(false);
        manageRefresh.setRefreshing(false);
    }

    /**
     * 初始化列表的变量
     */
    private void initListVariable() {
        adapter.setOnItemClickedListener(new RecyclerFooterAdapter.OnItemClicked<RecordModel>() {
            @Override
            public void onItemClicked(RecordModel model, RecyclerFooterAdapter.ViewHolder holder) {
                RecordActivity.start(getContext(), model, RecordActivity.RECORD_CHANGE);
            }
        });
        manageRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                if (!manageRefresh.isRefreshing()) {
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
