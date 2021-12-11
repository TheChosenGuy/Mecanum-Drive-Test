// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.Buttons;
import frc.robot.Constants.Controllers;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class DriveByJoysticks extends CommandBase {
  
  interface ToggelDriveMode{
    void toggle();
  }

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  public static boolean useJoysticks = true;
  private boolean hasBeenReleased = true;
  private double zSpeed = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveByJoysticks(Drivetrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    this.drivetrain = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Buttons.BUTTON_ONE.get()) {
      if(hasBeenReleased) {
        useJoysticks = !useJoysticks;
        hasBeenReleased = false;
      }
    }
    else hasBeenReleased = true;
    
    if(Buttons.USB_BUTTON_X.get()) zSpeed = 0.2;
    else if(Buttons.USB_BUTTON_Y.get()) zSpeed = -0.2;
    else zSpeed = 0;

    if(useJoysticks) drivetrain.move(
      Controllers.RIGHT_JOYSTICK.getX(),
      Controllers.RIGHT_JOYSTICK.getY(),
      Controllers.RIGHT_JOYSTICK.getZ());
    else drivetrain.move(
      Controllers.GAMEPAD.getX(Hand.kLeft) * -1,
      Controllers.GAMEPAD.getY(Hand.kLeft),
      Controllers.GAMEPAD.getX(Hand.kRight));
    if(Buttons.RIGHT_BUMPER.get()) {
      drivetrain.move(
        Controllers.USB_JOYSTICK.getX() *.2, 
        Controllers.USB_JOYSTICK.getY() *-.2,
        zSpeed);
    }
  } 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.move(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}