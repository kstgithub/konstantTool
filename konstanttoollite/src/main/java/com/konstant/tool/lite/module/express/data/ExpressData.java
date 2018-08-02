package com.konstant.tool.lite.module.express.data;

/**
 * 描述:本地保存的物流信息的实体类
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:09
 * 备注:
 */

public class ExpressData {

    private String company;
    private String orderNo;
    private String remark;
    private String state;

    public ExpressData() {
    }

    public ExpressData(String company, String orderNo, String remark, String state) {
        this.company = company;
        this.orderNo = orderNo;
        this.remark = remark;
        this.state = state;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ExpressData && orderNo.equals(((ExpressData) obj).orderNo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"company\":\"")
                .append(company).append('\"');
        sb.append(",\"orderNo\":\"")
                .append(orderNo).append('\"');
        sb.append(",\"remark\":\"")
                .append(remark).append('\"');
        sb.append(",\"state\":\"")
                .append(state).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
