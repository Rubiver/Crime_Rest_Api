package com.example.final_crime.controllers;

import com.example.final_crime.dto.CrimeDTO;
import com.example.final_crime.dto.OfficeDTO;
import com.example.final_crime.services.CrimeService;
import com.google.gson.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CrimeController {

    @Autowired
    private CrimeService crimeService;
    @GetMapping("/get")
    public String get(){
        return "hi";
    }

    @GetMapping("/getOfficee")
    public String getOffice() throws Exception {
        OfficeDTO dto = new OfficeDTO();
        dto = crimeService.getOfficeData();
        String sendJson = "{coordinate:{lat:"+dto.getLat()+",lng:"+dto.getLng()+"},office:"+dto.getOffice()+", crime_data : {murder:"+dto.getMurder()+",robbery:"+dto.getRobbery()+",theft:"+dto.getTheft()+",violence:"+dto.getViolence()+"}}";
        return sendJson;
    }

    @GetMapping("/getOffice")
    public JsonObject getOfficeWithRegion(@RequestParam("lat")double lat, @RequestParam("lng") double lng) throws Exception {
//        lat = 37.563185;
//        lng = 126.991361;

        //서울 서대문 경찰서 근처
//        lat = 37.562956;
//        lng = 126.966359;

        //Query 1 500m
        OfficeDTO dto = crimeService.getOffice(lat,lng);
        //Qurey 2 1000m
        dto = crimeService.getOffice1km(lat,lng);
        //Qurey 3 1500m

        System.out.println(lat+" " +lng);

        String sendJson = "{coordinate:{lat:"+dto.getLat()+",lng:"+dto.getLng()+"},office:"+dto.getOffice()+", crime_data : {murder:"+dto.getMurder()+",robbery:"+dto.getRobbery()+",theft:"+dto.getTheft()+",violence:"+dto.getViolence()+"}}";
        JsonObject newJson = new JsonObject();
        newJson = JsonParser.parseString(sendJson).getAsJsonObject();
        return newJson;
    }

    @GetMapping("/getData/{region}")
    public String getDatas(@PathVariable("region") String region) throws Exception{



        JsonObject newJson = new JsonObject();
        try{
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyAQHkfB1xIYWjYbBPuObxZkQ2DTFvudUuk")
                    .build();
            GeocodingResult[] results =  GeocodingApi.geocode(context,
                    region).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(results[0].addressComponents));

            System.out.println(gson.toJson(results));

            String json = String.valueOf(gson.toJson(results[0].addressComponents)); // 여기에 주어진 JSON 데이터를 넣으세요

            String localityLongName = null;

            // JSON 파싱을 위해 Gson 라이브러리를 사용합니다.
            JsonParser jsonParser = new JsonParser();
            JsonArray Jarr = jsonParser.parse(json).getAsJsonArray();
            System.out.println(Jarr);
            System.out.println(Jarr.size());
            for(int i=0; i< Jarr.size(); i++){
                JsonObject component = Jarr.get(i).getAsJsonObject();
                System.out.println(component);
                System.out.println(component.size());

                a:for (int j = 0; j < component.size(); j++) {
                    JsonArray Jarr_type = component.getAsJsonArray("types");
//                    System.out.println("타입 내부 값 : "+Jarr_type);
//                    System.out.println(Jarr_type.size());
                    String Jel_type = Jarr_type.get(0).getAsString();
//                    System.out.println(Jel_type);
                    //ADMINISTRATIVE_AREA_LEVEL_1
                    if(Jel_type.equals("LOCALITY")){
                        System.out.println("진짜 데이터 : "+component.get("longName"));
                        localityLongName = component.get("longName").getAsString();
                        break a;
                    }

                    if(localityLongName == null){
                        if(Jel_type.equals("ADMINISTRATIVE_AREA_LEVEL_1")){
                            System.out.println("진짜 데이터 : "+component.get("longName"));
                            localityLongName = component.get("longName").getAsString();
                            break;
                        }
                        break;
                    }
                }
            }

            if (localityLongName != null) {
                System.out.println("Local Name: " + localityLongName);
            } else {
                System.out.println("Local Name not found");
            }

            JsonElement Jel_location = JsonParser.parseString(gson.toJson(results[0].geometry));

            JsonObject Job_location  = Jel_location.getAsJsonObject();

            JsonObject Job_lc = (JsonObject) Job_location.get("location");

            List<CrimeDTO> list = crimeService.getCrimeData(localityLongName);
            Map<String, Integer> MappingData = new HashMap<>();

            for (CrimeDTO crime : list) {
                String midCrimeClass = crime.getMid_crime_class();
                int locationValue = Integer.parseInt(crime.getNumber(localityLongName));

                if (MappingData.containsKey(midCrimeClass)) {
                    int currentValue = MappingData.get(midCrimeClass);
                    MappingData.put(midCrimeClass, currentValue + locationValue);
                } else {
                    MappingData.put(midCrimeClass, locationValue);
                }
            }

            String sendJson = "{location: "+localityLongName+", coordinate:"+Job_lc+", crime_data : "+MappingData+"}";

            newJson = JsonParser.parseString(sendJson).getAsJsonObject();
            System.out.println(newJson);

            context.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
        return newJson.toString() + "region : "+region;
    }
}
