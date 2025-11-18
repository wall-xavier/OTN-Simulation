/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.Commands.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    String src;
    int index = 0;

    public Tokenizer(String sourceCode){

        src = sourceCode;

    }

    public List<Token> tokenize() {

    StringBuilder buffer = new StringBuilder("");
    List<Token> tokens = new ArrayList<>();
    
    while (peek() != null) {

        if (Character.isWhitespace(peek())) {
        
            consume();
        
        }
        
        else if (Character.isAlphabetic(peek())) {
            
            buffer.append(consume()); 
            
            while (peek() != null && Character.isLetterOrDigit(peek())) {
            
                buffer.append(consume());
            
            }

            String value = buffer.toString();
            value = value.toUpperCase();
            
            if (value.equals("ROADM") || value.equals("WSS") || value.equals("PORT") || value.equals("TRANSPONDER_CARD") || value.equals("TRANSPONDER") || value.equals("FIBER")) {

                tokens.add(new Token(Token.types.OBJECT, value));
            
            } else if (value.equals("ADD") || value.equals("MODIFY") || value.equals("REMOVE")){
        
                tokens.add(new Token(Token.types.ACTION, value));

            } else{

                tokens.add(new Token(Token.types.VALUE, value));
            
            }
            
            buffer.setLength(0); 
        }
    }

    return tokens;
}

    private Character peek(){

        if(index + 1 >= src.length()){

            return null;

        } else{

            return src.charAt(index);
        }

    }

    private char consume(){

        return src.charAt(index++);

    }
    
}
