package ParseHTML;

public class Station {
    private String name;
    private String lineNumber;

    public Station (String name, String lineNumber){
        this.name = name;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "Станция " + name + ", " + lineNumber;
    }
}
