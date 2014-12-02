package con.andraft.pref;
import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class PrefGLSurfaceView extends GLSurfaceView{
	private Render render;
	private final Igra igra=new Igra();
	public PrefGLSurfaceView(Context context){
		super(context);
		init();
	}
	public PrefGLSurfaceView(Context context,AttributeSet atr){
		super(context,atr);
		init();
	}
	private void init(){
		render=new Render();
		setEGLContextClientVersion(2);
		setEGLConfigChooser(8,8,8,8,16,0);
		setRenderer(render);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	float x1=0,x2,y1=0,y2,dx,dy;
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event){
		switch(event.getAction()){
		case (MotionEvent.ACTION_DOWN):
			x1=event.getX();
			y1=event.getY();
			Log.d("xxx","down");
			igra.razdacha();
			break;
		case (MotionEvent.ACTION_UP):
			x1=y1=0;
			return false;
		case (MotionEvent.ACTION_MOVE):
			x2=event.getX();
			y2=event.getY();
			dx=x2-x1;
			dy=y2-y1;
			if(dx>0)
				Log.d("xxx","to right"+dx);
			else
				Log.d("xxx","to left"+dx);
			if(dy<0)
				Log.d("xxx","to up"+dy);
			else
				Log.d("xxx","to down"+dy);
			// render.rotateMatrix(dx,dy);
		}
		return true;
	}
}
