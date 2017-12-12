package com.dbLock.enums;

/**
 * 锁状态 <br>
 * 
 * @Author fugaoyang
 */
public enum LockStatus {
	P("P", "处理中"), 
	F("F", "处理失败或异常"), 
	S("S", "处理成功");

	private String name = null;

	private String desc = null;

	LockStatus(String name, String desc) {
		setName(name);

		setDesc(desc);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
