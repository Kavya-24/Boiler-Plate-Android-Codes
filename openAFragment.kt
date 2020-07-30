/**
 * Takes a fragment parameter.Opens a fragmnet on the activity with id container
 */

private fun openFragment(fragment: Fragment) {

    
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    transaction.replace(R.id.container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()

}

/**
 * Open a fragmnet from anither fragmnet using communicator methods and pass data
 */

//Communicator class
interface Communicator {

    fun goToFragmentX(bundle : Bundle)

}

/**
 * Extend Communicator to main activty which will override a method
 */

 //...Reference Activty
 fun goToFragmentX(bundle : Bundle){

        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = FragmentX()
        frag2.arguments = bundle

        transaction.replace(R.id.container, frag2)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()

   
 }


//..In the fragment from where you will go
var communicator : Communicator = context as activity
//Create intent bundle
communicator.goToFragmentX(bundle)


//..In fragment X
var arg : Bundle? = this.requireArguments()

