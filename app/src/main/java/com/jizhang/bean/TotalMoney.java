package com.jizhang.bean;

/**
 * Entity mapped to table TOTAL_MONEY.
 */
public class TotalMoney {

    private Long id;
    private float moneyUncollected;
    private float moneyExpenditure;
    private float moneyReceive;
    private float moneyGain;

    public TotalMoney() {
    }

    public TotalMoney(Long id) {
        this.id = id;
    }

    public TotalMoney(Long id, float moneyUncollected, float moneyExpenditure, float moneyReceive, float moneyGain) {
        this.id = id;
        this.moneyUncollected = moneyUncollected;
        this.moneyExpenditure = moneyExpenditure;
        this.moneyReceive = moneyReceive;
        this.moneyGain = moneyGain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMoneyUncollected() {
        return moneyUncollected;
    }

    public void setMoneyUncollected(float moneyUncollected) {
        this.moneyUncollected = moneyUncollected;
    }

    public float getMoneyExpenditure() {
        return moneyExpenditure;
    }

    public void setMoneyExpenditure(float moneyExpenditure) {
        this.moneyExpenditure = moneyExpenditure;
    }

    public float getMoneyReceive() {
        return moneyReceive;
    }

    public void setMoneyReceive(float moneyReceive) {
        this.moneyReceive = moneyReceive;
    }

    public float getMoneyGain() {
        return moneyGain;
    }

    public void setMoneyGain(float moneyGain) {
        this.moneyGain = moneyGain;
    }

}
