/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;
import frc.robot.loops.Looper;

/**
 * SubsystemManager takes in a list of classes that extend the abstract class "Subsystem".
 * This class is able to call the abstract methods contained within the "Subsystem" class on EVERY subsystem within the "mAllSubsystems" list.
 */
public class SubsystemManager implements ILooper {
    public static SubsystemManager mInstance = null;

    private List<Subsystem> mAllSubsystems;
    private List<Loop> mLoops = new ArrayList<>();

    private SubsystemManager() {}

    public static SubsystemManager getInstance() {
        if (mInstance == null) {
            mInstance = new SubsystemManager();
        }

        return mInstance;
    }

    public void outputToSmartDashboard() {
        mAllSubsystems.forEach(Subsystem::outputTelemetry);
    }

    public void zeroSensors() {
        mAllSubsystems.forEach(Subsystem::zeroSensors);
    }

    public boolean checkSubsystems() {
        boolean areAllOk = true;
        for (Subsystem s : mAllSubsystems) {
            areAllOk &= s.checkSystem();
        }
        return areAllOk;
    }

    public void stop() {
        mAllSubsystems.forEach(Subsystem::stop);
    }

    public List<Subsystem> getSubsystems() {
        return mAllSubsystems;
    }

    public void setSubsystems(Subsystem... allSubsystems) {
        mAllSubsystems = Arrays.asList(allSubsystems);
    }

    private class EnabledLoop implements Loop {
        @Override
        public void onStart(double timestamp) {
            mLoops.forEach(l -> l.onStart(timestamp));
        }

        @Override
        public void onLoop(double timestamp) {
            mAllSubsystems.forEach(Subsystem::readPeriodicInputs);
            mLoops.forEach(l -> l.onLoop(timestamp));
            mAllSubsystems.forEach(Subsystem::writePeriodicOutputs);
        }

        @Override
        public void onStop(double timestamp) {
            mLoops.forEach(l -> l.onStop(timestamp));
        }
    }

    private class DisabledLoop implements Loop {
        @Override
        public void onStart(double timestamp) {}

        @Override
        public void onLoop(double timestamp) {
            mAllSubsystems.forEach(Subsystem::readPeriodicInputs);
        }

        @Override
        public void onStop(double timestamp) {}
    }

    /**
     * Goes through every subsystem in the mAllSubsystems list and, if the registerEnabledLoops method within the subsystem is set up correctly, 
     * adds the Loop within it to the mLoops list. Now, within the class "EnabledLoop" (which implements Loop) which is found in the SubsystemManager class,
     * the onStart, onLoop, and onStop of each Loop in mLoops is called in the onStart, onLoop, and onStop of EnabledLoop. Finally, "enabledLooper", the argument for this method,
     * registers this "EnabledLoop" class to itself. So when the onLoop of EnabledLoop is called by enabledLooper, it calls the onLoop of every subsystem. The inner workings of enabledLooper
     * have to do with runnables and threads. The methods that are used in Robot are the "start" and "stop" methods. Yes this is very confusing. Reach out to
     * a software board memeber if you need help.
     * 
     * @see SubsystemManager SubsystemManager Class
     * @see Looper Looper Class
     */
    public void registerEnabledLoops(Looper enabledLooper) {
        mAllSubsystems.forEach(s -> s.registerEnabledLoops(this));
        enabledLooper.register(new EnabledLoop());
    }

    /**
     * Registers the DisabledLoop class located within SubsystemManager to the argument Looper passed in.
     * @see DisabledLoop DisabledLoop CLass
     */
    public void registerDisabledLoops(Looper disabledLooper) {
        disabledLooper.register(new DisabledLoop());
    }

    @Override
    public void register(Loop loop) {
        mLoops.add(loop);
    }
}
