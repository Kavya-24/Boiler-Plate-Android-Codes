fun findFragment(): String? {
    val fm = supportFragmentManager.findFragmentById(R.id.container)
    val fragmentName: String = fm!!::class.java.simpleName
    return fragmentName
}