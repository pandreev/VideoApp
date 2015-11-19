package bg.unisofia.fmi.videoapp.convertor;

import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageListener extends MediaListenerAdapter {

    private String snapshotDir;
    private String snapshotName;
    private static int mVideoStreamIndex = -1;
    private boolean doSnapshop;

    public ImageListener(final String snapshotDir, final String snapshotName) {
        this.snapshotDir = snapshotDir;
        this.snapshotName = snapshotName;
        this.doSnapshop = false;
    }

    @Override
    public void onVideoPicture(final IVideoPictureEvent event) {
        if (event.getStreamIndex() != mVideoStreamIndex) {
            if (mVideoStreamIndex == -1) {
                mVideoStreamIndex = event.getStreamIndex();
            } else {
                return;
            }
        }

        if (doSnapshop) {
            try {
                ImageIO.write(event.getImage(), "png", new File(snapshotDir + snapshotName + ".png"));
                doSnapshop = false;
            } catch (IOException e) {
                // do nothing
            }
        }
        if (event.getTimeStamp() == 0) {
            doSnapshop = true;
        }
    }


}
