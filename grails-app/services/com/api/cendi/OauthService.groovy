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
import rest.RestService

class OauthService {

	def PERMISSIONS_MAP = [
            admin:"A",
            guest:"API",
            developer:"P,AIPM,MIPM,AIPA",
            dealer:"P,AIPM,MIPM"
    ]

    def origin      = "CENDI"
    
    def serviceMethod() {

    }
}
