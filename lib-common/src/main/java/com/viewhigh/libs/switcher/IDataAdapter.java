package com.viewhigh.libs.switcher;

/**
 * Created by huntero on 17-5-31.
 */

public interface IDataAdapter<DATA> {
    void setData(DATA data);

    boolean isEmpty();
}
