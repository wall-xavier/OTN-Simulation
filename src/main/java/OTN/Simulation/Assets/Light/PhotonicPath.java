package OTN.Simulation.Assets.Light;

public class PhotonicPath{

   double wavelength;
  
   public PhotonicPath(double wave){
   
      wavelength = wave;
      
   }
   
   public double getPhotonicWavelength(){
   
      return wavelength;
      
   }

}