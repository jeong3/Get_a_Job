package Get_a_Job.mapper;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.AuthInfoDTO;

@Mapper
public interface LoginMapper {

	AuthInfoDTO LoginSelectOne(String userId);

}
