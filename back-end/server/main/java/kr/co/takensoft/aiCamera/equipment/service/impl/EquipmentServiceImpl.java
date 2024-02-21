package kr.co.takensoft.aiCamera.equipment.service.impl;


import common.util.CommonUtil;
import common.util.idgn.IdGenerate;
import kr.co.takensoft.aiCamera.common.PaginationSupport;
import kr.co.takensoft.aiCamera.equipment.dao.EquipmentDAO;
import kr.co.takensoft.aiCamera.equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentDAO equipmentDAO;
    private final IdGenerate idGenerate;

    /**
     * 장비 등록
     *
     * @author 황대찬
     * @since 2024.01.15
     */

    @Override
    public int equipmentInsert(HashMap<String, Object> params) throws Exception {
        String eqpmn_id = idGenerate.getNextId("eqpmn_reg_info");
        params.replace("eqpmn_id",eqpmn_id);
        params.put("rgtr_id",  getUserId());

        return equipmentDAO.equipmentInsert(params);
    }

    /**
     * 세션에서 유저 id를 얻는 메서드
     *
     * @author 김예진
     * @since 2024.02.14
     */
    private String getUserId() {
        HttpServletRequest request = CommonUtil.getHttpServletRequest();
        HttpSession session = request.getSession(false);
        HashMap<String, Object> loginUser = (HashMap<String, Object>) session.getAttribute("loginUser");
        return (String) loginUser.get("user_id");
    }

    /**
     * 장비 목록 조회
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @Override
    public List<HashMap<String, Object>> equipmentSelectList(HashMap<String, Object> params) throws Exception {
        if(params.get("currentPage") != null){
            int currentPage = (int) params.get("currentPage");
            int perPage = Integer.parseInt(params.get("perPage").toString());
            int pagingRowIndex = PaginationSupport.pagingRowIndexForPostgreSql(currentPage, perPage);
            params.put("pagingRowIndex", pagingRowIndex);
            params.replace("perPage",perPage);
        }

        return equipmentDAO.equipmentSelectList(params);
    }

    /**
     * 장비 목록 조회 COUNT
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @Override
    public int equipmentSelectListCount(HashMap<String, Object> params) throws Exception {
        return equipmentDAO.equipmentSelectListCount(params);
    }
    /**
     * 장비 상세보기
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @Override
    public HashMap<String, Object> equipmentSelectOne(HashMap<String, Object> params) throws Exception {
        return equipmentDAO.equipmentSelectOne(params);
    }
    /**
     * 장비 수정
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @Override
    public int equipmentUpdate(HashMap<String, Object> params) throws Exception {
        params.put("mdfr_id", getUserId());
        return equipmentDAO.equipmentUpdate(params);
    }

    /**
     * 장비 삭제
     *
     * @author 황대찬
     * @since 2024.01.30
     */
    public int equipmentDeleteOne(HashMap<String, Object> params) throws Exception {
        return equipmentDAO.equipmentDelete(params);
    }

    /**
     * 장비 선택 삭제
     *
     * @author 황대찬
     * @since 2024.01.15
     */
    @Override
    public int equipmentDelete(HashMap<String, Object> params) throws Exception {
            int result = 0;

            List<HashMap<String, Object>> deleteList = (ArrayList<HashMap<String, Object>>) params.get("deleteList");
            for (HashMap<String, Object> item : deleteList) {
                result += equipmentDAO.equipmentDelete(item);
            }

            return result;
    }
    /**
     * 장애 유무를 포함한 장비 목록 조회
     *
     * @author 김예진
     * @since 2024.02.01
     */
    public List<HashMap<String, Object>> eqpmnSelectListIncludeTroblYN(HashMap<String, Object> params) throws Exception {
        return equipmentDAO.eqpmnSelectListIncludeTroblYN(params);
    }

}

