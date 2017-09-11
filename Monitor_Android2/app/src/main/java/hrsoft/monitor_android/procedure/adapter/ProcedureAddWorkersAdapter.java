package hrsoft.monitor_android.procedure.adapter;

import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import hrsoft.monitor_android.R;
import hrsoft.monitor_android.base.adapter.RecyclerViewAdapter;
import hrsoft.monitor_android.mine.model.WorkerModel;

/**
 * @author YangCihang
 * @since 17/9/10.
 * email yangcihang@hrsoft.ne
 */

public class ProcedureAddWorkersAdapter extends RecyclerViewAdapter<WorkerModel> {
    /**
     * 标志此工人是否被选中或者已被排班
     */
    private List<Boolean> flagList;
    private List<Integer> selectedModels;

    public ProcedureAddWorkersAdapter(Context context) {
        super(context);
    }

    /**
     * 返回已选择的工人
     */
    public List<Integer> getSelectedModels() {
        for (int i = 0; i < dataList.size(); i++) {
            if (flagList.get(i)) {
                selectedModels.add(dataList.get(i).getId());
            }
        }
        return selectedModels;
    }

    public void setData(Collection<WorkerModel> data, List<WorkerModel> schedulingWorkers) {
        //初始化变量，设置标志位,变量为目前没有排班的工人
        this.dataList.clear();
        selectedModels = new ArrayList<>();
        flagList = new ArrayList<>();
        List<WorkerModel> models = new ArrayList<>();
        models.addAll(data);
        dataList.addAll(data);
        for (int i = 0; i < models.size(); i++) {
            for (int j = 0; j < schedulingWorkers.size(); j++) {
                if (models.get(i).getId().equals(schedulingWorkers.get(j).getId())) {
                    dataList.remove(models.get(i));
                }
            }
        }
        for (WorkerModel model : models) {
            flagList.add(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder<WorkerModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_rec_procedure_add_workers, parent, false));
    }

    class ItemHolder extends ViewHolder<WorkerModel> {
        @BindView(R.id.item_worker_name)
        TextView nameTxt;
        @BindView(R.id.item_worker_check)
        CheckBox workerCheck;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final WorkerModel workerModel, final int position) {
            nameTxt.setText(workerModel.getName());
            if (flagList.get(position)) {
                workerCheck.setChecked(true);
            } else {
                workerCheck.setChecked(false);
            }

            workerCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flagList.set(position, !flagList.get(position));
                    refresh();
                }
            });
        }
    }
}

