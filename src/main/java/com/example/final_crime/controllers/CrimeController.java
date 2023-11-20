package com.example.final_crime.controllers;

import com.example.final_crime.dto.CenterDTO;
import com.example.final_crime.dto.CrimeDTO;
import com.example.final_crime.dto.FacilityDTO;
import com.example.final_crime.dto.OfficeDTO;
import com.example.final_crime.services.CrimeService;
import com.google.gson.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CrimeController {

	@Autowired
	private CrimeService crimeService;

	@GetMapping("/get")
	public String getd() {
		return "hi";
	}

	@GetMapping("/getNews")
    public JsonArray getNews(){
        String clientId = "8M51tq8SIYOfBaUE3CGN"; //애플리케이션 클라이언트 아이디
        String clientSecret = "LZh6bsBlTP"; //애플리케이션 클라이언트 시크릿
        Gson gson = new Gson();
        
        try{
        	String text = URLEncoder.encode("[속보],서울,폭행,도주,살인", "UTF-8");
        	String apiURL = "https://openapi.naver.com/v1/search/news.json?query="+text+"&display=40&start=1&sort=date";
        	URL url = new URL(apiURL);
        	HttpURLConnection con = (HttpURLConnection)url.openConnection();
        	con.setRequestMethod("GET");
        	con.setRequestProperty("X-Naver-Client-Id", clientId);
        	con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        	int responseCode = con.getResponseCode();
        	BufferedReader br;
        	if(responseCode==200) { 
        		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        	} else {
        		br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        	}
        	String inputLine;
        	StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null){
				response.append(inputLine);
			}
			br.close();
			String temp = response.toString();
			List<String> list = new ArrayList<>();
			JsonArray itemsArray = JsonParser.parseString(temp)
	                .getAsJsonObject()
	                .getAsJsonArray("items");

	        for (int i = 0; i < itemsArray.size(); i++) {
	            JsonObject itemObject = itemsArray.get(i).getAsJsonObject();
	            String title = itemObject.get("title").getAsString();
	            String originallink = itemObject.get("originallink").getAsString();
	            list.add("{title:\""+title+"\",link:\""+originallink+"\"}");

	            // Print or process the extracted data as needed
	            System.out.println("Title: " + title);
	            System.out.println("Originallink: " + originallink);
	            System.out.println();
	        }
	        System.out.println(list);
	        JsonArray js = JsonParser.parseString(list.toString()).getAsJsonArray();
			return js;
        } catch (Exception e) {
        	System.out.println(e);
        }
        return null;        
    }

	@GetMapping("/allCenter")
	public JsonArray getCenter() throws Exception {
		List<CenterDTO> list = crimeService.allCenter();
		List<String> data = new ArrayList<>();
		Gson gson = new Gson();

		for (int i = 0; i < list.size(); i++) {
			String stations = "{agency:'" + list.get(i).getAgency() + "',police_office:'"
					+ list.get(i).getPolice_office() + "',office_name:'" + list.get(i).getGovernment_office_name()
					+ "',division:'" + list.get(i).getDivision() + "',center_name:'" + list.get(i).getCenter_name()
					+ "',address:'" + list.get(i).getAddress() + "'}";
			data.add(stations);
		}

		System.out.println(data);
		JsonArray js = JsonParser.parseString(data.toString()).getAsJsonArray();
		return js;
	}

	@GetMapping("/allFacility")
	public JsonArray getSafeFacility() throws Exception {
		List<FacilityDTO> list = crimeService.allFacility();
		List<String> data = new ArrayList<>();
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z~!@#$%^&*()_+|<>?:{}]";
		Gson gson = new Gson();

		for (int i = 0; i < list.size(); i++) {
			String facility = "";
			if (list.get(i).getPhone().contains(match)) {
				String tempPhone = list.get(i).getPhone().replaceAll(match, "");
				facility = "{coordinate:{lat:'" + list.get(i).getLat() + "',lng:'" + list.get(i).getLng()
						+ "'},location:'" + list.get(i).getLocation_1() + "',code:'" + list.get(i).getFacility_code()
						+ "',name:'" + list.get(i).getName() + "',phone:'" + tempPhone + "'}";
			} else {
				facility = "{coordinate:{lat:'" + list.get(i).getLat() + "',lng:'" + list.get(i).getLng()
						+ "'},location:'" + list.get(i).getLocation_1() + "',code:'" + list.get(i).getFacility_code()
						+ "',name:'" + list.get(i).getName() + "',phone:'" + list.get(i).getPhone() + "'}";
				;
			}

			data.add(facility);
		}
		JsonArray js = new JsonArray();
		System.out.println(data);
		try {
			js = JsonParser.parseString(data.toString()).getAsJsonArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return js;
		}
	}

	private static final String UPLOAD_DIR = "uploads";

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		File file = new File(UPLOAD_DIR + "/" + fileName);

		Resource resource = (Resource) new FileSystemResource(file);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.body(resource);
	}

	@PostMapping("/audioUpload")
	public ResponseEntity<String> handleAudioUpload(@RequestParam("audio") MultipartFile audioFile) {
		if (audioFile.isEmpty()) {
			return new ResponseEntity<>("업로드된 파일이 없습니다.", HttpStatus.BAD_REQUEST);
		}
		System.out.println(audioFile.toString());
		try {
			// 업로드된 PCM 파일을 WAV로 변환하여 저장합니다.
			byte[] wavData = convertPCMtoWAV(audioFile.getBytes());

			// WAV 파일을 저장할 디렉토리를 설정합니다.
			Path uploadPath = Paths.get(UPLOAD_DIR);
			String filePath = uploadPath.toAbsolutePath().toString() + File.separator + audioFile.getOriginalFilename();

			// 디렉토리가 없으면 생성합니다.
			File dir = new File(uploadPath.toAbsolutePath().toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// WAV 파일을 저장합니다.
			try (OutputStream outputStream = new FileOutputStream(filePath)) {
				outputStream.write(wavData);
			}
			System.out.println(wavData.toString());
			System.out.println(filePath);

			return new ResponseEntity<>("오디오 파일이 성공적으로 업로드되었습니다.", HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("오디오 파일 업로드 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static byte[] generateWavHeader(byte[] pcmData, int sampleRate, int channels, int bitsPerSample)
			throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		// WAV 헤더 생성
		writeString(outputStream, "RIFF"); // ChunkID
		writeInt(outputStream, 36 + pcmData.length); // ChunkSize
		writeString(outputStream, "WAVE"); // Format

		writeString(outputStream, "fmt "); // Subchunk1ID
		writeInt(outputStream, 16); // Subchunk1Size
		writeShort(outputStream, (short) 1); // AudioFormat (PCM)
		writeShort(outputStream, (short) channels); // NumChannels
		writeInt(outputStream, sampleRate); // SampleRate
		writeInt(outputStream, sampleRate * channels * bitsPerSample / 8); // ByteRate
		writeShort(outputStream, (short) (channels * bitsPerSample / 8)); // BlockAlign
		writeShort(outputStream, (short) bitsPerSample); // BitsPerSample

		writeString(outputStream, "data"); // Subchunk2ID
		writeInt(outputStream, pcmData.length); // Subchunk2Size

		// PCM 데이터 추가
		outputStream.write(pcmData);

		return outputStream.toByteArray();
	}

	private static void writeString(ByteArrayOutputStream stream, String value) throws IOException {
		byte[] bytes = value.getBytes();
		stream.write(bytes);
	}

	private static void writeInt(ByteArrayOutputStream stream, int value) throws IOException {
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
		stream.write(bytes);
	}

	private static void writeShort(ByteArrayOutputStream stream, short value) throws IOException {
		byte[] bytes = ByteBuffer.allocate(2).putShort(value).array();
		stream.write(bytes);
	}

	private byte[] convertPCMtoWAV(byte[] pcmData) throws IOException {
		// 여기에 PCM 데이터를 WAV로 변환하는 코드를 작성하세요.
		// 예제로 진행하려면 추가 라이브러리를 사용하거나 WAV 파일 형식에 대한 자세한 이해가 필요합니다.
		// 여기서는 간단한 예제를 제공하고 있습니다.

		// 예제: 간단한 WAV 헤더 추가
		int dataSize = pcmData.length;
		int totalSize = 36 + dataSize; // WAV 헤더 크기 (36 바이트) + PCM 데이터 크기

		// byte[] wavData = new byte[totalSize];

		// WAV 헤더 추가
		// 여기에 WAV 헤더를 생성하고 wavData에 추가하는 코드를 작성하세요.\

		byte[] wavData = generateWavHeader(pcmData, 44100, 1, 16);

		// PCM 데이터 추가
		System.arraycopy(pcmData, 0, wavData, 36, dataSize);

		return wavData;
	}

	@GetMapping("/allOffice")
	public JsonArray getOffice() throws Exception {
		List<OfficeDTO> list = crimeService.allOffice();
		List<String> data = new ArrayList<>();
		Gson gson = new Gson();

		String stations = "{";

		for (int i = 0; i < list.size(); i++) {
			stations = "{coordinate:{lat:" + list.get(i).getLat() + ",lng:" + list.get(i).getLng() + "},office:"
					+ list.get(i).getOffice() + ", crime_data : {murder:" + list.get(i).getMurder() + ",robbery:"
					+ list.get(i).getRobbery() + ",theft:" + list.get(i).getTheft() + ",violence:"
					+ list.get(i).getViolence() + "}}";
			data.add(stations);
		}
		stations = stations + "}";

		System.out.println(stations);
		System.out.println(data);
		JsonArray js = JsonParser.parseString(data.toString()).getAsJsonArray();
		return js;
	}

	@GetMapping("/getOffice")
	public JsonObject getOfficeWithRegion(@RequestParam("lat") double lat, @RequestParam("lng") double lng)
			throws Exception {
//        lat = 37.563185;
//        lng = 126.991361;

		// 서울 서대문 경찰서 근처
//        lat = 37.562956;
//        lng = 126.966359;

		// Query 1 500m
		OfficeDTO dto = crimeService.getOffice(lat, lng);
		// Qurey 2 1000m
		// dto = crimeService.getOffice1km(lat,lng);
		// Qurey 3 1500m

		System.out.println(lat + " " + lng);

		String sendJson = "{coordinate:{lat:" + dto.getLat() + ",lng:" + dto.getLng() + "},office:" + dto.getOffice()
				+ ", crime_data : {murder:" + dto.getMurder() + ",robbery:" + dto.getRobbery() + ",theft:"
				+ dto.getTheft() + ",violence:" + dto.getViolence() + "}}";
		JsonObject newJson = new JsonObject();
		newJson = JsonParser.parseString(sendJson).getAsJsonObject();
		return newJson;
	}

	@GetMapping("/getData/{region}")
	public String getDatas(@PathVariable("region") String region) throws Exception {

		JsonObject newJson = new JsonObject();
		try {
			GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyAQHkfB1xIYWjYbBPuObxZkQ2DTFvudUuk")
					.build();
			GeocodingResult[] results = GeocodingApi.geocode(context, region).await();
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
			for (int i = 0; i < Jarr.size(); i++) {
				JsonObject component = Jarr.get(i).getAsJsonObject();
				System.out.println(component);
				System.out.println(component.size());

				a: for (int j = 0; j < component.size(); j++) {
					JsonArray Jarr_type = component.getAsJsonArray("types");
//                    System.out.println("타입 내부 값 : "+Jarr_type);
//                    System.out.println(Jarr_type.size());
					String Jel_type = Jarr_type.get(0).getAsString();
//                    System.out.println(Jel_type);
					// ADMINISTRATIVE_AREA_LEVEL_1
					if (Jel_type.equals("LOCALITY")) {
						System.out.println("진짜 데이터 : " + component.get("longName"));
						localityLongName = component.get("longName").getAsString();
						break a;
					}

					if (localityLongName == null) {
						if (Jel_type.equals("ADMINISTRATIVE_AREA_LEVEL_1")) {
							System.out.println("진짜 데이터 : " + component.get("longName"));
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

			JsonObject Job_location = Jel_location.getAsJsonObject();

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

			String sendJson = "{location: " + localityLongName + ", coordinate:" + Job_lc + ", crime_data : "
					+ MappingData + "}";

			newJson = JsonParser.parseString(sendJson).getAsJsonObject();
			System.out.println(newJson);

			context.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newJson.toString() + "region : " + region;
	}
}
