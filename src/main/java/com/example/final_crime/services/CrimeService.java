package com.example.final_crime.services;

import com.example.final_crime.dto.CenterDTO;
import com.example.final_crime.dto.CrimeDTO;
import com.example.final_crime.dto.FacilityDTO;
import com.example.final_crime.dto.OfficeDTO;
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

    public List<OfficeDTO> allOffice() throws Exception{
        return crimeMapper.allOffice();
    }

    public List<FacilityDTO> allFacility() throws Exception{
        return crimeMapper.allFacility();
    }

    public List<CenterDTO> allCenter() throws Exception{
        return crimeMapper.allCenter();
    }
}
