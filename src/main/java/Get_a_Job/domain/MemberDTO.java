package Get_a_Job.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Alias("memberDTO")
@Data
public class MemberDTO {
	String memberNum;
	String memberName;
	String memberId;
	String memberPw;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date memberRegist;
	String gender;
	String memberEmail;
	String memberPhone;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date memberBirth;
}
