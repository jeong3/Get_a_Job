package Get_a_Job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Get_a_Job.mapper.AutoNumMapper;

@Service
public class AutoNumService {
	@Autowired
	AutoNumMapper autoNumMapper;
	public String execute(String sep, String col, Integer len, String table) {
		String autoNum = autoNumMapper.AutoNumSelect(sep, col, len, table);
		return autoNum;
	}
}
