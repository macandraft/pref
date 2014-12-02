package con.andraft.pref.cards;
import android.opengl.Matrix;
import con.andraft.pref.Igra;

public class Karta{
	private Mast mast;
	private CardData carddata;
	private Ouner ouner=Ouner.coloda;
	public Ouner getOuner(){
		return ouner;
	}
	public void setOuner(Ouner ouner){
		switch(ouner){
		case bitoe:
			break;
		case coloda:
			break;
		case ctol:
			break;
		case igrok1:
			Igra.igrok1.add(this);
			break;
		case igrok2:
			Igra.igrok2.add(this);
			break;
		case igrokmain:
			Igra.igrokMain.add(this);
			break;
		case pricup:
			break;
		default:
			break;
		}
		this.ouner=ouner;
	}
	private float[] matrix=new float[16];
	int i;
	public Karta(int i){
		this.i=i;
		Matrix.setIdentityM(matrix,0);
		// Matrix.rotateM(matrix,0,180,1.0f,0.0f,0.0f);// перевернуть карту
		// Matrix.rotateM(matrix,0,90,0.0f,1.0f,0.0f);
		if(i>23){
			mast=Mast.cherva;
			i=i-24;
		}else if(i>15){
			mast=Mast.buba;
			i=i-16;
		}else if(i>7){
			mast=Mast.cresta;
			i=i-8;
		}else
			mast=Mast.pica;
		carddata=CardData.values()[i];
	}
	public CardData getCarddata(){
		Matrix.setIdentityM(matrix,0);
		switch(ouner){
		case bitoe:
			Matrix.translateM(matrix,0,-4f,-1+i/1000f,0f);
			break;
		case coloda:
			Matrix.translateM(matrix,0,0,0,-1f);
			Matrix.rotateM(matrix,0,180,1.0f,0.0f,0.0f);// перевернуть карту
			break;
		case ctol:
			Matrix.translateM(matrix,0,-3f,0,-4.0f);
			break;
		case igrok1:
			Matrix.translateM(matrix,0,-2f,0,-4f-Igra.igrok1.indexOf(this));
			Matrix.rotateM(matrix,0,90,0.0f,1.0f,0.0f);
			Matrix.scaleM(matrix,0,0.5f,1,1);
			break;
		case igrok2:
			Matrix.translateM(matrix,0,-2f+Igra.igrok2.indexOf(this),0,-4f
					-Igra.igrok1.indexOf(this));
			Matrix.translateM(matrix,0,2f,0,-4f);
			Matrix.rotateM(matrix,0,90,0.0f,1.0f,0.0f);
			Matrix.scaleM(matrix,0,0.5f,1,1);
			break;
		case igrokmain:
			Matrix.translateM(matrix,0,-6f,2+i/100f,-4.0f);
			break;
		case pricup:
			Matrix.translateM(matrix,0,-6f,1+i/100f,-4.0f);
			break;
		default:
			break;
		}
		return carddata;
	}
	public Mast getMast(){
		return mast;
	}
	public float[] getMatrix(){
		return matrix;
	}
}
