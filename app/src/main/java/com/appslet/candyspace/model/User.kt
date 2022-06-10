package com.appslet.candyspace.model

import kotlinx.serialization.Serializable

@Serializable
data class User( val badge_counts : Badges, val account_id : Long?, val is_employee : Boolean?,
                 val last_access_date : Long?, val reputation : Int, val creation_date : Long?,
                 val user_type : String?, val user_id : Long?, val location : String?, val link : String?,
                 val profile_image : String?, val  display_name : String?)  {

    @Serializable
    data class Badges(val bronze : Int, val silver : Int, val gold : Int)

}