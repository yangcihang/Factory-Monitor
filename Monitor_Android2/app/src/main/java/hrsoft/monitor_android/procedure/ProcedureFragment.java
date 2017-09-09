package hrsoft.monitor_android.procedure;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.fragment.BaseFragment;
import hrsoft.monitor_android.procedure.adapter.ProcedureVpAdapter;

/**
 * @author YangCihang
 * @since 17/9/7.
 * email yangcihang@hrsoft.net
 */

public class ProcedureFragment extends BaseFragment {
    @BindView(R.id.tab_procedure_list) TabLayout procedureListTab;
    @BindView(R.id.vp_procedure_list) ViewPager procedureListVp;
    private ProcedureVpAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_procedure;
    }

    @Override
    protected void initVariable() {
        adapter = new ProcedureVpAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    protected void initView() {
        procedureListVp.setAdapter(adapter);
        procedureListTab.setupWithViewPager(procedureListVp);
    }

    @Override
    protected void loadData() {

    }

}
