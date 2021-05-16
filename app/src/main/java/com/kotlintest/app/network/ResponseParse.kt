package com.kotlintest.app.network

import okhttp3.ResponseBody

object ResponseParse {


    fun LogingetReponse(responseBody: ResponseBody):String{
        val res = responseBody.string()
        if (res != null && !res.equals("")){
            val ostar = "<MobSubmitLogInResponse xmlns=\"http://tempuri.org/\">"
            val oend = "</MobSubmitLogInResult>"
            val Envelopestr = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            val Envelopeoend = "</s:Envelope>"
            val Bodystr = "<s:Body>"
            val Bodyend = "</s:Body>"


            if (res.contains(Envelopestr) && res.contains(Envelopeoend)) {
                val startIndex: Int = res.indexOf(Envelopestr) + Envelopestr.length
                val endIndex = res.lastIndexOf(Envelopeoend)
                val ores = res.substring(startIndex, endIndex)

                if (ores.contains(Bodystr) && ores.contains(Bodyend)){
                    val startIndexbody: Int = ores.indexOf(Bodystr) + Bodystr.length
                    val endIndexbody = ores.lastIndexOf(Bodyend)
                    val orebody = ores.substring(startIndexbody, endIndexbody)

                    if (orebody.contains(ostar) && orebody.contains(oend)){
                        val startIndexout: Int = orebody.indexOf(ostar) + ostar.length+oend.length-1
                        val endIndexout = orebody.lastIndexOf(oend)
                        val orerepons = orebody.substring(startIndexout, endIndexout)
                        return orerepons
                    }
                }

                return ""


            }else{
                return ""
            }
        }

        return "";
    }

    fun RegisterReponse(responseBody: ResponseBody):String{
        val res = responseBody.string()
        if (res != null && !res.equals("")){
            val ostar = "<MobUserRegistrationResponse xmlns=\"http://tempuri.org/\">"
            val oend = "</MobUserRegistrationResponse>"
            val Envelopestr = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            val Envelopeoend = "</s:Envelope>"
            val Bodystr = "<s:Body>"
            val Bodyend = "</s:Body>"


            if (res.contains(Envelopestr) && res.contains(Envelopeoend)) {
                val startIndex: Int = res.indexOf(Envelopestr) + Envelopestr.length
                val endIndex = res.lastIndexOf(Envelopeoend)
                val ores = res.substring(startIndex, endIndex)

                if (ores.contains(Bodystr) && ores.contains(Bodyend)){
                    val startIndexbody: Int = ores.indexOf(Bodystr) + Bodystr.length
                    val endIndexbody = ores.lastIndexOf(Bodyend)
                    val orebody = ores.substring(startIndexbody, endIndexbody)

                    if (orebody.contains(ostar) && orebody.contains(oend)){
                        val startIndexout: Int = orebody.indexOf(ostar) + ostar.length+oend.length
                        val endIndexout = orebody.lastIndexOf(oend)
                        var orerepons = orebody.substring(startIndexout, endIndexout)
                        orerepons = orerepons.replace(oend, "")
                        return orerepons
                    }
                }

                return ""


            }else{
                return ""
            }
        }

        return "";
    }
    fun ForgotPasswordReponse(responseBody: ResponseBody):String{
        val res = responseBody.string()
        if (res != null && !res.equals("")){
            val ostar = "<MobSubmitForgotPasswordResponse xmlns=\"http://tempuri.org/\">"
            val oend = "</MobSubmitForgotPasswordResponse>"
            val Envelopestr = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            val Envelopeoend = "</s:Envelope>"
            val Bodystr = "<s:Body>"
            val Bodyend = "</s:Body>"


            if (res.contains(Envelopestr) && res.contains(Envelopeoend)) {
                val startIndex: Int = res.indexOf(Envelopestr) + Envelopestr.length
                val endIndex = res.lastIndexOf(Envelopeoend)
                val ores = res.substring(startIndex, endIndex)

                if (ores.contains(Bodystr) && ores.contains(Bodyend)){
                    val startIndexbody: Int = ores.indexOf(Bodystr) + Bodystr.length
                    val endIndexbody = ores.lastIndexOf(Bodyend)
                    val orebody = ores.substring(startIndexbody, endIndexbody)

                    if (orebody.contains(ostar) && orebody.contains(oend)){
                        val startIndexout: Int = orebody.indexOf(ostar) + ostar.length+oend.length-1
                        val endIndexout = orebody.lastIndexOf(oend)
                        val orerepons = orebody.substring(startIndexout, endIndexout)
                        return orerepons
                    }
                }

                return ""


            }else{
                return ""
            }
        }

        return "";
    }
    fun LogoutReponse(responseBody: ResponseBody):String{
        val res = responseBody.string()
        if (res != null && !res.equals("")){
            val ostar = "<MobSubmitLogOutResponse xmlns=\"http://tempuri.org/\">"
            val oend = "</MobSubmitLogOutResponse>"
            val Envelopestr = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            val Envelopeoend = "</s:Envelope>"
            val Bodystr = "<s:Body>"
            val Bodyend = "</s:Body>"


            if (res.contains(Envelopestr) && res.contains(Envelopeoend)) {
                val startIndex: Int = res.indexOf(Envelopestr) + Envelopestr.length
                val endIndex = res.lastIndexOf(Envelopeoend)
                val ores = res.substring(startIndex, endIndex)

                if (ores.contains(Bodystr) && ores.contains(Bodyend)){
                    val startIndexbody: Int = ores.indexOf(Bodystr) + Bodystr.length
                    val endIndexbody = ores.lastIndexOf(Bodyend)
                    val orebody = ores.substring(startIndexbody, endIndexbody)

                    if (orebody.contains(ostar) && orebody.contains(oend)){
                        val startIndexout: Int = orebody.indexOf(ostar) + ostar.length+oend.length-1
                        val endIndexout = orebody.lastIndexOf(oend)
                        val orerepons = orebody.substring(startIndexout, endIndexout)
                        return orerepons
                    }
                }

                return ""


            }else{
                return ""
            }
        }

        return "";
    }
}