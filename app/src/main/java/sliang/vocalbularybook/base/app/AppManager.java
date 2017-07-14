package sliang.vocalbularybook.base.app;

import android.app.Activity;
import android.os.Process;
import android.util.Log;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

	static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public void getSize(String name) {
		System.out.println("---" + name + "--" + activityStack.size());
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
		Log.i("XM",activity.getClass().getName());
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
		Log.i("XM", activity.getClass().getName());
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		Stack<Activity> activitys = new Stack<Activity>();
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				activitys.add(activity);
			}
		}

		for (Activity activity : activitys) {
			finishActivity(activity);
		}
	}



	/**
	 * 结束除了指定类名的所有Activity
	 */
	public void finishActivityExcept(Class<?> cls) {

		Stack<Activity> activitys = new Stack<Activity>();
		for (Activity activity : activityStack) {
			if (!activity.getClass().equals(cls)) {
				activitys.add(activity);
			}
		}

		for (Activity activity : activitys) {
				finishActivity(activity);
		}

	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit() {
		try {
			finishAllActivity();
			int pid = Process.myPid();
			Process.killProcess(pid);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
