package com.dbLock.manager;

import com.dbLock.enums.LockSource;
import com.dbLock.enums.LockStatus;

/**
 * 锁接口 <br>
 * 
 * @Author fugaoyang
 *
 */
public interface DbLockManager {

	/**
	 * 加锁
	 */
	boolean lock(String outerSerialNo, String custNo, LockSource source);

	/**
	 * 解锁
	 */
	void unLock(String outerSerialNo, String custNo, LockSource source, LockStatus targetStatus);

}
