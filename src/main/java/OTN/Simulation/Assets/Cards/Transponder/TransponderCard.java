package OTN.Simulation.Assets.Cards.Transponder;
import OTN.Simulation.Assets.Cards.Transponder.Assets.Transponder;

public class TransponderCard {
    
    int portCount;
    Transponder [] ports = new Transponder [portCount];

    public TransponderCard(int portCount){

        portCount = this.portCount;
        
        for(int i = 0; i < ports.length; i++){
        
            ports[i] = new Transponder(1000, true, 2);

        }
    }

    public Transponder getPort(int index){

        return ports[index];
    }

}
