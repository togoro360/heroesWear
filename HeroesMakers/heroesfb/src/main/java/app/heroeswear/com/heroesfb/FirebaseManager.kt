package app.heroeswear.com.heroesfb

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task





/**
 * Created by livnatavikasis on 29/04/2018.
 */
 class FirebaseManager(){

    lateinit var mAuth: FirebaseAuth
    var  mCurrentUser: FirebaseUser? = null
    fun onCreate(){
        mAuth = FirebaseAuth.getInstance();

    }

    fun onStart(): FirebaseUser? {
                 // Check if user is signed in (non-null) and update UI accordingly.
           mCurrentUser = mAuth.getCurrentUser();
            return mCurrentUser
    }

    fun createAccount(email: String, pwd: String ): FirebaseUser? {
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                mCurrentUser = mAuth.currentUser
//                        updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                mCurrentUser = null //updateUI(null)
            }
        }
        return mCurrentUser
    }

    fun signInUser(email: String, pwd: String): FirebaseUser?{
        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                mCurrentUser = mAuth.currentUser
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
               mCurrentUser = null
            }
        }
        return mCurrentUser
    }

    fun signOut(){
        mAuth.signOut()
    }
    companion object {
        fun newInstance(): FirebaseManager{
            return FirebaseManager()
        }
    }
}