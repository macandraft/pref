package con.andraft.pref;
import java.util.ArrayList;
import java.util.List;
import con.andraft.pref.cards.Karta;
import con.andraft.pref.cards.Ouner;

public class Igra{
	public static Karta karts[]=new Karta[32];
	public static List<Karta> igrok1=new ArrayList<>();
	public static List<Karta> igrok2=new ArrayList<>();
	public static List<Karta> igrokMain=new ArrayList<>();
	// public static List <Karta> igrokMain=new ArrayList<>();
	static{
		for(int i=0;i<karts.length;i++)
			karts[i]=new Karta(i);
	}
	public void razdacha(){
		// karts[1].setOuner(Ouner.bitoe);
		// karts[3].setOuner(Ouner.ctol);
		// karts[4].setOuner(Ouner.coloda);
		karts[31].setOuner(Ouner.igrok2);
		karts[30].setOuner(Ouner.igrok1);
		karts[29].setOuner(Ouner.igrok1);
		karts[21].setOuner(Ouner.igrok2);
		karts[6].setOuner(Ouner.igrokmain);
		// karts[7].setOuner(Ouner.pricup);
	}
}
