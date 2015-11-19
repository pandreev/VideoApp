package bg.unisofia.fmi.videoapp.util;


import bg.unisofia.fmi.videoapp.convertor.VideoConvertor;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton
public class ConverterScheduler {

    private boolean hasRunningTask;
    private static int nextTaskNumber = 0;


    @Inject
    private VideoConvertor converter;

    @Schedule(second = "1", minute = "*/2", hour = "*", info = "Report every 5 Minutes", persistent = false)
    @AccessTimeout(value = 60, unit = TimeUnit.MINUTES)
    public void convertVideos() {
        if (!hasRunningTask) {
            try {
                InitialContext cxt = new InitialContext();
                DataSource ds = (DataSource) cxt.lookup("java:jboss/datasources/videoAppDS");
                Connection connection = ds.getConnection();
                Statement stmt = connection.createStatement();
                String query = "select * from ConvertTask where sequenceNumber =" + nextTaskNumber;
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    hasRunningTask = true;
                    nextTaskNumber++;
                    boolean isConverted = false;
                    final String uploadDir = rs.getString("uploadDir");
                    final String convertDir = rs.getString("convertDir");
                    final String snapshotDir = rs.getString("snapshotDir");
                    final String videoName = rs.getString("videoName");
                    if (!new File(uploadDir + videoName).exists()) {
                        hasRunningTask = false;
                        break;
                    }
                    while (!isConverted) {
                        isConverted = converter.convertRegularVideo(uploadDir, convertDir, snapshotDir, videoName);
                    }
                    isConverted = false;
                    while (!isConverted) {
                        isConverted = converter.convertLowQualityVideo(uploadDir, convertDir, snapshotDir, videoName);
                    }
                    isConverted = false;
                    while (!isConverted) {
                        isConverted = converter.convertHighQualityVideo(uploadDir, convertDir, snapshotDir, videoName);
                    }
                }
            } catch (Exception e) {
                //will try again
            }
            hasRunningTask = false;
        }
    }


}
