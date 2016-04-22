package com.example.app.robolectric;

import org.androidannotations.api.BackgroundExecutor;
import org.assertj.core.util.Strings;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.util.Scheduler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure synchronous execution of android annotations background tasks.
 * <p/>
 * Robolectric is designed to execute all background tasks synced to avoid flaky tests. Current
 * it miss support for android annotations so this class will do the job.
 * <p/>
 * Additional the shadow tries to simulate the behaviour of android annotations cancelable tasks.
 * But real interrupt feature is not supported because there is no async with robolectric tests.
 */
@Implements(BackgroundExecutor.class)
public class ShadowBackgroundExecutor {

    static Map<String, Runnable> cancelableTasks = new HashMap<>();

    @Implementation
    public static synchronized void execute(final BackgroundExecutor.Task task) {
        String taskId = extractTaskId(task);

        if (Strings.isNullOrEmpty(taskId)) {
            Robolectric.getBackgroundThreadScheduler().post(task);
        } else {
            Runnable taskWrapper = wrapAsCancelableTask(task, taskId);
            Robolectric.getBackgroundThreadScheduler().post(taskWrapper);
        }
    }

    /**
     * Stop the run of queued background cancelableTasks.
     * <p/>
     * To use it you must stop automatic background task execution with
     * {@link }Robolectric#getBackgroundthreadScheduler()} and pause it {@link Scheduler#pause()}
     */
    public static synchronized void cancelAll(String id, boolean mayInterruptIfRunning) {
        Runnable runnable = cancelableTasks.remove(id);
        Robolectric.getBackgroundThreadScheduler().remove(runnable);
    }

    private static Runnable wrapAsCancelableTask(final BackgroundExecutor.Task task, String taskId) {
        final String fTaskId = taskId;
        Runnable taskWrapper = new Runnable() {
            @Override
            public void run() {
                task.run();
                cancelableTasks.remove(fTaskId);
            }
        };
        cancelableTasks.put(fTaskId, taskWrapper);
        return taskWrapper;
    }

    private static String extractTaskId(BackgroundExecutor.Task task) {
        try {
            Field privateStringField = BackgroundExecutor.Task.class.getDeclaredField("id");
            privateStringField.setAccessible(true);
            return (String) privateStringField.get(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}