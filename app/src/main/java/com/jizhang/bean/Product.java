package com.jizhang.bean;

/**
 * Entity mapped to table PRODUCT.
 */
public class Product {

    private Long id;
    private String name;
    private int spec;
    private Integer useNum;
    private float oldPrice;
    private float oldTotalPrice;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(Long id, String name, int spec, Integer useNum, float oldPrice, float oldTotalPrice) {
        this.id = id;
        this.name = name;
        this.spec = spec;
        this.useNum = useNum;
        this.oldPrice = oldPrice;
        this.oldTotalPrice = oldTotalPrice;
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

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public float getOldTotalPrice() {
        return oldTotalPrice;
    }

    public void setOldTotalPrice(float oldTotalPrice) {
        this.oldTotalPrice = oldTotalPrice;
    }

}
