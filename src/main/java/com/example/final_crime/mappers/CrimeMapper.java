package com.example.final_crime.mappers;

import com.example.final_crime.dto.CrimeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrimeMapper {
    public List<CrimeDTO> getCrimeData(@Param("region") String region) throws Exception;
}
