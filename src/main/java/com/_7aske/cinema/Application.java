package com._7aske.cinema;

import com._7aske.grain.GrainAppRunner;
import com._7aske.grain.core.configuration.GrainApplication;

@GrainApplication
public class Application {
    public static void main(String[] args) {
        GrainAppRunner.run(Application.class);
    }
}
