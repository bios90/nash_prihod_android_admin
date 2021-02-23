package com.dimfcompany.nashprihodadmin.base

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import java.lang.RuntimeException

interface MyError
{
    val error_text: String
}

class MyUnknownError : RuntimeException(getStringMy(R.string.default_error)), MyError
{
    override val error_text: String
        get() = message!!
}

class ParsingError : RuntimeException(getStringMy(R.string.parsing_error)), MyError
{
    override val error_text: String
        get() = message!!
}

class NoInternetError : RuntimeException(getStringMy(R.string.no_internet_error)), MyError
{
    override val error_text: String
        get() = message!!
}

class MyServerError(str: String) : RuntimeException(str), MyError
{
    override val error_text: String
        get() = message!!
}