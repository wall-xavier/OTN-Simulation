/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.Commands.CommandGeneration;

import OTN.Commands.Parse.ParseTree.StatementNode;
import java.util.List;


public class CommandGenerator {
    
    List <StatementNode> StatementNodes;

    public CommandGenerator(List <StatementNode> StatementNodes){

        this.StatementNodes = StatementNodes;

    }

    public void generator(){

        if(StatementNodes != null && !StatementNodes.isEmpty()){
            System.out.println("Input: " + StatementNodes.get(0).deviceNode.object.value + " " + StatementNodes.get(0).actionNode.actionToken.value + " " + StatementNodes.get(0).objectNode.object.value + " " + StatementNodes.get(0).objectNameNode.name.value);
        } else {
            System.out.println("No statements parsed.");
        }

    }
}
