package com.issmart.entity;

/**
 * 分数平衡实体
 * 当前发生行为的减去score
 * 当前发生行为的标签平均加上score/标签数量的分数
 * 
 * @author Administrator
 *
 */
public class ScoreBalanceEntity {
	
	/**
	 * deviceMac
	 */
	private String deviceMac;
	
	/**
	 * 当前展台应减实体
	 */
	private double score;
	
	/**
	 * 当前展台标签应加实体
	 */
	private double labelScore;
	
	/**
	 * 存在访问行为
	 */
	private boolean existVisitBehavior;
	
	/**
	 * 存在按一按行为
	 */
	private boolean existPressBehavior;
	
	/**
	 * 存在贴一贴行为
	 */
	private boolean existStickBehavior;
	
	/**
	 * 存在like反馈行为
	 */
	private boolean existLikeBehavior;
	
	/**
	 * 存在disLike反馈行为
	 */
	private boolean existDisLikeBehavior;

	public ScoreBalanceEntity(String deviceMac) {
		super();
		this.deviceMac = deviceMac;
	}

	public ScoreBalanceEntity() {
		super();
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getLabelScore() {
		return labelScore;
	}

	public void setLabelScore(double labelScore) {
		this.labelScore = labelScore;
	}

	public boolean isExistVisitBehavior() {
		return existVisitBehavior;
	}

	public void setExistVisitBehavior(boolean existVisitBehavior) {
		this.existVisitBehavior = existVisitBehavior;
	}

	public boolean isExistPressBehavior() {
		return existPressBehavior;
	}

	public void setExistPressBehavior(boolean existPressBehavior) {
		this.existPressBehavior = existPressBehavior;
	}

	public boolean isExistStickBehavior() {
		return existStickBehavior;
	}

	public void setExistStickBehavior(boolean existStickBehavior) {
		this.existStickBehavior = existStickBehavior;
	}

	public boolean isExistLikeBehavior() {
		return existLikeBehavior;
	}

	public void setExistLikeBehavior(boolean existLikeBehavior) {
		this.existLikeBehavior = existLikeBehavior;
	}

	public boolean isExistDisLikeBehavior() {
		return existDisLikeBehavior;
	}

	public void setExistDisLikeBehavior(boolean existDisLikeBehavior) {
		this.existDisLikeBehavior = existDisLikeBehavior;
	}
}
