package com.example.homework_day_10.tools;

public class NewThreadRunner extends CustomRunner {
    @Override
    public void run(RunActivity runActivity, int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NewThreadRunner.super.run(runActivity, time);
            }
        }).start();
    }
}
