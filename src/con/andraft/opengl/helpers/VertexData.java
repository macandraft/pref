package con.andraft.opengl.helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.util.Log;
import con.andraft.pref.MainActivity;

public class VertexData{
	public float vertex[]=null;
	public float colors[]=null;
	public short order[]=null;
	public VertexData(final int blenderResourceId){
		List<Float> vertexList=new ArrayList<Float>();
		List<Float> colorsList=new ArrayList<Float>();
		List<Short> orderList=new ArrayList<Short>();
		final InputStream inputStream=MainActivity.getRes().openRawResource(
				blenderResourceId);
		final InputStreamReader inputStreamReader=new InputStreamReader(
				inputStream);
		final BufferedReader bufferedReader=new BufferedReader(
				inputStreamReader);
		String line;
		Random random=new Random();
		try{
			while((line=bufferedReader.readLine())!=null){
				String[] l=line.split(" ");
				if(l[0].equals("v")){
					colorsList.add(random.nextFloat()*(-1)+1);
					colorsList.add(random.nextFloat()*(-1)+1);
					colorsList.add(random.nextFloat()*(-1)+1);
					colorsList.add(1f);
					for(int j=1;j<l.length;j++)
						try{
							vertexList.add(Float.parseFloat(l[j]));
						}catch(Exception e){
						}
				}else if(l[0].equals("f")){
					if(l.length==4){
						for(int j=1;j<l.length;j++)
							orderList.add((short)(Short.parseShort(l[j])-1));
					}else{
						final int sorted[]={Integer.parseInt(l[2])-1,
								Short.parseShort(l[4])-1,
								Short.parseShort(l[1])-1,
								Short.parseShort(l[2])-1,
								Short.parseShort(l[3])-1,
								Short.parseShort(l[4])-1};
						for(int j=0;j<sorted.length;j++){
							orderList.add((short)sorted[j]);
						}
					}
				}
			}
			vertex=new float[vertexList.size()];
			colors=new float[colorsList.size()];
			order=new short[orderList.size()];
			for(Float f: vertexList)
				vertex[vertexList.indexOf(f)]=f;
			for(Float f: colorsList)
				colors[colorsList.indexOf(f)]=f;
			for(Short f: orderList)
				order[orderList.indexOf(f)]=f;
			Log.d("xxx","vertex size"+vertexList.size());
			Log.d("xxx","color size"+colorsList.size());
		}catch(IOException e){
			Log.d("xxx","exception");
		}
	}
}
