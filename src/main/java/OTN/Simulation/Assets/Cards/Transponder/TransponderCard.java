package OTN.Simulation.Assets.Cards.Transponder;
import OTN.Simulation.Assets.Cards.Transponder.Assets.Transponder;

public class TransponderCard {
    
    int portCount;
    Transponder [] ports;

    public TransponderCard(int portCount){

        this.portCount = portCount;
        this.ports = new Transponder [portCount];
        
        for(int i = 0; i < ports.length; i++){
        
            ports[i] = new Transponder(1000, true, 2);

        }
    }

    public Transponder getPort(int index){

        return ports[index];

    }

}
