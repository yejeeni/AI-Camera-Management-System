package kr.co.takensoft.aiCamera.statusControl.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * statusControl DAO 입니다.
 *
 * @author 김예진
 * @since 2024.01.15
 */
@Mapper
public interface StatusControlDAO {

    /**
     * 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    public HashMap<String, Object> todayObjectDetectEventSumByAllEquipment() throws Exception;

    /**
     * 특정 카메라 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    public HashMap<String, Object> todayObjectDetectEventSumByEquipment(HashMap<String, Object> params) throws Exception;

    /**
     * 객체 인식 목록 COUNT
     *
     * @author 김예진
     * @since 2024.01.15
     */
    public int objectDetectEventSelectListCount(HashMap<String, Object> params) throws Exception;

    /**
     * 객체 인식 목록 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    public List<HashMap<String, Object>> objectDetectEventSelectList(HashMap<String, Object> params) throws Exception;

    /**
     * 오늘 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    public List<HashMap<String, Object>> todayRecentObjectDetectEventList() throws Exception;

    /**
     * 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    public List<HashMap<String, Object>> recentObjectDetectEventList() throws Exception;

    /**
     * 특정 장비의 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.29
     */
    public List<HashMap<String, Object>>recentObjectDetectEventByEqpmn(HashMap<String, Object> params) throws Exception;

}
