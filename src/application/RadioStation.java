package application;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/******
 * Referenced Code from Daniel Paje 
 * Modified by Tam Henry Le Nguyen
 */

public class RadioStation 
{
	Clip standardCityClip;
	Clip raveCityClip;

	RadioStation()
	{
		initMusic();
	}
	
	private void initMusic() 
	{
		try 
		{
			InputStream standardCityTest = new BufferedInputStream(new FileInputStream("res/audio/FurElise.wav"));
			AudioInputStream standardCityIn = AudioSystem.getAudioInputStream(standardCityTest);
			standardCityClip = AudioSystem.getClip();
			standardCityClip.open(standardCityIn);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
 
		try 
		{
			InputStream raveCityTest = new BufferedInputStream(new FileInputStream("res/audio/ScaryMonstersCut.wav"));
			AudioInputStream raveCityIn = AudioSystem.getAudioInputStream(raveCityTest);
			raveCityClip = AudioSystem.getClip();
			raveCityClip.open(raveCityIn);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if (standardCityClip != null) 
		{
			standardCityClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
	}
	
	public void stopBGMusic() 
	{
		if (standardCityClip.isRunning()) 
		{
			standardCityClip.stop();
		}
	}
	
	public void startBGMusic() 
	{
		stopRaveMusic();
		
		if (standardCityClip != null) 
		{
			standardCityClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void startRaveMusic() 
	{
		if (raveCityClip != null) 
		{
			stopBGMusic();
 
			System.out.println("RAVE");
			raveCityClip.setFramePosition(0);
			raveCityClip.start();
			raveCityClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
 
	public void stopRaveMusic() 
	{
		if (raveCityClip.isRunning()) 
		{
			raveCityClip.stop();
		}
	}
}	
