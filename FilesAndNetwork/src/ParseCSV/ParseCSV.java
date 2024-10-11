package ParseCSV;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ParseCSV {

    private static final String path1 = "data\\data\\4\\6\\dates-1.csv";
    private static final String path2 = "data\\data\\0\\5\\dates-2.csv";
    private static final String path3 = "data\\data\\9\\6\\dates-3.csv";
    private static final String dateFormat = "dd.MM.yyyy";

    private static List<StationWithDate> getParseList(String path){
        List<StationWithDate> stationList = new ArrayList<>();
        try {
            List<String> linesList = Files.readAllLines(Paths.get(path));
            String firstLine = null;
            for (String str : linesList){
                if(firstLine == null) {
                    firstLine = str;
                    continue;
                }
                String[] line = str.split(",");
                stationList.add(new StationWithDate(line[0], (new SimpleDateFormat(dateFormat)).parse(line[1])));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return stationList;
    }

    public static List<StationWithDate> getStationWithDateList(){
        HashSet<StationWithDate> stationsAndDate = new HashSet<>(getParseList(path1));
        stationsAndDate.addAll(getParseList(path2));
        stationsAndDate.addAll(getParseList(path3));
        List<StationWithDate> stationWithDateList = new ArrayList<>(stationsAndDate);
        Collections.sort(stationWithDateList);
        return stationWithDateList;
    }
}
