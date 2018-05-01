package common

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import app.heroeswear.com.heroesfb.FirebaseManager
import app.heroeswear.com.heroesmakers.R
import app.heroeswear.com.heroesmakers.login.Activities.HomePageActivity
import app.heroeswear.com.heroesmakers.login.models.User
import common.controls.SmoothActionBarDrawerToggle

/**
 * Created by livnatavikasis on 29/04/2018.
 */


open class BaseActivity : AppCompatActivity() ,  NavigationView.OnNavigationItemSelectedListener
{


    @VisibleForTesting
    var mProgressDialog: ProgressDialog? = null
    protected var fbManager: FirebaseManager? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mDrawerToggle: SmoothActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    protected fun populateNavigationHeaderView(isInit:  Boolean)
    {
        val headerView = mNavigationView?.getHeaderView(0)
        (headerView?.findViewById(R.id.lbl_name) as TextView).setText(fbManager?.mCurrentUser?.email)

    }
        protected fun initNavigationDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        mDrawerToggle = SmoothActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name,
                R.string.app_name)
        mDrawerLayout?.setDrawerListener(mDrawerToggle)

        mNavigationView = findViewById(R.id.navigation_view) as NavigationView
//        populateNavigationHeaderView(true)
 mNavigationView?.setNavigationItemSelectedListener(this)

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.setMessage(getString(R.string.loading))
            mProgressDialog!!.isIndeterminate = true
        }

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    fun openHomePage(user: User) {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}
