package com.dbLock.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dbLock.enums.LockSource;
import com.dbLock.enums.LockStatus;
import com.dbLock.mapper.DbSyncLockMapper;
import com.dbLock.model.TradeSyncLock;
import com.dbLock.util.ThreadLogUtils;

/**
 * 
 * 数据库锁实现<br>
 * 
 * @author fugaoyang
 *
 */
@Service
public class DbLockManagerImpl implements DbLockManager {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DbSyncLockMapper lockMapper;

	@Transactional
	public boolean lock(String outerSerialNo, String custNo, LockSource source) {

		boolean isLock = false;
		TradeSyncLock lock = null;
		try {
			lock = lockMapper.find(outerSerialNo, custNo, source.getCode());

			if (null == lock) {
				lock = new TradeSyncLock();
				createLock(lock, outerSerialNo, custNo, source);

				int num = lockMapper.insert(lock);
				if (num == 1) {
					isLock = true;
				}

				LOG.info(ThreadLogUtils.getLogPrefix() + "加入锁，客户号[{}]，锁类型[{}]", custNo, source.getCode());
				return isLock;
			}

			// 根据交易类型进行加锁
			isLock = switchSynsLock(lock, source);
			LOG.info(ThreadLogUtils.getLogPrefix() + "更新锁，客户号[{}]，锁类型[{}]", custNo, source.getCode());

		} catch (Exception e) {
			LOG.error(ThreadLogUtils.getLogPrefix() + "交易加锁异常, 客户号：" + custNo, e);
		}
		return isLock;
	}

	@Transactional
	public void unLock(String outerSerialNo, String custNo, LockSource source, LockStatus targetStatus) {

		try {
			TradeSyncLock lock = lockMapper.find(outerSerialNo, custNo, source.getCode());

			if (null != lock) {
				lockMapper.update(lock.getId(), targetStatus.getName(), LockStatus.P.getName(),
						ThreadLogUtils.getCurrThreadUuid(), ThreadLogUtils.getCurrThreadUuid());
			}

			LOG.info(ThreadLogUtils.getLogPrefix() + "释放锁，客户号[{}]，锁类型[{}]", custNo, source.getCode());
		} catch (Exception e) {
			LOG.error(ThreadLogUtils.getLogPrefix() + "释放锁异常, 客户号：{}", custNo, e);
		}
	}

	/**
	 * 匹配加锁
	 */
	private boolean switchSynsLock(TradeSyncLock lock, LockSource source) {
		boolean isLock = false;

		switch (source) {
		case WITHDRAW:
			;
			isLock = tradeSynsLock(lock);
			break;
		case WITHDRAW_RETRY:
			;
			isLock = retrySynsLock(lock);
			break;
		default:
			;
		}
		return isLock;
	}

	/**
	 * 交易同步锁
	 */
	private boolean tradeSynsLock(TradeSyncLock lock) {
		// 处理中的不加锁，即不执行交易操作
		if (LockStatus.P.getName().equals(lock.getStatus())) {
			return false;
		}

		int num = lockMapper.update(lock.getId(), LockStatus.P.getName(), LockStatus.S.getName(),
				ThreadLogUtils.getCurrThreadUuid(), null);
		if (num == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 补偿同步锁
	 */
	private boolean retrySynsLock(TradeSyncLock lock) {
		// 处理中或处理完成的不加锁，即不执行补偿操作
		if (LockStatus.P.getName().equals(lock.getStatus()) || LockStatus.S.getName().equals(lock.getStatus())) {
			return false;
		}

		int num = lockMapper.update(lock.getId(), LockStatus.P.getName(), LockStatus.F.getName(),
				ThreadLogUtils.getCurrThreadUuid(), null);
		if (num == 1) {
			return true;
		}
		return false;
	}

	private void createLock(TradeSyncLock lock, String outerSerialNo, String custNo, LockSource source) {
		lock.setOuterSerialNo(outerSerialNo);
		lock.setCustNo(custNo);
		lock.setSourceCode(source.getCode());
		lock.setThreadNo(ThreadLogUtils.getCurrThreadUuid());
		lock.setStatus(LockStatus.P.getName());
		lock.setRemark(source.getDesc());
	}

}
