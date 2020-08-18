/**
 * Gradle dependency
 * 
 */

 //App Update Dependency
 implementation 'com.google.android.play:core:1.7.3'


/*
Main Activity
*/

//Variables
private var appUpdateManager: AppUpdateManager? = null
private var RC_APP_UPDATE = 1249


    //App Update

    private fun updateApp() {

        appUpdateManager = AppUpdateManagerFactory.create(this)
        installStateUpdatedListener?.let { appUpdateManager!!.registerListener(it) }

    }

    //Install State Listener
    var installStateUpdatedListener: InstallStateUpdatedListener? =
        object : InstallStateUpdatedListener {
            override fun onStateUpdate(state: InstallState) {
                if (state.installStatus() === InstallStatus.DOWNLOADED) {
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                    popupSnackbarForCompleteUpdate()
                } else if (state.installStatus() === InstallStatus.INSTALLED) {
                    appUpdateManager?.unregisterListener(this)
                } else {
                    Log.i(
                        "Activity",
                        "InstallStateUpdatedListener: state: " + state.installStatus()
                    )
                }
            }
        }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != Activity.RESULT_OK) {
                Log.e("Androud", "onActivityResult: app download failed")
            }
        }
    }

    //PopUpView
    private fun popupSnackbarForCompleteUpdate(): Unit {
        val snackbar: Snackbar = Snackbar.make(
            findViewById(R.id.main_drawer__layout),
            "App has been updated",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Install") { view ->
            appUpdateManager?.completeUpdate()
        }
        snackbar.setActionTextColor(resources.getColor(R.color.wildColor))
        snackbar.show()
    }



override fun onResume() {
    super.onResume()
    Handler().postDelayed({ updateApp() }, 1000)
}

override fun onStop() {
    installStateUpdatedListener?.let { appUpdateManager?.unregisterListener(it) }
    super.onStop()
}


