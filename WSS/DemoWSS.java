public class DemoWSS{

   public static void main(String[]args){
   
      String [] sites = {"Las Vegas", "Salt Lake City", "Los Angeles" , "Dallas" , "Billings", "Casper", "Santa Fe", "Denver", "Local (Drop At This ROADM)"};
      int ports = sites.length;
      
      WSS demoWSS = new WSS(sites, ports);
      
      demoWSS.getSiteLayout();
      
   
   }
}