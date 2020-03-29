/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.drivers.LazyTalonSRX;
import frc.robot.Ports;
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;
import frc.robot.subsystems.requests.Request;

/**
 * EXAMPLE SOFT SUBSYSTEM FOR FRC ENHANCED
 */
public class ExampleSoftSubsystem extends Subsystem {
    private static ExampleSoftSubsystem instance = null;
    /**
     * @return Returns a static instance of this object so that there are not
     * multiple instances of it running at the same time.
     */
    public static ExampleSoftSubsystem getInstance() {
        if(instance == null) {
            instance = new ExampleSoftSubsystem();
        }
        return instance;
    }

    private ExampleSoftState softState;
    //Declare all variables here
    //Declare all LazyTalonSRX and LazySolenoids here
    private LazyTalonSRX testTalon;

    public ExampleSoftSubsystem() {
        /*
        Use TalonSRX libraries to setup all talons. Make sure if there are masters and slaves to set up each type properly
        Example:
        mainMotors = Arrays.asList(motor1, motor2, motor3);
        for(LazyTalonSRX tsx : mainMotors) {
            //Do setup here
        }
        */
        testTalon = new LazyTalonSRX("Talon 1", Ports.TEST_TALON1);
    }

    /**
     * ONE OF TWO POSSIBLE SUBSYSTEM ENUM "TYPES".
     * Used for more "analog" subsystems like DriveTrains and Elevators.
     * Enum values in here are constantly being checked with a switch/case statement.
     * In onLoop methods will be called that read sensors.
     */
    public enum ExampleSoftState {
        //Describe states of the subsystem here
        SOFTSTATE1;
    }

    /**
     * onStart and onEnd run once each at the initialization and ending respectively.
     * onLoop runs constantly and therefore must be synchronized.
     */
    private final Loop loop = new Loop() {
        @Override
        public void onStart(double timestamp) {
            
        }

        /**
         * This is where states are checked and sensors are read accordingly.
         * The reason this is done is because reading from sensors is an expensive process
         * so we only read specific sensors when we need to at specific times during specific states.
         * WRITING TO TALONS DONE IN writePeriodicOutputs() for SOFT SUBSYSTEMS.
         */
        @Override
        public void onLoop(double timestamp) {
            //The "synchronized" keyword is used to ensure that this loop doesn't have multiple instances of itself running on separate threads
            synchronized (ExampleSoftSubsystem.this) {
                //For Soft Enum
                switch (softState) {
                    case SOFTSTATE1:
                        //Sensors to read during STATE1
                        state1SensorCheck();  
                        break;
                    default:
                        //Default state, either for error handling or sensor reading in default state
                        break;
                }
            }
        }
        @Override
        public void onStop(double timestamp) {
            stop();
        }
    };

    public synchronized void state1SensorCheck() {
        //Read sensors here
    }

    public synchronized void powerToMotorExample(double power) {
        PeriodicIO.motorSpeedExample = power;
    }

    public void writeToLog() {
        //Write to log here
    }

    public void readPeriodicInputs() {
        //Read from sensors and write to PeriodicIO if needed, runs constantly.
        //Don't put TOO many sensors in here. Just handle soft variable setting
    }

    public void writePeriodicOutputs() {
        //THIS IS (usually) WHERE POWERS AND STATES ARE OUTPUT for Soft Subsystems
        //Powers to motors and solenoids
        testTalon.set(ControlMode.PercentOutput, PeriodicIO.motorSpeedExample);
    }

    public void outputTelemetry() {
        //Output information to SmartDashboard here
        SmartDashboard.putNumber("TestInfo", 2976);
    }

    /**
     * Called when onStop in looper is called.
     */
    public void stop() {

    }

    /**
     * Call when you need to zero ALL sensors.
     */
    public void zeroSensors() {
        //Code to reset sensors goes in here
    }

    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(loop);
    }

    //If needed, make all conversion methods here for easy access

    public static class PeriodicIO {
        /* 
        This is just a nice place to declare variables that store sensor data
        Also variables can be declared here that store numbers that will be set to motors using writePeriodicOutputs()
        Example:
        //Inputs
        static double leftEncoder;
        static double rightEncoder;
        //Outputs
        static double leftPowerDemand;
        static double rightPowerDemand;
        */
        static double motorSpeedExample;
    }

    /**
     * Used for automated control.
     * Chains with other requests to make automated actions.
     */
    public Request ExampleRequest() {
        return new Request() {

            @Override
            public void act() {
                //Request actions go here
            }
        };
    }

    /**
     * Run a test of the whole subsystem here and return a boolean that represents if the subsystem is ok (true) or bad (false)
     */
    @Override
    public boolean checkSystem() {
        
        return false;
    }
}
