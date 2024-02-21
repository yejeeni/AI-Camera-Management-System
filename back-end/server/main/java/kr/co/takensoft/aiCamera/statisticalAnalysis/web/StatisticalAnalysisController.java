package kr.co.takensoft.aiCamera.statisticalAnalysis.web;

import kr.co.takensoft.aiCamera.statisticalAnalysis.service.StatisticalAnalysisService;
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
public class StatisticalAnalysisController {

    private final StatisticalAnalysisService statisticalAnalysisService;

    /**
     * 객체 인식 데이터의 시간별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @RequestMapping(value = "/hourlyAnalsDetectData.json", method = RequestMethod.POST)
    public ModelAndView hourlyAnalsDetectData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("hourlyAnalsDetectData", statisticalAnalysisService.hourlyAnalsDetectData(params));
        return modelAndView;
    }

    /**
     * 객체 인식 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @RequestMapping(value = "/dailyAnalsDetectData.json", method = RequestMethod.POST)
    public ModelAndView dailyAnalsEventData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("dailyAnalsDetectData", statisticalAnalysisService.dailyAnalsDetectData(params));
        return modelAndView;
    }

    /**
     * 객체 인식 데이터의 월별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @RequestMapping(value = "/monthlyAnalsDetectData.json", method = RequestMethod.POST)
    public ModelAndView monthlyAnalsDetectData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("monthlyAnalsDetectData", statisticalAnalysisService.monthlyAnalsDetectData(params));
        return modelAndView;
    }


    /**
     * 장비 장애 데이터의 시간별 산출
     *
     * @author 김예진
     * @since 2024.01.24
     */
    @RequestMapping(value = "/hourlyAnalsTroubleData.json", method = RequestMethod.POST)
    public ModelAndView hourlyAnalsTroubleData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("hrData", statisticalAnalysisService.hourlyAnalsTroubleData(params));
        return modelAndView;
    }

    /**
     * 장비 장애 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.25
     */
    @RequestMapping(value = "/dailyAnalsTroubleData.json", method = RequestMethod.POST)
    public ModelAndView dailyAnalsTroubleData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statisticalAnalysisService.dailyAnalsTroubleData(params));
        return modelAndView;
    }

    /**
     * 장비 장애 데이터의 일별 산출
     *
     * @author 김예진
     * @since 2024.01.25
     */
    @RequestMapping(value = "/monthlyAnalsTroubleData.json", method = RequestMethod.POST)
    public ModelAndView monthlyAnalsTroubleData(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", statisticalAnalysisService.monthlyAnalsTroubleData(params));
        return modelAndView;
    }
}
