package OTN.Simulation.Assets.Cards.WSS.Assets;
import OTN.Simulation.Assets.Cards.Transponder.Assets.Transponder;
public class WSSPort {
    
    String portSite;
    int portIndex;
    Transponder mappedTransponder;

    public WSSPort(String site, int index, Transponder transponderToMap){

        portSite = site;
        portIndex = index;
        mappedTransponder = transponderToMap;

    }
}
