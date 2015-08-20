package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameMusic.MusicThread;
import cn.edu.nju.components.ExitWhite;
import cn.edu.nju.components.NextWhite;
import cn.edu.nju.components.RestartWhite;
import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.publicTools.Tail;
import cn.edu.nju.spaceTools.ExitInSpace;
import cn.edu.nju.spaceTools.ObjectsInSpace;
import cn.edu.nju.spaceTools.PlanetC;
import cn.edu.nju.spaceTools.SpaceEngine;
import cn.edu.nju.spaceTools.SpaceShip;

public class SpaceMission1 extends BackgroundPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	File f = new File("solidcolor.jpg");
	BufferedImage bi;
	DrawParticle part = new DrawParticle();
	SpaceEngine engine;
	Tail tail;
	
	ArrayList<ObjectsInSpace> objects = new ArrayList<ObjectsInSpace>();
	
	ArrayList<Ellipse2D> sixballs = new ArrayList<Ellipse2D>();
	Point p1 = new Point();
	Point p2 = new Point();
	boolean isPressed = false;
	boolean isReleased = false;
	
	RestartWhite restart = new RestartWhite();
	ExitWhite exit = new ExitWhite();
	NextWhite next = new NextWhite();
	
	int launcherNum = 1;
	
	
	public SpaceMission1(){
		
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<6;i++){
			sixballs.add(new Ellipse2D.Float());
		}
		
		this.removeAll();
		
		
		this.add(restart);
		this.add(exit);
		this.add(next);
		
		SpaceLis slis = new SpaceLis();
		this.addMouseListener(slis);
		this.addMouseMotionListener(slis);
		
		SpaceShip spaceship = new SpaceShip(150,360);
		PlanetC planetA = new PlanetC(450,360,500,0,0,200);
		PlanetC planetB = new PlanetC(800,360,600,0,0,200);
		ExitInSpace exitInSpace = new ExitInSpace(1100, 360, 100);
		objects.add(spaceship);
		objects.add(planetA);
		objects.add(planetB);
		objects.add(exitInSpace);
		tail = new Tail(spaceship);
		engine = new SpaceEngine(objects, part);
	}
	
	
	public void init(){
		super.init();
		isPressed = false;
		isReleased = false;
		launcherNum = 1;
		engine = new SpaceEngine(objects, part);
		this.removeAll();
		 restart = new RestartWhite();
		 exit = new ExitWhite();
		 next = new NextWhite();
		
		this.add(restart);
		this.add(exit);
		this.add(next);
		
		SpaceShip ship = new SpaceShip(150,360);
		objects.set(0, ship);
		tail = new Tail(ship);
	}
	
	public void paintIBuffer(){
		coordinateTrans();
		engine.update();
		p1.setLocation(objects.get(0).x,objects.get(0).y);
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
		gBuffer.drawImage(bi,0,0,1280,720,null);
		
		
		paintBackground();
		tail.drawTail(iBuffer);
		
		for(int i=0;i<objects.size();i++){
			objects.get(i).draw(iBuffer);
		}
		
		if(isPressed){
			gBuffer.setColor(Color.WHITE);
			for(int i=0;i<6;i++)
				gBuffer.fill((Ellipse2D)sixballs.get(i));
		}
		
		part.draw(iBuffer);
	}
	
	public void paintComponent(Graphics g){
		if(restart.isMouseClicked()){
			init();
		}
		
		if(engine.isMissionComplete()||next.isMouseClicked()){
			needChange =true;
			changeTo  = "spaceMission2";
		}

		if(exit.isMouseClicked()){
			needChange = true;
			changeTo = "startPanel";
		}
		
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0, null);
	}
	
	private void computeLoc(ArrayList<Ellipse2D> sixballs){
  	   
	   double deltaX = p2.getX()-p1.getX();
  	   double deltaY = p2.getY()-p1.getY();
  	       
  	   for(int i =0;i<6;i++){
  		   double x = p1.getX()+(i/5.0)*deltaX;
  		   double y = p1.getY()+(i/5.0)*deltaY;
  		   Ellipse2D.Float ellipse = (Ellipse2D.Float)sixballs.get(i);
  		   ellipse.setFrameFromCenter(x,y,x+5,y+5);
  	   }
  	   
     }
	
	
	
	class SpaceLis extends PanelLis{
		
		
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		
			if(launcherNum>0){
		   isPressed =true;
		   p2 = (Point) e.getPoint();
		  p2 = coordinateTrans(p2);
		  
		  double deltax = p2.getX() - p1.getX();
          double deltay = p2.getY() - p1.getY();
          
          objects.get(0).vx = deltax/100000;
          objects.get(0).vy = deltay/100000;
		  
		   computeLoc(sixballs);
			}
		}



		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			  
		}



		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(launcherNum>0){
			isPressed =true;
			 p1.setLocation(objects.get(0).x, objects.get(0).y);
			}
		}



		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(launcherNum>0){
			isPressed = false;
            isReleased = true;
            
            double deltax = (p2.getX() - p1.getX())*0.03;
            double deltay = (p2.getY() - p1.getY())*0.03;
            
            objects.get(0).vx = deltax;
            objects.get(0).vy = deltay;
            
            launcherNum--;
			}
			//music 
			MusicThread musicthread = new MusicThread ();
			musicthread.creatMT("rock00", 1);
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
}
		
	

}
