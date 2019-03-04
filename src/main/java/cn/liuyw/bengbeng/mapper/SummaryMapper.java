package cn.liuyw.bengbeng.mapper;

import cn.liuyw.bengbeng.bean.OpenNoSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liuyw on 19/2/19.
 */
@Mapper
public interface SummaryMapper {

    /**
     * 查询所有汇总数据的结果
     * @param guessType
     * @param summaryType
     * @return
     */
    OpenNoSummary querySummaryAll(@Param("guessType") String guessType,@Param("summaryType") int summaryType);

    /**
     * 查询指定某天的全包盈亏率
     * @param guessType
     * @param openTime
     * @return
     */
    OpenNoSummary queryAllInSum(@Param("guessType") String guessType,@Param("openTime") String openTime);


    /**
     * 查询某天的汇总数据
     * @param guessType
     * @param summaryType
     * @param openTime 为空时查询全部
     * @return 返回近14天走势
     */
    List<OpenNoSummary> querySummaryList(@Param("guessType") String guessType,@Param("summaryType") int summaryType,@Param("openTime") String openTime);

    /**
     * 插入某天的统计数据
     * @param openNoSummary
     * @return
     */
    Integer insertSummary(OpenNoSummary openNoSummary);

    /**
     * 更新某天的统计数据
     * @param openNoSummary
     * @return
     */
    Integer updateSummary(OpenNoSummary openNoSummary);
}
