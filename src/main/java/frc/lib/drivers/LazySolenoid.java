/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib.drivers;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * This class only writes to hardware when it is required.
 * It ultimately reduces traffic in the CAN bus reducing latency
 */

public class LazySolenoid extends Solenoid {

    protected boolean state = false;

    public LazySolenoid(int deviceID) {
        super(deviceID);
    }

    public boolean getLastState() {
        return state;
    }

    @Override
    public void set(boolean on) {
        if(state != on) {
            state = on;
            super.set(on);
        }
    }

}