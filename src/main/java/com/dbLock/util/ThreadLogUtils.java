package com.dbLock.util;

import java.util.UUID;

/**
 * 
 * 线程处理<br>
 * 
 * @author fugaoyang
 *
 */
public class ThreadLogUtils {

	private static ThreadLogUtils instance = null;

	private ThreadLogUtils() {
		setInstance(this);
	}

	// 初始化标志
	private static final Object __noop = new Object();
	private static ThreadLocal<Object> __flag = new InheritableThreadLocal<Object>() {
		@Override
		protected Object initialValue() {
			return null;
		}
	};

	// 当前线程的UUID信息，主要用于打印日志；
	private static ThreadLocal<String> currLogUuid = new InheritableThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return UUID.randomUUID().toString()/* .toUpperCase() */;
		}
	};

	private static ThreadLocal<String> currThreadUuid = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return UUIDGenerator.getUuid();
		}
	};

	public static void clear(Boolean isNew) {
		if (isNew) {

			currLogUuid.remove();

			__flag.remove();

			currThreadUuid.remove();

		}
	}

	public static String getCurrLogUuid() {
		if (!isInitialized()) {
			throw new IllegalStateException("TLS未初始化");
		}

		return currLogUuid.get();
	}

	public static String getCurrThreadUuid() {
		return currThreadUuid.get();
	}

	public static void clearCurrThreadUuid() {
		currThreadUuid.remove();
	}

	public static String getLogPrefix() {
		if (!isInitialized()) {
			return "";
		}

		return "<uuid=" + getCurrLogUuid() + ">";
	}

	private static boolean isInitialized() {
		return __flag.get() != null;
	}

	/**
	 * 初始化上下文，如果已经初始化则返回false，否则返回true<br/>
	 *
	 * @return
	 */
	public static boolean initialize() {
		if (isInitialized()) {
			return false;
		}

		__flag.set(__noop);
		return true;
	}

	private static void setInstance(ThreadLogUtils instance) {
		ThreadLogUtils.instance = instance;
	}

	public static ThreadLogUtils getInstance() {
		return instance;
	}

}
