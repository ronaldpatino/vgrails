package com.libertech.rtmx.model.common

class UserLoginController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userLoginInstanceList: UserLogin.list(params), userLoginInstanceTotal: UserLogin.count()]
    }

    def create = {
        def userLoginInstance = new UserLogin()
        userLoginInstance.properties = params
        return [userLoginInstance: userLoginInstance]
    }

    def save = {
        def userLoginInstance = new UserLogin(params)
        if (userLoginInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), userLoginInstance.id])}"
            redirect(action: "show", id: userLoginInstance.id)
        }
        else {
            render(view: "create", model: [userLoginInstance: userLoginInstance])
        }
    }

    def show = {
        def userLoginInstance = UserLogin.get(params.id)
        if (!userLoginInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userLoginInstance: userLoginInstance]
        }
    }

    def edit = {
        def userLoginInstance = UserLogin.get(params.id)
        if (!userLoginInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userLoginInstance: userLoginInstance]
        }
    }

    def update = {
        def userLoginInstance = UserLogin.get(params.id)
        if (userLoginInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userLoginInstance.version > version) {
                    
                    userLoginInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userLogin.label', default: 'UserLogin')] as Object[], "Another user has updated this UserLogin while you were editing")
                    render(view: "edit", model: [userLoginInstance: userLoginInstance])
                    return
                }
            }
            userLoginInstance.properties = params
            if (!userLoginInstance.hasErrors() && userLoginInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), userLoginInstance.id])}"
                redirect(action: "show", id: userLoginInstance.id)
            }
            else {
                render(view: "edit", model: [userLoginInstance: userLoginInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userLoginInstance = UserLogin.get(params.id)
        if (userLoginInstance) {
            try {
                userLoginInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
    }
}
