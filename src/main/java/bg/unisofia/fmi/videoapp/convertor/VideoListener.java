package bg.unisofia.fmi.videoapp.convertor;


import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IStreamCoder;

public class VideoListener extends MediaToolAdapter {
    private Integer width;
    private Integer height;

    public VideoListener(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onAddStream(IAddStreamEvent event) {
        int streamIndex = event.getStreamIndex();
        IStreamCoder streamCoder = event.getSource().getContainer().getStream(streamIndex).getStreamCoder();
        if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
            streamCoder.setWidth(width);
            streamCoder.setHeight(height);
        }
        super.onAddStream(event);
    }

}
