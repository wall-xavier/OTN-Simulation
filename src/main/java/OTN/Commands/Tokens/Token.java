package OTN.Commands.Tokens;

public class Token{

    public enum types {
        KEYWORD,
        INTLIT,
        SEMICOLON};

    public types type;
    public String value;

    public Token(types type, String value){

        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {

        return "[" + type + ": \"" + value + "\"]\n";
    
    }
}
