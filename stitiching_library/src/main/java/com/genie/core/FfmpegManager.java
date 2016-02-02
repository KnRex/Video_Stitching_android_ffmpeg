package com.genie.core;

import android.content.Context;


import com.genie.listeners.CompletionListener;
import com.genie.stitiching_library.R;
import com.genie.utils.Utils;
import com.genie.utils.VideoStitchingRequest;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karthik on 21/01/16.
 */
public class FfmpegManager {

    private static FfmpegManager manager;

    private String mFfmpegInstallPath;

    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    // A queue of Runnables
    private final BlockingQueue<Runnable> mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // Creates a thread pool manager
    ThreadPoolExecutor mDecodeThreadPool = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            mDecodeWorkQueue);

    private FfmpegManager() {

    }

    public synchronized static FfmpegManager getInstance() {

        if (manager == null) {
            manager = new FfmpegManager();

        }
        return manager;
    }




    public void stitchVideos(Context context, VideoStitchingRequest videoStitchingRequest, CompletionListener completionListener) {
        installFfmpeg(context);
        StitchingTask stitchingTask = new StitchingTask(context, mFfmpegInstallPath, videoStitchingRequest, completionListener);
        mDecodeThreadPool.execute(stitchingTask);
    }

    /*
    * Installs the ffmpeg library in temporay path.
    */
    private void installFfmpeg(Context context) {

        String arch = System.getProperty("os.arch");
        String arc = arch.substring(0, 3).toUpperCase();
        String rarc = "";
        int rawFileId;
        if (arc.equals("ARM")) {
            rawFileId = R.raw.ffmpeg;
        } else if (arc.equals("MIP")) {
            rawFileId = R.raw.ffmpeg;
        } else if (arc.equals("X86")) {
            rawFileId = R.raw.ffmpeg_x86;
        } else {
            rawFileId = R.raw.ffmpeg;
        }

        File ffmpegFile = new File(context.getCacheDir(), "ffmpeg");
        mFfmpegInstallPath = ffmpegFile.toString();
        Utils.installBinaryFromRaw(context, rawFileId, ffmpegFile);
        ffmpegFile.setExecutable(true);
    }

}
