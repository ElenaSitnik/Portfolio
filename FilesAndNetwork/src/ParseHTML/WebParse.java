package ParseHTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebParse {
    private static final Document doc = Jsoup.parse(getHTMLFile());

    private static String getHTMLFile(){
        StringBuilder builder = new StringBuilder();
        try {
            List<String> str = Files.readAllLines(Paths.get("data/code.html"));
            str.forEach(s -> builder.append(s).append("\n"));
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return builder.toString();
    }

    public static List<String> getLineNumbers() {
        Elements elements = doc.select("span");
        ArrayList<String> lineNumbers = new ArrayList<>();
        for (Element element : elements) {
            String lineNumber = element.attr("data-line");
            if (!lineNumber.isEmpty()) {
                lineNumbers.add(lineNumber);
            }
        }
        return lineNumbers;
    }

    private static List<String> getLineNames(){
        Elements elements = doc.select("span[data-line]");
        ArrayList<String> lineNames = new ArrayList<>();
        elements.forEach(element -> lineNames.add(element.text()));
        return lineNames;
    }

    public static List<Line> getLineList(){
        ArrayList<Line> lineList = new ArrayList<>();
        for (int i = 0; i < getLineNumbers().size(); i++){
            lineList.add(new Line(getLineNames().get(i), getLineNumbers().get(i)));
        }
        return lineList;
    }

    private static List<String> getStationNames(){
        ArrayList<String> stations = new ArrayList<>();
        Elements elements = doc.select("p");
        elements.forEach(element -> stations.add(element.text()));
        return stations;
    }

    public static List<Station> getStationsList(){
        ArrayList<Station> stationsList = new ArrayList<>();
        for (int i = 0, j = -1; i < getStationNames().size(); i++){
            String[] station = getStationNames().get(i).split("\s",2);
            if (station[0].equals("1.")){
                j++;
            }
            stationsList.add(new Station(station[1], getLineNumbers().get(j)));
        }
        return stationsList;
    }

    public static HashSet<String> getConnectionStation() {
        Elements elements = doc.select("span");
        ArrayList<String> connectInfo = new ArrayList<>();
        HashSet<String> connectionStations = new HashSet<>();
        for (Element element : elements) {
            String connection = element.attr("title");
            if (!connection.isEmpty()) {
                connectInfo.add(connection);
            }
        }
        for (String connectStationInfo : connectInfo){
            String regex = "«([^»]+)»";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(connectStationInfo);
            if (matcher.find()) {
                String connectionStation = matcher.group(1);
                connectionStations.add(connectionStation);
            }
        }
        return connectionStations;
    }
}
