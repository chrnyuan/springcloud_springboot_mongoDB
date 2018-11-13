package com.issmart.util;
/**
 * 访问相应时长其标签加上相应分数，但目标减去相应分数最低为0
 * press,like,dislike直接为0
 * 
 * @author Administrator
 *
 */
public enum ScoreEnum {
	/**
	 *  5分钟以内visit行为2分
	 */
	VISITINFIVE("visitInFive",2),
	/**
	 *  5分钟到10分钟visit行为1分
	 */
	VISITINFIVEANDTEN("visitInFiveAndTen",1),
	/**
	 *  大于10分钟visit行为0.5分
	 */
	VISITOUTTEN("visitOutTen",0.1),
	/**
	 * 5分钟以内press行为15分
	 */
	PRESSINFIVE("pressInFive",20),
	/**
	 * 5分钟到10分钟press行为10分
	 */
	PRESSINFIVEANDTEN("pressInFiveAndTen",10),
	/**
	 * 大于10分钟press行为5分
	 */
	PRESSOUTTEN("pressOutTen",1),
	/**
	 * 5分钟以内press行为15分
	 */
	STICKINFIVE("stickInFive",15),
	/**
	 * 5分钟到10分钟press行为10分
	 */
	STICKINFIVEANDTEN("stickInFiveAndTen",8),
	/**
	 * 大于10分钟press行为5分
	 */
	STICKOUTTEN("stickOutTen",1),
	/**
	 * stick行为10分
	 */
	LIKE("like",20),
	/**
	 * stick行为10分
	 */
	DISLIKE("dislike",-20),
	
	/**
	 * 标签最低分数
	 */
	RESETLABELBEHAVIOR("resetLabelBehavior",-10),
	
	/**
	 * 重置
	 */
	RESETBEHAVIOR("resetBehavior",-50);
	
    private final String key;
    private final double value;
    
    private ScoreEnum(String key,double value){
        this.key = key;
        this.value = value;
    }
    //根据key获取枚举
    public static ScoreEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(ScoreEnum temp:ScoreEnum.values()){
            if(temp.getKey().equals(key)){
                return temp;
            }
        }
        return null;
    }
    public String getKey() {
        return key;
    }
    public double getValue() {
        return value;
    }
}
