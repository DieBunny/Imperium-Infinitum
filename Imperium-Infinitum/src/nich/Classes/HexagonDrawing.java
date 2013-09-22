package nich.Classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import nich.Package.R;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class HexagonDrawing {
	
	public List<List<Planet>> planetList = new ArrayList<List<Planet>>();
	public List<List<Planet>> rhombList = new ArrayList<List<Planet>>();
	
	public void DrawHex (RelativeLayout layout, Context context, int n, int displayHeight, int displayWidth)
	{
		int z = 0;
		switch (n)
		{
		   case 2:
		        z=9; 
		      break;
		   case 3:
		        z=12;
		      break;
		   case 4:
		         z=15;
		      break;
		   default:
		      break;
		}
		
		
		n=12;
		//hex size depends on screen size and player count
		//int oneHex = (displayHeight-(displayHeight/5))/(n);
		int oneHex = 78;
		int i, j;
		float y,x;
		//d should be changed. not precise for now
		float d=(float) (oneHex / 2* Math.sqrt(3)-(oneHex/5.5));// d is the distance between 2 points 
		for(i=0; i<=(n-1); i++) {
		    y = (float) ((Math.sqrt(3)*i*d)/2.0);
		    for (j = 0; j < (2*z-1-i); j++) {
		        x = (float) ((-(2*n-i-2)*d)/2.0 + j*d);
		        RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(oneHex, oneHex);
				ImageButton imgBtn = new ImageButton(context);
				imgBtn.setBackgroundColor(Color.TRANSPARENT);
				imgBtn.setImageResource(R.drawable.hexwt);
				shareParams.leftMargin = Math.abs((int) (x+oneHex*12));
				shareParams.topMargin = Math.abs((int) (y+oneHex*8));
				imgBtn.setLayoutParams(shareParams);
				imgBtn.setScaleType(ScaleType.FIT_XY);
				//tags - I doubt we will use them
				//just place where to write down array element`s [i][j]
				//Planet xxx = new Planet();
				//xxx.xPos = i;
				//xxx.yPos = j;
				//imgBtn.setTag(xxx);
				layout.addView(imgBtn);
		        
		        if (y!=0) {
			        RelativeLayout.LayoutParams shareParams1 = new RelativeLayout.LayoutParams(oneHex, oneHex);
					ImageButton imgBtn1 = new ImageButton(context);
					imgBtn1.setBackgroundColor(Color.TRANSPARENT);
					imgBtn1.setImageResource(R.drawable.hexwt);
					shareParams1.leftMargin = Math.abs((int) (x+oneHex*12));
					shareParams1.topMargin = Math.abs((int) (-y+oneHex*8));
					imgBtn1.setLayoutParams(shareParams1);
					imgBtn1.setScaleType(ScaleType.FIT_XY);
					//just place where to write down array element`s [i][j]
					//Planet xyx = new Planet();
					//xyx.xPos = -i;
					//xyx.yPos = j+i;
					//imgBtn1.setTag(xyx);
					layout.addView(imgBtn1);
		        }
		    }

		}
	}

	public void GetXmlPlanets(int playerCount, XmlResourceParser xpp )
	{
		String tempName = new String();
		Random rand = new Random();
		List<Integer> randInt = new ArrayList<Integer>();
		List<Integer> randIntRhombus = new ArrayList<Integer>();
		
		for(int j =0; j < playerCount; j++)
		{
			int r = rand.nextInt(7);
			int rr = rand.nextInt(6);
			while (randInt.contains(r))
			{r = rand.nextInt(7);}
			while (randIntRhombus.contains(rr))
			{rr = rand.nextInt(6);}
			
			randInt.add(r);
			randIntRhombus.add(rr);
		}
		
			try {
				xpp.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   int eventType = 0;
			try {
				eventType = xpp.getEventType();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int i =0;
			int r=0;
		   while (eventType != XmlPullParser.END_DOCUMENT)
		   {
			   
			   tempName = xpp.getName();
			   if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("Hexagon"))
			   {
				   if (randInt.contains(i))
				   {
				   
				   List<Planet> tempList = new ArrayList<Planet>();
				   while(eventType!=XmlPullParser.END_TAG )
				   {tempName = xpp.getName();
					   if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("Planet"))
					   { Planet newPlanet = new Planet();
						   while(eventType!=XmlPullParser.END_TAG)
						   {tempName = xpp.getName();
						   
							
							
							   if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("xPos"))
							   {
								   try {
									eventType = xpp.next();
								} catch (XmlPullParserException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								   if (eventType==XmlPullParser.TEXT)
								   {
									   newPlanet.xPos = Integer.parseInt(xpp.getText());
									   try {
										eventType = xpp.next();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }

							   }
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("YPos"))
							   {
								   try {
										eventType = xpp.next();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									   if (eventType==XmlPullParser.TEXT)
									   {
										   newPlanet.yPos = Integer.parseInt(xpp.getText());
										   try {
											eventType = xpp.next();
										} catch (XmlPullParserException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									   }
							   }
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("CombatPhases"))
							   {
								   while(eventType!=XmlPullParser.END_TAG)
								   {tempName = xpp.getName();
									   if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("LongRange"))
									   {newPlanet.combatPhases = new CombatPhases(0,0,0,0);
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.longRange=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("MediumRange"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.mediumRange=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("ShortRange"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.shortRange=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("LastStand"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.lastStand=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   
									   try {
										   tempName = xpp.getName();
										eventType = xpp.next();
										tempName = xpp.getName();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }
							   }
							   
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("Resources"))
							   {
								   while(eventType!=XmlPullParser.END_TAG)
								   {tempName = xpp.getName();
									   if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Fuel"))
									   {newPlanet.planetRes = new nich.Classes.Resources(0, 0, 0, 0, 0);
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Fuel=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Research"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Research=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("BattleShip"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.BattleShip=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Destroyer"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Destroyer=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Frigate"))
									   {
										   try {
											   tempName = xpp.getName();
												eventType = xpp.next();
												tempName = xpp.getName();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Frigate=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   try {
										   tempName = xpp.getName();
										eventType = xpp.next();
										tempName = xpp.getName();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }
							   }
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("PlanetType"))
							   {		   try {
									eventType = xpp.next();
								} catch (XmlPullParserException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								   if (eventType==XmlPullParser.TEXT)
								   {
									   newPlanet.planetType=xpp.getText();
									   try {
										eventType = xpp.next();
										tempName = xpp.getName();

										
										   
										   
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }
							   }
							   try {
								   eventType = xpp.next();
							} catch (XmlPullParserException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							   
						   }
						   if(eventType==XmlPullParser.END_TAG && xpp.getName().equals("Planet"))
							{tempList.add(newPlanet);
							try {
								eventType = xpp.next();
							} catch (XmlPullParserException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							tempName = xpp.getName();}
						   
					   }
					   else
					   {try {
						   tempName = xpp.getName();
						eventType = xpp.next();
						tempName = xpp.getName();
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				   }
				   planetList.add(tempList);

			   }i++;
			   }
			   //RHOMBUS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("Rhombus"))
			   {tempName = xpp.getName();
				   if (randIntRhombus.contains(r))
				   {
				   
				   List<Planet> tempList = new ArrayList<Planet>();
				   while(eventType!=XmlPullParser.END_TAG )
				   {tempName = xpp.getName();
					   if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("Planet"))
					   { Planet newPlanet = new Planet();
						   while(eventType!=XmlPullParser.END_TAG)
						   {tempName = xpp.getName();
						   
							
							
							   if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("xPos"))
							   {
								   try {
									eventType = xpp.next();
								} catch (XmlPullParserException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								   if (eventType==XmlPullParser.TEXT)
								   {
									   newPlanet.xPos = Integer.parseInt(xpp.getText());
									   try {
										eventType = xpp.next();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }

							   }
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("YPos"))
							   {
								   try {
										eventType = xpp.next();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									   if (eventType==XmlPullParser.TEXT)
									   {
										   newPlanet.yPos = Integer.parseInt(xpp.getText());
										   try {
											eventType = xpp.next();
										} catch (XmlPullParserException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									   }
							   }
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("CombatPhases"))
							   {
								   while(eventType!=XmlPullParser.END_TAG)
								   {tempName = xpp.getName();
									   if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("LongRange"))
									   {newPlanet.combatPhases = new CombatPhases(0,0,0,0);
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.longRange=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("MediumRange"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.mediumRange=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("ShortRange"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.shortRange=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("LastStand"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.combatPhases.lastStand=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   
									   try {
										   tempName = xpp.getName();
										eventType = xpp.next();
										tempName = xpp.getName();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }
							   }
							   
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("Resources"))
							   {
								   while(eventType!=XmlPullParser.END_TAG)
								   {tempName = xpp.getName();
									   if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Fuel"))
									   {newPlanet.planetRes = new nich.Classes.Resources(0, 0, 0, 0, 0);
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Fuel=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Research"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Research=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("BattleShip"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.BattleShip=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Destroyer"))
									   {
										   try {
												eventType = xpp.next();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Destroyer=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   else if(eventType==XmlPullParser.START_TAG && xpp.getName().equals("Frigate"))
									   {
										   try {
											   tempName = xpp.getName();
												eventType = xpp.next();
												tempName = xpp.getName();
											} catch (XmlPullParserException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											   if (eventType==XmlPullParser.TEXT)
											   {
												   newPlanet.planetRes.Frigate=Integer.parseInt(xpp.getText());
												   try {
													eventType = xpp.next();
												} catch (XmlPullParserException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   }
									   }
									   try {
										   tempName = xpp.getName();
										eventType = xpp.next();
										tempName = xpp.getName();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }
							   }
							   else if (eventType==XmlPullParser.START_TAG && xpp.getName().equals("PlanetType"))
							   {		   try {
									eventType = xpp.next();
								} catch (XmlPullParserException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								   if (eventType==XmlPullParser.TEXT)
								   {
									   newPlanet.planetType=xpp.getText();
									   try {
										eventType = xpp.next();
										tempName = xpp.getName();

										
										   
										   
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								   }
							   }
							   try {
								   eventType = xpp.next();
							} catch (XmlPullParserException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							   
						   }
						   if(eventType==XmlPullParser.END_TAG && xpp.getName().equals("Planet"))
							{tempList.add(newPlanet);
							try {
								eventType = xpp.next();
							} catch (XmlPullParserException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							tempName = xpp.getName();}
						   
					   }
					   else
					   {try {
						   tempName = xpp.getName();
						eventType = xpp.next();
						tempName = xpp.getName();
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				   }
				   rhombList.add(tempList);

			   }r++;
			   }
		   
			   
			   try {
				   tempName = xpp.getName();
				   eventType = xpp.next();
				   tempName = xpp.getName();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		   }
		   RandomRotation();
	}

	public List<List<Planet>> RandomRotation()
	{
		//0	 		X	Y
		//60		X+Y	-X 
		
		List<List<Planet>> rotatedPlanets = new ArrayList<List<Planet>>();
		for (int i=0; i<planetList.size(); i++)
		{
			Random random = new Random();
			int rand = random.nextInt(6);
			for (int j =0; j<rand; j++)
			{
				for (int p =0; p<planetList.get(i).size();p++)
				{
					int xxx = planetList.get(i).get(p).xPos;
					int yyy = planetList.get(i).get(p).yPos;
					planetList.get(i).get(p).xPos = xxx+yyy;
					planetList.get(i).get(p).yPos = -xxx;
				}
			}
			rotatedPlanets.add(planetList.get(i));
		}
		return rotatedPlanets;
	}
	
	public List<Planet> ConvertCoordinates()
	{
		List<Planet> finalPlanets = new ArrayList<Planet>();
		for (int i=0; i<planetList.size(); i++)
		{
			finalPlanets.addAll(planetList.get(i));
		}
		return finalPlanets;
	}
	
}
