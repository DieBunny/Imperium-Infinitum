package nich.Package;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class RulesPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules_page);

		String rulesText = "<h2>Who wins?</h2>"
		+"<p>The first player to reach the defined amount of influence points, or to eliminate all other players from the board, wins the game.</p>"
		+"<h2>How to get influence points?</h2>"
		+"<p>1 influence for each Researched technology and for each uncontested planet under your control.</p>"
		+"<h2>Movement</h2>"
		+"<p>Each stack of ships may move 3 hexes + 1 hex for each fuel spent. Stacks must finish their move on a planet. You can't stop in empty hexes.</p>"
		+"<h2>Planets</h2>"
		+"<p>Controlling a planet gives player 1 influence and allows collecting 1 resource of the 2 produced by the planet during the 1st step of the game turn."
		+"Player is only 'controlling' a planet if he has his control marker on it and there are no enemy ships on that planet (otherwise the planet counts as contested and belongs to noone).</p>"
		+"<h2>Resources</h2>"
		+"<p>Research - used to research technologies.</br>"
		+"Fuel - moving ships. Unused fuel is discarded at the end of turn.</br>"
		+"Ships - place new ships.</p>"
		+"<h2>Fleets</h2>"
		+"<p>Fleet is a stack of 5 or more ships moving together. You need a fleet to move to neutral planet or any planet with enemy ships and to take control of planets.</p>"
		+"<h2>How to occupy a planet</h2>"
		+"<p>One of your fleets orbiting a neutral or enemy planet may spend all their movement points to take control of that planet.</p>"
		+"<h2>Battles</h2>"
		+"<h3><ul><li>phases</li></ul></h3>"
		+"<p>Each battle consists of 1-4 phases. Which phases the battle will consist of depends on the particular planet.</p>"
		+"<h3><ul><li>bidding</li></ul></h3>"
		+"<p>During each phase of battle each player secretly commits a number of ships. This ships will be removed from play after the battle (It is assumed that they have taken serious damage or were destroyed).</p>"
		+"<h3><ul><li>ship type values</li></ul></h3>"
		+"<p>Each committed ship adds 1 to the strength of the fleet in all phases, but one. Depending on the ship type, it adds 2 to the strength of the fleet (instead of only 1) if used in the appropriate phase."
		+"For example, Destroyers add 2 to the strength of the fleet during the Medium range phase (2nd phase of battle) but only 1 during all other phases.</p><p>"
		+"<table border='1'>"
		+"<caption>Table shows ship value for each phase</caption>"
		+"<tr>"
		+"<th>Phase/Ship type </th>"
		    +"<th>BattleShip</th>"
		    +"<th>Destroyer</th>"
		    +"<th>Frigatte</th>"
		  +"</tr>"
		  +"<tr>"
		    +"<td>First Phase</td>"
		    +"<td><b>2</b></td>"
		    +"<td>1</td>"
		    +"<td>1</td>"
		  +"</tr>"
		  +"<tr>"
		    +"<td>Second Phase</td>"
		    +"<td>1</td>"
		    +"<td><b>2</b></td>"
		    +"<td>1</td>"
		  +"</tr>"
		  +"<tr>"
		    +"<td>Third Phase</td>"
		    +"<td>1</td>"
		    +"<td>1</td>"
		    +"<td><b>2</b></td>"
		  +"</tr>"
		  +"<tr>"
		    +"<td>Fourth Phase</td>"
		    +"<td>1</td>"
		    +"<td>1</td>"
		    +"<td>1</td>"
		  +"</tr>"
		+"</table>"
		+"</p>"

		+"<h3><ul><li>who wins battle?</li></ul></h3>"
		+"<p>During each phase of battle, player with the most fleet strength in that phase, gains victory points. The amount may vary for each phase and depends on the planet the battle is taking place at."
		+"In case both players have the same fleet strength in a given phase, none of them gains victory points."
		+"The player with most victory points wins the entire battle. In case of a draw in vps, the player with most remaining (uncommitted) ships wins. In the rare case when players have the same amount of remaining ships, defending player wins.</p>"

		+"<h2>Attacking/Retreating</h2>"
		+"<p>Attacking is moving your fleet to an enemy-controlled planet, or a planet containing another player's ships."
		+"If there are enemy ships there, the battle occurs.</br>"
		+"The player who lost the battle must retreat."
		+"Retreating is similar to normal movement - each fleet may retreat 3 hexes + 1 hex for each fuel spent."
		+"Attacking player must always retreat to the planet he attacked from. Defending player must retreat to the nearest planet he controls or to the nearest neutral planet (without enemy ships), but only if it is closer than any planet that he controls."
		+"If a player doesn't have enough fuel to retreat, then the remaining ships are lost. Ships that have retreated may not move until the next turn.</p>"

		+"<h2>Research cards</h2>"
		+"<p>Research cards alter the rules of the game."
		+"Players research new technologies by buying technology cards from the common technology card pool, using research points."
		+"Each card has a predetermined basic cost."
		+"The cost of each research card is further increased by 1 for each technology researched by the player(s) with the most researched technologies."
		+"Whenever a player researches a technology card, he draws two card from technology deck, discards one and places the 2nd one to the technology card pool, so that there are always 3 cards available for research."
		+"Each player also has one random 'secret technology' card which works in the same way as common technology cards (including cost increase), except it is hidden from other players and only the owner of the secret technology card may research it.</p>";
				
		WebView rulesWebview=  (WebView) findViewById(R.id.webView1);
		rulesWebview.loadDataWithBaseURL(null, rulesText, "text/html", "UTF-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rules_page, menu);
		return true;
	}

}
