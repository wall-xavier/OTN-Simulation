package OTN.Simulation.Assets.Light;

public class Fiber {
    
    double length;
    final double ATTENUATION = .2;
    String label;

    public Fiber(double len, String lb){

        length = len;
        label = lb;
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
