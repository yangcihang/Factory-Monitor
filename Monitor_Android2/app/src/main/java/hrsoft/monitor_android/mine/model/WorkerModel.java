package hrsoft.monitor_android.mine.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author YangCihang
 * @since 17/9/8.
 * email yangcihang@hrsoft.net
 */

public class WorkerModel implements Serializable {
    private Integer id; //工人id
    private String name;//工人姓名
    private String position;//职位
    private String no = ""; //工号
    private String mobile;//手机号
    private int groupId;//

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public Integer getId() {
        return id;
    }

    public WorkerModel(Integer id, String name, String position, String no, String mobile) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.no = no;
        this.mobile = mobile;
    }

    public WorkerModel() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(name) || TextUtils.isEmpty(position);
    }

    @Override
    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        WorkerModel model = (WorkerModel) obj;
        return name.equals(model.getName())
                && id.equals(model.getId())
                && position.equals(model.getPosition())
                && no.equals(model.getNo())
                && mobile.equals(model.getMobile());
    }
}
