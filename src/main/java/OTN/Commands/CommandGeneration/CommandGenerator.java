package OTN.Commands.CommandGeneration;

import OTN.Commands.Parse.ParseTree.DeviceNode;


public class CommandGenerator {
    
    DeviceNode rootNode;

    public CommandGenerator(DeviceNode node){

        rootNode = node;

    }

    public void generator(){

        System.out.println("Created a WSS:");
        System.out.println("WSS " + rootNode.modifier.int_lit.value);

    }
}
