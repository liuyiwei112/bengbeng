package cn.liuyw.bengbeng.service;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.bean.OpenNoLabelEnum;
import cn.liuyw.bengbeng.bean.Pager;
import cn.liuyw.bengbeng.constants.OpenNoConstant;
import cn.liuyw.bengbeng.exception.BusinessException;
import cn.liuyw.bengbeng.mapper.OpenNoMapper;
import cn.liuyw.bengbeng.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by liuyw on 17/8/31.
 */
@Service
public class OpenNoService {

    @Autowired
    OpenNoMapper openNoMapper;

    public Pager<OpenNo> getOpenNoByType(String guessType, Integer page, Integer pageSize) {
        List<OpenNo> openNoList = openNoMapper.getOpenNoByType(guessType, (page - 1) * pageSize, pageSize);
        for (OpenNo openNo : openNoList) {
            if (guessType.equals("luck")) {
                openNo.setAllNo(OpenNoConstant.luckAllNo);
            } else if (guessType.equals("happy")) {
                openNo.setAllNo(OpenNoConstant.happyAllNo);
            } else if (guessType.equals("pk5")) {
                openNo.setAllNo(OpenNoConstant.pk5AllNo);
            } else if (guessType.equals("sgs")) {
                openNo.setAllNo(OpenNoConstant.sgsAllNo);
            }
        }
        Integer count = openNoMapper.getOpenNoCount(guessType);
        Pager<OpenNo> pager = new Pager<OpenNo>();
        pager.setList(openNoList);
        pager.paging(page, pageSize, count);
        return pager;
    }

    public void addOpenNo(List<OpenNo> openNoList) {
        int successNum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        for (OpenNo openNo : openNoList) {
            if (openNoMapper.onceQueryOpenNo(openNo.getGuessType(), openNo.getIssue()) == null) {
                if (openNo.getGuessType().equals("pk5") || openNo.getGuessType().equals("sgs")) {
                    openNo.setOpenNoLabel(OpenNoLabelEnum.getLabelByNum(openNo.getGuessType(), openNo.getOpenNo()));
                } else {
                    Integer no = Integer.parseInt(openNo.getOpenNo());
                    if (no < 10) {
                        openNo.setOpenNoLabel("0" + no);
                    } else {
                        openNo.setOpenNoLabel("" + no);
                    }
                }
                openNo.setOpenTime(sdf.format(new Date()) + '-' + openNo.getOpenTime());
                openNoMapper.addOpenNo(openNo);
                successNum++;
                System.out.println("数据更新中:" + openNo.getIssue() + "------" + openNo.getOpenTime() + "------" + openNo.getOpenNoLabel() + "-------");
            } else {
                System.out.println("数据更新中:" + openNo.getIssue() + "------已存在");
                break;
            }
        }
        if (successNum != openNoList.size()) {
            throw new BusinessException("OpenNo.Exist", successNum + "");
        }
    }

    public void updateOpenNoPl() throws BusinessException {
        try {
            HttpUtil.doLogin();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Url.Login.Error");
        }

        List<OpenNo> list = openNoMapper.getOpenNoWithoutPl();
        int count = 0;
        int total = list.size();
        for (OpenNo openNo : list) {
            try {
                count++;
                Map resultMap = HttpUtil.refreshPL(openNo.getGuessType(), openNo.getIssue(), openNo.getOpenNo());
                openNo.setReturnRate((Float) resultMap.get("returnRate"));
                openNo.setAllReturnRate((String) resultMap.get("allReturnRate"));
                openNoMapper.updatePl(openNo);
                System.out.println("序号:(" + count + "/" + total + ") 赔率更新中:" + openNo.getGuessType() + "------" + openNo.getIssue() + "------" + openNo.getOpenNoLabel() + "------" + openNo.getReturnRate() + "-------");
                int max = 300;
                int min = 200;
                Random random = new Random();
                Thread.sleep(random.nextInt(max) % (max - min + 1) + min);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("Pl.Url.Error", openNo.getId().toString());
            }
        }
    }

    public void updateOpenTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        List<OpenNo> openNoList = openNoMapper.getOpenNoByType("sgs", 0, 1000);
        for (OpenNo openNo : openNoList) {
            openNo.setOpenTime(sdf.format(new Date()) + '-' + HttpUtil.calSgsOpenTime(Integer.parseInt(openNo.getIssue())));
            openNoMapper.updateOpenTime(openNo);
        }
    }


}
