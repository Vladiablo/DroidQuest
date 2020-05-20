package koleukhov.rksi.ru.droidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeceitActivity extends AppCompatActivity
{
    public static final String KEY_IS_BUTTON_PRESSED = "is_button_pressed";

    private boolean mAnswerIsTrue;
    private boolean mIsButtonPressed;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static final String EXTRA_ANSWER_IS_TRUE = "koleukhov.rksi.ru.droidquest.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "koleukhov.rksi.ru.droidquest.answer_shown";

    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    private void buttonPress()
    {
        if(mAnswerIsTrue)
        {
            mAnswerTextView.setText(R.string.true_button);
        }
        else
        {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
        mIsButtonPressed = true;
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent i = new Intent(packageContext, DeceitActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result)
    {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deceit);

        if(savedInstanceState != null) mIsButtonPressed = savedInstanceState.getBoolean(KEY_IS_BUTTON_PRESSED, false);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonPress();
            }
        });

        if(mIsButtonPressed) buttonPress();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_IS_BUTTON_PRESSED, mIsButtonPressed);
    }
}
