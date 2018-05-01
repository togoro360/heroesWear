package com.audio.player.classes;

import android.app.Application;
import android.accounts.Account;
import android.content.Context;
import android.support.multidex.MultiDex;

public class MyApplication extends Application
{
	private static MyApplication instance;
	private Account activeAccount = null;

	public static MyApplication getInstance()
	{
		return instance;
	}

	public static Context getContext()
	{
		return instance;
	}

	@Override
	public void onCreate()
	{
		instance = this;
		super.onCreate();
	}

	protected void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);

		MultiDex.install(this);
	}

	public void setActiveAccount(Account account)
	{
		this.activeAccount = account;
	}

	public Account getActiveAccount()
	{
		return activeAccount;
	}
}