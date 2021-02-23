package com.dimfcompany.nashprihodadmin.logic.utils

import java.lang.StringBuilder
import java.util.*

class StringManager
{
    companion object
    {
        fun listOfStringToSingle(strings: List<String>): String
        {
            return listOfStringToSingle(strings, "\n")
        }

        fun listOfStringToSingle(strings: List<String>, separator: String): String
        {
            val sb = StringBuilder()
            for (element in strings)
            {
                sb.append(element)
                if (strings.indexOf(element) != strings.size - 1)
                {
                    sb.append(separator)
                }
            }

            return sb.toString()
        }

        fun getNameForNewFile(extension: String): String
        {
            var name = Date().formatToString(DateManager.FORMAT_FOR_PHOTO_FILE)
            if (!extension.isEmpty())
            {
                name += ".$extension"
            }
            return name
        }
    }
}


fun String.Companion.getRandomString(size: Int = 20): String
{
    val all_chars: String = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val sb: StringBuilder = StringBuilder(size)
    val random: Random = Random()

    for (i in 0..size)
    {
        sb.append(all_chars.get(random.nextInt(all_chars.length)))
    }

    return sb.toString()
}
