package kr.co.takensoft.aiCamera.statisticalAnalysis.service;

import java.util.HashMap;
import java.util.List;

public interface StatisticalAnalysisService {

    /**
     * 객체 인식 데이터의 시간별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> hourlyAnalsDetectData(HashMap<String, Object> params) throws Exception;

    /**
     * 객체 인식 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> dailyAnalsDetectData(HashMap<String, Object> params) throws Exception;

    /**
     * 객체 인식 데이터의 월별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> monthlyAnalsDetectData(HashMap<String, Object> params) throws Exception;


    /**
     * 장비 장애 데이터의 시간별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> hourlyAnalsTroubleData(HashMap<String, Object> params) throws Exception;

    /**
     * 장비 장애 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.25
     */
    public List<HashMap<String, Object>> dailyAnalsTroubleData(HashMap<String, Object> params) throws Exception;

    /**
     * 장비 장애 데이터의 월별 산출
     *
     * @author 김예진
     * @since 2024.01.25
     */
    public List<HashMap<String, Object>> monthlyAnalsTroubleData(HashMap<String, Object> params) throws Exception;
}
