/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Store all constants here for easy access.
 */
public class Constants {
	// Timeout for CAN commands and error checking
	public static final int kTimeOutMs = 10;

	// Cycle speed for looper threads
	public static final double kLooperDt = 0.01;

	//Unknowns
	public static final double kTargetHeight = 0.0;
	public static final double kLensHeight = 0.0;
	public static final double kLensHorizontalAngle = 0.0;
	public static final double kCompressorShutOffCurrent = 0.0;
	public static final double kDriveWheelTrackWidthMeters = 0.0;
	public static final double kDriveWheelDiameter = 0.0;
	public static final double kDriveMaxVelocity = 0;
	public static final double kDriveMaxAcceleration = 0;

	//Drive
	public static final double kQuickStopThreshold = 0;
	public static final double kQuickStopAlpha = 0.05;
	public static final double kDriveWheelDiameterInMeters = kDriveWheelDiameter * 0.0254;
	public static final double kDriveWheelRadiusInches = kDriveWheelDiameter / 2.0;
	public static final double kDriveWheelTrackRadiusMeters = kDriveWheelTrackWidthMeters / 2.0;
	public static final double kDriveKaVolts = 0.2;
	public static final double kDriveKvVolts = 1.98;
	public static final double kDriveKsVolts = 0.22;
	public static final double kDriveTurnKs = 0.05;
}
