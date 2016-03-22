##  Video Stitching - android FFmpeg

An efficient and flexible library for stitching videos. It uses FFMPEG binary file to stich the videos.
Supports latest android version. Example project included. 
This library depends on [android-ffmpeg](https://github.com/guardianproject/android-ffmpeg) to get the FFMPEG binary file.

**Usage :**

//Get Singleton Instance  

      FfmpegManager manager = FfmpegManager.getInstance();
 
 //Create Video Stiching Request Object
 
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

![Screenshot]()

## **License**
The MIT License (MIT)

Copyright (c) 2016 Karthikeyan Gopal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
