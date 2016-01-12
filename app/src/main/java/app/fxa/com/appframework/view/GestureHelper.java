package app.fxa.com.appframework.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
   
public class GestureHelper implements OnGestureListener {  
    public GestureDetector gesture_detector;  
    private int screen_width;  
    private int screen_height;  
    private OnFlingListener listener_onfling;  
    public static abstract class OnFlingListener {  
        public abstract void OnFlingLeft();  
        public abstract void OnFlingRight();  
        public abstract void OnFlingUp(); 
        public abstract void OnFlinDown(); 
    }  
       
    public GestureHelper(Context context) {  
        DisplayMetrics dm = context.getResources().getDisplayMetrics();  
        screen_width = dm.widthPixels;  
        screen_height = dm.heightPixels;  
        gesture_detector = new GestureDetector(context, this);  
    }  
       
    public void setOnFlingListener(OnFlingListener listener) {  
        listener_onfling = listener;  
    }  
       
    public boolean onTouchEvent(MotionEvent event) {  
        return gesture_detector.onTouchEvent(event);  
    }  
       
     
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {  
    	   float x = e2.getX() - e1.getX();
           float y = e2.getY() - e1.getY();
           //限制必须得划过屏幕的1/N才能算划过
           float x_limit = screen_width / 10;
           float y_limit = screen_height / 10;
           float x_abs = Math.abs(x);
           float y_abs = Math.abs(y);
           if(x_abs >= y_abs){
               //gesture left or right
               if(x > x_limit || x < -x_limit){
                   if(x>0){
                       //right
                	   listener_onfling.OnFlingRight();
                   }else if(x<=0){
                       //left
                	   listener_onfling.OnFlingLeft();
                   }
               }
           }else{
               //gesture down or up
               if(y > y_limit || y < -y_limit){
                   if(y>0){
                       //down
                	   listener_onfling.OnFlinDown();
                   }else if(y<=0){
                       //up
                	   listener_onfling.OnFlingUp();
                   }
               }
           }
        return true;  
    }  
       
    public boolean onDown(MotionEvent e) {  
        return false;  
    }  
       
     
    public void onLongPress(MotionEvent e) {  
    }  
       
     
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,  
            float distanceY) {  
        return false;  
    }  
       
     
    public void onShowPress(MotionEvent e) {  
    }  
       
     
    public boolean onSingleTapUp(MotionEvent e) {  
        return false;  
    }  
}