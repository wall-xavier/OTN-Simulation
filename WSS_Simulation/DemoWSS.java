public class DemoWSS{

   public static void main(String[]args){
   
      WSS demoWSS = new WSS();
      String [] sites = {"Las Vegas", "Salt Lake City", "Los Angeles" , "Dallas" , "Billings", "Casper", "Santa Fe", "Denver", "Local (Drop At This ROADM)"};
      int ports = sites.length;
      
      demoWSS.setSiteLayout(sites, ports);
      
      demoWSS.getSiteLayout();
   
   }
}