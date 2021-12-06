// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.commands.DriveByJoysticks;

public class Drivetrain extends SubsystemBase {
  
  private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
  /** Creates a new ExampleSubsystem. */
  public Drivetrain(CANSparkMax frontLeft, CANSparkMax frontRight, CANSparkMax backLeft, CANSparkMax backRight) {
    setDefaultCommand(new DriveByJoysticks(this));
    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.backLeft = backLeft;
    this.backRight = backRight;
  }

  public void move(double xSpeed, double ySpeed, double zRotation) {
    SmartDashboard.putNumber("xSpeed", xSpeed);
    SmartDashboard.putNumber("ySpeed", ySpeed);
    SmartDashboard.putNumber("zRotation", zRotation);

    // If speed is greater than or less than 1 speed will be set to equal 1 or -1 respectfully    
    ySpeed = MathUtil.clamp(ySpeed, -1.0, 1.0);
    ySpeed = applyDeadband(ySpeed, deadband);

    xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
    xSpeed = applyDeadband(xSpeed, deadband);

    double denominator = Math.max(Math.abs(ySpeed) + Math.abs(xSpeed) + Math.abs(zRotation), 1);
    frontLeft.set((xSpeed + ySpeed + zRotation)/ denominator);
    frontRight.set((-xSpeed + ySpeed - zRotation)/ denominator);
    backLeft.set((-xSpeed + ySpeed + zRotation)/ denominator);
    backRight.set((xSpeed + ySpeed - zRotation)/ denominator);

    frontLeft.set(1.0);
    SmartDashboard.putNumber("Front Left", frontLeft.get());
    SmartDashboard.putNumber("Front Right", (-xSpeed + ySpeed - zRotation)/ denominator);
    SmartDashboard.putNumber("Back Left", (-xSpeed + ySpeed + zRotation)/ denominator);
    SmartDashboard.putNumber("Back Right", (xSpeed + ySpeed - zRotation)/ denominator);
  }

  // Returns 0.0 if the given value is within the specified range around zero.
  // The remaining range between the deadband and 1.0 is scaled from 0.0 to 1.0.
  private final double deadband = 0.02;
  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
}
