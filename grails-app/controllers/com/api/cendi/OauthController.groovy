package com.api.cendi

import javax.servlet.http.HttpServletResponse
import grails.converters.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.plugin.gson.converters.GSON
import oauth.exceptions.NotFoundException
import oauth.exceptions.ConflictException
import oauth.exceptions.BadRequestException
import org.springframework.dao.OptimisticLockingFailureException

class OauthController {

    def index() { }
}
