package frc.team61.robot;

public class RobotMap {
    private static final int autoVersionNo = 1;

    //autoFile is a global constant that keeps you from recording into a different file than the one you play from
    public static final String autoFile = "/home/lvuser/recordedauto" + autoVersionNo + ".csv";

    //Joystick Ports
    public static int leftStick = 0;
    public static int rightStick = 1;
    public static int elevStick = 2;
    public static int clawStick = 3;

    //Motor Ports
    public static int mLeftA = 1;
    public static int mLeftB = 2;
    public static int mRightA = 3;
    public static int mRightB = 4;
    public static int mLiftA = 6;
    public static int mLiftB = 5;

    //Solonoids
    public static int pcmModule = 11;
    public static int sClawLifterChannelA = 0;
    public static int sClawLifterChannelB = 1;
    public static int sClawChannelA = 2;
    public static int sClawChannelB = 3;
    public static int sBarChannelA = 6;
    public static int sBarChannelB = 7;
}
