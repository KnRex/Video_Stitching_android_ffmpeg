# Video_Stitching_android_ffmpeg

An efficient and flexible library for stitching videos. It uses FFMPEG binary file to stich the videos.


Example project included.

Usage :

 //Get Singleton Instance
 FfmpegManager manager = FfmpegManager.getInstance();
 
 //Create Video Stiching Request
 VideoStitchingRequest videoStitchingRequest = new VideoStitchingRequest.Builder()
                                                .inputVideoFilePath(videoinputArrayList)
                                                .outputPath(outputFilePath)
                                                .build();

//Call stitch videos method
  manager.stitchVideos(AddVideoActivity.this, videoStitchingRequest, new CompletionListener() {
            @Override
            public void onProcessCompleted(String message) {
               
            }
        });
    }
    
    
Stitched video file will be created in the given output file path.


