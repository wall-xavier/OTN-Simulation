/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.Commands.Parse.ParseTree;

public class StatementNode {
    
    public ObjectNode deviceNode;
    public ActionNode actionNode;
    public ObjectNameNode deviceName;
    public ObjectNode objectNode;
    public ObjectNameNode objectNameNode;
    public ValueNode valueNode;
    public FieldNode fieldNode;
    
    public static enum types{
        STATEMENT,
        HELP;
    }

    public types type;

    public StatementNode(ObjectNode device, ActionNode action, ObjectNameNode devName, ObjectNode object, ObjectNameNode name, ValueNode val){

        type = types.STATEMENT;
        deviceNode = device;
        actionNode = action;
        deviceName = devName;
        objectNode = object;
        objectNameNode = name;
        valueNode = val;

    }

    public StatementNode(ObjectNode device, ActionNode action, ObjectNameNode devName, ObjectNode object, ObjectNameNode name){

        type = types.STATEMENT;
        deviceNode = device;
        actionNode = action;
        deviceName = devName;
        objectNode = object;
        objectNameNode = name;
        valueNode = null;

    }

    public StatementNode(ObjectNode device, types type){

        deviceNode = device;
        this.type = type;

    }

    public StatementNode(types type){

        this.type = type;

    }

  
    public StatementNode(ObjectNode device, ActionNode action, ObjectNameNode name, FieldNode field, ValueNode value){

        deviceNode = device;
        actionNode = action;
        deviceName = name;
        fieldNode = field;
        valueNode = value;

    }
  
    //Device][Device Name][Action][Field][Value

}
