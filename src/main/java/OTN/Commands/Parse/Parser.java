/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.Commands.Parse;

import OTN.Commands.Tokens.Token;
import OTN.Commands.Parse.ParseTree.*;
import java.util.List;
import java.util.ArrayList;

public class Parser {

    List<Token> tokens;
    int index = 0;
    
    public Parser(List<Token> toReadTokens){

        tokens = toReadTokens;

    }

    public ActionNode parseAction(){

        if(peek() != null && peek().type == Token.types.ACTION){

            ActionNode actionNode = new ActionNode(consume());

            return actionNode;

        }

        else{

            return null;

        }
    }

    public ObjectNode parseObject(){

        if(peek() != null && peek().type == Token.types.OBJECT){

            ObjectNode objectNode = new ObjectNode(consume());

            return objectNode;

        }

        else{

            return null;

        }
    }

    public ObjectNameNode parseObjectName(){

        if(peek() != null && peek().type == Token.types.VALUE){

            ObjectNameNode objectNameNode = new ObjectNameNode(consume());

            return objectNameNode;

        }

        else{

            return null;

        }
    }

    public ValueNode parseValue(){

        if(peek() != null && peek().type == Token.types.VALUE){

            ValueNode valueNode = new ValueNode(consume());

            return valueNode;

        }

        else{

            return null;

        }
    }

    public List<StatementNode> parse(){

        List<StatementNode> statementNodes = new ArrayList<>();

        while(peek() != null){

            ObjectNode deviceNode = null;
            ActionNode actionNode = null;
            ObjectNode objectNode = null;
            ObjectNameNode objectName = null;
            ValueNode value = null;

            if(peek() != null && peek().type == Token.types.OBJECT){
                deviceNode = parseObject();
            }
            
            if(peek() != null && peek().type == Token.types.ACTION){
                actionNode = parseAction();
            }
            
            if(peek() != null && peek().type == Token.types.OBJECT){
                objectNode = parseObject();
            }
            
            if(peek() != null && peek().type == Token.types.VALUE){
                objectName = parseObjectName();
            }
            
            if(peek() != null && peek().type == Token.types.VALUE){
                value = parseValue();
            }
            
            if(deviceNode != null && actionNode != null && objectNode != null && objectName != null){
                if(value != null){
                    statementNodes.add(new StatementNode(deviceNode, actionNode, objectNode, objectName, value));
                } else {
                    statementNodes.add(new StatementNode(deviceNode, actionNode, objectNode, objectName));
                }
            } else {
                if(peek() != null) {
                    consume();
                }
            }
            
        }

        return statementNodes;

    }

    private Token peek(){

    if (index >= tokens.size()){ 
        return null;

    } 
    else {
    
        return tokens.get(index); 
    
    }
}

    private Token consume(){

        return tokens.get(index++);

    }

}
