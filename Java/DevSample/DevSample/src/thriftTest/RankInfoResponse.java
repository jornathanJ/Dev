package thriftTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RankInfoResponse {

	private List<RankInfo> rankInfo = new ArrayList<RankInfo>();

	public RankInfoResponse() {
	}

	public RankInfoResponse(List<RankInfo> rankInfo) {
		this.rankInfo = rankInfo;
	}

	public List<RankInfo> getRankInfo() {
		return rankInfo;
	}

	public void setRankInfo(List<RankInfo> rankInfo) {
		this.rankInfo = rankInfo;
	}

	

}
