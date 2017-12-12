/**
 * 
 */
package com.dbLock.trade.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dbLock.enums.LockSource;
import com.dbLock.enums.LockStatus;
import com.dbLock.manager.DbLockManager;
import com.dbLock.util.ThreadLogUtils;

/**
 * @author fugaoyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
public class TradeDbLockTest {

	@Autowired
	private DbLockManager dbLockManager;

	/**
	 * 取现交易同步锁测试<br>
	 * 真对同一个客户，同一个操作同一时刻是唯一的<br>
	 */
	@Test
	public void withdrawTest() {
		// 外部传入参数
		String outerSerialNo = "";
		String custNo = "";

		boolean isiInit = false;
		try {
			isiInit = ThreadLogUtils.initialize();
			dbLockManager.lock(outerSerialNo, custNo, LockSource.WITHDRAW);
		} catch (Exception e) {
			// LOG
		} finally {
			try {
				dbLockManager.unLock(outerSerialNo, custNo, LockSource.WITHDRAW, LockStatus.S);
			} catch (Exception e) {
				// LOG
			}
			ThreadLogUtils.clear(isiInit);
		}

	}

	/**
	 * 取现交易补偿重试同步锁测试<br>
	 * 真对同一笔交易同一个补偿同一时刻是唯一的，补偿成功的不可以再补偿<br>
	 */
	@Test
	public void withdrawRepairTest() {
		// 内部扫描交易参数
		String outerSerialNo = ""; // 交易流水号
		String custNo = "";

		boolean isiInit = false;
		try {
			isiInit = ThreadLogUtils.initialize();
			dbLockManager.lock(outerSerialNo, custNo, LockSource.WITHDRAW_RETRY);
		} catch (Exception e) {
			// LOG
		} finally {
			try {
				dbLockManager.unLock(outerSerialNo, custNo, LockSource.WITHDRAW_RETRY, LockStatus.S);
			} catch (Exception e) {
				// LOG
			}
			ThreadLogUtils.clear(isiInit);
		}

	}

}
