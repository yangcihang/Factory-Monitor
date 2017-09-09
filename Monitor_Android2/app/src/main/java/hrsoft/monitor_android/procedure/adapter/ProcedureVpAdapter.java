package hrsoft.monitor_android.procedure.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import hrsoft.monitor_android.App;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.procedure.fragment.ProcedureContentFragment;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureVpAdapter extends FragmentPagerAdapter {
    private final int PAGE_SUM = 4;
    private ProcedureContentFragment processingProcedureContentFragment;
    private ProcedureContentFragment finishedProcedureContentFragment;
    private ProcedureContentFragment unstartProcedureContentFragment;
    private ProcedureContentFragment allProcedureContentFragment;

    public ProcedureVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ProcedureContentFragment.TYPE_ALL:
                if (allProcedureContentFragment == null) {
                    allProcedureContentFragment = ProcedureContentFragment
                            .createFragment(ProcedureContentFragment.TYPE_ALL);
                }
                fragment = allProcedureContentFragment;
                break;
            case ProcedureContentFragment.TYPE_PROCESSING:
                if (processingProcedureContentFragment == null) {
                    processingProcedureContentFragment = ProcedureContentFragment.
                            createFragment(ProcedureContentFragment.TYPE_PROCESSING);
                }
                fragment = processingProcedureContentFragment;
                break;
            case ProcedureContentFragment.TYPE_FINISHED:
                if (finishedProcedureContentFragment == null) {
                    finishedProcedureContentFragment = ProcedureContentFragment.
                            createFragment(ProcedureContentFragment.TYPE_FINISHED);
                }
                fragment = finishedProcedureContentFragment;
                break;
            case ProcedureContentFragment.TYPE_UNSTART:
                if (unstartProcedureContentFragment == null) {
                    unstartProcedureContentFragment = ProcedureContentFragment.
                            createFragment(ProcedureContentFragment.TYPE_UNSTART);
                }
                fragment = unstartProcedureContentFragment;
                break;
            default:
                break;
        }
        return fragment;
    }

    /**
     * 不做销毁处理
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public int getCount() {
        return PAGE_SUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case ProcedureContentFragment.TYPE_ALL:
                title = App.getInstance().getString(R.string.title_procedure_all);
                break;
            case ProcedureContentFragment.TYPE_PROCESSING:
                title = App.getInstance().getString(R.string.title_procedure_processing);
                break;
            case ProcedureContentFragment.TYPE_FINISHED:
                title = App.getInstance().getString(R.string.title_procedure_finished);
                break;
            case ProcedureContentFragment.TYPE_UNSTART:
                title = App.getInstance().getString(R.string.title_procedure_unstart);
                break;
            default:
                break;
        }
        return title;
    }
}
