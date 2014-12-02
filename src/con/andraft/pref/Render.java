package con.andraft.pref;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import con.andraft.opengl.helpers.ShaderHelper;
import con.andraft.opengl.helpers.TextureHelper;
import con.andraft.pref.cards.Karta;

public class Render implements GLSurfaceView.Renderer{
	private float[] mViewMatrix=new float[16];
	private float[] mProjectionMatrix=new float[16];
	private float[] mMVPMatrix=new float[16];
	private float[] mLightModelMatrix=new float[16];
	private float[] stollMatrix=new float[16];
	private final FloatBuffer mCubePositions;
	private final FloatBuffer mCubeNormals;
	private final FloatBuffer mCubeTextureCoordinates;
	private int mMVPMatrixHandle;
	private int mMVMatrixHandle;
	private int mLightPosHandle;
	private int mTextureUniformHandle;
	private int mPositionHandle;
	private int mNormalHandle;
	private int mTextureCoordinateHandle;
	private final int mBytesPerFloat=4;
	private final int mNormalDataSize=3;
	private final int mTextureCoordinateDataSize=2;
	private final float[] mLightPosInWorldSpace=new float[4];
	private final float[] mLightPosInEyeSpace=new float[4];
	private int progHandle;
	private int mTextureDataHandle;
	private int mMast;
	public Render(){
		final float[] cubePositionData={//
		// Top face
				-1.0f,0,-1.0f,//
				-1.0f,0,1.0f,//
				1.0f,0,-1.0f,//
				-1.0f,0,1.0f,//
				1.0f,0,1.0f,//
				1.0f,0,-1.0f,//
				//
				-1.0f,0.001f,-1.0f,//
				1.0f,0.001f,-1.0f,//
				-1.0f,0.001f,1.0f,//
				1.0f,0.001f,1.0f,//
				-1.0f,0.001f,1.0f,//
				1.0f,0.001f,-1.0f,//
		//
		};
		final float[] cubeNormalData={
				// Top face
				0.0f,1.0f,0.0f,0.0f,1.0f,0.0f,0.0f,1.0f,0.0f,//
				0.0f,1.0f,0.0f,0.0f,1.0f,0.0f,0.0f,1.0f,0.0f,
				//
				0.0f,-1.0f,0.0f,0.0f,-1.0f,0.0f,0.0f,-1.0f,0.0f,//
				0.0f,-1.0f,0.0f,0.0f,-1.0f,0.0f,0.0f,-1.0f,0.0f,
		//
		};
		final float[] cubeTextureCoordinateData={//
		0.0f,0.0f,//
				0.0f,1f/4f,//
				1f/9f,0.0f,//
				0.0f,1f/4f,//
				1f/9f,1f/4f,//
				1f/9f,0.0f,//
				//
				8/9f,0f,//
				1f,0f,//
				8/9f,0.25f, //
				1f,0.25f, //
				8/9f,0.25f, //
				1f,0f,//
		};
		mCubePositions=ByteBuffer
				.allocateDirect(cubePositionData.length*mBytesPerFloat)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mCubePositions.put(cubePositionData).position(0);
		mCubeNormals=ByteBuffer
				.allocateDirect(cubeNormalData.length*mBytesPerFloat)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mCubeNormals.put(cubeNormalData).position(0);
		mCubeTextureCoordinates=ByteBuffer
				.allocateDirect(cubeTextureCoordinateData.length*mBytesPerFloat)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mCubeTextureCoordinates.put(cubeTextureCoordinateData).position(0);
	}
	@Override
	public void onSurfaceCreated(GL10 glUnused,EGLConfig config){
		final int vertexShaderHandle=ShaderHelper.compileShader(
				GLES20.GL_VERTEX_SHADER,R.raw.bmvertex);
		final int fragmentShaderHandle=ShaderHelper.compileShader(
				GLES20.GL_FRAGMENT_SHADER,R.raw.bmfrag);
		progHandle=ShaderHelper.createAndLinkProgram(vertexShaderHandle,
				fragmentShaderHandle,new String[]{"a_Position","a_Normal",
						"a_TexCoordinate"});
		mTextureDataHandle=TextureHelper.loadTexture(R.drawable.cards);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureDataHandle);
	}
	@Override
	public void onSurfaceChanged(GL10 glUnused,int width,int height){
		GLES20.glViewport(0,0,width,height);
		final float ratio=(float)width/height;
		final float left=-ratio;
		final float right=ratio;
		final float bottom=-1.0f;
		final float top=1.0f;
		final float near=1.0f;
		final float far=25.0f;
		Matrix.frustumM(mProjectionMatrix,0,left,right,bottom,top,near,far);
		Matrix.setIdentityM(mViewMatrix,0);
		Matrix.rotateM(mProjectionMatrix,0,30,1,0,0);
		Matrix.translateM(mViewMatrix,0,0,-3.0f,0f);
		Matrix.setIdentityM(stollMatrix,0);
		Matrix.translateM(stollMatrix,0,0f,-1f,-3f);
		// Matrix.rotateM(stollMatrix,0,180,1.0f,0.0f,0.0f);// перевернуть карту
	}
	@Override
	public void onDrawFrame(GL10 glUnused){
		GLES20.glClearColor(0.0f,0.5f,0.0f,0.5f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glUseProgram(progHandle);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
		// GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glEnable(GL10.GL_TEXTURE_2D);
		long time=SystemClock.uptimeMillis()%10000L;
		float angleInDegrees=(360.0f/10000.0f)*((int)time);
		mMVPMatrixHandle=GLES20.glGetUniformLocation(progHandle,"u_MVPMatrix");
		mMVMatrixHandle=GLES20.glGetUniformLocation(progHandle,"u_MVMatrix");
		mLightPosHandle=GLES20.glGetUniformLocation(progHandle,"u_LightPos");
		mTextureUniformHandle=GLES20.glGetUniformLocation(progHandle,
				"u_Texture");
		mPositionHandle=GLES20.glGetAttribLocation(progHandle,"a_Position");
		mNormalHandle=GLES20.glGetAttribLocation(progHandle,"a_Normal");
		mTextureCoordinateHandle=GLES20.glGetAttribLocation(progHandle,
				"a_TexCoordinate");
		mMast=GLES20.glGetUniformLocation(progHandle,"u_Mast");
		GLES20.glUniform1i(mTextureUniformHandle,0);
		Matrix.setIdentityM(mLightModelMatrix,0);
		Matrix.translateM(mLightModelMatrix,0,0.0f,2.0f,-2.0f);
		Matrix.rotateM(mLightModelMatrix,0,angleInDegrees*2,0.0f,1.0f,0.0f);
		Matrix.multiplyMV(mLightPosInEyeSpace,0,mViewMatrix,0,
				mLightPosInWorldSpace,0);
		drawStol();
		for(Karta k: Igra.karts)
			drawCards(k);
		// drawCards(Igra.karts[0]);
		// Matrix.setIdentityM(mModelMatrix,0);
		// Matrix.translateM(mModelMatrix,0,0.0f,0.2f,0.1f);
		// Matrix.rotateM(mModelMatrix,0,angleInDegrees,0.0f,0.0f,1.0f);
		// drawCube(1,6);
	}
	private void drawStol(){
		mCubePositions.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,
				mCubePositions);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		mCubeNormals.position(0);
		GLES20.glVertexAttribPointer(mNormalHandle,mNormalDataSize,
				GLES20.GL_FLOAT,false,0,mCubeNormals);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		mCubeTextureCoordinates.position(9);
		GLES20.glVertexAttribPointer(mTextureCoordinateHandle,
				mTextureCoordinateDataSize,GLES20.GL_FLOAT,false,0,
				mCubeTextureCoordinates);
		GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
		Matrix.multiplyMM(mMVPMatrix,0,mViewMatrix,0,stollMatrix,0);
		GLES20.glUniformMatrix4fv(mMVMatrixHandle,1,false,mMVPMatrix,0);
		Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mMVPMatrix,0);
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mMVPMatrix,0);
		GLES20.glUniform3f(mLightPosHandle,mLightPosInEyeSpace[0],
				mLightPosInEyeSpace[1],mLightPosInEyeSpace[2]);
		GLES20.glUniform2f(mMast,2f/4f,8f/9f);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
	}
	private void drawCards(Karta karta){
		mCubePositions.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,
				mCubePositions);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		mCubeNormals.position(0);
		GLES20.glVertexAttribPointer(mNormalHandle,mNormalDataSize,
				GLES20.GL_FLOAT,false,0,mCubeNormals);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		mCubeTextureCoordinates.position(0);
		GLES20.glVertexAttribPointer(mTextureCoordinateHandle,
				mTextureCoordinateDataSize,GLES20.GL_FLOAT,false,0,
				mCubeTextureCoordinates);
		GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
		Matrix.multiplyMM(mMVPMatrix,0,mViewMatrix,0,karta.getMatrix(),0);
		GLES20.glUniformMatrix4fv(mMVMatrixHandle,1,false,mMVPMatrix,0);
		Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mMVPMatrix,0);
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mMVPMatrix,0);
		GLES20.glUniform3f(mLightPosHandle,mLightPosInEyeSpace[0],
				mLightPosInEyeSpace[1],mLightPosInEyeSpace[2]);
		GLES20.glUniform2f(mMast,karta.getCarddata().ordinal()/9f,karta
				.getMast().ordinal()/4f);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,12);
	}
}