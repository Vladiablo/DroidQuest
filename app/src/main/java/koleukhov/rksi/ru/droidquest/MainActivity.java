package koleukhov.rksi.ru.droidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_CODE_DECEIT = 0;

    public static final String TAG = "QuestActivity";
    public static final String KEY_INDEX = "index";
    public static final String KEY_IS_DECEITER = "is_deceiter";
    public static final String KEY_DECEIT_INDEX= "deceit_index";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mDeceitButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.question_android, true),
                    new Question(R.string.question_linear, false),
                    new Question(R.string.question_service, false),
                    new Question(R.string.question_res, true),
                    new Question(R.string.question_manifest, true),
                    new Question(R.string.question_server, false),
                    new Question(R.string.question_dalvik, true),
                    new Question(R.string.question_multitasking, true),
                    new Question(R.string.question_bill_gates, false),
                    new Question(R.string.question_google_services, false)
            };
    private int mCurrentIndex = 0;
    private boolean mIsDeceiter;
    private int mDeceitIndex = -1;

    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        Log.d("DroidQuest", "Current index: " + mCurrentIndex);
        Log.d("DroidQuest", "Deceit index: " + mDeceitIndex);
        Log.d("DroidQuest", "Is deceiter: " + mIsDeceiter);
        if(mIsDeceiter && (mDeceitIndex == mCurrentIndex))
        {
            messageResId = R.string.judgement_toast;
            Log.d("DroidQuest", "Toast set");
        }
        else
        {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called.");

        if(savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsDeceiter = savedInstanceState.getBoolean(KEY_IS_DECEITER, false);
            mDeceitIndex = savedInstanceState.getInt(KEY_DECEIT_INDEX, -1);
        }

        setContentView(R.layout.activity_main);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mDeceitButton = (Button) findViewById(R.id.deceit_button);

        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mQuestionBank.length + mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mDeceitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = DeceitActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_DECEIT);
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode == REQUEST_CODE_DECEIT)
        {
            if(data == null)
            {
                return;
            }
            mIsDeceiter = DeceitActivity.wasAnswerShown(data);
            mDeceitIndex = mCurrentIndex;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_IS_DECEITER, mIsDeceiter);
        savedInstanceState.putInt(KEY_DECEIT_INDEX, mDeceitIndex);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called.");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called.");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called.");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called.");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called.");
    }
}
