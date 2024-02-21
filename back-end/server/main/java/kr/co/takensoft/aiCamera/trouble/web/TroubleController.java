package kr.co.takensoft.aiCamera.trouble.web;

import kr.co.takensoft.aiCamera.data.service.ErrorListComparison;
import kr.co.takensoft.aiCamera.trouble.service.TroubleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TroubleController {

    private final TroubleService troubleService;
    /**
     * 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.19
     */
    @RequestMapping(value = "/eqpmnTroubleSelectList.json", method = RequestMethod.POST)
    public ModelAndView eqpmnTroubleSelectList(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("selectList", troubleService.eqpmnTroubleSelectList(params));
        modelAndView.addObject("selectListCount",troubleService.eqpmnTroubleSelectListCount(params)); // 페이지네이션에 필요한 목록 count
        return modelAndView;
    }

    /**
     * 최신 5개 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.23
     */
    @RequestMapping(value = "/recentEqpmnTroubleSelectList.json", method = RequestMethod.POST)
    public ModelAndView recentEqpmnTroubleSelectList() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", troubleService.recentEqpmnTroubleSelectList());
        return modelAndView;
    }

    /**
     * 특정 장비의 최신 5개 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @RequestMapping(value = "/recentEqpmnTroubleSelectListByEqpmn.json", method = RequestMethod.POST)
    public ModelAndView recentEqpmnTroubleSelectListByEqpmn(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", troubleService.recentEqpmnTroubleSelectListByEqpmn(params));
        return modelAndView;
    }

    /**
     * 오늘 최신 5개 장비 장애 목록 조회
     *
     * @author 김예진
     * @since 2024.02.01
     */
    @RequestMapping(value = "/todayRecentEqpmnTroubleSelectList.json", method = RequestMethod.POST)
    public ModelAndView todayRecentEqpmnTroubleSelectList() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", troubleService.todayRecentEqpmnTroubleSelectList());
        return modelAndView;
    }

    /**
     * 전체 카메라의 오늘 하루 장애 정보 수 SUM
     *
     * @author 김예진
     * @since 2024.01.26
     */
    @RequestMapping(value = "/todayEqpmnTroubleSum.json", method = RequestMethod.POST)
    public ModelAndView todayEqpmnTroubleSum() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", troubleService.todayEqpmnTroubleSum());
        return modelAndView;
    }

}
