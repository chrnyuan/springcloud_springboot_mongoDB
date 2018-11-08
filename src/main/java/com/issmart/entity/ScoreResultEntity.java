package com.issmart.entity;

/**
 * 计算得每个boothId最终应加分数
 * 
 * @author Administrator
 *
 */
public class ScoreResultEntity {
	
	/**
     * unitId
     */
    private String unitId;

	/**
	 * boothId
	 */
	private String boothId;
	
	/**
	 * 最终应加分数
	 */
	private double score;
	
	/**
	 * 触发置0行为
	 */
	private boolean resetBehavior;
	
	public ScoreResultEntity() {
		super();
	}

	public ScoreResultEntity(String boothId) {
		super();
		this.boothId = boothId;
	}

	public String getBoothId() {
		return boothId;
	}

	public void setBoothId(String boothId) {
		this.boothId = boothId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public boolean isResetBehavior() {
		return resetBehavior;
	}

	public void setResetBehavior(boolean resetBehavior) {
		this.resetBehavior = resetBehavior;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
