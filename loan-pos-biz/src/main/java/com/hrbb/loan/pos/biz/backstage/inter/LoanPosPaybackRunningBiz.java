package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.List;
import java.util.Map;

public interface LoanPosPaybackRunningBiz {
	List<Map<String, Object>> queryPaybackRunningInfo(Map<String, Object> reqMap);

	List<Map<String, Object>> queryPaybackRunnigRecordByReceiptId(
			Map<String, Object> reqMap);

	long countNumber(Map<String, Object> reqMap);
}
