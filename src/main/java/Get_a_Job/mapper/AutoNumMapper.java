package Get_a_Job.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoNumMapper {
	public String AutoNumSelect(@Param("sep")String sep
			, @Param("col")String col
			, @Param("len")Integer len
			, @Param("table")String table);
	
}
