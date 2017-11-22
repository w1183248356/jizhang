package com.viewhigh.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 处理ScrollView嵌套ListView
 */
public class ScrollListView extends ListView {

	public ScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
