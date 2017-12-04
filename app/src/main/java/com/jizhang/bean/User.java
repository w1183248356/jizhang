package com.jizhang.bean;

/**
 * Entity mapped to table USER.
 */
public class User {

    private Long id;
    private String userName;
    private float oweMoenty;
    private Integer useNum;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String userName, float oweMoenty, Integer useNum) {
        this.id = id;
        this.userName = userName;
        this.oweMoenty = oweMoenty;
        this.useNum = useNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getOweMoenty() {
        return oweMoenty;
    }

    public void setOweMoenty(float oweMoenty) {
        this.oweMoenty = oweMoenty;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

}
