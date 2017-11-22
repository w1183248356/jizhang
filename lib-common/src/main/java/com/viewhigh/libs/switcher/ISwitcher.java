package com.viewhigh.libs.switcher;

import android.view.View;

/**
 * Created by huntero on 17-5-27.
 */

public interface ISwitcher<DATA> {

    void setVisible(boolean visible);

    void updateData(DATA data);

    void showLoading();

    void showNoNetwork();

    void restoreView();
}
