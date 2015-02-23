package com.so2studio.stepcounter;

import java.io.File;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainactivity.R;


/**
 * This activity is launched when the user touches on the "share" button
 * inherently to a training.
 */
public class SendEmailActivity extends ActionBarActivity implements View.OnClickListener{
	
	/**
	 * EditText for the email address.
	 */
    private EditText editTextEmail;
    /**
	 * EditText for the email subject.
	 */
    private EditText editTextSubject;
    /**
	 * EditText for the email message.
	 */
    private EditText editTextMessage;
    /**
	 * Send button.
	 */
    private Button btnSend;
    /**
	 * Insert attachment button.
	 */
    private Button btnAttachment;
    /**
     * Email address string.
     */
    private String email;
    /**
     * Email address subject.
     */
    private String subject;
    /**
     * Email address message.
     */
    private String message;
    /**
     * Email address attachment file.
     */
    private String attachmentFile;
    
    private Uri URI = null;
    private static final int PICK = 101;
    private int columnIndex;
    
    
    /**
     * OnCreate method implementation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        
        //Initializes the EditText.
        editTextEmail = (EditText) findViewById(R.id.editTextTo);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        
      //Initializes the Buttons.
        btnAttachment = (Button) findViewById(R.id.buttonAttachment);
        btnSend = (Button) findViewById(R.id.buttonSend);
        
        btnSend.setOnClickListener(this);
        btnAttachment.setOnClickListener(this);
    }
    
    
    /**
     * This method is invoked when the selection activity returns it's results. 
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK && resultCode == RESULT_OK) {
            /**
             * Get Path
             */
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            attachmentFile = cursor.getString(columnIndex);
            Log.e("Attachment Path:", attachmentFile);
            URI = Uri.parse("file://" + attachmentFile);
            cursor.close();
        }
    }
    
    
    /**
     * OnclickListener method implementation.
     */
    @Override
    public void onClick(View v) {
    	
    	/*
    	 * If the btnAttachment button is touched, the gallery activity starts and
    	 * the selected file will be returned to this activity.
    	 */
        if (v == btnAttachment) {
            openGallery();
        }
        
        /*
         * When the send button is touched, the email is sent.
         */
        if (v == btnSend) {
            try {
                email = editTextEmail.getText().toString();
                subject = editTextSubject.getText().toString();
                message = editTextMessage.getText().toString();

                final Intent emailIntent = new Intent(
                        Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { email });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        subject);
                if (URI != null) {
                    emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                }
                emailIntent
                        .putExtra(Intent.EXTRA_TEXT, message);
                this.startActivity(Intent.createChooser(emailIntent,
                        "Sending email..."));
                
                
                File file = new File("training.stc");
                boolean deleted = file.delete();
                Log.v("log_tag","deleted: " + deleted);
                
            } catch (Throwable t) {
                Toast.makeText(this,
                        "Request failed try again: " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

    }
    
    
    
    /**
     * This method creates a new Activity which will return
     * the file to be sent via email.
     */
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK);

    }

}
