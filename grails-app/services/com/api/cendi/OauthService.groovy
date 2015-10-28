package com.api.cendi

import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.joda.time.format.DateTimeParser

import java.text.DateFormat
import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient

import grails.converters.*
import oauth.exceptions.NotFoundException
import oauth.exceptions.ConflictException
import oauth.exceptions.BadRequestException
import com.api.rest.RestService

public class OauthService {

	def PERMISSIONS_MAP = [
            admin:"A",
            //guest:"API",
            //developer:"P,AIPM,MIPM,AIPA",
            teacher:"AIPM",
            student:"API" 
    ]

    def origin      = "CENDI"
    
    public def getToken(def dominio, def jsonOauth) {
        def email = jsonOauth?.email.decodeSecure().toString()
        def password = jsonOauth?.password.decodeSecure().toString()

        def user_id
        def token
        def status
        def user_type
        def permissions
        def result
        
        Map json = [:]

        if(!email){
            throw new BadRequestException("Invalid JSON, email is missing.")
        }

        if(!password){
            throw new BadRequestException("Invalid JSON, password is missing.")
        }
        
        def queryParams = [
            email:email,
            password:password,
            admin:"CENDIADMIN_T4FG637F"
        ]

        result = new RestService().getResource(dominio,"/users/search/teacher",queryParams).data

        if(!result){
            throw new NotFoundException("Invalid email or password")
        }

        user_id = result.user_id
        status = result.status
        user_type = result.user_type

        permissions = PERMISSIONS_MAP[user_type]

        if(!permissions){
            throw new NotFoundException("Bad permissions of user.")
        }

        token = generateToken(user_id,status,user_type)

        json.access_token = token
        json.token_expiration_date = (new Date() + 1).format("MM-dd-yyyy")
        json.user_id = user_id
        json.user_type = user_type

        json
    }

    def generateToken(def user_id,def status,def user_type){
        def token
        def cal = Calendar.instance
        def dayYear = cal.get(Calendar.DAY_OF_YEAR)

        token = user_id+"-"+status+"-"+dayYear+"-"+user_type
        token = token.encodeAsSecure()
        token = (origin+"_"+token+"_"+user_id).toString()

        token
    }
}
