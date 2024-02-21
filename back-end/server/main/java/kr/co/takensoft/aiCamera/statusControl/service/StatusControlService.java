package kr.co.takensoft.aiCamera.statusControl.service;

import java.util.HashMap;
import java.util.List;

/**
 * statusControl 관련 서비스 interface 입니다.
 *
 * @author 김예진
 * @since 2024.01.15
 */
public interface StatusControlService {

    /**
     * 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    public HashMap<String, Object> todayObjectDetectEventSumByAllEquipment() throws Exception;

    /**
     * 특정 카메라의 오늘 누적 인식 수 COUNT 조회
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
     * @since 2024.01.96
     */
    public List<HashMap<String, Object>>recentObjectDetectEventByEqpmn(HashMap<String, Object> params) throws Exception;


}
