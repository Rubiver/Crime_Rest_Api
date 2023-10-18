package com.example.final_crime.services;

import com.example.final_crime.dto.CrimeDTO;
import com.example.final_crime.mappers.CrimeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrimeService {

    @Autowired
    private CrimeMapper crimeMapper;

    public List<CrimeDTO> getCrimeData(String region) throws Exception{

        return crimeMapper.getCrimeData(region);

    }
}
