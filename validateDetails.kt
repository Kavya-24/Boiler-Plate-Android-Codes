//Usable objects 

private fun validateName(string: String): Boolean {
    //Check if the name has only alphabets and not special characters or numbers

    for (c in string) {
        if (c !in 'A'..'Z' && c !in 'a'..'z' && c != ' ') {
            return false
        }
    }
    return true
}


private fun validateUsername(string: String): Boolean {

    //Not allowing numbers in username
    for (c in string) {
        if (c !in 'A'..'Z' && c !in 'a'..'z' && c !in '0'..'9') {
            return false
        }
    }
    return true
}


private fun validateEmail(email: String): Boolean {
    //Email REGEX
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val validator = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
  
    return validator.toRegex().matches(email)
}


fun String.checkPasswordSecurity(): Boolean {
    var containsSmallLetter = false
    var containsCapitalLetter = false
    var containsNumber = false
    var containsSpecialSign = false

    //Alter the paramters as per requirements
    forEach {
        when (it) {
            in 'a'..'z' -> containsSmallLetter = true
            in 'A'..'Z' -> containsCapitalLetter = true
            in '0'..'9' -> containsNumber = true
            in '!'..'/' -> containsSpecialSign = true
            in ':'..'@' -> containsSpecialSign = true
            in '['..'`' -> containsSpecialSign = true
            in '{'..'~' -> containsSpecialSign = true
        }
    }

    return (containsSmallLetter && containsCapitalLetter && containsNumber && containsSpecialSign)
}

/**
 *  A good practice is use string errors when possible
 *  Also check the fields.length != 0
 * 
 */

 