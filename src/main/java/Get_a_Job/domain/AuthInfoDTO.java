package Get_a_Job.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("authInfoDTO")
public class AuthInfoDTO {
	String userNum;
	String userId;
	String userPw;
	String userName;
	String userEmail;
	String grade;
	
}
