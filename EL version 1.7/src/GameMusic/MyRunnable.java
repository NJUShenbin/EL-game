package GameMusic;


public class MyRunnable implements Runnable {
	
		public String musicNum = "00";
		public int looptimes =1;
    public void run (){
    	myAudioPlayer map = new myAudioPlayer();
        for (int i=0; i<looptimes;i++){
    	   map.play(musicNum);
    	}

    	
    }
}
