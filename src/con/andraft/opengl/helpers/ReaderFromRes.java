package con.andraft.opengl.helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import con.andraft.pref.MainActivity;

public class ReaderFromRes{
	public static String readTextFileFromRawResource(final int resourceId){
		final InputStream inputStream=MainActivity.getRes().openRawResource(
				resourceId);
		final InputStreamReader inputStreamReader=new InputStreamReader(
				inputStream);
		final BufferedReader bufferedReader=new BufferedReader(
				inputStreamReader);
		String nextLine;
		final StringBuilder body=new StringBuilder();
		try{
			while((nextLine=bufferedReader.readLine())!=null){
				body.append(nextLine);
				body.append('\n');
			}
		}catch(IOException e){
			return null;
		}
		return body.toString();
	}
	public static String readVertex(final int resourceId){
		final InputStream inputStream=MainActivity.getRes().openRawResource(
				resourceId);
		final InputStreamReader inputStreamReader=new InputStreamReader(
				inputStream);
		final BufferedReader bufferedReader=new BufferedReader(
				inputStreamReader);
		String nextLine;
		final StringBuilder body=new StringBuilder();
		try{
			while((nextLine=bufferedReader.readLine())!=null){
				body.append(nextLine);
				body.append('\n');
			}
		}catch(IOException e){
			return null;
		}
		return body.toString();
	}
}
