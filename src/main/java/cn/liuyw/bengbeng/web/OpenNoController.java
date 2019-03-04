package cn.liuyw.bengbeng.web;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.bean.Pager;
import cn.liuyw.bengbeng.bean.Result;
import cn.liuyw.bengbeng.exception.BusinessException;
import cn.liuyw.bengbeng.service.OpenNoService;
import cn.liuyw.bengbeng.service.SummaryService;
import cn.liuyw.bengbeng.utils.HttpUtil;
import cn.liuyw.bengbeng.utils.StringUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyw on 17/8/31.
 */
@RestController
@RequestMapping("/no")
public class OpenNoController {

    @Autowired
    private OpenNoService openNoService;

    @GetMapping("/list/{guessType}")
    public Result getOpenNoByType(@PathVariable("guessType") String guessType,
                                  @RequestParam String openTime,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("pageSize") Integer pageSize) throws BusinessException{
        Pager<OpenNo> pager = openNoService.getOpenNoByType(guessType,openTime,page,pageSize);
        return Result.ok(pager);
    }

    @PostMapping("/add")
    public Result addOpenNo(@RequestBody List<OpenNo> openNoList) throws BusinessException{
        openNoService.addOpenNo(openNoList);
        return Result.ok();
    }

    @GetMapping("/synOpenData/{guessType}")
    public Result synOpenData(@PathVariable String guessType) throws BusinessException{
        List<OpenNo> list = new ArrayList<>();
        try{
            if(guessType.equals("pk5")){
                list = HttpUtil.getPK();
            }else if(guessType.equals("sgs")){
                list = HttpUtil.getSgs();
            }else{
                list = HttpUtil.getLuckOrHappy(guessType);
            }
        }catch(Exception e){
            throw new BusinessException("Url.Connect.Error");
        }
        openNoService.addOpenNo(list);
        return Result.ok();
    }

    @GetMapping("/synPl")
    public Result synOpenNoPl() throws BusinessException{
        openNoService.updateOpenNoPl();
        return Result.ok();
    }

    @GetMapping("/updateSgsTime")
    public Result updateSgsTime() throws BusinessException{
        openNoService.updateOpenTime();
        return Result.ok();
    }

}
