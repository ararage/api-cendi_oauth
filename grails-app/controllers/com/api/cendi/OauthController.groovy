package com.api.cendi

import javax.servlet.http.HttpServletResponse
import grails.converters.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import oauth.exceptions.NotFoundException
import oauth.exceptions.ConflictException
import oauth.exceptions.BadRequestException
import org.springframework.dao.OptimisticLockingFailureException
import com.api.cendi.OauthService

class OauthController {

    def setHeaders(){
        response.setContentType "application/json; charset=utf-8"
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "application/json;charset=UTF-8");
    }
    
    def renderException(def e){

        def statusCode
        def error

        try{
            statusCode  = e.status
            error       = e.error

        }catch(Exception ex){

            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            error = "internal_server_error"
        }

        response.setStatus(statusCode)

        def mapExcepction = [
            message: e.getMessage(),
            status: statusCode,
            error: error
        ]

        render mapExcepction as JSON
    }
    
    def notAllowed(){
        def method = request.method
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED)
        setHeaders()
        def mapResult = [
            message: "Method "+method.toString()+" not allowed",
            status: HttpServletResponse.SC_METHOD_NOT_ALLOWED,
            error:"not_allowed"
        ]
        render mapResult as JSON
    }
    
    def obtainToken(){
        def dominio = "http://"+request.getServerName()+":"+request.getServerPort()
        setHeaders()
        boolean needsProcessing = true
        int retryCounter = 0
        int maxretry=15
        def result
        
        while(needsProcessing && retryCounter < maxretry) {
            try {
                result = new OauthService().getToken(dominio, request.JSON)
                response.setStatus(HttpServletResponse.SC_ACCEPTED)
                render result as JSON
                needsProcessing=false;
            }catch(BadRequestException e){
                needsProcessing=false;
                renderException(e)
            }catch(NotFoundException e){
                needsProcessing=false;
                renderException(e)
            }catch(ConflictException e){
                needsProcessing=false;
                renderException(e)
            }catch (OptimisticLockingFailureException olfex) {
                if((retryCounter += 1) >= maxretry) renderException(olfex);
            }catch(Exception e){
              println "Oauth Exception error----> "+e
              needsProcessing=false;
              renderException(e)
            }
         }
    }
    
}
