package com.issmart.entity;

/**
 * 获取每个标签平均得分
 * 
 * @author Administrator
 *
 */
public class ScoreLabelEntity {
	
	private String labelId;
	
	private double score;

	public ScoreLabelEntity() {
		super();
	}

	public ScoreLabelEntity(String labelId) {
		super();
		this.labelId = labelId;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
