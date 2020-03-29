/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.lib.controllers.OverridesController;
import frc.lib.controllers.Xbox;
import frc.lib.util.CrashTracker;
import frc.robot.auto.ModeExecutor;
import frc.robot.auto.ModeSelector;
import frc.robot.auto.modes.AutoModeBase;
import frc.robot.loops.Looper;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.ExampleHardSubsystem;
import frc.robot.subsystems.ExampleSoftSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.ExampleHardSubsystem.ExampleHardState;

/**
 * Notes about robot:
 * Clock cycle (frame) is 0.02s
 */
public class Robot extends TimedRobot {

  //Loopers
  private final Looper mEnabledLooper = new Looper();
  private final Looper mDisabledLooper = new Looper();

  //Controllers
  private final Xbox mExampleController = new Xbox(0);
  private final OverridesController mOverridesController = OverridesController.getInstance();

  //Subsystems
  private final SubsystemManager mSubsystemManager = SubsystemManager.getInstance();
  private final Superstructure s = Superstructure.getInstance();
  private final ExampleHardSubsystem mExampleHardSubsystem = ExampleHardSubsystem.getInstance();
  private final ExampleSoftSubsystem mExampleSoftSubsystem = ExampleSoftSubsystem.getInstance();
  private final Drive mDrive = Drive.getInstance();
  private final Limelight limelight = Limelight.getInstance();

  //Selectors (auto)
  private ModeSelector mModeSelector = new ModeSelector();
  private ModeExecutor mExampleExecutor;

  Robot() {
    CrashTracker.logRobotConstruction();
  }

  /**
   * This function is called once when the robot is turned on
   */
  @Override
  public void robotInit() {
    try {
      CrashTracker.logRobotInit();
      mSubsystemManager.setSubsystems(
        s,
        mExampleHardSubsystem,
        mExampleSoftSubsystem,
        mDrive,
        limelight
      );
      mSubsystemManager.registerEnabledLoops(mEnabledLooper);
      mSubsystemManager.registerDisabledLoops(mDisabledLooper);
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  /**
   * This function is called every frame when the robot is on
   */
  @Override
  public void robotPeriodic() {
    try {
      //Only put stuff here if ABSOLUTLEY NECESSARY, as it will run all the time
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  /**
   * This function is called once when the robot disables
   */
  @Override
  public void disabledInit() {
    try {
      CrashTracker.logDisabledInit();
      mEnabledLooper.stop();
      mDisabledLooper.start();
      if(mExampleExecutor != null) {
        mExampleExecutor.stop();
      }
      mModeSelector.reset();
      mModeSelector.updateModeSelection();
      mExampleExecutor = new ModeExecutor();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  /**
   * This function is called every frame when the robot is disabled
   */
  @Override
  public void disabledPeriodic() {
    try {
      mModeSelector.updateModeSelection();
      Optional<AutoModeBase> exampleMode = mModeSelector.getAutoMode();
      if(exampleMode.isPresent() && exampleMode.get() != mExampleExecutor.getAutoMode()) {
        System.out.println("Set auto mode to: " + exampleMode.get().getClass().toString());
        mExampleExecutor.setAutoMode(exampleMode.get());
      }
      Optional<AutoModeBase> exampleTestMode = mModeSelector.getTestMode();
      if(exampleTestMode.isPresent() && exampleTestMode.get() != mExampleExecutor.getAutoMode()) {
        System.out.println("Set test mode to: " + exampleTestMode.get().getClass().toString());
      }
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  /**
   * This function is called once when the robot enters the auto period
   */
  @Override
  public void autonomousInit() {
    try {
      CrashTracker.logAutoInit();
      mDisabledLooper.stop();
      mEnabledLooper.start();
      mExampleExecutor.stop();
      mDrive.zeroSensors();
      mExampleExecutor.start();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    } 
  }

  /**
   * This function is called every frame during autonomous
   */
  @Override
  public void autonomousPeriodic() {
    try {
      //Not much is needed here, as ModeExecutor takes care of executing the auto mode
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  /**
   * This function is called once on enabling teleop
   */
  @Override
  public void teleopInit() {
    try {
      mDisabledLooper.stop();
      if(mExampleExecutor != null) {
        mExampleExecutor.stop();
      }
      mEnabledLooper.start();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  /**
   * This function is called every frame during operator control
   */
  @Override
  public void teleopPeriodic() {
    try {
      mExampleController.update();
      mOverridesController.update();
      executeTeleop();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
    
  }

  /**
   * This function is called once on enabling test mode
   */
  @Override
  public void testInit() {
    try {
      CrashTracker.logTestInit();
      mDisabledLooper.stop();
      mEnabledLooper.start();
      mModeSelector.updateModeSelection();
      Optional<AutoModeBase> exampleTestMode = mModeSelector.getTestMode();
      mExampleExecutor.setAutoMode(exampleTestMode.get());
      mExampleExecutor.stop();
      mExampleExecutor.start();
    } catch(Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  /**
   * This function is called every frame during test mode
   */
  @Override
  public void testPeriodic() { 
    try {
      //Not much is needed here, as ModeExecutor takes care of executing the test mode
    } catch(Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  /**
   * Where button presses can be linked to requests or methods within subsystems
   */
  public void executeTeleop() {
    //Examples of linked button presses
    mExampleSoftSubsystem.powerToMotorExample(mExampleController.getY(Hand.kLeft));
    if(mExampleController.getAButton()) {
      mExampleHardSubsystem.conformToState(ExampleHardState.HARDSTATE1);
    } else {
      mExampleHardSubsystem.conformToState(ExampleHardState.HARDSTATE2);
    }
    if(mExampleController.getBButton()) {
      s.exampleSubRoutine();
    }
  }
}
