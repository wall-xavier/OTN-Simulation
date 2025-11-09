package OTN.Simulation.Assets.Light;
import OTN.Simulation.Assets.Cards.Transponder.Assets.Transponder;
public class Fiber {
    
    double length;
    final double ATTENUATION = .2;
    String label;
    Transponder ASide;
    Transponder ZSide;

    public Fiber(double len, String lb, Transponder A, Transponder Z){

        length = len;
        label = lb;
        ASide = A;
        ZSide = Z;
    }

    public String getLabel(){
        
        return label;

    }

    public double getLength(){

        return length;

    }

    public void attenuateLight(Photons light){

        light.setPhotonResultantPower((light.getPhotonTXPower() * ATTENUATION));
    
    }

}
