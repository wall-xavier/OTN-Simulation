package OTN.Simulation.Assets.Cards.Transponder;
import OTN.Simulation.Assets.Cards.Transponder.Assets.Transponder;
import OTN.Simulation.Assets.Cards.WSS.WSS;

public class TransponderCard {
    
    final int PORTCOUNT = 16;
    Transponder [] ports = new Transponder [PORTCOUNT];
    WSS mappedWSS;

    public TransponderCard(WSS toMapWSS){

        mappedWSS = toMapWSS;

    }

}
