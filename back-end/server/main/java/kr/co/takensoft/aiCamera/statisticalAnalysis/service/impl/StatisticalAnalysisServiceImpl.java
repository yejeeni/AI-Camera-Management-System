package kr.co.takensoft.aiCamera.statisticalAnalysis.service.impl;

import kr.co.takensoft.aiCamera.statisticalAnalysis.dao.StatisticalAnalysisDAO;
import kr.co.takensoft.aiCamera.statisticalAnalysis.service.StatisticalAnalysisService;
import kr.co.takensoft.aiCamera.statusControl.dao.StatusControlDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticalAnalysisServiceImpl implements StatisticalAnalysisService {

    private final StatisticalAnalysisDAO statisticalAnalysisDAO;

    /**
     * 객체 인식 데이터의 시간별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @Override
    public List<HashMap<String, Object>> hourlyAnalsDetectData(HashMap<String, Object> params) throws Exception {
        List<HashMap<String, Object>> result = statisticalAnalysisDAO.hourlyAnalsDetectData(params);
        if (result.size() > 0) {
            return statisticalAnalysisDAO.hourlyAnalsDetectData(params);
        } else {
            return emptyObjectDetectData();
        }

    }

    /**
     * 객체 인식 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @Override
    public List<HashMap<String, Object>> dailyAnalsDetectData(HashMap<String, Object> params) throws Exception {
        List<HashMap<String, Object>> result = statisticalAnalysisDAO.dailyAnalsDetectData(params);
        if (result.size() > 0) {
            return statisticalAnalysisDAO.dailyAnalsDetectData(params);
        } else {
            return emptyObjectDetectData();
        }
    }

    /**
     * 객체 인식 데이터의 월별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @Override
    public List<HashMap<String, Object>> monthlyAnalsDetectData(HashMap<String, Object> params) throws Exception {
        List<HashMap<String, Object>> result = statisticalAnalysisDAO.monthlyAnalsDetectData(params);
        if (result.size() > 0) {
            return result;
        } else {
            return emptyObjectDetectData();
        }
    }

    /**
     * 장비 장애 데이터의 시간별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @Override
    public List<HashMap<String, Object>> hourlyAnalsTroubleData(HashMap<String, Object> params) throws Exception {
        List<HashMap<String, Object>> result = statisticalAnalysisDAO.hourlyAnalsTroubleData(params);
        if (result.size() > 0) {
            return result;
        } else {
            return emptyEqpmnTroubleData();
        }


    }

    /**
     * 장비 장애 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.25
     */
    @Override
    public List<HashMap<String, Object>> dailyAnalsTroubleData(HashMap<String, Object> params) throws Exception {
        List<HashMap<String, Object>> result = statisticalAnalysisDAO.dailyAnalsTroubleData(params);
        if (result.size() > 0) {
            return result;
        } else {
            return emptyEqpmnTroubleData();
        }
    }

    /**
     * 장비 장애 데이터의 월별 산출
     *
     * @author 김예진
     * @since 2024.01.25
     */
    @Override
    public List<HashMap<String, Object>> monthlyAnalsTroubleData(HashMap<String, Object> params) throws Exception {
        List<HashMap<String, Object>> result = statisticalAnalysisDAO.monthlyAnalsTroubleData(params);
        if (result.size() > 0) {
            return result;
        } else {
            return emptyEqpmnTroubleData();
        }
    }

    /**
     * 빈 객체인식 통계 데이터를 생성하는 메서드
     *
     * @author 김예진
     * @since 2024.02.13
     */
    private List<HashMap<String, Object>> emptyObjectDetectData() {
        ArrayList<HashMap<String, Object>> dataArray = new ArrayList<>();

        HashMap<String, Object> data = new HashMap<>();
        data.put("year", "0");
        data.put("버스", 0);
        data.put("보행자", 0);
        data.put("승용차", 0);
        data.put("오토바이", 0);
        data.put("자전거", 0);
        data.put("트럭", 0);
        dataArray.add(data);

        return dataArray;
    }

    /**
     * 빈 장비장애 통계 데이터를 생성하는 메서드
     *
     * @author 김예진
     * @since 2024.02.13
     */
    private List<HashMap<String, Object>> emptyEqpmnTroubleData() {
        ArrayList<HashMap<String, Object>> dataArray = new ArrayList<>();

        HashMap<String, Object> data = new HashMap<>();
        data.put("date", "0");
        data.put("value1", 0);
        data.put("value2", 0);
        data.put("value3", 0);
        data.put("총합", 0);
        dataArray.add(data);

        return dataArray;
    }


}
