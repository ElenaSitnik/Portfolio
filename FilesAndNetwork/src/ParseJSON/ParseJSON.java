package ParseJSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ParseJSON {
    private static final String path1 = "data/data/2/4/depths-1.json";
    private static final String path2 = "data/data/4/6/depths-3.json";
    private static final String path3 = "data/data/7/1/depths-2.json";
    private static HashSet<StationWithDepth> stationsAndDepth = new HashSet<>();

    public static HashSet<StationWithDepth> getStationWithDepthList(){
        getList(path1);
        getList(path2);
        getList(path3);

        ArrayList<StationWithDepth> list = new ArrayList<>(stationsAndDepth);

        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.size(); j++){
                if (!list.get(i).getName().equals("Смоленская") && !list.get(i).getName().equals("Арбатская") &&
                        list.get(i).getName().equals(list.get(j).getName()) &&
                        list.get(i).getDepth() < list.get(j).getDepth()){
                    list.remove(list.get(j));
                    list.add(list.get(i));
                    break;
                }
            }
        }
        stationsAndDepth.clear();
        stationsAndDepth.addAll(list);
        return stationsAndDepth;
    }

    private static String getJsonFile(String path){
        String jsonFile = "";
        try{
            jsonFile = Files.readString(Paths.get(path));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return jsonFile;
    }

    private static void getList (String path) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(getJsonFile(path));
            stationsAndDepth.addAll(parseJSONFile(array));
        } catch (ParseException e){
            e.printStackTrace();
        }
    }


    private static List<StationWithDepth> parseJSONFile(JSONArray array){
        List<StationWithDepth> stationList = new ArrayList<>();
        array.forEach(stationObject -> {
            JSONObject stationJsonObject = (JSONObject) stationObject;
            String depthString = (String)stationJsonObject.get("depth");
            if (Character.isDigit(depthString.charAt(0)) || depthString.startsWith("-")) {
                StationWithDepth station = new StationWithDepth(
                        (String) stationJsonObject.get("station_name"),
                        Double.parseDouble(((String) stationJsonObject.get("depth")).replace(',', '.'))
                );
                stationList.add(station);
            }
        });
        return stationList;
    }
}
