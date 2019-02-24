package cn.liuyw.bengbeng.mapper;

import cn.liuyw.bengbeng.bean.OpenNo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyw on 17/8/31.
 */
@Mapper
public interface OpenNoMapper {

    /**
     * 根据种类查询列表数据
     * @param guessType
     * @param page
     * @param pageSize
     * @return
     */
    List<OpenNo> getOpenNoByType(@Param("guessType") String guessType,@Param("page") Integer page,@Param("pageSize") Integer pageSize);

    Integer getOpenNoCount(@Param("guessType") String guessType);

    Integer addOpenNo(OpenNo openNo);

    OpenNo onceQueryOpenNo(@Param("guessType") String guessType,@Param("issue") String issue);

    List<OpenNo> getOpenNoWithoutPl();

    Integer updatePl(OpenNo openNo);

    Integer updateOpenTime(OpenNo openNo);

}
