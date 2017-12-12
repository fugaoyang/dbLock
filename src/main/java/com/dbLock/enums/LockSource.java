package com.dbLock.enums;

/**
 * 锁资源 <br>
 * 
 * @Author fugaoyang
 */
public enum LockSource {
	WITHDRAW("01", "取现"), 
	WITHDRAW_RETRY("02", "取现重试"),;

	private String code = null;

	private String desc = null;

	LockSource(String code, String desc) {
		setCode(code);

		setDesc(desc);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
