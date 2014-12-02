package con.andraft.opengl.helpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BmHelper{
	private ArrayList<Float> tx=new ArrayList<Float>();
	private ArrayList<Float> tv=new ArrayList<Float>();
	private ArrayList<Float> tn=new ArrayList<Float>();
	public void addTexture(Float[] downleft,Float[] upleft,Float[] upright,
			Float[] downright){
		final List<Float> downLeft=Arrays.asList(downleft);
		final List<Float> upRight=Arrays.asList(upright);
		final List<Float> rexturePosition=Arrays.asList(new Float[]{0f,0f,1f,
				1f,0f,1f,0f,0f,1f,0f,1f,1f});
		final List<Float> Normals=Arrays.asList(new Float[]{0f,1f,0f});
		if(upleft==null)
			upleft=new Float[]{downLeft.get(0),upRight.get(1),downLeft.get(2)};
		if(downright==null)
			downright=new Float[]{upRight.get(0),downLeft.get(1),upRight.get(2)};
		final List<Float> downRight=Arrays.asList(downright);
		final List<Float> upLeft=Arrays.asList(upleft);
		tx.addAll(rexturePosition);
		tv.addAll(downLeft);
		tv.addAll(upRight);
		tv.addAll(upLeft);
		tv.addAll(downLeft);
		tv.addAll(downRight);
		tv.addAll(upRight);
		for(int j=0;j<6;j++)
			tn.addAll(Normals);
	}
	private float[] listFloatToarray(List<Float> l){
		final float arr[]=new float[l.size()];
		for(int i=0;i<arr.length;i++)
			arr[i]=l.get(i);
		return arr;
	}
	public float[] getVertex(){
		return listFloatToarray(tv);
	}
	public float[] getNormal(){
		return listFloatToarray(tn);
	}
	public float[] getTextureCoordinate(){
		return listFloatToarray(tx);
	}
}
