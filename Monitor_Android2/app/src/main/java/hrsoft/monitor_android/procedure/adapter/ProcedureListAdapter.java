package hrsoft.monitor_android.procedure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerFooterAdapter;
import hrsoft.monitor_android.procedure.model.ProcedureModel;
import hrsoft.monitor_android.util.TimeUtil;

import static android.view.View.GONE;

/**
 * @author YangCihang
 * @since 17/9/9.
 * email yangcihang@hrsoft.net
 */

public class ProcedureListAdapter extends RecyclerFooterAdapter<ProcedureModel> {
    private OnScheduleClicked onScheduleClickedListener;
    private OnScheduleClicked onConfirmClickedListener;

    public void setOnScheduleClickedListener(OnScheduleClicked onScheduleClickedListener) {
        this.onScheduleClickedListener = onScheduleClickedListener;
    }

    public void setOnConfirmClickedListener(OnScheduleClicked onConfirmClickedListener) {
        this.onConfirmClickedListener = onConfirmClickedListener;
    }

    public ProcedureListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DATA_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.item_rec_procedure, parent, false));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    class ItemHolder extends ViewHolder<ProcedureModel> {
        @BindView(R.id.txt_procedure_id) TextView idTxt;
        @BindView(R.id.txt_procedure_title) TextView procedureTitleTxt;
        @BindView(R.id.txt_procedure_center_title) TextView centerTitleTxt;
        @BindView(R.id.txt_procedure_total) TextView totalTxt;
        @BindView(R.id.txt_procedure_success_count) TextView successCountTxt;
        @BindView(R.id.progress_procedure) ProgressBar procedureProgress;
        @BindView(R.id.txt_procedure_create_time) TextView createTimeTxt;
        @BindView(R.id.txt_procedure_end_time) TextView endTimeTxt;
        @BindView(R.id.btn_scheduling) Button scheduleBtn;
        @BindView(R.id.btn_confirm_procedure) Button confirmBtn;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final ProcedureModel procedureModel, int position) {
            int percent = (int) (((float) procedureModel.getSuccessCount() / (float) procedureModel.getTotalCount()) * 100);
            String createTime =
                    TimeUtil.setStampToString(TimeUtil.setStringToStamp(procedureModel.getStartTime(),
                            TimeUtil.DATE_DEFAULT_FORMAT), TimeUtil.DATE_DEFAULT_FORMAT);
            String endTime =
                    TimeUtil.setStampToString(TimeUtil.setStringToStamp(procedureModel.getEndTime(),
                            TimeUtil.DATE_DEFAULT_FORMAT), TimeUtil.DATE_DEFAULT_FORMAT);

            idTxt.setText("工序号:" + String.valueOf(procedureModel.getId()));
            procedureTitleTxt.setText(procedureModel.getName());
            totalTxt.setText(String.valueOf(procedureModel.getTotalCount()));
            successCountTxt.setText(String.valueOf(procedureModel.getSuccessCount()));
            procedureProgress.setProgress(percent);
            createTimeTxt.setText(createTime);
            endTimeTxt.setText(endTime);
            if (percent >= 100 && procedureModel.getStatus() == Integer.valueOf(ProcedureModel.TYPE_PROCESSING)) {
                confirmBtn.setVisibility(View.VISIBLE);
            } else {
                confirmBtn.setVisibility(GONE);
            }
            switch (procedureModel.getStatus()) {
                case 1://进行中
                    centerTitleTxt.setText("进行中");
                    scheduleBtn.setVisibility(View.VISIBLE);
                    scheduleBtn.setText(R.string.text_change_schedule);
                    break;
                case 0://未开始
                    scheduleBtn.setVisibility(View.VISIBLE);
                    centerTitleTxt.setText("未开始");
                    scheduleBtn.setText(R.string.text_to_schedule);
                    break;
                case 2://已结束
                    centerTitleTxt.setText("已结束");
                    scheduleBtn.setVisibility(GONE);
                    break;
                default:
                    break;

            }
            scheduleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onScheduleClickedListener != null) {
                        onScheduleClickedListener.getProcedure(procedureModel);
                    }
                }
            });
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onConfirmClickedListener.getProcedure(procedureModel);
                }
            });
        }
    }

    public interface OnScheduleClicked {
        void getProcedure(ProcedureModel model);
    }
}
