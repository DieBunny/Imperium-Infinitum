package nich.Package;

import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class StartPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_page, menu);
		return true;
	}
	
	public void NavigateToMain(View v)
	{
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		int rbId = rg.getCheckedRadioButtonId();
		 View radioButton = rg.findViewById(rbId);
		    int radioId = rg.indexOfChild(radioButton);
		    RadioButton btn = (RadioButton) rg.getChildAt(radioId);
		    String playerText = (String) btn.getText();
		    playerText = playerText.substring(0,1);
		    int playerCount = Integer.parseInt(playerText);
		    
		final Button button = (Button) findViewById(R.id.StartBtnId);
		
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Set player names");
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		for (int i=0; i<playerCount; i++)
		{
		TextView label = new TextView(layout.getContext());
		//label.setLayoutParams(new LayoutParams(100, LayoutParams.MATCH_PARENT));
		label.setText(i+1 + ". Player:");
		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(12);
		EditText textfield = new EditText(layout.getContext());
		//textfield.setLayoutParams(new LayoutParams(100, LayoutParams.MATCH_PARENT));
		textfield.setFilters(filters);
		layout.addView(label);
		layout.addView(textfield);
		}
		
		dialog.setView(layout).setPositiveButton("GO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
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

}
