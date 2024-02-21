package kr.co.takensoft.aiCamera.trouble.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * Trouble DAO 입니다.
 *
 * @author 김예진
 * @since 2024.01.19
 */
@Mapper
public interface TroubleDAO {

    /**
     * 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.19
     */
    public List<HashMap<String, Object>> eqpmnTroubleSelectList(HashMap<String, Object> params) throws Exception;

    /**
     * 장비 장애 목록 COUNT
     *
     * @author 김예진
     * @since 2024.01.19
     */
    public int eqpmnTroubleSelectListCount(HashMap<String, Object> params) throws Exception;

    /**
     * 최신 5개 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> recentEqpmnTroubleSelectList() throws Exception;

    /**
     * 오늘 최신 5개 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.02.01
     */
    public List<HashMap<String, Object>> todayRecentEqpmnTroubleSelectList() throws Exception;

    /**
     * 특정 장비의 최신 5개 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> recentEqpmnTroubleSelectListByEqpmn(HashMap<String, Object> params) throws Exception;


    /**
     * 전체 카메라의 오늘 하루 장애 정보 수 SUM
     *
     * @author 김예진
     * @since 2024.01.26
     */
    public HashMap<String, Object> todayEqpmnTroubleSum() throws Exception;

}
