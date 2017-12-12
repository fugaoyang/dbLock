package com.dbLock.util.test;

import java.util.UUID;

import com.dbLock.util.ThreadLogUtils;
import com.dbLock.util.UUIDGenerator;

public class ThreadLogTest {

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

	public String getCurrLogUuid() {
		return currLogUuid.get();
	}

	public String getCurrThreadUuid() {
		return currThreadUuid.get();
	}

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 10; i++) {
			new Thread() {
				boolean b = false;
				@Override
				public void run() {
					try {
						b = ThreadLogUtils.initialize();
						if (b) {
							System.out.println(Thread.currentThread().getName() + "--" + ThreadLogUtils.getLogPrefix());
						}
					} catch (Exception e) {
                     // log
					} finally {
						ThreadLogUtils.clear(b);
					}
				
				}
			}.start();
		}

		Thread.sleep(2000);
		System.out.println("-----------------------");

		final ThreadLogTest test = new ThreadLogTest();

		for (int i = 0; i < 10; i++) {
			new Thread() {
				@Override
				public void run() {
					boolean b = ThreadLogUtils.initialize();
					if (b) {
						System.out.println(Thread.currentThread().getName() + "--" + test.getCurrLogUuid());
					}
				}
			}.start();
		}

	}

}
