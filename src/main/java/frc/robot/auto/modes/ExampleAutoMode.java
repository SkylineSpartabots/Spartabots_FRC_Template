/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.modes;

import frc.robot.auto.ModeEndedException;
import frc.robot.auto.actions.DrivePathAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.auto.actions.WaitAction;
import frc.robot.paths.PathGenerator;
import frc.robot.subsystems.Superstructure;

/**
 * Add your docs here.
 */
public class ExampleAutoMode extends AutoModeBase {
    //Subsystems OR Structures
    Superstructure s;

    //Actions
    RunOnceAction exampleAction;
    DrivePathAction examplePathAction;
    DrivePathAction exampleReversePathAction;

    public ExampleAutoMode() {
        s = Superstructure.getInstance();
        exampleAction = new RunOnceAction() {
			@Override
			public void runOnce() {
                s.exampleSubRoutine();
			}
        };
        examplePathAction = new DrivePathAction(PathGenerator.getInstance().getPathSet().exampleTrajectory, false);
        exampleReversePathAction = new DrivePathAction(PathGenerator.getInstance().getPathSet().exampleReverseTrajectory, true);
    }

    @Override
    protected void routine() throws ModeEndedException {
        runAction(exampleAction); //Run exampleSubRoutine
        runAction(new WaitAction(1000)); //Wait for 1 second (1000 ms)
        runAction(examplePathAction); //Run first forward path
        runAction(new WaitAction(1000)); //Wait for 1 second (1000 ms)
        runAction(exampleReversePathAction); //Run backwards path and stop
        runAction(exampleAction); //Run exampleSubRoutine
    }
}
