package kr.co.takensoft.aiCamera.system.web;


import common.util.AuthUtil;
import kr.co.takensoft.aiCamera.popup.service.PopupService;
import kr.co.takensoft.aiCamera.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * User Controller
 *
 * @author 김재민
 * @since 2023.10.30
 */

//@Controller
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //세션에 저장 되어 있는 user_id 를 getUserId에 저장
    private String getUserId(HttpServletRequest request){
        HttpSession session = request.getSession();
        HashMap<String, Object> loginUser = (HashMap<String, Object>) session.getAttribute("loginUser");
        return loginUser.get("user_id").toString();
    }

    /**
     *  사용자 등록
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @RequestMapping(value = "/userInsert.json", method = RequestMethod.POST)
    public ModelAndView userInsert(HttpServletRequest request, @RequestBody HashMap<String, Object> params) throws Exception {
        //세션 에서 받아온 user_id로 등록자 ID 추가
        params.put("rgtr_id", getUserId(request));
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userInsert(params));
        return mav;
    }


    /**
     *  사용자 로그인 여부 조회
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @RequestMapping(value = "/loginUserSelectOne.json", method = RequestMethod.POST)
    public ModelAndView loginUserSelectOne () throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        HashMap<String, Object> check= AuthUtil.getLoginUser();
        mav.addObject("loginUser", check);
        HashMap<String, Object> check2= AuthUtil.getKey();
        mav.addObject("key", check2);
        return mav;
    }

    /**
     *  사용자 목록 조회
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @RequestMapping(value = "/userSelectList.json", method = RequestMethod.POST)
    public ModelAndView userSelectList(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("userSelectList", userService.userSelectList(params));
        mav.addObject("userSelectListCount", userService.userSelectListCount(params));
        return mav;

    }

    /**
     *  사용자 수정
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @RequestMapping(value = "/userUpdate.json", method = RequestMethod.POST)
    public ModelAndView userUpdate(HttpServletRequest request, @RequestBody HashMap<String, Object> params) throws Exception {
        // 세션 에서 받아온 user_id로 수정자 ID 추가
        params.put("mdfr_id", getUserId(request));
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userUpdate(params));
        return mav;
    }

    /**
     *  사용자 마이 페이지 정보 수정
     *
     * @author 김재민
     * @since 2024.02.05
     */
    @RequestMapping(value = "/MyUserUpdate.json", method = RequestMethod.POST)
    public ModelAndView MyUserUpdate(HttpServletRequest request, @RequestBody HashMap<String, Object> params) throws Exception {
        // 세션 에서 받아온 user_id로 수정자 ID 추가
        params.put("mdfr_id", getUserId(request));
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.MyUserUpdate(params));
        return mav;
    }

    /**
     *  사용자 비밀 번호 초기화
     *
     * @author 김재민
     * @since 2024.01.23
     */
    @RequestMapping(value = "/userResetPw.json", method = RequestMethod.POST)
    public ModelAndView userResetPw(HttpServletRequest request, @RequestBody HashMap<String, Object> params) throws Exception {
        // 세션 에서 받아온 user_id로 수정자 ID 추가
        params.put("mdfr_id", getUserId(request));
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userResetPw(params));
        return mav;
    }
    /**
     *  사용자 비밀 번호 확인
     *
     * @author 김재민
     * @since 2024.01.31
     */
    @RequestMapping(value = "/userPwCheck.json", method = RequestMethod.POST)
    public ModelAndView userPwCheck(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userPwCheck(params));
        return mav;
    }

    /**
     *  사용자 비밀 번호 변경
     *
     * @author 김재민
     * @since 2024.01.31
     */
    @RequestMapping(value = "/userPwUpdate.json", method = RequestMethod.POST)
    public ModelAndView userPwUpdate(HttpServletRequest request, @RequestBody HashMap<String, Object> params) throws Exception {
        // 세션 에서 받아온 user_id로 수정자 ID 추가
        params.put("mdfr_id", getUserId(request));
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userPwUpdate(params));
        return mav;
    }

    /**
     *  사용자 삭제
     *
     * @author 김재민
     * @since 2024.01.15
     */
    @RequestMapping(value = "/userDelete.json", method = RequestMethod.POST)
    public ModelAndView userDelete(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userDelete(params));
        return mav;
    }

    /**
     *  사용자 상세 조회
     *
     * @author 김재민
     * @since 2024.01.30
     */
    @RequestMapping(value = "/userSelectOne.json", method = RequestMethod.POST)
    public ModelAndView userSelectOne(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userSelectOne(params));
        return mav;
    }

    /**
     *  회원가입 시 ID 중복 확인
     *
     * @author 김재민
     * @since 2024.01.15
     */

    @RequestMapping(value = "/userIdDuplicateCheck.json", method = RequestMethod.POST)
    public ModelAndView userIdDuplicateCheck(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userIdDuplicateCheck(params));
        return mav;
    }

    /**
     *  사용자 로그인
     *
     * @author 김재민
     * @since 2024.01.15
     */

    @RequestMapping(value = "/userLogin.json", method = RequestMethod.POST)
    public ModelAndView userLogin(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userLogin(params));
        return mav;
    }

    /**
     *  사용자 로그 아웃
     *
     * @author 김재민
     * @since 2024.01.26
     */

    @RequestMapping(value = "/userLogout.json", method = RequestMethod.POST)
    public ModelAndView userLogout(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userLogout());
        return mav;
    }

    /** ******************** 사용자 접속 기록 ******************** */

    /**
     *  사용자 접속 기록 등록
     *
     * @author 김재민
     * @since 2024.01.25
     */
    @RequestMapping(value = "/userAccessRcordInsert.json", method = RequestMethod.POST)
    public ModelAndView userAccessRcordInsert(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", userService.userAccessRcordInsert(params));
        return mav;
    }

    /**
     *  사용자 접속 기록 조회
     *
     * @author 김재민
     * @since 2024.01.22
     */

    @RequestMapping(value = "/userAccessRcordSelectList.json", method = RequestMethod.POST)
    public ModelAndView userAccessLogSelectList(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("userAccessRcordSelectList", userService.userAccessRcordSelectList(params));
        mav.addObject("userAccessRcordSelectListCount", userService.userAccessRcordSelectListCount(params));

        return mav;

    }
}

