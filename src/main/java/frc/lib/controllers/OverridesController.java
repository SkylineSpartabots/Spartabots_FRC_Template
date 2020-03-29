/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class OverridesController {

    private static OverridesController mInstance = null;
    public static OverridesController getInstance() {
        if(mInstance == null) {
            mInstance = new OverridesController();
        }
        return mInstance;
    }

    private final Joystick mJoystick;

    public final Override exampleOverride1, exampleOverride2, compressorOverride;

    private final int EXAMPLE1 = 1;
    private final int EXAMPLE2 = 2;
    private final int COMPRESSOR = 3;

    private OverridesController() {
        mJoystick = new Joystick(2);

        exampleOverride1 = new Override(EXAMPLE1);
        exampleOverride2 = new Override(EXAMPLE2);
        compressorOverride = new Override(COMPRESSOR);
    }


    public class Override {

        private final int mButtonNumber;
        public Override(int buttonNumber) {
            mButtonNumber = buttonNumber;
        }

        private boolean mPrevIsActive = false;
        private double mStateChangedTimestamp = 0.0;
        private double mStateChangeTimeThreshold = 0.1;

        private boolean mIsEnabled = false;

        public boolean isEnabled() {
            return mIsEnabled;
        }


        public void update() {
            boolean isActive = mJoystick.getRawButton(mButtonNumber);

            if(isActive) {
                if(!mPrevIsActive) {
                    mStateChangedTimestamp = Timer.getFPGATimestamp();
                } else {
                    mIsEnabled = Timer.getFPGATimestamp() - mStateChangedTimestamp > mStateChangeTimeThreshold;
                }
            } else {
                if(mPrevIsActive) {
                    mStateChangedTimestamp = Timer.getFPGATimestamp();
                } else {
                    mIsEnabled = !(Timer.getFPGATimestamp() - mStateChangedTimestamp > mStateChangeTimeThreshold);
                }
            }

            mPrevIsActive = isActive;
        }

    }

    public void update() {
        exampleOverride1.update();
        exampleOverride2.update();
    }

}
