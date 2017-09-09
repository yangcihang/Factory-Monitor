package hrsoft.monitor_android.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.mine.model.WorkerModel;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class WorkListAdapter extends RecyclerFooterAdapter<WorkerModel> {
    private DeleteWorker onDeletedWorkerListener;

    public WorkListAdapter(Context context) {
        super(context);
    }

    public void setOnDeletedWorkerListener(DeleteWorker onDeletedWorkerListener) {
        this.onDeletedWorkerListener = onDeletedWorkerListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DATA_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.item_rec_worker, parent, false));
        }
        return super.onCreateViewHolder(parent, viewType);
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

