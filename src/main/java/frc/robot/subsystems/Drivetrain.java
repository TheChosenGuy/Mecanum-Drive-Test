// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Buttons;
import frc.robot.commands.DriveByJoysticks;

public class Drivetrain extends SubsystemBase {
  
  private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
  MecanumDrive mecanumDrive;
  /** Creates a new ExampleSubsystem. */
  public Drivetrain(CANSparkMax frontLeft, CANSparkMax frontRight, CANSparkMax backLeft, CANSparkMax backRight) {
    setDefaultCommand(new DriveByJoysticks(this));
    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.backLeft = backLeft;
    this.backRight = backRight;
    mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
  }

  public void move(double xSpeed, double ySpeed, double zRotation) {
    SmartDashboard.putBoolean("Button One", Buttons.BUTTON_ONE.get());
    SmartDashboard.putBoolean("Use Joysticks", DriveByJoysticks.useJoysticks);

    SmartDashboard.putNumber("xSpeed", xSpeed);
    SmartDashboard.putNumber("ySpeed", ySpeed);
    SmartDashboard.putNumber("zRotation", zRotation);

    // mecanumDrive.setRightSideInverted(true); 
    mecanumDrive.setDeadband(0.1); // Adjust as needed
    mecanumDrive.setMaxOutput(.5);
    
    // TODO: Truncate or round speeds to 0.01 
    mecanumDrive.driveCartesian(ySpeed, xSpeed, zRotation);
  }

}
