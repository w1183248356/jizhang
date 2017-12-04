package com.jizhang.bean;

/**
 * Entity mapped to table JI_ZHANG_DTL.
 */
public class JiZhangDtl {

    private Long id;
    private String name;
    private String prdtName;
    private int prdtSpec;
    private float prdtPrice;
    private float prdtTotalPrice;
    private int prdtAmount;
    private int type;

    public JiZhangDtl() {
    }

    public JiZhangDtl(Long id) {
        this.id = id;
    }

    public JiZhangDtl(Long id, String name, String prdtName, int prdtSpec, float prdtPrice, float prdtTotalPrice, int prdtAmount, int type) {
        this.id = id;
        this.name = name;
        this.prdtName = prdtName;
        this.prdtSpec = prdtSpec;
        this.prdtPrice = prdtPrice;
        this.prdtTotalPrice = prdtTotalPrice;
        this.prdtAmount = prdtAmount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrdtName() {
        return prdtName;
    }

    public void setPrdtName(String prdtName) {
        this.prdtName = prdtName;
    }

    public int getPrdtSpec() {
        return prdtSpec;
    }

    public void setPrdtSpec(int prdtSpec) {
        this.prdtSpec = prdtSpec;
    }

    public float getPrdtPrice() {
        return prdtPrice;
    }

    public void setPrdtPrice(float prdtPrice) {
        this.prdtPrice = prdtPrice;
    }

    public float getPrdtTotalPrice() {
        return prdtTotalPrice;
    }

    public void setPrdtTotalPrice(float prdtTotalPrice) {
        this.prdtTotalPrice = prdtTotalPrice;
    }

    public int getPrdtAmount() {
        return prdtAmount;
    }

    public void setPrdtAmount(int prdtAmount) {
        this.prdtAmount = prdtAmount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
