package com.example.final_crime.services;

import com.example.final_crime.dto.*;
import com.example.final_crime.mappers.CrimeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrimeService {

    @Autowired
    private CrimeMapper crimeMapper;

    public List<CrimeDTO> getCrimeData(String region) throws Exception{

        return crimeMapper.getCrimeData(region);
    }

    public OfficeDTO getOfficeData() throws Exception{
        return crimeMapper.getOfficeData();
    }

    public OfficeDTO getOffice(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getOffice(data);
    }

    public OfficeDTO getOffice1km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getOffice1km(data);
    }

    public OfficeDTO getOffice2km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getOffice2km(data);
    }

    public OfficeDTO getOffice10km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getOffice10km(data);
    }

    public CenterDTO getCenter(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getCenter(data);
    }

    public CenterDTO getCenter1km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getCenter1km(data);
    }

    public CenterDTO getCenter2km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getCenter2km(data);
    }

    public CenterDTO getCenter10km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getCenter10km(data);
    }

    public CenterDTO getCenter20km(double lat, double lng) throws Exception{
        Map<String,Double> data = new HashMap<>();
        data.put("lat",lat);
        data.put("lng",lng);
        return crimeMapper.getCenter20km(data);
    }

    public List<OfficeDTO> allOffice() throws Exception{
        return crimeMapper.allOffice();
    }

    public List<FacilityDTO> allFacility() throws Exception{
        return crimeMapper.allFacility();
    }

    public List<CenterDTO> allCenter() throws Exception{
        return crimeMapper.allCenter();
    }

    public int uploadLocation(UploadDTO dto) throws Exception{
        return crimeMapper.uploadLocation(dto);
    }
}
