package kr.co.takensoft.aiCamera.system.service.impl;

import common.util.AuthUtil;
import common.util.CommonUtil;
import common.util.EncryptionUtil;
import kr.co.takensoft.aiCamera.common.PaginationSupport;
import kr.co.takensoft.aiCamera.common.SesssionEventListener;
import kr.co.takensoft.aiCamera.system.dao.UserDAO;
import kr.co.takensoft.aiCamera.system.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  사용자 관련 서비스
 *
 * @author 김재민
 * @since 2024.01.15
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final String salt = "kimjaeminIsJjangJjangManBro";

    /**
     *  사용자 등록
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @Override
    public int userInsert(HashMap<String, Object> params) throws Exception {
        //사용자 사용 상태
        params.put("use_yn","Y");
        String user_pw = params.get("user_pw").toString();
        String user_id = params.get("user_id").toString();
        String user_name = params.get("user_name").toString();
        String user_eml = params.get("user_eml").toString();
        String user_telno = params.get("user_telno").toString();


        params.replace("user_pw",EncryptionUtil.oneWayEncrypt(user_pw + user_id));
        params.replace("user_pw", EncryptionUtil.oneWayEncrypt((String)params.get("user_pw") + user_id));
        //암호화
        params.replace("user_name",EncryptionUtil.dbEncrypt(salt, user_name));
        params.replace("user_eml",EncryptionUtil.dbEncrypt(salt, user_eml));
        params.replace("user_telno",EncryptionUtil.dbEncrypt(salt, user_telno));


        return userDAO.userInsert(params);
    }


    /**
     *  사용자 목록 조회
     *
     * @author 김재민
     * @since 2024.01.15
     */

    @Override
    public List<HashMap<String, Object>> userSelectList(HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = Integer.parseInt(params.get("perPage").toString());
        int pagingRowIndex = PaginationSupport.pagingRowIndexForPostgreSql(currentPage, perPage);
        params.put("pagingRowIndex", pagingRowIndex);
        params.replace("perPage",perPage);

        if(params.get("searchText") != "" && "user_name".equals(params.get("searchType"))) {
            params.replace("searchText", EncryptionUtil.dbEncrypt(salt, params.get("searchText").toString()));
        }
        if(params.get("searchText") != "" && "user_eml".equals(params.get("searchType"))) {
            params.replace("searchText", EncryptionUtil.dbEncrypt(salt, params.get("searchText").toString()));
        }
        if(params.get("searchText") != "" && "user_telno".equals(params.get("searchType"))) {
            params.replace("searchText", EncryptionUtil.dbEncrypt(salt, params.get("searchText").toString()));
        }

        List<HashMap<String,Object>> list =  userDAO.userSelectList(params);
        for(HashMap<String,Object> userOne : list){
            //복호화
            userOne.replace("user_name",EncryptionUtil.dbDecrypt(salt ,userOne.get("user_name").toString()));
            userOne.replace("user_eml",EncryptionUtil.dbDecrypt(salt ,userOne.get("user_eml").toString()));
            userOne.replace("user_telno",EncryptionUtil.dbDecrypt(salt ,userOne.get("user_telno").toString()));
        }
        return list;
    }

    /**
     *  사용자 목록 COUNT
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @Override
    public int userSelectListCount (HashMap<String, Object> params) throws Exception {
        return userDAO.userSelectListCount(params);
    }

    /**
     *  사용자 선택 삭제
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @Override
    public int userDelete(HashMap<String, Object> params) throws Exception {
        int result = 0;
        List<HashMap<String, Object>> deleteList = (ArrayList<HashMap<String, Object>>) params.get("deleteList");
        for (HashMap<String, Object> deleteOne : deleteList) {
            result += userDAO.userDelete(deleteOne);
        }
        return result;
    }

    /**
     *  사용자 상세 조회
     *
     * @author 김재민
     * @since 2024.01.30
     * @return
     */
    @Override
    public HashMap<String, Object> userSelectOne(HashMap<String, Object> params) throws Exception {
        HashMap<String, Object> userOne = userDAO.userSelectOne(params);

        userOne.replace("user_name",EncryptionUtil.dbDecrypt(salt ,userOne.get("user_name").toString()));
        userOne.replace("user_eml",EncryptionUtil.dbDecrypt(salt ,userOne.get("user_eml").toString()));
        userOne.replace("user_telno",EncryptionUtil.dbDecrypt(salt ,userOne.get("user_telno").toString()));
        return userOne;
    }


    /**
     *  사용자 수정
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @Override
    public int userUpdate (HashMap<String, Object> params) throws Exception {
        String user_name = params.get("user_name").toString();
        String user_eml = params.get("user_eml").toString();
        String user_telno = params.get("user_telno").toString();
        //암호화
        params.replace("user_name",EncryptionUtil.dbEncrypt(salt, user_name));
        params.replace("user_eml",EncryptionUtil.dbEncrypt(salt, user_eml));
        params.replace("user_telno",EncryptionUtil.dbEncrypt(salt, user_telno));

        return userDAO.userUpdate(params);
    }

    /**
     *  사용자 마이 페이지 정보 수정
     *
     * @author 김재민
     * @since 2024.02.05
     */
    @Override
    public int MyUserUpdate (HashMap<String, Object> params) throws Exception {
        String user_name = params.get("user_name").toString();
        String user_eml = params.get("user_eml").toString();
        String user_telno = params.get("user_telno").toString();
        //암호화
        params.replace("user_name",EncryptionUtil.dbEncrypt(salt, user_name));
        params.replace("user_eml",EncryptionUtil.dbEncrypt(salt, user_eml));
        params.replace("user_telno",EncryptionUtil.dbEncrypt(salt, user_telno));

        return userDAO.MyUserUpdate(params);
    }

    /**
     *  사용자 비밀 번호 확인
     *
     * @author 김재민
     * @since 2024.01.31
     */
    @Override
    public int userPwCheck (HashMap<String, Object> params) throws Exception {
        String user_id = params.get("user_id").toString();
        String old_pw = params.get("old_pw").toString();
        //사용자 가 입력한 기존 비밀 번호 확인
        params.replace("old_pw",EncryptionUtil.oneWayEncrypt(old_pw + user_id));
        params.replace("old_pw", EncryptionUtil.oneWayEncrypt((String)params.get("old_pw") + user_id));

        return userDAO.userPwCheck(params);
    }

    /**
     *  사용자 비밀 번호 변경
     *
     * @author 김재민
     * @since 2024.01.31
     */
    @Override
    public int userPwUpdate (HashMap<String, Object> params) throws Exception {
        String user_id = params.get("user_id").toString();
        String new_pw = params.get("new_pw").toString();
        //사용자 가 변경한 비밀 번호 암호화
        params.replace("new_pw", EncryptionUtil.oneWayEncrypt(new_pw + user_id));
        params.replace("new_pw", EncryptionUtil.oneWayEncrypt((String)params.get("new_pw") + user_id));

        return userDAO.userPwUpdate(params);
    }

    /**
     *  사용자 비밀 번호 초기화
     *
     * @author 김재민
     * @since 2024.01.31
     */
    @Override
    public int userResetPw(HashMap<String, Object> params) throws Exception {

        String user_id = params.get("user_id").toString();

        //초기화 값인 user_id를 비밀 번호 암호화 방식과 동일 하게 암호화
        params.replace("user_pw", EncryptionUtil.oneWayEncrypt(user_id + user_id));
        params.replace("user_pw", EncryptionUtil.oneWayEncrypt((String)params.get("user_pw") + user_id));

        return userDAO.userResetPw(params);
    }


    /**
     *  사용자 등록 시 ID 중복 확인
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @Override
    public int userIdDuplicateCheck(HashMap<String, Object> params) throws Exception {
        return userDAO.userIdDuplicateCheck(params);
    }

    /**
     *  사용자 로그인
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @Override
    public HashMap<String,Object> userLogin(HashMap<String, Object> params) throws Exception {
        String user_id = params.get("user_id").toString();
        String user_pw = params.get("user_pw").toString();
        params.replace("user_pw", EncryptionUtil.oneWayEncrypt(user_pw + user_id));
        params.replace("user_pw", EncryptionUtil.oneWayEncrypt((String) params.get("user_pw") + user_id));

        boolean isLoginSuccess = false;
        HashMap<String, Object> beforeLoginUser = userDAO.userLogin(params);

        if (beforeLoginUser == null) {
            // 로그인 실패 처리
            return null;
        }

        // 로그인 성공 여부 확인
        if (beforeLoginUser != null && beforeLoginUser.containsKey("user_id")) {
            isLoginSuccess = true;

            /* 로그인 처리 이후, 세션처리(시작) */
            //세션 무효화처리
            userLogout();
            //세션: 무효화

            String userId = beforeLoginUser.get("user_id").toString();
            //현재 로그인할 userId와 동일한 로그인 session을 가진 session ID 목록 조회
            List<String> sessionIds = SesssionEventListener.duplicationLoginSessionIdSelectListByUserId(userId);
            /*
             * 현재 로그인 요청한 userId와 동일한 접속 session이 1명 이상 있고,
             * 중복 로그인 가능 유무가 true이면 -> 중복 로그인 처리를 진행
             * 중복 로그인 가능 유무가 false이면 -> 기존 로그인 유저를 session에서 제거
             * */
            if (sessionIds.size() > 0 && AuthUtil.IS_POSSIBLE_DUPLICATION_LOGIN == false) {
                //기존 로그인 유저 session에서 제거
                SesssionEventListener.duplicationLoginSessionDeleteByUserId(userId);
            }

            //현재 Http Request 객체 가지고 오기
            HttpServletRequest request = CommonUtil.getHttpServletRequest();
            //기존 session이 존재하더라도 제거하고 새로운 세션 생성
            HttpSession session = request.getSession(true);
            //세션 timeout
            session.setMaxInactiveInterval(AuthUtil.SESSION_MAX_TIME);
            /* 로그인 세션 생성 */
            System.out.println("login sessions test1: " + session);
            System.out.println("AuthUtil.LOGIN_USER_SESSION = " + AuthUtil.LOGIN_USER_SESSION);
            System.out.println("beforeLoginUser = " + beforeLoginUser);

            session.setAttribute(AuthUtil.LOGIN_USER_SESSION, beforeLoginUser);

            session.setAttribute("loginUser", beforeLoginUser);
            Object loginUser = session.getAttribute("loginUser");
            String getSession = loginUser.toString();
            System.out.println("getSession = " + getSession);

            /* 로그인 처리이후, 세션처리(종료) */

            // 접속 타입
            String device_ty = "";
            String userAgent = request.getHeader("User-Agent").toUpperCase();
            if (userAgent.indexOf("MOBI") > -1 || userAgent.indexOf("MOBILE") > -1) {
                device_ty = "MOBILE";
            } else {
                device_ty = "PC";
            }
            beforeLoginUser.put("device_ty", device_ty);

            String ip = null;

            ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-RealIP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("REMOTE_ADDR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            beforeLoginUser.put("login_ip", ip);

            LocalDateTime date = LocalDateTime.now();
            String stats_date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS"));
            beforeLoginUser.put("stats_date", stats_date);


            if (beforeLoginUser == null) {
                beforeLoginUser = new HashMap<>();
            }
            //로그인 성공 정보
            beforeLoginUser.put("isLoginSuccess", isLoginSuccess);

            //user_name 복호화
            beforeLoginUser.replace("user_name",EncryptionUtil.dbDecrypt(salt ,beforeLoginUser.get("user_name").toString()));
            String user_name = null;
            beforeLoginUser.put("loginUser_name", user_name);

            //접속 기록 추가
            int result = userDAO.userAccessRcordInsert(beforeLoginUser);
            if (result > 0) {
                return beforeLoginUser;
            } else {
                return null;
            }
        } else {
            // 로그인 실패 처리
            return null;
        }
    }

    /**
     * @author 김재민
     * @since 2024.01.25
     *
     * 세션 무효화 (로그아웃 처리)
     */
    public boolean userLogout () throws Exception {
        boolean isLogoutSuccess = true;
        try {
            //현재 Http Request의 객체 가지고 오기
            HttpServletRequest request = CommonUtil.getHttpServletRequest();
            //기존 session이 존재하면 session값을 받아오고, 존재하지 않으면 null을 반환
            HttpSession session = request.getSession(false);
            //기존 session이 존재하면 -> session 삭제
            if(session != null){
                session.invalidate();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
            isLogoutSuccess = false;
        }
        return isLogoutSuccess;
    }

    /**
     * @author 김재민
     * @since 2024.01.25
     *
     * 사용자 접속 기록 등록
     */
    @Override
    public int userAccessRcordInsert(HashMap<String, Object> params) throws Exception {
        return userDAO.userAccessRcordInsert(params);
    }

    /** ******************** 유저 접속 기록 ******************** */

    /**
     *  사용자 접속 기록 조회
     *
     * @author 김재민
     * @since 2024.01.22
     */
    @Override
    public List<HashMap<String, Object>> userAccessRcordSelectList(HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = Integer.parseInt(params.get("perPage").toString());
        int pagingRowIndex = PaginationSupport.pagingRowIndexForPostgreSql(currentPage, perPage);
        params.put("pagingRowIndex", pagingRowIndex);
        params.replace("perPage",perPage);
        System.out.println(params);
        return userDAO.userAccessRcordSelectList(params);
    }

    /**
     *  사용자 접속 기록 조회 COUNT
     *
     * @author 김재민
     * @since 2024.01.22
     */

    @Override
    public int userAccessRcordSelectListCount (HashMap<String, Object> params) throws Exception {
        return userDAO.userAccessRcordSelectListCount(params);
    }


}
