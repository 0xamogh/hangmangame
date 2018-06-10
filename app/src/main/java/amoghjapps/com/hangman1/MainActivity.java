package amoghjapps.com.hangman1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    String str="ambulance";;
    char[] defstr={'-','-','-','-','-','-','-','-','-'};
    int len;
    public int incr=0;

    char chr;
    char asci;
    public TextView text;
    public TextView counter;
    public TextView letter;
    public ImageView img;
    EditText edit;
    Button guess;
    String content;
    int BestScore;
    TextView bestscr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedpref= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= sharedpref.edit();
        len=str.length();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.textView);
        letter=findViewById(R.id.letters);
        edit=findViewById(R.id.editext);
        counter=findViewById(R.id.counter);
        guess=findViewById(R.id.guessbtn);
        img=findViewById(R.id.imageView);
        bestscr=findViewById(R.id.bestscr);
        for(int j=0;j<str.length();j++){
            defstr[j]='-';
        }

        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content=edit.getText().toString();
                chr=content.charAt(0);
               defstr= getDefstr(guess,str,chr,defstr,false);
               edit.setText("");
            }

        });
    }

    public char[] getDefstr(Button b,String string,char c,char[] deflt,Boolean charpresent) {

        for(int i=0;i<string.length();i++){
            asci=string.charAt(i);
            if(asci==c){
                deflt[i]=c;
                charpresent=true;
                text.setText(String.valueOf(deflt));
            }
        }
        if(charpresent==false){
            incr++;

            counter.setText(Integer.toString(incr));
            switch(incr){
                case 1 :
                    img.setImageResource(R.drawable.hang0);
                    break;
                case 2:
                    img.setImageResource(R.drawable.hang1);
                    break;
                case 3:
                    img.setImageResource(R.drawable.hang2);
                    break;
                case 4:
                    img.setImageResource(R.drawable.hang3);
                    break;
                case 5:
                    img.setImageResource(R.drawable.hang4);
                    break;
                case 6:
                    img.setImageResource(R.drawable.hang5);
                    dialogueLost();
                    break;

            }
            letter.setText(letter.getText().toString().concat(" "+Character.toString(c)));
            Context context=getApplicationContext();
            Toast.makeText(context,Character.toString(c) +" is not present in the word",Toast.LENGTH_LONG).show();

        }
        if(str.equals(deflt.toString())){ // for some reason this code doesnt work... PLEASE HELP
            dialogueWon();
        }

        return deflt;

    }
    public void dialogueLost(){
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("You have lost")
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                }).create().show();

    }
    public void dialogueWon(){
        if(bestscr.getText().toString().isEmpty()){
            bestscr.setText(counter.getText().toString());
        }
        if(Integer.parseInt(counter.getText().toString())<Integer.parseInt(bestscr.getText().toString())){
            bestscr.setText(counter.getText());

        }
        new AlertDialog.Builder(this)
                .setTitle("You Win!")
                .setMessage("You guessed the word correctly")
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                }).create().show();
    }
}




