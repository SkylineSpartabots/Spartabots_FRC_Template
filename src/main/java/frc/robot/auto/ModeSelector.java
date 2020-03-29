/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.TelemetryUtil;
import frc.lib.util.TelemetryUtil.PrintStyle;
import frc.robot.auto.modes.AutoModeBase;
import frc.robot.auto.modes.DoNothing;
import frc.robot.auto.modes.ExampleAutoMode;

public class ModeSelector {

    private enum StartingPosition {
        POS_A, POS_B, POS_C
    }

    private enum AutoModes {
        DO_NOTHING,
        EXAMPLE_AUTO
    }

    private enum TestModes {
        EXAMPLE_TEST_MODE;
    }



    private AutoModes mDesiredAutoMode = null;
    private StartingPosition mStartingPosition = null;
    private TestModes mDesiredTestMode = null;

    private SendableChooser<AutoModes> mAutoModeChooser;
    private SendableChooser<StartingPosition> mStartPositionChooser;
    private SendableChooser<TestModes> mTestModeChooser;

    private Optional<AutoModeBase> mAutoMode = Optional.empty();
    private Optional<AutoModeBase> mTestMode = Optional.empty();


    public ModeSelector() {
        //Auto start position sendable chooser configuration
        mStartPositionChooser = new SendableChooser<>();
        mStartPositionChooser.setDefaultOption("Position A", StartingPosition.POS_A);
        mStartPositionChooser.addOption("Position B", StartingPosition.POS_B);
        mStartPositionChooser.addOption("Position C", StartingPosition.POS_C);

        SmartDashboard.putData("Auto Starting Position", mStartPositionChooser);

        //Auto mode sendable chooser configuration
        mAutoModeChooser = new SendableChooser<>();
        mAutoModeChooser.setDefaultOption("Do Nothing", AutoModes.DO_NOTHING);
        mAutoModeChooser.addOption("Leave Line", AutoModes.EXAMPLE_AUTO);

        SmartDashboard.putData("Auto Mode", mAutoModeChooser);

        //Test mode sendable chooser configuration
        mTestModeChooser = new SendableChooser<>();
        mTestModeChooser.setDefaultOption("Example Test", TestModes.EXAMPLE_TEST_MODE);

        SmartDashboard.putData("Test Mode", mTestModeChooser);
    }


    public void updateModeSelection() {
        StartingPosition desiredStart = mStartPositionChooser.getSelected();
        AutoModes desiredAuto = mAutoModeChooser.getSelected();
        TestModes desiredTest = mTestModeChooser.getSelected();

        if(mStartingPosition != desiredStart || mDesiredAutoMode != desiredAuto 
            || mDesiredTestMode != desiredTest) {
            
            TelemetryUtil.print("Updating choosers: Starting Position -> " + desiredStart.name()
                + ", Auto Mode -> " + desiredAuto.name() + ", Test Mode -> " + desiredTest.name(),
                PrintStyle.INFO, true);
            mAutoMode = getAutoModeForParams(desiredAuto, desiredStart);
            mTestMode = getTestModeForParams(desiredTest);
        }

        mStartingPosition = desiredStart;
        mDesiredAutoMode = desiredAuto;
        mDesiredTestMode = desiredTest;
    }


    private Optional<AutoModeBase> getAutoModeForParams(AutoModes mode, StartingPosition position) {
        switch(mode) {
            case DO_NOTHING:
                return Optional.of(new DoNothing());
            case EXAMPLE_AUTO:
                return Optional.of(new ExampleAutoMode());
            default:
                break;
        }
        TelemetryUtil.print("No valid auto mode found for " + mode.name(), PrintStyle.ERROR, true);
        return Optional.empty();
    }

    private Optional<AutoModeBase> getTestModeForParams(TestModes mode) {
        switch(mode) {
            case EXAMPLE_TEST_MODE:
                return Optional.empty(); //Add test returns/runs here
            default:
                break;
        }
        TelemetryUtil.print("No valid test mode found for " + mode.name(), PrintStyle.ERROR, true);
        return Optional.empty();
    }


    public void reset() {
        mAutoMode = Optional.empty();
        mTestMode = Optional.empty();
        mDesiredAutoMode = null;
        mDesiredTestMode = null;
        mStartingPosition = null;
    }


    public Optional<AutoModeBase> getAutoMode() {
        return mAutoMode;
    }

    public Optional<AutoModeBase> getTestMode() {
        return mTestMode;
    }
}
