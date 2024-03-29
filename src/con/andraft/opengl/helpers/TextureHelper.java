package con.andraft.opengl.helpers;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import con.andraft.pref.MainActivity;

public class TextureHelper{
	public static int loadTexture(final int resourceId){
		final int[] textureHandle=new int[1];
		GLES20.glGenTextures(1,textureHandle,0);
		if(textureHandle[0]!=0){
			final BitmapFactory.Options options=new BitmapFactory.Options();
			options.inScaled=false; // No pre-scaling
			final Bitmap bitmap=BitmapFactory.decodeResource(
					MainActivity.getRes(),resourceId,options);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureHandle[0]);
			// Set filtering
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
			// Load the bitmap into the bound texture.
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
			bitmap.recycle();
		}
		if(textureHandle[0]==0){
			throw new RuntimeException("Error loading texture.");
		}
		return textureHandle[0];
	}
}
