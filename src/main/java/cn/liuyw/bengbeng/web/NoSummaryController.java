package cn.liuyw.bengbeng.web;

import cn.liuyw.bengbeng.bean.Result;
import cn.liuyw.bengbeng.service.SummaryService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuyw on 19/3/4.
 */
@RestController
@RequestMapping("/summary")
public class NoSummaryController {

    @Autowired
    private SummaryService summaryService;

    /**
     * 查询首页汇总统计数据
     * @param summaryType
     * @return
     */
    @GetMapping("/summaryAll")
    public Result summaryAll(@RequestParam int summaryType){
        return Result.ok(summaryService.querySummaryAll(summaryType));
    }

    /**
     * 统计指定某天的数据
     * @param guessType
     * @param summaryType 0-全包 1-保留赔率高的部分
     * @param openTime 指定日期
     * @return
     */
    @PostMapping("/someday/{guessType}")
    public Result sumSomeDay(@PathVariable String guessType,@NotBlank @RequestParam int summaryType, @NotBlank @RequestParam String openTime){
        return Result.ok(summaryService.insertSomeDaySummary(guessType,summaryType,openTime));
    }

    /**
     * 统计当前日期往前推14天的统计数据
     * @param guessType
     * @param summaryType
     * @return
     */
    @GetMapping("/someday/list/{guessType}")
    public Result getSomeDayList(@PathVariable String guessType,@RequestParam int summaryType){
        return Result.ok(summaryService.querySummary(guessType,summaryType));
    }


}
