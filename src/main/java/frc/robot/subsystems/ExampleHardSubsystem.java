/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.drivers.LazySolenoid;
import frc.lib.drivers.LazyTalonSRX;
import frc.robot.Ports;
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;
import frc.robot.subsystems.requests.Request;

/**
 * EXAMPLE HARD SUBSYSTEM FOR FRC ENHANCED
 */
public class ExampleHardSubsystem extends Subsystem {
    private static ExampleHardSubsystem instance = null;
    /**
     * @return Returns a static instance of this object so that there are not 
     * multiple instances of it running at the same time
     */
    public static ExampleHardSubsystem getInstance() {
        if(instance == null) {
            instance = new ExampleHardSubsystem();
        }
        return instance;
    }

    private ExampleHardState hardState;
    //Declare all variables here
    //Declare all LazyTalonSRX and LazySolenoids here
    private LazyTalonSRX testTalon;
    private LazySolenoid testSolenoid;

    public ExampleHardSubsystem() {
        /*
        Use TalonSRX libraries to setup all talons. Make sure if there are masters and slaves to set up each type properly
        Link to TalonSRX Docs: http://www.ctr-electronics.com/downloads/api/java/html/enumcom_1_1ctre_1_1phoenix_1_1motorcontrol_1_1_neutral_mode.html#aa61e3fa77cf9ae7024cb0f5699aedbf8
        
        Example:
        motor1 = new LazyTalonSRX(Ports.MOTOR_1_PORT);
        motor2 = new LazyTalonSRX(Ports.MOTOR_2_PORT);
        motor3 = new LazyTalonSRX(Ports.MOTOR_3_PORT);
        mainMotors = Arrays.asList(motor1, motor2, motor3);
        for(LazyTalonSRX tsx : mainMotors) {
            //Do setup here
        }
        */
        testTalon = new LazyTalonSRX("Talon 2", Ports.TEST_TALON2);
        testSolenoid = new LazySolenoid(Ports.TEST_SOLENOID);
    }

    /**
     * ONE OF TWO POSSIBLE SUBSYSTEM ENUM "TYPES".
     * Used for more "digital" subsystems like Pistons and Intakes.
     * Enum values in here have inputs for LazyTalons and LazySolenoids.
     * In onLoop methods will be called logically conformToState when needed.
     */
    public enum ExampleHardState {
        //Describe states of the subsystem here
        HARDSTATE1(false, 0.0),
        HARDSTATE2(true, 1.0);

        public boolean exampleBoolean;
        public double exampleDouble;

        private ExampleHardState(boolean exampleBoolean, double exampleDouble) {
            this.exampleBoolean = exampleBoolean;
            this.exampleDouble = exampleDouble;
        }
    }

    public void conformToState(ExampleHardState desiredState) {
        testSolenoid.set(desiredState.exampleBoolean);
        testTalon.set(ControlMode.PercentOutput, desiredState.exampleDouble);
    }

    private ExampleHardState currentHardState = ExampleHardState.HARDSTATE1; //State to begin on
    private boolean stateChanged = false; 

    /**
     * Sets the state in the code for local accessing. 
     * Information in currentHardState can be used for logic operations.
     * @param newState Takes in an enum with values to compare to the currently saved enum in currentHardState.
     */
    private synchronized void setState(ExampleHardState newState) {
        if(currentHardState != newState) {
            currentHardState = newState;
            stateChanged = true;
        }
    }

    /**
     * Used for logic processing.
     * @return Returns the current state of the enum.
     */
    public ExampleHardState getState() {
        return currentHardState;
    }

    /**
     * Checks if the state has been changed since execution of state change.
     * @return If the state has been changed (true) or not (false).
     */
    public boolean isStateChanged() {
        return stateChanged;
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
         * This is where logic operations using variables set in other methods are used to find out the state the robot needs to be in.
         */
        @Override
        public void onLoop(double timestamp) {
            //The "synchronized" keyword is used to ensure that this loop doesn't have multiple instances of itself running on separate threads.
            synchronized (ExampleHardSubsystem.this) {
                switch (hardState) {
                    case HARDSTATE1:
                        //Do something in HARDSTATE1
                        break;

                    case HARDSTATE2:
                        //Do something in HARDSTATE2
                        break;

                    default:
                        //Default state, either for error handling or setting a default state for the subsystem
                        break;
                }
            }

            //Set stateChanged back to false at end of loop (Only for Hard Subsystems)
            stateChanged = false;
        }
        @Override
        public void onStop(double timestamp) {
            stop();
        }
    };

    public synchronized void state1SensorCheck() {
        //Read sensors here
    }

    public void writeToLog() {
        //Write to log here
    }

    public void readPeriodicInputs() {
        //Read from sensors and write to PeriodicIO if needed, runs constantly.
        //Don't put TOO many sensors in here. Just handle soft variable setting
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

    /**
     * Used for automated control.
     * Chains with other requests to make automated actions.
     */
    public Request ExampleRequest() {
        return new Request() {

            @Override
            public void act() {
                //Request actions go here
                setState(ExampleHardState.HARDSTATE1);
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
