package com.dimfcompany.nashprihodadmin.logic

import android.util.Patterns
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.utils.StringManager
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderAlerter
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem

class ValidationData(var is_valid: Boolean = true, var errors: ArrayList<String> = ArrayList())
{
    fun getErrorMessage(): String
    {
        return StringManager.listOfStringToSingle(errors)
    }

    fun validateNotNullString(str: String?, field_name: String, min_chars: Int?)
    {
        if (str.isNullOrEmpty())
        {
            is_valid = false
            errors.add(ValidationManager.getPleaseFillStr(field_name))
        }
        else if (min_chars != null)
        {
            if (str.length < min_chars)
            {
                is_valid = false
                errors.add("Поле \"$field_name\" должно содержать минимум $min_chars символов")
            }
        }
    }

    fun validateEmail(email: String?)
    {
        if (email.isNullOrEmpty())
        {
            is_valid = false
            errors.add(ValidationManager.getPleaseFillStr("Email"))
        }
        else if (!ValidationManager.isEmail(email))
        {
            errors.add(getStringMy(R.string.enter_valid_email))
            is_valid = false
        }
    }

    fun validatePasswords(password_1: String?, password_2: String?)
    {
        if (password_1 == null)
        {
            is_valid = false
            errors.add(ValidationManager.getPleaseFillStr("Пароль"))
        }
        else if (password_1.length < 8)
        {
            is_valid = false
            errors.add("Пароль должен содержать минимум 8 символов")
        }
        else if (password_2 == null)
        {
            is_valid = false
            errors.add(ValidationManager.getPleaseFillStr("Повторите"))
        }
        else if (!password_1.equals(password_2))
        {
            is_valid = false
            errors.add("Пароли не совпадают")
        }
    }

    fun validateFile(file_item: MyFileItem?, field_name: String)
    {
        val error_str = "Добавьте файл '$field_name'"
        if (file_item == null)
        {
            is_valid = false
            errors.add(error_str)
        }
        else if (file_item.getFile().length() == 0L)
        {
            is_valid = false
            errors.add(error_str)
        }
    }

    fun validateUrl(str: String?, field_name: String)
    {
        if (str.isNullOrEmpty())
        {
            is_valid = false
            errors.add(ValidationManager.getPleaseFillStr(field_name))
        }
        else if (!URLUtil.isValidUrl(str))
        {
            is_valid = false
            errors.add("Поле '$field_name' должно быть ссылкой")
        }
    }

    fun show(act: AppCompatActivity)
    {
        BuilderAlerter.getRedBuilder(this.getErrorMessage())
                .show(act)
    }
}

class ValidationManager
{
    companion object
    {
        fun getPleaseFillStr(field: String): String
        {
            return getStringMy(R.string.please_fill, field)
        }

        fun isEmail(email: String?): Boolean
        {
            if (email == null)
            {
                return false
            }
            val matcher = Patterns.EMAIL_ADDRESS.matcher(email)
            return matcher.matches()
        }

        fun validateRegister(first_name: String?, last_name: String?, email: String?, password_1: String?, password_2: String?): ValidationData
        {
            val data = ValidationData()
            data.validateNotNullString(first_name, "Имя", 2)
            data.validateNotNullString(last_name, "Фамилия", 2)
            data.validateEmail(email)
            data.validatePasswords(password_1, password_2)
            return data
        }

        fun validateLogin(email: String?, password: String?): ValidationData
        {
            val data = ValidationData()
            data.validateNotNullString(password, "Пароль", 8)
            data.validateEmail(email)
            return data
        }

        fun validateNewsAddEdit(news: ModelNews): ValidationData
        {
            val data = ValidationData()
            data.validateNotNullString(news.title, "Название", 3)
            data.validateNotNullString(news.text, "Текст", 8)
            return data
        }

        fun validateNoticeAddEdit(notice: ModelNotice): ValidationData
        {
            val data = ValidationData()
            data.validateNotNullString(notice.title, "Название", 3)
            data.validateNotNullString(notice.text, "Текст", 8)
            return data
        }
    }
}

