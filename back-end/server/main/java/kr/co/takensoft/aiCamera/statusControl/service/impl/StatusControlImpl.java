package kr.co.takensoft.aiCamera.statusControl.service.impl;

import kr.co.takensoft.aiCamera.common.PaginationSupport;
import kr.co.takensoft.aiCamera.statusControl.dao.StatusControlDAO;
import kr.co.takensoft.aiCamera.statusControl.service.StatusControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 *  statusControl 관련 서비스 로직 입니다.
 *
 * @author 김예진
 * @since 2024.01.15
 */

@Service
@RequiredArgsConstructor
public class StatusControlImpl implements StatusControlService {

    private final StatusControlDAO statusControlDAO;

    /**
     * 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    @Override
    public HashMap<String, Object> todayObjectDetectEventSumByAllEquipment() throws Exception {
        HashMap<String, Object> result = statusControlDAO.todayObjectDetectEventSumByAllEquipment();
        if (result != null) {
            return result;
        } else {
            return getEmptyHashMap();
        }
    }

    /**
     * 빈 객체인식 데이터를 생성하는 메서드
     *
     * @author 김예진
     * @since 2024.02.07
     */
    private HashMap<String, Object> getEmptyHashMap() {
        HashMap<String, Object> emptyData = new HashMap<>();
        // 빈 데이터
        emptyData.put("보행자", 0);
        emptyData.put("자전거", 0);
        emptyData.put("승용차", 0);
        emptyData.put("오토바이", 0);
        emptyData.put("버스", 0);
        emptyData.put("트럭", 0);

        return emptyData;
    }

    /**
     * 특정 카메라 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    @Override
    public HashMap<String, Object> todayObjectDetectEventSumByEquipment(HashMap<String, Object> params) throws Exception {
        return statusControlDAO.todayObjectDetectEventSumByEquipment(params);
    }

    /**
     * 객체 인식 목록 수 COUNT
     *
     * @author 김예진
     * @since 2024.01.15
     */
    @Override
    public int objectDetectEventSelectListCount(HashMap<String, Object> params) throws Exception {
        return statusControlDAO.objectDetectEventSelectListCount(params);
    }

    /**
     * 객체 인식 목록 조회
     *
     * @author 김예진
     * @since 2024.01.16
     */
    @Override
    public List<HashMap<String, Object>> objectDetectEventSelectList(HashMap<String, Object> params) throws Exception{
        int currentPage = (int) params.get("currentPage");
        int perPage = Integer.parseInt(params.get("perPage").toString());
        int pagingRowIndex = PaginationSupport.pagingRowIndexForPostgreSql(currentPage, perPage);
        params.put("pagingRowIndex", pagingRowIndex);
        params.replace("perPage", perPage);

        return statusControlDAO.objectDetectEventSelectList(params);
    }
    /**
     * 오늘 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    @Override
    public List<HashMap<String, Object>> todayRecentObjectDetectEventList() throws Exception {
        return statusControlDAO.todayRecentObjectDetectEventList();
    }

    /**
     * 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    @Override
    public List<HashMap<String, Object>> recentObjectDetectEventList() throws Exception {
        return statusControlDAO.recentObjectDetectEventList();
    }

    /**
     * 특정 장비의 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.96
     */
    @Override
    public List<HashMap<String, Object>>recentObjectDetectEventByEqpmn(HashMap<String, Object> params) throws Exception {
        return statusControlDAO.recentObjectDetectEventByEqpmn(params);
    }


}
