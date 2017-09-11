package hrsoft.monitor_android.procedure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.procedure.model.ProcedureRecordModel;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureDetailAdapter extends RecyclerViewAdapter<ProcedureRecordModel> {
    public ProcedureDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder<ProcedureRecordModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_rec_procedure_record, parent, false));
    }

    class ItemHolder extends ViewHolder<ProcedureRecordModel> {
        @BindView(R.id.txt_procedure_detail_time) TextView timeTxt;
        @BindView(R.id.txt_procedure_detail_total) TextView totalTxt;
        @BindView(R.id.txt_procedure_detail_success_percent) TextView successPercentTxt;
        @BindView(R.id.txt_procedure_detail_success_count) TextView successCountTxt;
        @BindView(R.id.txt_procedure_detail_no_success_count) TextView noSuccessCountTxt;
        @BindView(R.id.txt_procedure_detail_record_people) TextView recordPeopleTxt;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(ProcedureRecordModel procedureRecordModel, int position) {
            if (position == 0) {
                timeTxt.setText("时间");
                totalTxt.setText("总数");
                successPercentTxt.setText("合格率");
                successCountTxt.setText("合格品");
                noSuccessCountTxt.setText("次品数");
                recordPeopleTxt.setText("记录人");
            } else {
                int percent = (int) (((float) procedureRecordModel.getSuccessCount() /
                        (float) procedureRecordModel.getTotalCount()) * 100);
                int failedCount = procedureRecordModel.getTotalCount() - procedureRecordModel.getSuccessCount();
                timeTxt.setText(procedureRecordModel.getCreatedAt());
                totalTxt.setText(String.valueOf(procedureRecordModel.getTotalCount()));
                successPercentTxt.setText(String.valueOf(percent));
                successCountTxt.setText(String.valueOf(procedureRecordModel.getSuccessCount()));
                noSuccessCountTxt.setText(String.valueOf(failedCount));
                recordPeopleTxt.setText(procedureRecordModel.getLeaderName());
            }
        }
    }
}
