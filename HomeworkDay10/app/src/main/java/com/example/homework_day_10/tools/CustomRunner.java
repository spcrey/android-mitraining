package com.example.homework_day_10.tools;

import com.example.homework_day_10.MainActivity;

abstract public class CustomRunner {
    public interface RunActivity {
        void run();
    }

    public void run(RunActivity runActivity, int time) {
        try {
            Thread.sleep(time);
            runActivity.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
