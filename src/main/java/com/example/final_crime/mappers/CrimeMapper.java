package com.example.final_crime.mappers;

import com.example.final_crime.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CrimeMapper {
    public List<CrimeDTO> getCrimeData(@Param("region") String region) throws Exception;

    public OfficeDTO getOfficeData() throws Exception;

    public OfficeDTO getOffice(Map<String,Double> data) throws Exception;

    public OfficeDTO getOffice1km(Map<String,Double> data) throws Exception;

    public List<OfficeDTO> allOffice() throws Exception;

    public List<FacilityDTO> allFacility() throws Exception;

    public List<CenterDTO> allCenter() throws Exception;

    public int uploadLocation(UploadDTO dto) throws Exception;
}
