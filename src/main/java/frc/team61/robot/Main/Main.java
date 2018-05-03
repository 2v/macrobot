package frc.team61.robot.Main;

import frc.team61.robot.Macro.Player;
import frc.team61.robot.Macro.Recorder;
import frc.team61.robot.Subsystems.Drivetrain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static edu.wpi.first.wpilibj.RobotState.isOperatorControl;

// the entry point of our application
public class Main extends Drivetrain implements Runnable {

    private final CountDownLatch countDownLatch  = new CountDownLatch(1);

    private Drivetrain drivetrain = new Drivetrain();

    private boolean isRecording = false;

    Recorder recorder = null;

    public Main() {
        // constructor
    }

    public void robotInit() {
        // initialization when robot is first turned on
    }

    public void autonomous() {
        //during autnomous, create new player object to read recorded file
        Player player = null;

        //try to create a new player
        //if there is a file, great - you have a new non-null object "player"
        try {
            player = new Player();
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        }

        //once autonomous is enabled
        while (isAutonomous()) {
            //as long as there is a file you found, then use the player to scan the .csv file
            //and set the motor values to their specific motors
            if (player != null) {
                player.play(drivetrain);
            }
            //do nothing if there is no file
        }

        //if there is a player and you've disabled autonomous, then flush the rest of the values
        //and stop reading the file
        if(player!= null) {
            player.end(drivetrain);
        }
    }

    private Thread controllerThread = new Thread() {
        public void run() {
            try {
                countDownLatch.await();
            } catch(InterruptedException e) {
                e.printStackTrace(System.out);
            }

            while(isOperatorControl()) {
                // TODO add all the methods of controlling the robot
                // this is where all the commands to control the robot will be put
                tankDrive(getLeftSpeed(), getRightSpeed());
            }
        }
    };

    private Thread recorderThread = new Thread(){
        public void run(){
            try {
                countDownLatch.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            try {
                recorder = new Recorder();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            while(isOperatorControl()) {
                if (getRecordButton()) {
                    isRecording = !isRecording;
                }
                //if our record button has been pressed, lets start recording!
                if (isRecording) {
                    System.out.println("Recording Robot Macro");
                    try {
                        // if the recorder object has been created, start recording
                        if(recorder != null) {
                            recorder.record(drivetrain);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if(recorder != null) {
                    recorder.end();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {

    }

    public void run() {
        countDownLatch.countDown();
        controllerThread.start();
        recorderThread.start();
        autonomous();

    }

}
