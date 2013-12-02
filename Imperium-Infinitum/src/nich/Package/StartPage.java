package nich.Package;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import nich.Classes.CombatPhases;
import nich.Classes.Fuel;
import nich.Classes.Line;
import nich.Classes.Planet;
import nich.Classes.Player;
import nich.Classes.Research;
import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartPage extends Activity {

	ImpInfGlobal iiglobal;
	int[] facearray = new int[10];
	int currentface = 0;
	int[] collorArray = new int[4];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page);
		iiglobal = ImpInfGlobal.getInstance();
		
		facearray[0] = R.drawable.faceelders;
		facearray[1] = R.drawable.facechtns;
		facearray[2] = R.drawable.faceboobs;
		facearray[3] = R.drawable.facehorn;
		facearray[4] = R.drawable.faceskulls;
		facearray[5] = R.drawable.facemale;
		facearray[6] = R.drawable.facemedusa;
		facearray[7] = R.drawable.facebones;
		facearray[8] = R.drawable.faceblonde;
		facearray[9] = R.drawable.facehipopp;
		
		collorArray[0] = R.color.Red;
		collorArray[1] = R.color.Blue;
		collorArray[2] = R.color.Green;
		collorArray[3] = R.color.Gray;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_page, menu);
		return true;
	}
	
	public void NavigateToMain(View v)
	{
		//finds out which radiobutton was pressed and gets related playercount
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		int rbId = rg.getCheckedRadioButtonId();
		 View radioButton = rg.findViewById(rbId);
		 final int playerCount = Integer.parseInt((String) radioButton.getTag());

		    
		final Button button = (Button) findViewById(R.id.StartBtnId);
		
		//dynamically building dialog window, so user can enter player names
		Builder dialog = new AlertDialog.Builder(this);
		//dialog.setTitle("Set player names");
		final RelativeLayout layout = new RelativeLayout(this);
		layout.setBackgroundColor(getResources().getColor(R.color.ElementGray));
		
		for (int i=0; i<playerCount; i++)
		{
			RelativeLayout.LayoutParams shareParams = new RelativeLayout.LayoutParams(60,60);
			shareParams.leftMargin = 40;
			shareParams.topMargin = i*80+40;
			TextView label = new TextView(layout.getContext());
			label.setTextColor(getResources().getColor(R.color.Gray));
			label.setTypeface(null, Typeface.BOLD);
			label.setText(i+1 + ". Player:");
			label.setLayoutParams(shareParams);
			
			RelativeLayout.LayoutParams shareParams1 = new RelativeLayout.LayoutParams(210,60);
			shareParams1.leftMargin = 120;
			shareParams1.topMargin = i*80+20;
		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(12);
		EditText textfield = new EditText(layout.getContext());
		textfield.setTextColor(getResources().getColor(R.color.Gray));
		textfield.setText("player" + (i+1));
		textfield.setTypeface(null, Typeface.BOLD_ITALIC);
		textfield.setFilters(filters);
		textfield.setTag(i+1 + ". Player");
		textfield.setBackgroundResource(R.drawable.namebutton);
		textfield.setGravity(Gravity.CENTER);
		textfield.setLayoutParams(shareParams1);
		
		RelativeLayout.LayoutParams shareParams2 = new RelativeLayout.LayoutParams(60,60);
		shareParams2.leftMargin = 360;
		shareParams2.topMargin = i*80+20;
		ImageButton image = new ImageButton(this);
		image.setOnClickListener(new faceClickListener());
		image.setBackgroundResource(facearray[i]);
		image.setTag(i);
		image.setLayoutParams(shareParams2);
		
		layout.addView(label);
		layout.addView(textfield);
		layout.addView(image);
		}
		
		dialog.setView(layout).setPositiveButton("GO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i=0; i<playerCount; i++)
				{
					Player player = new Player();
					EditText playerName = (EditText) layout.findViewWithTag(i+1 + ". Player");
					player.name = playerName.getText().toString();
					ImageButton iii = (ImageButton) layout.getChildAt(3*i+2);
					player.image = facearray[(Integer) iii.getTag()];
					player.victoryPoints = 0;
					iiglobal.playerList.add(player);
				}
				
				Intent intent = new Intent(button.getContext(), MainPage.class);
				startActivity(intent);
				
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		dialog.show();
		
	}

	public void GoToRules(View v)
	{
		Intent intent = new Intent(v.getContext(), RulesPage.class);
		startActivity(intent);
	}

	
	public class faceClickListener implements OnClickListener{

		@Override
		public void onClick(View v) 
		{
			ImageButton imgBtn = (ImageButton) v;
			currentface = ((Integer) imgBtn.getTag()) +1;
			if (currentface>=10)
			{currentface = 0;}
			imgBtn.setBackgroundResource(facearray[currentface]);
			imgBtn.setTag(currentface);
		}

	}
}
