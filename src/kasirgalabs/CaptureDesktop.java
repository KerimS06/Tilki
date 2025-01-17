package kasirgalabs;

import com.teamdev.jxcapture.Codec;
import com.teamdev.jxcapture.CompressionQuality;
import com.teamdev.jxcapture.EncodingParameters;
import com.teamdev.jxcapture.VideoCapture;
import com.teamdev.jxcapture.video.Desktop;
import java.awt.Dimension;
import java.io.File;

public class CaptureDesktop implements Runnable{

    private final int KEYFRAMEINTERVAL = 1000;
    private String personName;
    private String format;
    private VideoCapture videoCapture = VideoCapture.create();

    CaptureDesktop(String a, String b) {
        a = a.toLowerCase();
        personName = a;
        
    }

    public void StartCaptureDesktop() {
        
    }

    public String getPersonName() {
        return personName;
    }

    public String getFormat() {
        return format;
    }

    public void StopCaptureDesktop() {
        videoCapture.stop();
    }

    public boolean status() {
        return videoCapture.isStarted();
    }

    @Override
    public void run() {
        videoCapture.setVideoSource(new Desktop());
        java.util.List<Codec> videoCodecs = videoCapture.getVideoCodecs();
        Codec videoCodec = videoCodecs.get(0);

        
        format = videoCapture.getVideoFormat().getId().toString();
        String videoName = personName + "." + format;
        File videoFileObject = new File(personName + "." + format);
        if(videoFileObject.exists()) // File exists, try finding new file name.
        {
            for(int i = 0; i < 100; i++) {
                videoFileObject = new File(i + "_" + videoName);
                if(!videoFileObject.exists()) {
                    videoName = i + "_" + videoName;
                    break;
                }
            }
        }
        CompressionQuality q = CompressionQuality.BEST;

        File video = new File(videoName);
        EncodingParameters encodingParameters = new EncodingParameters(video);
        // Resize output video
        encodingParameters.setSize(new Dimension(800, 600));
        encodingParameters.setBitrate(800000);
        encodingParameters.setFramerate(1);
        encodingParameters.setCodec(videoCodec);
        
        /*Please don't change the parameter of the method below.*/
        encodingParameters.setKeyFrameInterval(KEYFRAMEINTERVAL);
        
        encodingParameters.setCompressionQuality(q);

        videoCapture.setEncodingParameters(encodingParameters);
        try {
            videoCapture.start();
        }
        catch(Exception e) {
        }        
    }
}
