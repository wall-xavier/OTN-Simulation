package OTN.Simulation.Assets.Light;

public class Photons{

   double wavelength;
   double frequency;
   final int SPEED_MS =  299792458;
   double transmitPower_DB;
   double resultantPower_DB;
  
   public Photons(double wave, double power){
   
      wavelength = wave;
      frequency = SPEED_MS / wave;
      transmitPower_DB = power;
      
   }
   
   public double getPhotonWavelength(){

      return wavelength;

   }

   public double getPhotonFrequency(){

      return frequency;
      
   }

   public double getPhotonTXPower(){

      return transmitPower_DB;

   }

   public void setPhotonResultantPower(double power){

      resultantPower_DB = power;

   }

   public double getPhotonResultantPower(){

      return resultantPower_DB;
      
   }
}