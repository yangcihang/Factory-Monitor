package hrsoft.monitor_android.manage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.procedure.model.ProcedureModel;

/**
 * @author YangCihang
 * @since 17/9/10.
 * email yangcihang@hrsoft.net
 */

public class ManageProcedureListAdapter extends RecyclerViewAdapter<ProcedureModel> {
    public ManageProcedureListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder<ProcedureModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_rec_manage_procedure, parent, false));
    }

    class ItemHolder extends ViewHolder<ProcedureModel> {
        @BindView(R.id.txt_manage_procedure)
        TextView procedureTxt;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(ProcedureModel model, int position) {
            procedureTxt.setText(model.getName());
        }
    }
}
