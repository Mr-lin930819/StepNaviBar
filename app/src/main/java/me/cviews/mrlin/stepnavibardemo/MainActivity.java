package me.cviews.mrlin.stepnavibardemo;

import android.app.Activity;
import android.os.Bundle;

import me.cviews.mrlin.stepnavibar.StepNavigateBar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StepNavigateBar stepBarBlue, stepBarGreen, stepBarRed, stepBarOrange, stepBarPurple;
        stepBarBlue = (StepNavigateBar) findViewById(R.id.step_view_blue);
        stepBarGreen = (StepNavigateBar) findViewById(R.id.step_view_green);
        stepBarRed = (StepNavigateBar) findViewById(R.id.step_view_red);
        stepBarOrange = (StepNavigateBar) findViewById(R.id.step_view_yellow);
        stepBarPurple = (StepNavigateBar) findViewById(R.id.step_view_purple);

        String[] steps = new String[]{"Step1", "Step2", "Step3", "Step4"};
        String[] chnSteps = new String[]{"步骤一", "步骤二", "步骤三", "步骤四"};

        stepBarBlue.setStepNames(steps);
        stepBarBlue.setCurrentStep(1);
        stepBarGreen.setStepNames(steps);
        stepBarGreen.setCurrentStep(2);
        stepBarRed.setStepNames(chnSteps);
        stepBarRed.setCurrentStep(3);
        stepBarOrange.setStepNames(chnSteps);
        stepBarOrange.setCurrentStep(4);
        stepBarPurple.setStepNames("step1", "step2", "step3", "step4", "step5");
        stepBarPurple.setCurrentStep(4);

    }
}
