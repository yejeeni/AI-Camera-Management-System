package kr.co.takensoft.aiCamera.equipment.web;

import kr.co.takensoft.aiCamera.equipment.service.EquipmentService;
import kr.co.takensoft.aiCamera.trouble.service.TroubleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


/**
 * 장비 Controller 입니다.
 *
 * @author 황대찬
 * @since 2024.01.15
 */
@Controller
@AllArgsConstructor
public class EquipmentController {

    EquipmentService equipmentService;
    TroubleService troubleService;

    /**
     * 장비 등록
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @RequestMapping(value = "/equipmentInsert.json", method = RequestMethod.POST)
    public ModelAndView equipmentInsert(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", equipmentService.equipmentInsert(params));
        return mav;
    }


    /**
     * 장비 목록 조회
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @RequestMapping(value = "/equipmentSelectList.json", method = RequestMethod.POST)
    public ModelAndView equipmentSelectList(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("equipmentSelectList", equipmentService.equipmentSelectList(params));
        mav.addObject("equipmentSelectListCount", equipmentService.equipmentSelectListCount(params));
        return mav;
    }

    /**
     * 전체 장비, 장애 장비 목록 조회
     *
     * @author 김예진
     * @since 2024.01.31
     */
    @RequestMapping(value = "/allEqpmnAndTroblEqpmnSelectList.json", method = RequestMethod.POST)
    public ModelAndView allEqpmnAndTroblEqpmnSelectList(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("equipmentSelectList", equipmentService.equipmentSelectList(params));
        mav.addObject("equipmentSelectListCount", equipmentService.equipmentSelectListCount(params));
        return mav;
    }

    /**
     * 장비 상세보기
     *
     * @author 황대찬
     * @since 2024.01.15
     */

    @RequestMapping(value = "/equipmentSelectOne.json", method = RequestMethod.POST)
    public ModelAndView equipmentSelectOne(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", equipmentService.equipmentSelectOne(params));
        return mav;
    }


    /**
     * 장비 수정
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @RequestMapping(value = "/equipmentUpdate.json", method = RequestMethod.POST)
    public ModelAndView equipmentUpdate(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", equipmentService.equipmentUpdate(params));
        return mav;
    }


    /**
     * 장비 선택 삭제
     *
     * @author 황대찬
     * @since 2024.01.15
     */

    @RequestMapping(value = "/equipmentDelete.json", method = RequestMethod.POST)
    public ModelAndView equipmentDelete(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", equipmentService.equipmentDelete(params));
        return mav;
    }

    /**
     * 장비 삭제
     *
     * @author 황대찬
     * @since 2024.01.15
     */

    @RequestMapping(value = "/equipmentDeleteOne.json", method = RequestMethod.POST)
    public ModelAndView equipmentDeleteOne(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", equipmentService.equipmentDeleteOne(params));
        return mav;
    }

    /**
     * 장애 유무를 포함한 장비 목록 조회
     *
     * @author 김예진
     * @since 2024.02.01
     */
    @RequestMapping(value = "/eqpmnSelectListIncludeTroblYN.json", method = RequestMethod.POST)
    public ModelAndView eqpmnSelectListIncludeTroblYN(@RequestBody HashMap<String, Object> params) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("equipmentSelectList", equipmentService.eqpmnSelectListIncludeTroblYN(params));
        mav.addObject("equipmentSelectListCount", equipmentService.equipmentSelectListCount(params));
        return mav;
    }

}

