package hrsoft.monitor_android.manage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.manage.model.RecordModel;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class RecordListAdapter extends RecyclerFooterAdapter<RecordModel> {


    public RecordListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DATA_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.item_rec_manage_record, parent, false));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    class ItemHolder extends ViewHolder<RecordModel> {
        @BindView(R.id.txt_record_title) TextView titleTxt;
        @BindView(R.id.txt_record_count) TextView recordCountTxt;
        @BindView(R.id.txt_record_qualified) TextView recordQualifiedTxt;
        @BindView(R.id.txt_record_time) TextView timeTxt;
        @BindView(R.id.txt_record_id) TextView idTxt;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(RecordModel recordModel, int position) {
            titleTxt.setText(recordModel.getProcedureName());
            recordCountTxt.setText(String.valueOf(recordModel.getTotalCount()));
            recordQualifiedTxt.setText(String.valueOf(recordModel.getSuccessCount()));
            timeTxt.setText(recordModel.getUpdatedAt());
            idTxt.setText("工序号" + String.valueOf(String.valueOf(recordModel.getProcedureId())));
        }
    }
}
