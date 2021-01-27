// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import operatorControl;
public class Robot extends TimedRobot {

//hahaha
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-hank");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
   WPI_VictorSPX bRMotor = new WPI_VictorSPX(2);
   WPI_VictorSPX fRMotor = new WPI_VictorSPX(3);
   WPI_TalonSRX bLMotor = new WPI_TalonSRX(9);
   WPI_TalonSRX fLMotor = new WPI_TalonSRX(0);
   double steering_adjust = 0.0;
  
  @Override
  public void robotInit() {
    bRMotor.configFactoryDefault();
    fRMotor.configFactoryDefault();
    bLMotor.configFactoryDefault();
    fLMotor.configFactoryDefault();
    fRMotor.setInverted(true);
    bRMotor.setInverted(true);
    bRMotor.follow(fRMotor);
    bLMotor.follow(fLMotor);
    fRMotor.configPeakOutputForward(0.2,10);
    fLMotor.configPeakOutputForward(0.2,10);
    bRMotor.configPeakOutputForward(0.2,10);
    bLMotor.configPeakOutputForward(0.2,10);
    fRMotor.configPeakOutputReverse(-0.2,10);
    fLMotor.configPeakOutputReverse(-0.2,10);
    bRMotor.configPeakOutputReverse(-0.2,10);
    bLMotor.configPeakOutputReverse(-0.2,10);
  
   fRMotor.config_kF(0,0.01,30);
   fLMotor.config_kF(0,0.01,30);
   
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double Kp = -0.03d;  //cabom
    XboxController xbox = new XboxController(0);
    
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);

  if (xbox.getRawButton(1))
  {
  //float min_command = 0;
  x =  table.getEntry("tx").getDouble(0.0);
  double heading_error = -x;
  
  if (x > 1.0)
  {
    steering_adjust = Kp*heading_error; 
       
  }
  else if (x < 1.0)
  {
    steering_adjust = Kp*heading_error;
  }
  steering_adjust = Kp * x;
  left_command+=steering_adjust;
  right_command-=steering_adjust;
}
tankDrive(left_command,right_command);
SmartDashboard.putNumber("moteroutput",steering_adjust);

}



  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
