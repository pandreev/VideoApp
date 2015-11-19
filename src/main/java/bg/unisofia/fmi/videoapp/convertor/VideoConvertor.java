package bg.unisofia.fmi.videoapp.convertor;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import javax.ejb.Singleton;
import java.awt.image.BufferedImage;

@Singleton
public class VideoConvertor {

    private static final Integer LOW_WIDTH = 640;
    private static final Integer LOW_HEIGHT = 360;

    private static final Integer REGULAR_WIDTH = 1280;
    private static final Integer REGULAR_HEIGHT = 720;

    private static final Integer HIGH_WIDTH = 1920;
    private static final Integer HIGH_HEIGHT = 1080;

    public boolean convertRegularVideo(final String uploadDir, final String convertDir, final String snapshotDir, final String videoName) {
        return convertVideo(videoName, videoName.substring(0, videoName.lastIndexOf('.')) + "_reg.mp4", uploadDir, convertDir, snapshotDir, REGULAR_WIDTH, REGULAR_HEIGHT);
    }

    public boolean convertLowQualityVideo(final String uploadDir, final String convertDir, final String snapshotDir, final String videoName) {
        return convertVideo(videoName, videoName.substring(0, videoName.lastIndexOf('.')) + "_low.mp4", uploadDir, convertDir, snapshotDir, LOW_WIDTH, LOW_HEIGHT);
    }

    public boolean convertHighQualityVideo(final String uploadDir, final String convertDir, final String snapshotDir, final String videoName) {
        return convertVideo(videoName, videoName.substring(0, videoName.lastIndexOf('.')) + "_high.mp4", uploadDir, convertDir, snapshotDir, HIGH_WIDTH, HIGH_HEIGHT);
    }

    private boolean convertVideo(final String inputFile, final String outputFileName, final String inputDir, final String outputDir, final String snapshotDir, final Integer width, final Integer height) {
        VideoListener myVideoListener = new VideoListener(width, height);
        VideoResizer resizer = new VideoResizer(width, height);
        //
        IMediaReader reader = ToolFactory.makeReader(inputDir + inputFile);
        reader.addListener(resizer);
        if (height.equals(LOW_HEIGHT)) {
            reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
            reader.addListener(new ImageListener(snapshotDir, outputFileName.substring(0, outputFileName.indexOf("_"))));
        }
        //
        IMediaWriter writer = ToolFactory.makeWriter(outputDir + outputFileName, reader);
        resizer.addListener(writer);
        writer.addListener(myVideoListener);
        try {
            while (reader.readPacket() == null) {

            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
