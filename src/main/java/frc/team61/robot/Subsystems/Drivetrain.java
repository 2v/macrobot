package frc.team61.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team61.robot.RobotMap;

import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

public class Drivetrain extends IterativeRobot {

    Thread t1 = new Thread();

    // speed controllers
    // drive motors
    public TalonSRX firstLeftMotor;
    public TalonSRX secondLeftMotor;
    public TalonSRX firstRightMotor;
    public TalonSRX secondRightMotor;

    // lift motors
    public TalonSRX firstLiftMotor;
    public TalonSRX secondLiftMotor;

    // solenoids
    public Solenoid sClawLifterA;
    public Solenoid sClawLifterB;
    public Solenoid sClawA;
    public Solenoid sClawB;

    // joysticks
    private Joystick leftStick = new Joystick(RobotMap.leftStick);
    private Joystick rightStick = new Joystick(RobotMap.rightStick);
    private Joystick elevStick = new Joystick(RobotMap.elevStick);
    private Joystick clawStick = new Joystick(RobotMap.clawStick);

    public Drivetrain() {
        firstLeftMotor = new TalonSRX(RobotMap.mLeftA);
        secondLeftMotor = new TalonSRX(RobotMap.mLeftB);
        firstRightMotor = new TalonSRX(RobotMap.mRightA);
        secondRightMotor = new TalonSRX(RobotMap.mRightB);

        firstLiftMotor = new TalonSRX(RobotMap.mLiftA);
        secondLiftMotor = new TalonSRX(RobotMap.mLiftB);

        sClawLifterA = new Solenoid(RobotMap.pcmModule, RobotMap.sClawLifterChannelA);
        sClawLifterB = new Solenoid(RobotMap.pcmModule, RobotMap.sClawLifterChannelB);
        sClawA = new Solenoid(RobotMap.pcmModule, RobotMap.sClawChannelA);
        sClawB = new Solenoid(RobotMap.pcmModule, RobotMap.sClawChannelB);
    }

    public boolean getRecordButton() {
        return leftStick.getRawButton(9);
    }

    /**
     * Gets the position of the jElev Y Axis
     * @return joystick value scaled -1 to 1
     */
    public double getLiftSpeed() {
        return (elevStick.getY());
    }

    /**
     * Gets the position of the jLeft Y Axis
     * @return joystick value scaled -1 to 1
     */
    public double getLeftSpeed() {
        return (leftStick.getY());
    }

    /**
     * Gets the position of the jLeft Y Axis
     * @return joystick value scaled -1 to 1
     */
    public double getRightSpeed() {
        return (leftStick.getY());
    }

    /**
     * Gets the position of the jClaw Y Axis
     * @return joystick value scaled -1 to 1
     */
    public double getClawSpeed() { return (clawStick.getY()); }

    public double getLiftYUpSpeed() {
        if (getLiftSpeed() < 0) {
            return (elevStick.getY());
        } else {
            return 0;
        }
    }

    public double getLiftYDownSpeed() {
        if (getLiftSpeed() > 0) {
            return (elevStick.getY());
        } else {
            return 0;
        }
    }

    /**
     * Tank-drive controls.
     * @author Team 61 Programming
     * @param left Left motor value or motor stack value
     * @param right Right motor value or motor stack value
     */
    public void tankDrive(double left, double right) {
        moveLeftMotorStack(left);
        moveRightMotorStack(right);
    }

    /**
     * Reversed tank-drive controls.
     * @author Team 61 Programming
     * @param left Left motor value or motor stack value
     * @param right Right motor value or motor stack value
     */
    public void reverseTankDrive(double right, double left) {
        moveRightMotorStack(right);
        moveLeftMotorStack(left);
    }

    /**
     * Moves the multiple motors on the left side.
     * @author Team 61 Programming
     * @param speed
     */
    private void moveLeftMotorStack(double speed)
    {
//      speed = speed*-1.0;
        firstLeftMotor.set(PercentOutput, speed);
        secondLeftMotor.set(PercentOutput, speed);
    }

    /**
     * Moves the multiple motors on the right side.
     * @author Team 61 Programming
     * @param speed
     */
    private void moveRightMotorStack(double speed)
    {
        // negative to go in forward direction
        firstRightMotor.set(PercentOutput, -speed);
        secondRightMotor.set(PercentOutput, -speed);
    }

    /**
     * Stops the left and right motor stacks.
     * @author Team 61 Programming
     */
    public void stop() {
        moveLeftMotorStack(0.0);
        moveRightMotorStack(0.0);
    }

    public static void main(String[] args) {
    }

}
