package JSONWriter;

import ParseCSV.ParseCSV;
import ParseCSV.StationWithDate;

import ParseHTML.Line;
import ParseHTML.Station;
import ParseHTML.WebParse;
import ParseJSON.StationWithDepth;
import ParseJSON.ParseJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JSONWriter {

    private static List<StationWithDate> list = ParseCSV.getStationWithDateList();

    public static void writeStationsToJsonFile(){
        JSONObject stations = new JSONObject();
        JSONArray array = new JSONArray();

        for (StationInfo stationInfo : getStationsList()){
            JSONObject object = new JSONObject();
            object.put("name", stationInfo.getName());
            object.put("line", stationInfo.getLine());
            object.put("hasConnection", stationInfo.isHasConnection());
            if (!stationInfo.getDate().isEmpty()){
                object.put("date", stationInfo.getDate());
            }
            if (!stationInfo.getDepth().isNaN()){
                object.put("depth", stationInfo.getDepth());
            }
            array.add(object);
        }
        stations.put("stations", array);
        try (FileWriter file = new FileWriter("stations.json")) {
            file.write(stations.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMetroInfoToJSON(){
        JSONObject object = new JSONObject();
        object.put("stations", getStationsJsonObject());
        object.put("lines", getLineJsonObject());

        try (FileWriter file = new FileWriter("metroInfo.json")) {
            file.write(object.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<StationInfo> getStationsList(){
        List<StationInfo> stationsWithAllInfo = new ArrayList<>();
        for (Station station : WebParse.getStationsList()) {
            String nameStation = station.getName();
            Boolean hasConnected = false;
            if (WebParse.getConnectionStation().contains(nameStation)) {
                hasConnected = true;
            }
            String line = getLine(station.getLineNumber());
            String date = getOpeningDate(nameStation);
            Double depth = getDepth(nameStation);
            if (date.isEmpty() && depth.isNaN()){
                stationsWithAllInfo.add(new StationInfo(nameStation, line, hasConnected));
            } else if (!date.isEmpty() && depth.isNaN()){
                stationsWithAllInfo.add(new StationInfo(nameStation, line, hasConnected, date));
            } else if (date.isEmpty() && !depth.isNaN()){
                stationsWithAllInfo.add(new StationInfo(nameStation, line, hasConnected, depth));
            } else {
                stationsWithAllInfo.add(new StationInfo(nameStation, line, hasConnected, depth, date));
            }
        }
        return stationsWithAllInfo;
    }

    private static String getOpeningDate (String nameStation) {
        String date = "";
        for (StationWithDate station : list) {
            if (station.getName().equals(nameStation)) {
                date = new SimpleDateFormat("dd.MM.yyyy").format(station.getOpeningDate());
                list.remove(station);
                break;
            }
        }
        return date;
    }

    private static String getLine(String lineNumber) {
        String lineName = "";
        for (Line line : WebParse.getLineList()) {
            if (line.getNumber().equals(lineNumber)){
                lineName = line.getName();
                break;
            }
        }
        return lineName;
    }

    private static Double getDepth(String nameStation) {
        Double depth = Double.NaN;
        for (StationWithDepth station : ParseJSON.getStationWithDepthList()){
            if (station.getName().equals(nameStation)){
                depth = station.getDepth();
                break;
            }
        }
        return depth;
    }

    private static JSONArray getStationsOnLineJsonArray(String lineNumber){
        JSONArray array = new JSONArray();
        for (Station station : WebParse.getStationsList()){
            if (station.getLineNumber().equals(lineNumber)){
                array.add(station.getName());
            }
        }
        return array;
    }

    private static JSONObject getStationsJsonObject(){
        JSONObject object = new JSONObject();
        for (String lineNumber : WebParse.getLineNumbers()){
            object.put(lineNumber, getStationsOnLineJsonArray(lineNumber));
        }
        return object;
    }

    private static JSONArray getLineJsonObject(){
        JSONArray array = new JSONArray();

        for (Line line : WebParse.getLineList()){
            JSONObject object = new JSONObject();
            object.put("number", line.getNumber());
            object.put("name", line.getName());
            array.add(object);
        }
        return array;
    }
}
