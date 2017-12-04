package com.jizhang.bean;

/**
 * Entity mapped to table EXPEND_REASON.
 */
public class ExpendReason {

    private Long id;
    private String reason;
    private Integer useNum;

    public ExpendReason() {
    }

    public ExpendReason(Long id) {
        this.id = id;
    }

    public ExpendReason(Long id, String reason, Integer useNum) {
        this.id = id;
        this.reason = reason;
        this.useNum = useNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

}
