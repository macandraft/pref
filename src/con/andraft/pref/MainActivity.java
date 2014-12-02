package con.andraft.pref;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	private PrefGLSurfaceView mGLView;
	private static Resources res;
	@Override
	public void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mGLView=(PrefGLSurfaceView)findViewById(R.id.pref);
		setRes(this.getResources());
	}
	@Override
	protected void onPause(){
		super.onPause();
		mGLView.onPause();
	}
	@Override
	protected void onResume(){
		super.onResume();
		mGLView.onResume();
	}
	public static Resources getRes(){
		return res;
	}
	public static void setRes(Resources res){
		MainActivity.res=res;
	}
}
