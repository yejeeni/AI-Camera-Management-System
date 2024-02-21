package kr.co.takensoft.aiCamera.trouble.service.impl;

import kr.co.takensoft.aiCamera.common.PaginationSupport;
import kr.co.takensoft.aiCamera.data.service.ErrorListComparison;
import kr.co.takensoft.aiCamera.trouble.dao.TroubleDAO;
import kr.co.takensoft.aiCamera.trouble.service.TroubleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TroubleImpl implements TroubleService {

    private final TroubleDAO troubleDAO;

    /**
     * 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.19
     */
    @Override
    public List<HashMap<String, Object>> eqpmnTroubleSelectList(HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = Integer.parseInt(params.get("perPage").toString());
        int pagingRowIndex = PaginationSupport.pagingRowIndexForPostgreSql(currentPage, perPage);
        params.put("pagingRowIndex", pagingRowIndex);
        params.replace("perPage", perPage);

        return troubleDAO.eqpmnTroubleSelectList(params);
    }

    /**
     * 장비 장애 목록 COUNT
     *
     * @author 김예진
     * @since 2024.01.19
     */
    @Override
    public int eqpmnTroubleSelectListCount(HashMap<String, Object> params) throws Exception {
        return troubleDAO.eqpmnTroubleSelectListCount(params);
    }

    /**
     * 최신 5개 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.23
     */
    @Override
    public List<HashMap<String, Object>> recentEqpmnTroubleSelectList() throws Exception {
        return troubleDAO.recentEqpmnTroubleSelectList();
    }

    /**
     * 오늘 최신 5개 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.02.01
     */
    @Override
    public List<HashMap<String, Object>> todayRecentEqpmnTroubleSelectList() throws Exception {
        return troubleDAO.todayRecentEqpmnTroubleSelectList();
    }

    /**
     * 특정 장비의 최신 5개 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @Override
    public List<HashMap<String, Object>> recentEqpmnTroubleSelectListByEqpmn(HashMap<String, Object> params) throws Exception {
        return troubleDAO.recentEqpmnTroubleSelectListByEqpmn(params);
    }

    /**
     * 전체 카메라의 오늘 하루 장애 정보 수 SUM
     *
     * @author 김예진
     * @since 2024.01.26
     */
    @Override
    public HashMap<String, Object> todayEqpmnTroubleSum() throws Exception {
        HashMap<String, Object> result = troubleDAO.todayEqpmnTroubleSum();
        if (result != null) {
            return result;
        } else {
            return getEmptyHashMap();
        }
    }

    /**
     * 빈 장비장애 데이터를 생성하는 메서드
     *
     * @author 김예진
     * @since 2024.02.07
     */
    private HashMap<String, Object> getEmptyHashMap() {
        HashMap<String, Object> emptyData = new HashMap<>();
        // 빈 데이터
        emptyData.put("CPU", 0);
        emptyData.put("메모리", 0);
        emptyData.put("연결", 0);

        return emptyData;
    }

}
