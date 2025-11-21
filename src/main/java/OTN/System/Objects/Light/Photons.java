/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.System.Objects.Light;

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