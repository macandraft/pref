package con.andraft.opengl.helpers;
import android.opengl.GLES20;
import android.util.Log;

public class ShaderHelper{
	private static final String TAG="ShaderHelper";
	public static int compileShader(final int shaderType,String shaderSource){
		int shaderHandle=GLES20.glCreateShader(shaderType);
		if(shaderHandle!=0){
			GLES20.glShaderSource(shaderHandle,shaderSource);
			GLES20.glCompileShader(shaderHandle);
			final int[] compileStatus=new int[1];
			GLES20.glGetShaderiv(shaderHandle,GLES20.GL_COMPILE_STATUS,
					compileStatus,0);
			if(compileStatus[0]==0){
				Log.e(TAG,
						"Error compiling shader: "
								+GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle=0;
			}
		}
		if(shaderHandle==0){
			throw new RuntimeException("Error creating shader.");
		}
		return shaderHandle;
	}
	public static int compileShader(final int shaderType,final int r_Row_shader){
		int shaderHandle=GLES20.glCreateShader(shaderType);
		if(shaderHandle!=0){
			String shaderSource=ReaderFromRes
					.readTextFileFromRawResource(r_Row_shader);
			GLES20.glShaderSource(shaderHandle,shaderSource);
			GLES20.glCompileShader(shaderHandle);
			final int[] compileStatus=new int[1];
			GLES20.glGetShaderiv(shaderHandle,GLES20.GL_COMPILE_STATUS,
					compileStatus,0);
			if(compileStatus[0]==0){
				Log.e(TAG,
						"Error compiling shader: "
								+GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle=0;
			}
		}
		if(shaderHandle==0){
			throw new RuntimeException("Error creating shader.");
		}
		return shaderHandle;
	}
	public static int createAndLinkProgram(final int vertexShaderHandle,
			final int fragmentShaderHandle,final String[] attributes){
		int programHandle=GLES20.glCreateProgram();
		if(programHandle!=0){
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle,vertexShaderHandle);
			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle,fragmentShaderHandle);
			// Bind attributes
			if(attributes!=null){
				final int size=attributes.length;
				for(int i=0;i<size;i++){
					GLES20.glBindAttribLocation(programHandle,i,attributes[i]);
				}
			}
			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);
			// Get the link status.
			final int[] linkStatus=new int[1];
			GLES20.glGetProgramiv(programHandle,GLES20.GL_LINK_STATUS,
					linkStatus,0);
			// If the link failed, delete the program.
			if(linkStatus[0]==0){
				Log.e(TAG,
						"Error compiling program: "
								+GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle=0;
			}
		}
		if(programHandle==0){
			throw new RuntimeException("Error creating program.");
		}
		return programHandle;
	}
}
