package com.dbLock.mapper;

import com.dbLock.model.TradeSyncLock;

/**
 * @Author fugaoyang
 *
 */
public interface DbSyncLockMapper {

	int deleteByPrimaryKey(Long id);

	TradeSyncLock find(String outerSerialNo, String CustNo, String sourceCode);

	int insert(TradeSyncLock record);

	int update(Long id, String targetStatus, String originStatus, String threadNo, String originThreadNo);

}