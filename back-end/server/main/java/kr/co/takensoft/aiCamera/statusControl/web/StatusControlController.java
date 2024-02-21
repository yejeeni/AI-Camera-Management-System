package kr.co.takensoft.aiCamera.statusControl.web;

import kr.co.takensoft.aiCamera.data.service.ErrorListComparison;
import kr.co.takensoft.aiCamera.statusControl.service.StatusControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StatusControlController {

    private final StatusControlService statusControlService;


    /**
     * 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    @RequestMapping(value = "/todayObjectDetectEventSumByAllEquipment.json", method = RequestMethod.POST)
    public ModelAndView todayObjectDetectEventSumByAllEquipment() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statusControlService.todayObjectDetectEventSumByAllEquipment());
        return modelAndView;
    }

    /**
     * 특정 카메라 오늘 누적 인식 수 COUNT 조회
     *
     * @author 김예진
     * @since 2024.01.15
     */
    @RequestMapping(value = "/todayObjectDetectEventSumByEquipment.json", method = RequestMethod.POST)
    public ModelAndView todayObjectDetectEventSumByEquipment(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statusControlService.todayObjectDetectEventSumByEquipment(params));
        return modelAndView;
    }

    /**
      * 객체 인식 목록 조회
      *
      * @author 김예진
      * @since 2024.01.16
      */
    @RequestMapping(value = "/objectDetectEventSelectList.json", method = RequestMethod.POST)
    public ModelAndView objectDetectEventSelectList(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("selectList", statusControlService.objectDetectEventSelectList(params));
        modelAndView.addObject("selectListCount",statusControlService.objectDetectEventSelectListCount(params));
        return modelAndView;
    }

    /**
     * 오늘 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    @RequestMapping(value = "/todayRecentObjectDetectEventList.json", method = RequestMethod.POST)
    public ModelAndView todayRecentObjectDetectEventList() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statusControlService.todayRecentObjectDetectEventList());
        return modelAndView;
    }

    /**
     * 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    @RequestMapping(value = "/recentObjectDetectEventList.json", method = RequestMethod.POST)
    public ModelAndView recentObjectDetectEventList() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statusControlService.recentObjectDetectEventList());
        return modelAndView;
    }

    /**
     * 특정 장비의 최신 5개 객체 인식 기록 조회
     *
     * @author 김예진
     * @since 2024.01.96
     */
    @RequestMapping(value = "/recentObjectDetectEventByEqpmn.json", method = RequestMethod.POST)
    public ModelAndView recentObjectDetectEventByEqpmn(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statusControlService.recentObjectDetectEventByEqpmn(params));
        return modelAndView;
    }
}
