package hrsoft.monitor_android.manage;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.base.adapter.RecyclerScrollListener;
import hrsoft.monitor_android.base.fragment.BaseFragment;
import hrsoft.monitor_android.common.User;
import hrsoft.monitor_android.manage.adapter.RecordListAdapter;
import hrsoft.monitor_android.manage.model.RecordModel;
import hrsoft.monitor_android.manage.model.MangeHelper;
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
        getDataSource();
    }

    /**
     * 发起获取数据源的请求
     */
    private void getDataSource() {
        String p = String.valueOf(page);
        String s = String.valueOf(size);
        MangeHelper.requestManageList(String.valueOf(User.getGroupId()), p, s, this);
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
            public void onItemClicked(RecordModel homeChartModel, RecyclerFooterAdapter.ViewHolder holder) {
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
                if (!isLastPage) {
                    adapter.setToRefresh(true);
                    getDataSource();
                } else {
                    adapter.setToRefresh(false);
                }
            }
        });
    }
}
