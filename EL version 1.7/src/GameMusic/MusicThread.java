package GameMusic;



public class MusicThread {
      String  musictag="00";
      int Looptimes =1;
      MyRunnable threadJob;
   public  Thread mt;
     
	public  void choosemusic (String a){
		switch (a){
		case "bgm00": musictag = "bgm00"; break;
		case "bgm01": musictag = "bgm01"; break;
		case "bgm02": musictag = "bgm02"; break;
		case "rock00": musictag = "rock00"; break;
		case "rock01": musictag = "rock01"; break;
		case "explo00": musictag = "explo00"; break;
		case "explo01": musictag = "explo01"; break;
		case "explo02": musictag = "explo02"; break;
		case "explo03": musictag = "explo03"; break;
		case "explo04": musictag = "explo04"; break;
		case "trans00":musictag = "trans00"; break;
		case "trans01":musictag = "trans01"; break;
		
		
		
		
		}
	}
	
	public  void changeLoopTimes (int b){
		Looptimes = b;
		
	}
	public  void creatMT(String c , int d){
		choosemusic (c);
		changeLoopTimes(d);
	   threadJob = new MyRunnable();
	   threadJob.musicNum= musictag;
	   threadJob.looptimes=Looptimes;
	   mt = new Thread(threadJob);
	   mt.start();
	}
	public void sleepMyThread (){
	    try{
		   mt.sleep(1000);
		}catch (Exception e ){};
	
	}
}
