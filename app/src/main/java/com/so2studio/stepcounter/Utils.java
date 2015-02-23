package com.so2studio.stepcounter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.content.Context;
import android.content.Intent;

/**
 * Utility methods.
 * 
 * @author stephendnicholas.com
 */
public class Utils {

	/**
	 * Create a new file with the given name and content, in this application's
	 * cache directory.
	 * 
	 * @param context
	 *            - Context - context to use.
	 * @param fileName
	 *            - String - the name of the file to create.
	 * @param content
	 *            - String - the content to put in the new file.
	 * @throws java.io.IOException
	 */
	public static void createCachedFile(Context context, String fileName, String content) throws IOException {

		File cacheFile = new File(context.getCacheDir() + File.separator + fileName);

		cacheFile.createNewFile();

		FileOutputStream fos = new FileOutputStream(cacheFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
		PrintWriter pw = new PrintWriter(osw);

		pw.println(content);

		pw.flush();
		pw.close();
	}



	/**
	 * Returns an intent that can be used to launch Gmail and send an email with
	 * the specified file from this application's cache attached.
	 * 
	 * @param context
	 *            - Context - the context to use.
	 * @param email
	 *            - String - the 'to' email address.
	 * @param subject
	 *            - String - the email subject.
	 * @param body
	 *            - String - the email body.
	 * @param fileName
	 *            - String - the name of the file in this application's cache to
	 *            attach to the email.
	 * @return An Intent that can be used to launch the Gmail composer with the
	 *         specified file attached.
	 */
	public static Intent getSendEmailIntent(Context context, String email, String subject, String body, String fileName)throws IOException {

		final Intent emailIntent = new Intent(Intent.ACTION_SEND);

		
		emailIntent.setType("plain/text");

		// Add the recipients
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });

		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

		emailIntent.putExtra(Intent.EXTRA_TEXT, body);

		// Add the attachment by specifying a reference to our custom
		// ContentProvider
		// and the specific file of interest
		//emailIntent.putExtra(
				//Intent.EXTRA_STREAM,
				//Uri.parse("content://" + CachedFileProvider.AUTHORITY + "/" + fileName));

		File cacheFile = new File(context.getCacheDir() + File.separator + fileName);

		cacheFile.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(cacheFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
		PrintWriter pw = new PrintWriter(osw);

		pw.println("qualcosa di bello...");

		pw.flush();
		pw.close();
		
		emailIntent.putExtra(Intent.EXTRA_STREAM, cacheFile);
		
		return emailIntent;
		
	}
}