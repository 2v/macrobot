package frc.team61.robot.Macro;

import frc.team61.robot.RobotMap;
import frc.team61.robot.Subsystems.Drivetrain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.ctre.phoenix.motorcontrol.ControlMode.Current;
import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

public class Player {
    private Scanner scanner;
    private long startTime;

    private boolean onTime = true;
    private double nextDouble;
    private boolean ended = false;


    public Player() throws FileNotFoundException {
        // create a scanner to read the file created in the Record class
        scanner = new Scanner(Recorder.file);

        // let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
        scanner.useDelimiter(",|\\n");

        // set start time to the current time you begin autonomous
        startTime = System.currentTimeMillis();
    }

    public void play(Drivetrain drivetrain) {
        //if recordedAuto.csv has a double to read next, then read it
        if ((scanner != null) && (scanner.hasNextDouble() || scanner.hasNextBoolean())) {
            double delta;

            //if we have waited the recorded amount of time assigned to each respective motor value,
            //then move on to the next double value
            //prevents the macro playback from getting ahead of itself and writing different
            //motor values too quickly
            if(onTime) {
                //take next value
                nextDouble = scanner.nextDouble();
            }

            //time recorded for values minus how far into replaying it we are--> if not zero, hold up
            delta = nextDouble - (System.currentTimeMillis()-startTime);

            //if we are on time, then set motor values
            if (delta <= 0) {
                //for 2015 robot. these are all the motors available to manipulate during autonomous.
                //it is extremely important to set the motors in the SAME ORDER as was recorded in BTMacroRecord
                //otherwise, motor values will be sent to the wrong motors and the robot will be unpredicatable

                // drive motors
                drivetrain.firstLeftMotor.set(PercentOutput, scanner.nextDouble());
                drivetrain.secondLeftMotor.set(PercentOutput, scanner.nextDouble());
                drivetrain.firstRightMotor.set(PercentOutput, scanner.nextDouble());
                drivetrain.secondRightMotor.set(PercentOutput, scanner.nextDouble());

                // lift motors
                drivetrain.firstLiftMotor.set(PercentOutput, scanner.nextDouble());
                drivetrain.secondLiftMotor.set(PercentOutput, scanner.nextDouble());

                // claw solenoids
                drivetrain.sClawLifterA.set(scanner.nextBoolean());
                drivetrain.sClawLifterB.set(scanner.nextBoolean());
                drivetrain.sClawA.set(scanner.nextBoolean());
                drivetrain.sClawB.set(scanner.nextBoolean());

                //go to next double or boolean
                onTime = true;
            }
            //else don't change the values of the motors until we are "onTime"
            else {
                onTime = false;
            }
        }
        //end play, there are no more values to find
        else {
            this.end(drivetrain);
            if (scanner != null) {
                scanner.close();
                scanner = null;
            }
        }
    }

    //stop motors and end playing the recorded file
    public void end(Drivetrain drivetrain) {
        drivetrain.firstLeftMotor.set(Current, 0);
        drivetrain.secondLeftMotor.set(Current, 0);
        drivetrain.firstRightMotor.set(Current, 0);
        drivetrain.secondRightMotor.set(Current, 0);

        drivetrain.firstLiftMotor.set(Current, 0);
        drivetrain.secondLiftMotor.set(Current, 0);

        //if you want it to return to a specific point at the end of auto, change that here
        drivetrain.sClawLifterA.set(false);
        drivetrain.sClawLifterB.set(false);
        drivetrain.sClawA.set(false);
        drivetrain.sClawB.set(false);

        if (scanner != null) {
            scanner.close();
        }
        ended = true;

    }

    public boolean ended() {
        return ended;
    }
}
