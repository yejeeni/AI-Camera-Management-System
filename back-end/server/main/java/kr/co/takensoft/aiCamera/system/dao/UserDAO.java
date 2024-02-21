package kr.co.takensoft.aiCamera.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * UserDAO
 *
 * @author 김재민
 * @since 2024.01.15
 */
@Mapper
public interface UserDAO {

    /**
     * 사용자 등록
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public int userInsert(HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 목록 조회
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public List<HashMap<String, Object>> userSelectList(HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 목록 COUNT 조회
     *
     * @author 김재민
     * @since 2023.10.31
     */
    public int userSelectListCount (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 삭제
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public int userDelete (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 수정
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public int userUpdate (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 수정
     *
     * @author 김재민
     * @since 2024.02.05
     */
    public int MyUserUpdate (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 비밀 번호 확인
     *
     * @author 김재민
     * @since 2024.01.31
     */
    public int userPwCheck (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 비밀 번호 변경
     *
     * @author 김재민
     * @since 2024.01.31
     */
    public int userPwUpdate (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 비밀 번호 초기화
     *
     * @author 김재민
     * @since 2024.01.24
     */
    public int userResetPw (HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 ID 중복 확인
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public int userIdDuplicateCheck(HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 로그인
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public HashMap<String,Object> userLogin(HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 로그 아웃
     *
     * @author 김재민
     * @since 2024.01.26
     */
    public boolean userLogout() throws Exception;

    /**
     * 사용자 상세 조회
     *
     * @author 김재민
     * @since 2024.01.30
     */

    public HashMap<String,Object> userSelectOne(HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 접속 기록 등록
     *
     * @author 김재민
     * @since 2024.01.15
     */
    public int userAccessRcordInsert(HashMap<String, Object> params) throws Exception;


    /**
     * 사용자 접속 기록 조회
     *
     * @author 김재민
     * @since 2024.01.22
     */
    public List<HashMap<String, Object>> userAccessRcordSelectList(HashMap<String, Object> params) throws Exception;

    /**
     * 사용자 접속 기록 조회 COUNT
     *
     * @author 김재민
     * @since 2024.01.22
     */
    public int userAccessRcordSelectListCount (HashMap<String, Object> params) throws Exception;
}
