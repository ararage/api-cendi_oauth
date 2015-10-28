package com.api.rest

import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import grails.converters.JSON
import javassist.NotFoundException
import net.sf.json.JSONNull
import org.codehaus.groovy.grails.web.json.JSONObject
import oauth.exceptions.NotFoundException
import oauth.exceptions.ConflictException
import oauth.exceptions.BadRequestException
import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseException
import javax.servlet.http.HttpServletResponse

class RestService {

     def getResource(def urlBase,def resource, def queryParams){

        Map result = [:]
     
        //def restClient  = new RESTClient(urlBase)
     	def restClient  = new RESTClient("http://192.168.0.14")
    
        restClient.handler.failure = { resp,data -> 
        
            resp.setData(data);
            result.status   = resp.status
            result.reasonPhrase   = resp.statusLine.reasonPhrase
            result.data     = resp.data
            return resp 
        }
        
    
        def  resp = restClient.get(path:resource, 
            query:queryParams,
            requestContentType : 'application/json')
            
        result.status   = resp.status
        result.data     = resp.data
                
        restClient  =null
        result
    }


}
