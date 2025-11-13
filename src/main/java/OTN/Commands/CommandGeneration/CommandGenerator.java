/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

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
