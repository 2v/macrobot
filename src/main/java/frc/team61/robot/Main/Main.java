package frc.team61.robot.Main;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team61.robot.Macro.Player;
import frc.team61.robot.Macro.Recorder;
import frc.team61.robot.Subsystems.Drivetrain;

import java.io.FileNotFoundException;
import java.io.IOException;

// the entry point of our application
public class Main extends IterativeRobot {

    public boolean toggleOnOpenClaw = false;
    boolean togglePressedOpenClaw = false;
    public boolean toggleOnBar = false;
    boolean togglePressedBar = false;
    public boolean toggleOnLiftClaw = false;
    boolean togglePressedLiftClaw = false;
    public boolean toggleOnLift = false;
    boolean togglePressedLift = false;


    private Drivetrain drivetrain;

    private boolean isRecording = false;

    private boolean clawOpen = false;
    private boolean clawHigh = false;

    private Recorder recorder = null;
    private Player player = null;

    private Thread t;

    public void robotInit() {
        drivetrain = new Drivetrain();
    }

    public void autonomousInit() { // this needs to be fixed
        //during autonomous, create new player object to read recorded file

        //try to create a new player
        //if there is a file, great - you have a new non-null object "player"
        try {
            player = new Player();
            System.out.println("Player created");
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        }

        t = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (player != null) {
                    System.out.println("Playing");
                    player.play(drivetrain);
                }
                if (player.ended()) {
                    System.out.println("Player ended");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        t.start();
        System.out.println("Started player thread");
    }

    public void autonomousPeriodic() {

    }

    public void disabledInit() {
        if (player != null) {
            player.end(drivetrain);
            player = null;
        }
    }


    public void teleopInit() {
        try {
            recorder = new Recorder();
        } catch (IOException e) {
            e.printStackTrace();
        }

        t = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (!Thread.interrupted()) {
                //if our record button has been pressed, lets start recording!

                System.out.println("Recording Robot Macro");
                try {
                    if (recorder == null) {
                        recorder = new Recorder();
                    }
                    // if the recorder object has been created, start recording
                    if (recorder != null) {
                        recorder.record(drivetrain);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if ((System.currentTimeMillis() - startTime) > 30000) {
                    try {
                        if (recorder != null) {
                            recorder.end();
                            recorder = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        System.out.println("Recorder thread");
    }

    public void teleopPeriodic() {
//        if (drivetrain.getRecordButton()) {
//            isRecording = !isRecording;
//        }

        drivetrain.tankDrive(drivetrain.getLeftSpeed(), drivetrain.getRightSpeed());
        drivetrain.moveLiftMotorStack(drivetrain.getLiftSpeed());

        if (drivetrain.clawStick.getTrigger()) {
            if (!togglePressedOpenClaw) {
                toggleOnOpenClaw = !toggleOnOpenClaw;
                togglePressedOpenClaw = true;
            }
        } else {
            togglePressedOpenClaw = false;
        }

        if (drivetrain.elevStick.getTrigger()) {
            if (!togglePressedLiftClaw) {
                toggleOnLiftClaw = !toggleOnLiftClaw;
                togglePressedLiftClaw = true;
            }
        } else {
            togglePressedLiftClaw = false;
        }

        if (toggleOnOpenClaw) {
            drivetrain.liftClaw();
        } else {
            drivetrain.lowerClaw();
        }

        if (toggleOnLiftClaw) {
            drivetrain.openClaw();
        } else {
            drivetrain.closeClaw();
        }
    }
}

