package com.zepan.caifuyun.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
//com.zepan.yundong8.view.MyListView
/**
 * 自定义gridview，解决ListView中嵌套ListView显示不正常的问题
 * @author Administrator
 *
 */
public class MyListView extends ListView{  
    public MyListView(Context context, AttributeSet attrs) {   
          super(context, attrs);   
      }   
     
      public MyListView(Context context) {   
          super(context);   
      }   
     
      public MyListView(Context context, AttributeSet attrs, int defStyle) {   
          super(context, attrs, defStyle);   
      }   
     
      @Override   
      public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
     
          int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,   
                  MeasureSpec.AT_MOST);   
          super.onMeasure(widthMeasureSpec, expandSpec);   
      }   
}  