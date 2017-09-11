package hrsoft.monitor_android.manage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.mine.model.WorkerModel;

/**
 * @author YangCihang
 * @since 17/9/10.
 * email yangcihang@hrsoft.net
 */

public class WorkerListAdapter extends RecyclerViewAdapter<WorkerModel> {
    public WorkerListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder<WorkerModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_rec_manage_workers, parent, false));
    }

    class ItemHolder extends ViewHolder<WorkerModel> {
        @BindView(R.id.txt_manage_procedure_workers)
        TextView nameTxt;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(WorkerModel workerModel, int position) {
            nameTxt.setText(workerModel.getName());
        }
    }
}
