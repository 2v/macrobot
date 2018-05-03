package frc.team61.robot.Macro;

import frc.team61.robot.RobotMap;
import frc.team61.robot.Subsystems.Drivetrain;

import java.io.FileWriter;
import java.io.IOException;

public class Recorder {

    //this object writes values into the file we specify
    private FileWriter writer;

    private long startTime;

    public Recorder() throws IOException {
        //record the time we started recording
        startTime = System.currentTimeMillis();

        //put the filesystem location you are supposed to write to as a string
        //as the argument in this method, as of 2015 it is /home/lvuser/recordedAuto.csv
        writer = new FileWriter(RobotMap.autoFile);
    }


    public void record(Drivetrain drivetrain) throws IOException
    {
        if(writer != null)
        {
            // start each "frame" with the elapsed time since we started recording
            writer.append("" + (System.currentTimeMillis()-startTime));

            // in this chunk, use writer.append to add each type of data you want to record to the frame

            // drive motors
            writer.append("," + drivetrain.firstLeftMotor.getMotorOutputPercent());
            writer.append("," + drivetrain.secondLeftMotor.getMotorOutputPercent());
            writer.append("," + drivetrain.firstRightMotor.getMotorOutputPercent());
            writer.append("," + drivetrain.secondRightMotor.getMotorOutputPercent());

            // lift motors
            writer.append("," + drivetrain.firstLiftMotor.getMotorOutputPercent());
            writer.append("," + drivetrain.secondLiftMotor.getMotorOutputPercent());

            // claw solenoids
            writer.append("," + drivetrain.sClawLifterA.get());
            writer.append("," + drivetrain.sClawLifterB.get());
            writer.append("," + drivetrain.sClawA.get());
            writer.append("," + drivetrain.sClawB.get());


            /*
             * THE LAST ENTRY OF THINGS YOU RECORD NEEDS TO HAVE A DELIMITER CONCATENATED TO
             * THE STRING AT THE END. OTHERWISE GIVES NOSUCHELEMENTEXCEPTION
             */
            writer.append("," + drivetrain.sClawLifterA.get() + "\n");
        }
    }

    //this method closes the writer and makes sure that all the data you recorded makes it into the file
    public void end() throws IOException
    {
        if(writer !=null)
        {
            writer.flush();
            writer.close();
        }
    }
}

