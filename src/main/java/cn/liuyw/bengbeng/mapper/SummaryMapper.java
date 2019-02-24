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

    OpenNoSummary queryLuckSum(@Param("beishu") Integer beishu);

    List<OpenNoSummary> queryLuckSumByDay();

    OpenNoSummary queryHappySum(@Param("beishu") Integer beishu);

    List<OpenNoSummary> queryHappySumByDay();

    OpenNoSummary queryPk5Sum(@Param("beishu") Integer beishu);

    List<OpenNoSummary> queryPk5SumByDay();

    OpenNoSummary querySgsSum(@Param("beishu") Integer beishu);

    List<OpenNoSummary> querySgsSumByDay();

}
