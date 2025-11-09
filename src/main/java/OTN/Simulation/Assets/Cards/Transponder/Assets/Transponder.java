package OTN.Simulation.Assets.Cards.Transponder.Assets;

public class Transponder {
    
    double speed;
    boolean duplex; // Half (0) or Full (1)
    double rxSignalStrength_DBM;
    double txSignalStrength_DBM;
    final double RXSENSITIVITY_DBM = -40;

    public Transponder(double speed, boolean duplex, double txSignalStrength_DBM){

        speed = this.speed;
        duplex = this.duplex;
        txSignalStrength_DBM = this.txSignalStrength_DBM;
    }

    public double getSpeed(){

        return speed;

    }

    public boolean getDuplex(){

        return duplex;

    }

    public double getRXSignalStrength(){

        return rxSignalStrength_DBM;

    }

    public double getTXSignalStrength(){

        return txSignalStrength_DBM;
        
    }
}
