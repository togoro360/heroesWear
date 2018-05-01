package app.heroeswear.com.heroesfb

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.iid.FirebaseInstanceId


/**
 * Created by livnatavikasis on 29/04/2018.
 */
class FirebaseManager() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    var mCurrentUser: FirebaseUser? = null

    fun onCreate() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    fun onStart(): FirebaseUser? {
        // Check if user is signed in (non-null) and update UI accordingly.
        mCurrentUser = mAuth.getCurrentUser();
        return mCurrentUser
    }

    fun createAccount(email: String, pwd: String): FirebaseUser? {
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                mCurrentUser = mAuth.currentUser
                updateUserToken()
//                        updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                mCurrentUser = null //updateUI(null)
            }
        }
        return mCurrentUser
    }

    fun signInUser(email: String, pwd: String): FirebaseUser? {
        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                mCurrentUser = mAuth.currentUser
                updateUserToken()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                mCurrentUser = null
            }
        }
        return mCurrentUser
    }

    private fun updateUserToken() {
        val token = FirebaseInstanceId.getInstance().token
        mDatabase.child("users").child(getUid()).child("token").setValue(token)
        Log.d(TAG, "user push token: $token")
    }

    fun getUid(): String {
        return mCurrentUser!!.uid
    }

    fun signOut() {
        mAuth.signOut()
    }

    companion object {
        fun newInstance(): FirebaseManager {
            return FirebaseManager()
        }
    }
}