package JSONWriter;

public class StationInfo {

    private String name;
    private String line;
    private String date;
    private Double depth;
    private boolean hasConnection;

    public StationInfo(String name, String line, boolean hasConnection){
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;
        depth = Double.NaN;
        date = "";
    }
    public StationInfo(String name, String line, boolean hasConnection, Double depth){
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;
        this.depth = depth;
        date = "";
    }
    public StationInfo(String name, String line, boolean hasConnection, String date){
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;
        this.date = date;
        depth = Double.NaN;
    }

    public StationInfo(String name, String line, boolean hasConnection, Double depth, String date){
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;
        this.depth = depth;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public String getDate() {
        return date;
    }

    public Double getDepth() {
        return depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    @Override
    public String toString() {
        return "StationInfo{" +
                "name='" + name + '\'' +
                ", line='" + line + '\'' +
                ", date='" + date + '\'' +
                ", depth=" + depth +
                ", hasConnection=" + hasConnection +
                '}';
    }
}
