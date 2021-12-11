// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Buttons;
import frc.robot.Constants.Controllers;
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
    if(Math.abs(xSpeed) < 0.01) xSpeed = 0;
    if(Math.abs(ySpeed) < 0.01) ySpeed = 0;
    if(Math.abs(zRotation) < 0.01) zRotation = 0;

    SmartDashboard.putBoolean("Button One", Buttons.BUTTON_ONE.get());
    SmartDashboard.putBoolean("Right Bumper", Buttons.RIGHT_BUMPER.get());
    SmartDashboard.putBoolean("X", Buttons.USB_BUTTON_X.get());
    SmartDashboard.putBoolean("Y", Buttons.USB_BUTTON_Y.get());
    SmartDashboard.putBoolean("Use Joysticks", DriveByJoysticks.useJoysticks);

    SmartDashboard.putNumber("USB Joystick X", Controllers.USB_JOYSTICK.getX());
    SmartDashboard.putNumber("USB Joystick Y", Controllers.USB_JOYSTICK.getY());
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
