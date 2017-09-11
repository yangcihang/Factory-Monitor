package hrsoft.monitor_android.procedure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.mine.model.WorkerModel;

/**
 * 排班工人信息的列表
 *
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureWorkersListAdapter extends RecyclerViewAdapter<WorkerModel> {
    public ProcedureWorkersListAdapter(Context context) {
        super(context);
    }

    private DeleteWorker onDeletedWorkerListener;


    public void setOnDeletedWorkerListener(DeleteWorker onDeletedWorkerListener) {
        this.onDeletedWorkerListener = onDeletedWorkerListener;
    }

    @Override
    public ViewHolder<WorkerModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_rec_procedure_work_list, parent, false));
    }

    class ItemHolder extends ViewHolder<WorkerModel> {
        @BindView(R.id.item_worker_name)
        TextView nameTxt;
        @BindView(R.id.item_worker_delete)
        ImageView deleteImg;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final WorkerModel workerModel, final int position) {
            nameTxt.setText(workerModel.getName());
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeletedWorkerListener != null) {
                        onDeletedWorkerListener.onWorkerDelete(workerModel, position);
                    }
                }
            });
        }
    }

    public interface DeleteWorker {
        void onWorkerDelete(WorkerModel workerModel, int pos);
    }

}

